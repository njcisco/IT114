import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.StringReader;

public class Client {
  private String host;
  private int port;
  
  public static void main(String[] args) throws UnknownHostException, IOException {
    new Client("127.0.0.1", 19019).run();
  }
  public Client(String host, int port) {
    this.host = host;
    this.port = port;
  }
  public void run() throws UnknownHostException, IOException {
    // client server connection using port number and host id
    Socket client = new Socket(host, port);
    System.out.println("You are connected to ChatSpace Chatroom!");
    // Get Socket output stream (where the client send her mesg)
    PrintStream output = new PrintStream(client.getOutputStream());
    // chat user name prompt
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter your pseudo name: ");
    String nickname = sc.nextLine();
    // share the nickname with the server
    output.println(nickname);
    // initiate thread for server messages handlin
    new Thread(new RmessageHandler(client.getInputStream())).start();
    // read messages from keyboard and send to server
    System.out.println("enter a message: \n");
    // add other message
    while (sc.hasNextLine()) {
      output.println(sc.nextLine());
    }
    // end the terminal exuction
    output.close();
    sc.close();
    client.close();
  }
}



class RmessageHandler implements Runnable {

  private InputStream server;

  public RmessageHandler(InputStream server) {
    this.server = server;
  }

  public void run() {
    // print out recevived server messages
    Scanner s = new Scanner(server);
    String tmp = "";
    while (s.hasNextLine()) {
      tmp = s.nextLine();
      if (tmp.charAt(0) == '[') {
        tmp = tmp.substring(1, tmp.length()-1);
        System.out.println(
            "\nUSERS CONNECTED: " +
            new ArrayList<String>(Arrays.asList(tmp.split(","))) + "\n"
            );
      }else{
        try {
          System.out.println("\n" + getTagValue(tmp));
        } catch(Exception ignore){}
      }
    }
    s.close();
  }
  public static String getTagValue(String HTM){
    return  HTM.split(">")[2].split("<")[0] + HTM.split("<span>")[1].split("</span>")[0];
  }

}
