import java.io.*; 
import java.net.*; 

class GetStockClient 
{    
	static int PORT = 1050;	// Global variable declaring port number
	static String localhost = "localhost";
	static String TonyIP = "10.224.2.155";	// Anthony USF Wifi 
	static String CRodIP = "172.20.10.2";	// Christian Hotspot
	static String VaddIP = "192.168.1.24";	// Vaddanak Hotspot

	public static void main(String args[]) throws Exception    
	{
		boolean state = true;
		
		while(state)
		{
			String ipAddress = localhost;	// choose IP Address from the global variables
			
			BufferedReader inFromUser = 
					new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(ipAddress);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String sentence = inFromUser.readLine();
			sendData = null;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
			clientSocket.send(sendPacket);       
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println(modifiedSentence);
			
			sentence = "";
			modifiedSentence ="";
		} 
	}		
} 
