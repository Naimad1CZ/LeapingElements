package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {
    public static final int WIDTH = 1472;
    public static final int HEIGHT = 768;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("My Java Game");

        Group mainSceneGroup = new Group();
        Scene mainScene = new Scene(mainSceneGroup, WIDTH, HEIGHT);
        initializeMainScene(mainSceneGroup);

        Group gameSceneGroup = new Group();
        Scene gameScene = new Scene(gameSceneGroup, WIDTH, HEIGHT);
        Canvas gameCanvas = new Canvas(WIDTH, HEIGHT);
        gameSceneGroup.getChildren().add(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        stage.setScene(mainScene);
        new GameLoop(gc, stage, mainScene, gameScene).start();
        stage.show();
    }

    private void initializeMainScene(Group mainSceneGroup) {
        // background
        Canvas background = new Canvas(WIDTH, HEIGHT);
        GraphicsContext backgroundGC = background.getGraphicsContext2D();
        backgroundGC.drawImage(new Image("Other/BG.png"), 0, 0);
        mainSceneGroup.getChildren().add(background);

        // main menu
        GridPane mainMenu = new GridPane();
        String gridPaneStyle = "-fx-background-color: transparent;" +
                "-fx-background-image: url('Other/Main_menu.png'); " +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;";
        mainMenu.setStyle(gridPaneStyle);
        mainMenu.setPrefWidth(545);
        mainMenu.setPrefHeight(750);
        mainMenu.setTranslateX(Game.WIDTH / 2 - 545 / 2);
        mainMenu.setTranslateY(10);
        mainSceneGroup.getChildren().add(mainMenu);


        Button b1 = new MenuButton("1");
        Button b2 = new MenuButton("2");
        Button b3 = new MenuButton("3");
        Button b4 = new MenuButton("4");
        Button b5 = new MenuButton("5");
        Button loadLevel = new MenuButton("Load Level");
        Button credits = new MenuButton("Credits");

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
        vbox.getChildren().addAll(hBox1, hBox2, loadLevel, credits);

        // add vertical box to the main menu
        mainMenu.getChildren().add(vbox);
    }



}
