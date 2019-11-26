// package maverick;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SampleSocketServer{
	int port = -1;
	//private Thread clientListenThread = null;
	List<ServerThread> clients = new ArrayList<ServerThread>();
	public static boolean isRunning = true;
	public SampleSocketServer() {
		isRunning = true;
	}
	
	public synchronized void broadcast(String message) {
		String [] messageArr = message.split(":");
		String messagePart = messageArr[1];
		boolean privateMessage = messagePart.contains("@");
		if(privateMessage){
			// Sending private message to the sender and the receiver only
			String [] messagePartArr = messagePart.split(" ");
			String receiver = messagePartArr[1].replace("@", "");
			System.out.println("private receiver " + receiver);
			for(ServerThread client: clients){
				if(client.getClientName().equalsIgnoreCase(messageArr[0]) || client.getClientName().equalsIgnoreCase(receiver)) {
					client.send(message);
				}
			}
		} else {
			//iterate through all clients and attempt to send the message to each
			for(int i = 0; i < clients.size(); i++) {
				if(clients.get(i).isActive()) {
					clients.get(i).send(message);
				} else {
					// removing inactive users from the client list
					System.out.println("Removing disconnected user - "+ clients.get(i).getClientName());
					clients.remove(i);
				}
			}
			System.out.println("Sending message to " + clients.size() + " clients");
		}
	}

	private void start(int port) {
		this.port = port;
		System.out.println("Waiting for client");
		try(ServerSocket serverSocket = new ServerSocket(port);){
			while(SampleSocketServer.isRunning) {
				try {
					//use Consumer class to pass a callback to the thread for broadcasting messages
					//to all clients
					Consumer<String> callback = s -> broadcast(s);
					Socket client = serverSocket.accept();
					System.out.println("Client connected");
					ServerThread thread = new ServerThread(client,
							callback);
					thread.start();//start client thread
					// remove inactive users from the client list
					for(int i = 0; i < clients.size(); i++) {
						if(!clients.get(i).isActive()) {
							System.out.println("Removing disconnected user - "+ clients.get(i).getClientName());
							clients.remove(i);
						}
					}
					clients.add(thread);//add to client pool
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				isRunning = false;
				Thread.sleep(50);
				System.out.println("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] arg) {
		// run the server file as 'java SampleSocketServer <Port Number>'

		System.out.println("Starting Server");
		SampleSocketServer server = new SampleSocketServer();
		int port = -1;
		if(arg.length > 0){
			try{
				port = Integer.parseInt(arg[0]);
			}
			catch(Exception e){
				System.out.println("Invalid port: " + arg[0]);
			}		
		}
		if(port > -1){
			System.out.println("Server listening on 127.0.0.1:" + port);
			server.start(port);
		}
		System.out.println("Server Stopped");
	}
}
