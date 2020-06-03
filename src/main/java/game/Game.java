package game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application {
    public static final int WIDTH = 1472;
    public static final int HEIGHT = 768;

    /**
     * Launch the game
     * @param args args, don't put anything here
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize stuff and start a GameLoop
     * @param stage Stage in which the game happens
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Leaping Elements");

        Group mainSceneGroup = new Group();
        Scene mainScene = new Scene(mainSceneGroup, WIDTH, HEIGHT);
        ArrayList<MenuButton> buttonsToFurtherInitialize = initializeMainScene(mainSceneGroup);

        Group gameSceneGroup = new Group();
        Scene gameScene = new Scene(gameSceneGroup, WIDTH, HEIGHT);
        Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
        gameSceneGroup.getChildren().add(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        stage.setScene(mainScene);
        new GameLoop(gc, stage, mainScene, gameScene, buttonsToFurtherInitialize).start();
        stage.show();
    }

    /**
     * Initialize objects on main scene
     * @param mainSceneGroup Group to which we add containers
     * @return level buttons + load level button because they need to be initialize later
     */
    private ArrayList<MenuButton> initializeMainScene(Group mainSceneGroup) {
        // background
        Canvas background = new Canvas(WIDTH, HEIGHT);
        GraphicsContext backgroundGC = background.getGraphicsContext2D();
        backgroundGC.drawImage(new Image("Other/BG.png"), 0, 0);
        mainSceneGroup.getChildren().add(background);

        // main menu
        GridPane mainMenu = new GridPane();
        String mainMenuStyle = "-fx-background-color: transparent;" +
                "-fx-background-image: url('Other/Main_menu.png'); " +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;";
        mainMenu.setStyle(mainMenuStyle);
        mainMenu.setPrefWidth(545);
        mainMenu.setPrefHeight(750);
        mainMenu.setTranslateX(Game.WIDTH / 2 - 545 / 2);
        mainMenu.setTranslateY(10);
        mainSceneGroup.getChildren().add(mainMenu);

        MenuButton b1 = new MenuButton("1");
        MenuButton b2 = new MenuButton("2");
        MenuButton b3 = new MenuButton("3");
        MenuButton b4 = new MenuButton("4");
        MenuButton b5 = new MenuButton("5");
        MenuButton loadLevelButton = new MenuButton("Load Level");
        MenuButton creditsButton = new MenuButton("Credits");

        // add buttons to their respective horizontal boxes
        HBox hBox1 = new HBox();
        hBox1.setSpacing(30);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(b1, b2, b3);

        HBox hBox2 = new HBox();
        hBox2.setSpacing(30);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(b4, b5);

        // complete by adding it all to the vertical box
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(190, 50, 50, 90));
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hBox1, hBox2, loadLevelButton, creditsButton);

        // add vertical box to the main menu
        mainMenu.getChildren().add(vbox);

        // credits image
        GridPane credits = new GridPane();
        String creditsStyle = "-fx-background-color: transparent;" +
                "-fx-background-image: url('Other/Credits.png'); " +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;";
        credits.setStyle(creditsStyle);
        credits.setPrefWidth(420);
        credits.setPrefHeight(490);
        credits.setTranslateX(Game.WIDTH / 2 - 420 / 2);
        credits.setTranslateY(-500);
        mainSceneGroup.getChildren().add(credits);

        // set credits button to show credits
        creditsButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (credits.getTranslateY() < 0) {
                        credits.setTranslateY(85);
                    } else {
                        credits.setTranslateY(-500);
                    }
                    creditsButton.setButtonReleasedStyle();
                }
            }
        });

        // return level buttons + load level button because they need to be initialize later
        ArrayList<MenuButton> res = new ArrayList<>();
        res.add(b1);
        res.add(b2);
        res.add(b3);
        res.add(b4);
        res.add(b5);
        res.add(loadLevelButton);

        return res;
    }
}
