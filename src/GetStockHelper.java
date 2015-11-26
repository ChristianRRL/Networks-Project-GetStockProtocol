//import java.io.IOException;
import java.net.*;

public class GetStockHelper
{
	static String subnet1 = "192.168";
	static String subnet2 = "172.20";
	static String subnet3 = "10.224";
	
	// This helper function is meant to find an available local ip to pair with
	// There are some predefined subnets provided
	public String getIP() throws Exception
	{
		String subnet = subnet1;
		String ipAddressTemp = "";
		   int timeout = 1;
		   exitloop:
		   for (int i = 1; i < 255; i++)
		   {
			   for (int j = 1; j < 255; j++)
			   {
				   String host = subnet + "." + i + "." + j;
				   if (InetAddress.getByName(host).isReachable(timeout))
				   {
					   ipAddressTemp = host;
					   break exitloop;
				   }
				   else
				   {
			    	   System.out.print("host not found on the " + host + " subnet");
			    	   System.out.println("... Still looking");
				   }
			   }
		   }
//		   System.out.println("\nipAddressTemp: " + ipAddressTemp);
		   return ipAddressTemp;

	}
}
