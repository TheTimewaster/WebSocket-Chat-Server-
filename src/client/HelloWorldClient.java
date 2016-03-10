package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Scanner;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;


public class HelloWorldClient
{
	private static final String WS_URL = "ws://localhost:8080/hello";
	
	public static void main(String[] args)
	{
		WebSocketClient client = new WebSocketClient();
		try
		{
			client.start();
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			HelloWorldClientSocket socket = new HelloWorldClientSocket();
			client.connect(socket,new URI(WS_URL), request);
			
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			while(!"exit".equals(input))
			{
				input = scanner.nextLine();	
				socket.sendMessage(input);		
			}
			
			scanner.close();
		} 
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
