import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SampleSocketServerRev {
	//port number has to be the same (client and server)
	int port = 3500;
	public SampleSocketServerRev() {
	}
	// create a server socket and starting the server to be able to receive any connection through 3002 port(same as client port)
	private void start(int port) {
		this.port = port;
		System.out.println("Waiting for Users");
		try (ServerSocket serverSocket = new ServerSocket(port);
				Socket client = serverSocket.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			
			System.out.println("User connected, waiting for message");
			String fromClient = "";
			String toClient = "";
			while ((fromClient = in.readLine()) != null) {
				System.out.println("From client: " + fromClient);
				List<String> reversedInput = Arrays.asList(fromClient.split(""));
				//reverse the message received from client and send it back to server
				Collections.reverse(reversedInput);
				toClient = String.join("", reversedInput);
				toClient= toClient.toUpperCase();
				System.out.println("Sending to User: " + toClient);
				
				if ("kill server".equalsIgnoreCase(fromClient)) {
					out.println("Server received kill command, disconnecting");
					break;
				}
				else {
					out.println(toClient);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] arg) {
		System.out.println("Starting Server");
		SampleSocketServerRev server = new SampleSocketServerRev();
		server.start(3500);
		System.out.println("Server Stopped");
	}
}