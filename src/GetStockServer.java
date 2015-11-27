import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.net.DatagramSocket;
//import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InetAddress;


class GetStockServer
{
	static int PORT = 1050;	//global port number
	static byte[] respondMessage = new byte[1024];
	
	public static void main(String args[]) throws Exception //IOException, SocketException
	{
		DatagramSocket serverSocket = new DatagramSocket(PORT);	//connect to port socket
		List<String> users = new Vector<String>();
		
		while (true)
		{
			byte[] receiveMessage = new byte[1024];	//reset receiveMessage every time loop iterates
			
			DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
			serverSocket.receive(receivePacket);
			String message = new String(receivePacket.getData(),
					receivePacket.getOffset(),
					receivePacket.getLength(),
					"UTF-8");
			System.out.println("Received Message: " + message);
			
			
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();	//is this necessary?
			String messageCAPS = message.toUpperCase();
			
			// remotely shutdown server
			if (message.equals("OFF"))
			{
				System.out.println("Server shutting down");
				serverSocket.close();
				return;
			}
			
			// Check and make sure that there are at least 1 semicolon and 1 comma
			if (message.length() < 4 || (!message.contains(";") || !message.contains(",")))
			{
				sendMessage("INC", IPAddress, serverSocket, port);
			}
			else
			{
				String[] instruction = messageCAPS.split(";");
				String[] field = instruction[0].split(",");	// field[0] = CMD, field[1] = USR, field[2+] = QUO
				String command = message.substring(0, 3);		
				
				// Verify Command Field (function???)
		        switch (command) 
		        {
		        case "REG":
	            	if (verifyUserParameters(field) == false)
	            	{
	            		sendMessage("INP", IPAddress, serverSocket, port);
	            	}
	            	else
	            	{
	            		String user_name = field[1];
	            		if (verifyUserName(user_name) == false)
	            		{
	            			sendMessage("INU", IPAddress, serverSocket, port);
	            		}
	            		else
	            		{
	            			if (!users.contains(user_name))
	            			{
	            				users.add(user_name);
	            				sendMessage("ROK", IPAddress, serverSocket, port);
	            			}
	            			else
	            			{
	            				sendMessage("UAE", IPAddress, serverSocket, port);
	            			}
	            		}
	            	}
	            	break;
	            case "UNR":
	            	if (verifyUserParameters(field) == false)
	            	{
	            		sendMessage("INP", IPAddress, serverSocket, port);
	            	}
	            	else
	            	{
	            		String user_name = field[1];
	            		if (verifyUserName(user_name) == false)
	            		{
	            			sendMessage("INU", IPAddress, serverSocket, port);
	            		}
	            		else
	            		{
	            			if (users.contains(user_name))
	            			{
	            				users.remove(user_name);
	            				sendMessage("ROK", IPAddress, serverSocket, port);
	            			}
	            			else
	            			{
	            				sendMessage("UNR", IPAddress, serverSocket, port);
	            			}
	            		}
	
	            	}
	            	break;
	            case "QUO":
	            	if (verifyQuoteParameters(field) == false)
	            	{
	            		sendMessage("INP", IPAddress, serverSocket, port);
	            	}
	            	else
	            	{
	            		String user_name = field[1];
	            		if (!users.contains(user_name))
	            		{
	            			sendMessage("UNR", IPAddress, serverSocket, port);
	            		}
	            		else
	            		{
		            		//create a String quote_list array of only quotes (remove command & username)
		            		String[] stock_list = new String[field.length - 2];
		            		String quote_list = "";
		            		for (int i = 2; i < stock_list.length; i++)
		            		{
		            			stock_list[i] = field[i];
		            		}
		            		quote_list = "ROK" + quoteList(stock_list);
		            		sendMessage(quote_list, IPAddress, serverSocket, port);
//		            		sendMessage("TST", IPAddress, serverSocket, port);
	            		}
	            	}
	            	break;
	            default: 
					System.out.println("INC");
					sendMessage("INC", IPAddress, serverSocket, port);
	            	break;
	            }//END SWITCH
		        
			}//END OUTER ELSE
			
		}//END WHILE
		
	}//END MAIN
		
	public static boolean verifyUserParameters(String[] field)
	{
		boolean flag = true;
				
		// check to make sure there are exactly 2 parameters
		if (!(field.length == 2))
		{
			flag = false;
			return flag;
		}		
		
		for (int i = 0; i < field.length; i++)
		{
			if ( !(field[i].matches("[\\dA-Za-z]+")) )	// checks for digits, uppercase, & lowercase
			{
				flag = false;
			}
		}
		
		return flag;
	}
	
	// verifyUserName 'may' be non-static because it may need to be created in a separate class
	public static boolean verifyUserName(String user_name)
	{
		if (user_name.matches("[\\dA-Za-z]+")  && user_name.length() <= 32 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean verifyQuoteParameters(String[] field)
	{
		boolean flag = true;
				
		// check to make sure there are at least 3 parameters
		if (field.length < 3)
		{
			flag = false;
			return flag;
		}		
		
		for (int i = 0; i < field.length; i++)
		{
			if ( !(field[i].matches("[\\dA-Za-z]+")) )	// checks for digits, uppercase, & lowercase
			{
				flag = false;
			}
		}
		
		return flag;
	}

	// verifyQuote 'may' be non-static because i may need to be created/dependent on the User class
	public static String quoteList(String[] stock_list) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("stockfile.txt"));
//		List<String> stockData = new Vector<String>();
		Map<String, String> stockMap = new HashMap<String, String>();
		
		while (scanner.hasNextLine()) 
		{
			//stockData.add(scanner.nextLine());
			String[] parts = scanner.nextLine().split(" ");
			stockMap.put(parts[0], parts[1]);
//			String line = scanner.nextLine();
//			System.out.println(line);
        }
		
		for (String name: stockMap.keySet()){

            String key =name.toString();
            String value = stockMap.get(name).toString();  
            System.out.println(key + " " + value);  


} 
		
		String quote_list = ",";
		for(String stock : stock_list)
		{
			if (!stockMap.containsKey(stock))
			{
				quote_list += "-1";
			}
			else
			{
				quote_list += stockMap.get(stock);
			}

		}
		
		scanner.close();
		
		return  quote_list;
	}
	
	public static void sendMessage(String command, InetAddress IPAddress, DatagramSocket serverSocket, int port) throws IOException
	{
		respondMessage = command.getBytes();
		DatagramPacket respondPacket = new DatagramPacket(respondMessage,
				respondMessage.length,
				IPAddress,
				port);
		serverSocket.send(respondPacket);

	}
}