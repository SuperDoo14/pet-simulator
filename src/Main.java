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
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;


public class Main extends Application {

    private Rectangle nutrientsFillBar;
    private Rectangle nutrientsEmptyBar;
    private Rectangle happinessFillBar;
    private Rectangle happinessEmptyBar;
    private Rectangle energyFillBar;
    private Rectangle energyEmptyBar;

    private ImageView feedButton;
    private ImageView playButton;
    private ImageView sleepButton;

    private boolean isAnimationRunning = false;

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

    // disable all buttons from being clicked while animation runs
    private void disableButtons() {
        feedButton.setDisable(true);
        playButton.setDisable(true);
        sleepButton.setDisable(true);
    }

    // enable buttons to be clicked again
    private void enableButtons() {
        feedButton.setDisable(false);
        playButton.setDisable(false);
        sleepButton.setDisable(false);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene homeScene = createHomeScene(primaryStage);

        // show home scene first
        primaryStage.setScene(homeScene);
        primaryStage.show();

        Scene gameScene = createHomeScene(primaryStage);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Pet Simulator");
        primaryStage.show();
    }

    private void switchToGame(Stage primaryStage, String petName) {
        Scene gameScene = createGameScene(primaryStage, petName);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Pet Simulator - " + petName);
    }

    private Scene createHomeScene(Stage primaryStage) {
        // title
        Text title = new Text("Pet Simulator");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        // pet name input
        Text nameLabel = new Text("Enter your pet's name:");
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your pet's name");
        nameInput.setPrefWidth(200);

        // start button
        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        // button action to switch to play game
        startButton.setOnAction(e -> {
            String petName = nameInput.getText().trim();
            if (petName.isEmpty()) {
                petName = "pet";
            }

            // switch to game scene
            switchToGame(primaryStage, petName);
        });

        VBox homeLayout = new VBox(20, title, nameLabel, nameInput, startButton);
        homeLayout.setAlignment(Pos.CENTER);

        return new Scene(homeLayout, 600, 700);
    }

    private Scene createGameScene(Stage primaryStage, String petName) {
        myPet = new Pet(petName);

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

        feedButton = new ImageView(new Image("file:src/images/food.png"));
        playButton = new ImageView(new Image("file:src/images/play.png"));
        sleepButton = new ImageView(new Image("file:src/images/sleep.png"));

        // button to feed the pet
        feedButton.setOnMouseClicked(e -> {
            // check if animation is already running
            if (isAnimationRunning) {
                return; // ignore button click
            }

            isAnimationRunning = true;
            disableButtons();

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

                // re-enable buttons when animation finishes
                isAnimationRunning = false;
                enableButtons();
            });

            timeline.play();

            updateNutrientBar();
            updateHappinessBar();
            updateEnergyBar();
        });

        // button to play with pet
        playButton.setOnMouseClicked(e -> {
            // check if animation is already running
            if (isAnimationRunning) {
                return; // ignore button click
            }

            isAnimationRunning = true;
            disableButtons();

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

                afterTimeline.setOnFinished(finalEvent -> {
                    isAnimationRunning = false;
                    enableButtons();
                });

                afterTimeline.play();

            });

            timeline.play();

            updateNutrientBar();
            updateHappinessBar();
            updateEnergyBar();
        });

        // button to put the pet to sleep
        sleepButton.setOnMouseClicked(e -> {
            // check if animation is already running
            if (isAnimationRunning) {
                return; // ignore button click
            }

            isAnimationRunning = true;
            disableButtons();

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

                pauseTimeline.setOnFinished(finalEvent -> {
                    isAnimationRunning = false;
                    enableButtons();
                });

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

        // text labels for each bar
        Text nutrientsLabel = new Text("Nutrients");
        nutrientsLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        Text happinessLabel = new Text("Happiness");
        happinessLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        Text energyLabel = new Text("Energy");
        energyLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        // containers for bars with their labels
        VBox nutrientBarWithLabel = new VBox(5, nutrientBarContainer, nutrientsLabel);
        nutrientBarWithLabel.setAlignment(Pos.CENTER);

        VBox happinessBarWithLabel = new VBox(5, happinessBarContainer, happinessLabel);
        happinessBarWithLabel.setAlignment(Pos.CENTER);

        VBox energyBarWithLabel = new VBox(5, energyBarContainer, energyLabel);
        energyBarWithLabel.setAlignment(Pos.CENTER);

        // container for all of the bars in one
        HBox allBarsContainer = new HBox(30, nutrientBarWithLabel, happinessBarWithLabel, energyBarWithLabel);
        allBarsContainer.setAlignment(Pos.CENTER);

        // labels for buttons
        Text foodLabel = new Text("Food");
        foodLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Text playLabel = new Text("Play");
        playLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Text sleepLabel = new Text("Sleep");
        sleepLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        VBox foodButtonWithLabel = new VBox(5, feedButton, foodLabel);
        foodButtonWithLabel.setAlignment(Pos.CENTER);

        VBox playButtonWithLabel = new VBox(5, playButton, playLabel);
        playButtonWithLabel.setAlignment(Pos.CENTER);

        VBox sleepButtonWithLabel = new VBox(5, sleepButton, sleepLabel);
        sleepButtonWithLabel.setAlignment(Pos.CENTER);

        // container for all buttons and labels
        HBox buttonContainer = new HBox(30, foodButtonWithLabel, playButtonWithLabel, sleepButtonWithLabel);
        buttonContainer.setAlignment(Pos.CENTER);

        // create an invisible spacer at top
        Region topSpacer = new Region();
        topSpacer.setPrefHeight(60); // 60 pixels of empty space

        // create an invisible spacer at bottom
        Region bottomSpacer = new Region();
        bottomSpacer.setPrefHeight(120); // 120 pixels of empty space

        // layout for the gui components
        VBox root = new VBox(10, topSpacer, allBarsContainer, spriteView, buttonContainer, bottomSpacer);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 700);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}