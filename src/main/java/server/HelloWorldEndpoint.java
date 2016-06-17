package server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

@ServerEndpoint(value = "/chat")
public class HelloWorldEndpoint
{
	private final static SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-YYYY - HH:MM:SS");
	public static List<Session> _sessions = Collections.synchronizedList(new ArrayList<Session>());
	
	@OnOpen
	public void openSocket(Session session)
	{
		_sessions.add(session);
	}
	
	@OnClose
	public void closeSocket(Session session)
	{
		_sessions.remove(session);
	}
	
	@OnError
	public void handleSocketError(Session session, Throwable exception)
	{
		System.out.println("There was an error maintaining the Websocket connection: " + exception.getMessage());
		exception.printStackTrace();
	}
	
	@OnMessage
	public void messageSocket(String message, Session session)
	{
			synchronized (_sessions)
			{
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("timestamp", SDF.format(new Date()));
				jsonObj.addProperty("message",message);
				
				// send message to all open sessions
				for(Session client : _sessions)
				{
					try
					{
						client.getBasicRemote().sendText(jsonObj.toString());
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		
	}
}
