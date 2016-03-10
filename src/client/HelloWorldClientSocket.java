package client;



import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

@WebSocket
public class HelloWorldClientSocket
{
	Session _clientSession = null;
	
	public HelloWorldClientSocket()
	{
		
	}
	
	@OnWebSocketConnect
	public void openSocket(Session session)
	{
		this._clientSession = session;
	}
	
	@OnWebSocketClose
	public void closeSocket(int statusCode, String reason)
	{
		this._clientSession = null;
	}
	
	@OnWebSocketError
	public void handleSocketError(Session session, Throwable exception)
	{
		exception.printStackTrace();
	}
	
	@OnWebSocketMessage
	public void handleMessage(String message) throws Exception
	{
			JSONObject messageObject = new JSONObject(message);
			System.out.println(messageObject.get("timestamp") + "\t" + messageObject.get("message"));
		
	}
	
	public void sendMessage(String input) throws Exception
	{
		_clientSession.getRemote().sendString(input);
	}
}
