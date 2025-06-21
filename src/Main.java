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
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;


public class Main extends Application {

    private Rectangle nutrientsFillBar;
    private Rectangle nutrientsEmptyBar;
    private Rectangle happinessFillBar;
    private Rectangle happinessEmptyBar;
    private Rectangle energyFillBar;
    private Rectangle energyEmptyBar;

    private Pet myPet;

    // update nutrient bar visual
    private void updateNutrientBar() {
        // calculate widths
        double fillWidth = ((myPet.getNutrients() / 10.0) * 130);
        double emptyWidth = 130 - fillWidth;

        nutrientsFillBar.setWidth(fillWidth);
        nutrientsEmptyBar.setWidth(emptyWidth);

        // determine the colour of nutrient bar depending on value
        if (myPet.getNutrients() <= 3) {
            nutrientsFillBar.setFill(javafx.scene.paint.Color.RED);
        } else if (myPet.getNutrients() <= 6) {
            nutrientsFillBar.setFill(javafx.scene.paint.Color.GOLD);
        } else {
            nutrientsFillBar.setFill(javafx.scene.paint.Color.GREEN);
        }
    }

    // update happiness bar visual
    private void updateHappinessBar() {
        // calculate widths
        double fillWidth = ((myPet.getHappiness() / 10.0) * 130);
        double emptyWidth = 130 - fillWidth;

        happinessFillBar.setWidth(fillWidth);
        happinessEmptyBar.setWidth(emptyWidth);

        // determine the colour of happiness bar depending on value
        if (myPet.getHappiness() <= 3) {
            happinessFillBar.setFill(javafx.scene.paint.Color.RED);
        } else if (myPet.getHappiness() <= 6) {
            happinessFillBar.setFill(javafx.scene.paint.Color.GOLD);
        } else {
            happinessFillBar.setFill(javafx.scene.paint.Color.GREEN);
        }
    }

    // update energy bar visual
    private void updateEnergyBar() {
        // calculate widths
        double fillWidth = ((myPet.getEnergy() / 10.0) * 130);
        double emptyWidth = 130 - fillWidth;

        energyFillBar.setWidth(fillWidth);
        energyEmptyBar.setWidth(emptyWidth);

        // determine the colour of energy bar depending on value
        if (myPet.getEnergy() <= 3) {
            energyFillBar.setFill(javafx.scene.paint.Color.RED);
        } else if (myPet.getEnergy() <= 6) {
            energyFillBar.setFill(javafx.scene.paint.Color.GOLD);
        } else {
            energyFillBar.setFill(javafx.scene.paint.Color.GREEN);
        }
    }



    @Override
    public void start(Stage primaryStage) {
        myPet = new Pet("cat");

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

            updateNutrientBar();
            updateHappinessBar();
            updateEnergyBar();
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

            updateNutrientBar();
            updateHappinessBar();
            updateEnergyBar();
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
                    spriteView.setViewport(new Rectangle2D(0, 0, 384, 384));
                }));
                pauseTimeline.play();
            });

            updateNutrientBar();
            updateHappinessBar();
            updateEnergyBar();
        });

        // nutrients bar
        double nutrientsFillWidth = (myPet.getNutrients() / 10.0) * 130;
        double nutrientsEmptyWidth = 130 - nutrientsFillWidth;
        nutrientsFillBar = new Rectangle(nutrientsFillWidth, 10);
        nutrientsFillBar.setFill(javafx.scene.paint.Color.GREEN);
        nutrientsEmptyBar = new Rectangle(nutrientsEmptyWidth, 10);
        nutrientsEmptyBar.setFill(javafx.scene.paint.Color.LIGHTGRAY);

        // happiness bar
        double happinessFillWidth = (myPet.getHappiness() / 10.0) * 130;
        double happinessEmptyWidth = 130 - happinessFillWidth;
        happinessFillBar = new Rectangle(happinessFillWidth, 10);
        happinessFillBar.setFill(javafx.scene.paint.Color.GREEN);
        happinessEmptyBar = new Rectangle(happinessEmptyWidth, 10);
        happinessEmptyBar.setFill(javafx.scene.paint.Color.LIGHTGRAY);

        // energy bar
        double energyFillWidth = (myPet.getEnergy() / 10.0) * 130;
        double energyEmptyWidth = 130 - energyFillWidth;
        energyFillBar = new Rectangle(energyFillWidth, 10);
        energyFillBar.setFill(javafx.scene.paint.Color.GREEN);
        energyEmptyBar = new Rectangle(energyEmptyWidth, 10);
        energyEmptyBar.setFill(javafx.scene.paint.Color.LIGHTGRAY);

        // containers for each pet status bar
        HBox nutrientBarContainer = new HBox(nutrientsFillBar, nutrientsEmptyBar);
        HBox happinessBarContainer = new HBox(happinessFillBar, happinessEmptyBar);
        HBox energyBarContainer = new HBox(energyFillBar, energyEmptyBar);

        // container for all of the bars in one
        HBox allBarsContainer = new HBox(20, nutrientBarContainer, happinessBarContainer, energyBarContainer);
        allBarsContainer.setAlignment(Pos.CENTER);

        // layout for the gui components
        VBox root = new VBox(10, allBarsContainer, spriteView, petStatus, feedButton, playButton, sleepButton);
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