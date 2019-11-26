// package maverick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("chatBot.fxml"));
        primaryStage.setTitle("Chat Bot");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
			SampleSocketClient client = SampleSocketClient.getSocketClient();
			client.close();
		});
    }


    public static void main(String[] args) {
        launch(args);
    }
}
