// package maverick;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class SampleSocketClient implements Initializable {
	// Interface variables
	public Button joinButton;
	public TextField userNameField;
	public TextField ipAddressField;
	public TextField portField;
	public TextField messageField;
	public Button sendButton;
	public ColorPicker colourPicker;
	public TextFlow textFlowView;

	// Variables
	private Socket server;
	private static SampleSocketClient client = null;
	private PrintWriter out;
	private BufferedReader in;
	private String clientName;
	private static StringProperty text = new SimpleStringProperty();
	private String currentColour;

	public SampleSocketClient() {
	}

	public void setServer(Socket socket) {
		this.server = socket;
	}

	// connecting to the server
	public String connect(String address, int port) {
		try {
			client.setServer(new Socket(address,port));
			System.out.println("Client successfully connected");
			return "Client successfully connected";
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "Host Error";
		} catch (IOException e) {
			e.printStackTrace();
			return "I/O error";
		}
	}

	// starting the server
	public String start() throws IOException {
		if(server == null) {
			return "Error starting the server";
		}
		System.out.println("Client Started");
		this.out = new PrintWriter(server.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		return "Client Started";

	}

	// closing socket
	public void close() {
		if(getSocketClient().server != null && !getSocketClient().server.isClosed()) {
			try {
				getSocketClient().server.close();
				System.out.println("Closed socket");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// returning client object
	public static SampleSocketClient getSocketClient() {
		if (client == null) {
			client = new SampleSocketClient();
		}
		return client;
	}

	// Join server button event
	public void joinServer(ActionEvent actionEvent) {
		int port = Integer.parseInt(this.portField.textProperty().get());
		String address = this.ipAddressField.textProperty().get();
		getSocketClient().clientName = this.userNameField.textProperty().get();
		String status = getSocketClient().connect(address, port);
		updateTextAreaView(status + " " + Color.BLACK);
		try {
			String result = getSocketClient().start();
			// updating text view with connection status
			updateTextAreaView(result + " " + Color.BLACK);
			// sending client name
			getSocketClient().out.println(getSocketClient().clientName);
			getSocketClient().listenToServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function with the send message button event
	public void sendMessage(ActionEvent actionEvent) {
		try {
			if(!getSocketClient().server.isClosed()) {
				String message = this.messageField.textProperty().get();
				this.messageField.textProperty().setValue("");
				if(message != null && !message.isEmpty()) {
					getSocketClient().out.println(message + " " + this.currentColour);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Client shutdown");
		}

	}

	// listning to the server messages to update the UI
	public void listenToServer() {
		//Thread to listen for responses from server so it doesn't block main thread
		Thread fromServerThread = new Thread(() -> {
			try {
				String fromServer = "";
				while(!server.isClosed() && (fromServer = client.in.readLine()) != null) {
					System.out.println("Replay from server: " + fromServer);
					updateTextAreaView(fromServer);
				}
				System.out.println("Stopping server listen thread");
			}
			catch (Exception e) {
				if(!server.isClosed()) {
					e.printStackTrace();
					System.out.println("Server closed connection");
				}
				else {
					System.out.println("Connection closed");
				}
			}
			finally {
				close();
			}
		});
		fromServerThread.start();//start the thread
		System.out.println("Listening to Server..");
	}

	// update the text view
	private static void updateTextAreaView(String message) {
		// running in JavaFX Application thread
		Platform.runLater(() -> {
			text.set(message);
		});
	}

	// Initialize function runs first when loading the UI
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// running in JavaFX Application thread
		Platform.runLater(() -> {
			text.addListener((observable, oldValue, newValue) -> {
				String [] tempArr = newValue.split(" ");
				Text userName = new Text(tempArr[0] + " ");
				userName.setFill(Paint.valueOf(tempArr[tempArr.length-1]));
				this.textFlowView.getChildren().add(userName);
				// adding rest removing last element of the array
				for(int i=1; i<tempArr.length-1; i++) {
					this.textFlowView.getChildren().add(new Text(tempArr[i] + " "));
				}
				this.textFlowView.getChildren().add(new Text("\n"));
			});
		});

		// setting default pseudo username colour to black
		this.colourPicker.setValue(Color.BLACK);
		this.currentColour = this.colourPicker.getValue().toString();
	}

	public void changeColour(ActionEvent actionEvent) {
		this.currentColour = this.colourPicker.getValue().toString();
	}
}