import java.io.*; 
import java.net.*; 

class GetStockClient 
{    
	static int PORT = 1050;	// Global variable declaring port number
	static String localhost = "localhost";
	static String TonyIP = "10.249.65.165";	// Anthony USF Wifi 
	static String CRodIP = "172.20.10.2";	// Christian Hotspot
	static String VaddIP = "192.168.1.24";	// Vaddanak Hotspot
	static String Cwifi = "10.226.1.95"; //Christian wifi
	static String CHardine = "192.168.0.2";
	static String TonyChapel = "192.168.0.20";
	static String ChrisChapel = "192.168.0.5";
	static String TonyLocalIP = "192.168.0.2";
	static String actualIP = "10.247.52.47";

	public static void main(String args[]) throws Exception
	{
		boolean state = true;
		int count = 1;
		int timeout = 0;
		
		while(state)
		{
			String ipAddress = actualIP;	// choose IP Address from the global variables
			
			BufferedReader inFromUser = 
					new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(ipAddress);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String message = inFromUser.readLine();
			sendData = null;
			sendData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
			clientSocket.send(sendPacket);       			
			
			clientSocket.setSoTimeout(5000);
			
			while (timeout < 2)
			{
				
			try{				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				
				String modifiedMessage = new String(receivePacket.getData());
				System.out.println("S-to-C message " + count + " = " + modifiedMessage);
				
				message = "";
				modifiedMessage ="";
				timeout = 2;
			}catch(SocketTimeoutException e){
				timeout++;
				System.out.println("Sending attempt " + (timeout + 1));
				clientSocket.send(sendPacket);
				continue;
			}
			catch(IOException e)
			{
				continue;
			}
			
			
			
			}
			timeout = 0;
			count++;
		}
	}		
} 
