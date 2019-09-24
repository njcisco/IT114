import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SampleSocketClient {
	Socket server;
	public SampleSocketClient() {
		
	}
	//create a server socket and connect the client user ip address and a port number(IOException is used if anthing goes wrong)
	public void connect(String address, int port) {
		try {
			server = new Socket(address, port);
			System.out.println("Client connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//starting the process by inviting the client to type a message to be sent to the server
	public void start() throws IOException {
		if(server == null) {
			return;
		}
		System.out.println("Listening for input");
		try(Scanner si = new Scanner(System.in);
				PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));){
			String line = "";
			while(true) {
				try {
					System.out.println("Waiting for input");
					line = si.nextLine();
					if(!"quit".equalsIgnoreCase(line)) {
						out.println(line);
					}
					else {
						break;
					}
					line = "";
					String fromServer = in.readLine();
					//display the response from the server. if not printout "server disconnected"
					if(fromServer != null) {
						System.out.println("Reply from server: " + fromServer);
					}
					else {
						System.out.println("Server disconnected");
						break;
					}
				}
				catch(IOException e) {
					System.out.println("Connection dropped");
					break;
				}
			}
			System.out.println("Exited loop");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	//closing the socket
	private void close() {
		if(server != null) {
			try {
				server.close();
				System.out.println("Closed socket");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		SampleSocketClient client = new SampleSocketClient();
		client.connect("127.0.0.1", 3002);
		try {
			//if start is private, it's valid here since this main is part of the class
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}