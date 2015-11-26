import java.io.*;
import java.util.List;
import java.util.Vector;
import java.net.DatagramSocket;
//import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InetAddress;


class GetStockServer
{
	static int PORT = 1050;	//global port number
	static byte[] respondMessage = new byte[1024];
	
	public static void main(String args[]) throws IOException //, SocketException
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
			
			String[] instruction = message.split(";");
			String[] field = instruction[0].split(",");	// field[0] = CMD, field[1] = USR, field[2+] = QUO
			
			// First check for 
			if (messageCAPS.equals("OFF"))
			{
				System.out.println("Server shutting down");
				serverSocket.close();
				return;
			}
			// Verify Command Field (function???)
			if (verifyCommand(field[0]) == true)
			{				
				System.out.println("ROK");
				sendMessage("ROK", IPAddress, serverSocket, port);
								
			}
			else
			{
				System.out.println("INC");
				sendMessage("INC", IPAddress, serverSocket, port);
			}
			
		}
		
	}
	
	// verifyCommand should be static because it is not dependent on user or stock
	public static boolean verifyCommand(String command)
	{
		if (command.equals("REG") || command.equals("UNR") || command.equals("QUO"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// verifyUserName 'may' be non-static because it may need to be created in a separate class
	public static boolean verifyUserName(String username)
	{
		return false;
	}
	
	// verifyQuote 'may' be non-static because i may need to be created/dependent on the User class
	public static boolean verifyQuote(String quote)
	{
		return false;
	}
	
	public static void sendMessage(String command, InetAddress IPAddress, DatagramSocket serverSocket, int port) throws IOException
	{
		System.out.println(command);
		respondMessage = command.getBytes();
		DatagramPacket respondPacket = new DatagramPacket(respondMessage,
				respondMessage.length,
				IPAddress,
				port);
		serverSocket.send(respondPacket);

	}
}