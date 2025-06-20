import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pet myPet = new Pet("cat");

        Image spriteSheet = new Image("file:src/images/pet.png");
        ImageView spriteView = new ImageView(spriteSheet);

        spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));
        spriteView.setSmooth(false);
        spriteView.setPreserveRatio(false);
        spriteView.setFitWidth(384);
        spriteView.setFitHeight(384);

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
            spriteView.setViewport(new Rectangle2D(384 * 6, 0, 384, 384));

            KeyFrame firstKeyFrame = new KeyFrame(Duration.millis(300), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 7, 0, 384, 384));
            });

            KeyFrame secondKeyFrame = new KeyFrame(Duration.millis(600), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 6, 0, 384, 384));
            });

            Timeline timeline = new Timeline(firstKeyFrame, secondKeyFrame);
            timeline.setCycleCount(4);

            timeline.play();
        });

        sleepButton.setOnAction(e -> {
            myPet.sleep();
            petStatus.setText(myPet.getStatus());
        });

        VBox root = new VBox(spriteView, petStatus, feedButton, playButton, sleepButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("My Pet Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}