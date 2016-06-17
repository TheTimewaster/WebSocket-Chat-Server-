package client;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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
		JsonParser jsonParser = new JsonParser();
		JsonObject messageObject = (JsonObject) jsonParser.parse(message);
		System.out.println(messageObject.get("timestamp") + "\t" + messageObject.get("message"));

	}

	public void sendMessage(String input) throws Exception
	{
		_clientSession.getRemote().sendString(input);
	}
}
