// package maverick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

//Class to hold client connection and prevent it from blocking the main thread of the server
class ServerThread extends Thread {
    private Socket client;
    private String clientName;

    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = false;
    private Consumer<String> callback;

    public ServerThread(Socket myClient, Consumer<String> callback) throws IOException {
        this.client = myClient;
        this.callback = callback;
        isRunning = true;
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // setting the client name from the first message after joining the server
        this.clientName = in.readLine();
        System.out.println("Spawned thread for client " + clientName);
    }

    @Override
    public void run() {
        try {
            String fromClient = "", toClient = "";
            while (isRunning && !"disconnect".equalsIgnoreCase(fromClient) && (fromClient = in.readLine()) != null) {

                System.out.println("Received: " + fromClient);
                if (callback != null) {
                    toClient = clientName + ": " + fromClient;
                    System.out.println("Sending: " + toClient);
                    callback.accept(toClient);
                }
            }
        } catch (IOException e) {
			System.out.println("Connection reset");
//            e.printStackTrace();
        } finally {
            System.out.println("Server cleaning up IO for " + clientName);
            cleanup();
        }
    }

    public String getClientName() {
    	return this.clientName;
	}

    public void stopThread() {
        isRunning = false;
    }

    public boolean isActive() {
    	return isRunning;
	}

    public void send(String msg) {
        out.println(msg);
    }

    void cleanup() {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println("Input already closed");
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println("Output already closed");
            }
        }
        this.stopThread();
    }
}