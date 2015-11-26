import java.io.*; 
import java.net.*; 
class UDPClient 
{    
	private static int port = 1050;	// Global variable declaring port number
	private static String localhost = "localhost";
	private static String TonyIP = "10.224.2.155";	// Anthony USF Wifi 
	private static String CRodIP = "172.20.10.2";	// Christian Hotspot
	private static String VaddIP = "192.168.1.24";	// Vaddanak Hotspot

	public static void main(String args[]) throws Exception    
	{       
		boolean state = true;

		while(state)
		{
		
			BufferedReader inFromUser = 
					new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(CRodIP);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String sentence = inFromUser.readLine();
			sendData = null;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
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

// - See more at: https://systembash.com/a-simple-java-udp-server-and-udp-client/#sthash.ui72SbBg.dpuf