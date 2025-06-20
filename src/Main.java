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

        // loading sprite sheet image to be used within program
        Image spriteSheet = new Image("file:src/images/pet.png");
        ImageView spriteView = new ImageView(spriteSheet);

        // setting up the view for each sprite at 384x384 pixels
        spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));
        spriteView.setSmooth(false);
        spriteView.setPreserveRatio(false);
        spriteView.setFitWidth(384);
        spriteView.setFitHeight(384);

        Text petStatus = new Text(myPet.getStatus());

        Button feedButton = new Button("Feed Pet");
        Button playButton = new Button("Play with Pet");
        Button sleepButton = new Button("Put Pet to Sleep");

        // button to feed the pet
        feedButton.setOnAction(e -> {
            myPet.feed();
            petStatus.setText(myPet.getStatus());
            Timeline timeline = new Timeline();

            // loop through a series of sprite animations
            for (int spriteNum = 8; spriteNum <= 26; spriteNum++) {
                int timeMs = (spriteNum - 8) * 300; // increase the time of each sprite KeyFrame

                final int currentSprite = spriteNum; // get current Sprite number

                // show the current sprite in frame
                KeyFrame frame = new KeyFrame(Duration.millis(timeMs), event -> {
                    spriteView.setViewport(new Rectangle2D(384 * currentSprite, 0, 384, 384));
                });

                timeline.getKeyFrames().add(frame);
            }

            // once finished go back to sprite 0
            timeline.setOnFinished(event -> {
                spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));
            });

            timeline.play();
        });

        // button to play with pet
        playButton.setOnAction(e -> {
            myPet.play();
            petStatus.setText(myPet.getStatus());
            spriteView.setViewport(new Rectangle2D(384 * 6, 0, 384, 384));

            // set up keyframes for animation of playing with pet
            KeyFrame firstKeyFrame = new KeyFrame(Duration.millis(300), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 7, 0, 384, 384));
            });

            KeyFrame secondKeyFrame = new KeyFrame(Duration.millis(600), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 6, 0, 384, 384));
            });

            Timeline timeline = new Timeline(firstKeyFrame, secondKeyFrame);
            timeline.setCycleCount(6);

            // once finished load a heart animation to show pet happiness increase significantly
            timeline.setOnFinished(event -> {
                spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));

                KeyFrame hearts1 = new KeyFrame(Duration.millis(600), pauseEvent -> {
                    spriteView.setViewport(new Rectangle2D(384 * 27, 0, 384, 384));
                });

                KeyFrame hearts2 = new KeyFrame(Duration.millis(900), pauseEvent -> {
                    spriteView.setViewport(new Rectangle2D(384 * 28, 0, 384, 384));
                });

                KeyFrame hearts3 = new KeyFrame(Duration.millis(1200), pauseEvent -> {
                    spriteView.setViewport(new Rectangle2D(384 * 29, 0, 384, 384));
                });

                KeyFrame sprite0 = new KeyFrame(Duration.millis(2000), pauseEvent -> {
                    spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));
                });

                Timeline afterTimeline = new Timeline(hearts1, hearts2, hearts3, sprite0);
                afterTimeline.play();
            });

            timeline.play();
        });

        // button to put the pet to sleep
        sleepButton.setOnAction(e -> {
            myPet.sleep();
            petStatus.setText(myPet.getStatus());
            spriteView.setViewport(new Rectangle2D(384 * 3, 0, 384, 384));

            // set up keyframes for sleeping animation
            KeyFrame firstKeyFrame = new KeyFrame(Duration.millis(500), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 4, 0, 384, 384));
            });

            KeyFrame secondKeyFrame = new KeyFrame(Duration.millis(800), event -> {
                spriteView.setViewport(new Rectangle2D(384 * 5, 0, 384, 384));
            });

            Timeline timeline = new Timeline(firstKeyFrame, secondKeyFrame);
            timeline.setCycleCount(6);
            timeline.play();

            // once finished go back to default position
            timeline.setOnFinished(event -> {
                spriteView.setViewport(new Rectangle2D(384 * 3, 0, 384, 384));

                Timeline pauseTimeline = new Timeline(new KeyFrame(Duration.millis(1000), pauseEvent -> {
                    spriteView.setViewport(new Rectangle2D(384, 0, 384, 384));
                }));
                pauseTimeline.play();
            });
        });

        // layout for the gui components
        VBox root = new VBox(spriteView, petStatus, feedButton, playButton, sleepButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pet Simulator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}