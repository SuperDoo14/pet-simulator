import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pet myPet = new Pet("cat");

        Text petStatus = new Text(myPet.getStatus());

        Button feedButton = new Button("Feed Pet");
        Button playButton = new Button("Play with Pet");
        Button sleepButton = new Button("Put Pet to Sleep");

        feedButton.setOnAction(e -> {
            myPet.feed();
            petStatus.setText(myPet.getStatus());
        });

        playButton.setOnAction(e -> {
            myPet.play();
            petStatus.setText(myPet.getStatus());
        });

        sleepButton.setOnAction(e -> {
            myPet.sleep();
            petStatus.setText(myPet.getStatus());
        });

        VBox root = new VBox(petStatus, feedButton, playButton, sleepButton);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My Pet Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}