package damian.myjavagame.game;

import damian.myjavagame.plugin.api.utils.Constants;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    /**
     * Launch the damian.myjavagame.game.
     * @param args args, don't put anything here
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize stuff and start a GameLoop.
     * @param stage Stage in which the damian.myjavagame.game happens
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Leaping Elements");

        Group mainSceneGroup = new Group();
        Scene mainScene = new Scene(mainSceneGroup, Constants.WIDTH, Constants.HEIGHT);
        List<MenuButton> buttonsToFurtherInitialize = initializeMainScene(mainSceneGroup);

        Group gameSceneGroup = new Group();
        Scene gameScene = new Scene(gameSceneGroup, Constants.WIDTH, Constants.HEIGHT);
        Canvas gameCanvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        gameSceneGroup.getChildren().add(gameCanvas);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        stage.setScene(mainScene);
        new GameLoop(gc, stage, mainScene, gameScene, buttonsToFurtherInitialize).start();
        stage.show();
    }

    /**
     * Initialize objects on main scene.
     * @param mainSceneGroup Group to which we add containers
     * @return level buttons + load level button because they need to be initialize later
     */
    private List<MenuButton> initializeMainScene(Group mainSceneGroup) {
        initializeBackground(mainSceneGroup);
        GridPane mainMenu = initializeMainMenu(mainSceneGroup);

        MenuButton b1 = new MenuButton("1");
        MenuButton b2 = new MenuButton("2");
        MenuButton b3 = new MenuButton("3");
        MenuButton b4 = new MenuButton("4");
        MenuButton b5 = new MenuButton("5");
        MenuButton loadLevelButton = new MenuButton("Load Level");
        MenuButton loadPluginButton = new MenuButton("Load Plugin");

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
        vbox.getChildren().addAll(hBox1, hBox2, loadLevelButton, loadPluginButton);

        // add vertical box to the main menu
        mainMenu.getChildren().add(vbox);

        // return level buttons + load level button because they need to be initialize later
        ArrayList<MenuButton> res = new ArrayList<>();
        res.add(b1);
        res.add(b2);
        res.add(b3);
        res.add(b4);
        res.add(b5);
        res.add(loadLevelButton);
        res.add(loadPluginButton);

        return res;
    }

    private void initializeBackground(Group mainSceneGroup) {
        Canvas background = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        GraphicsContext backgroundGC = background.getGraphicsContext2D();
        backgroundGC.drawImage(new Image("Other/BG.png"), 0, 0);
        mainSceneGroup.getChildren().add(background);
    }

    private GridPane initializeMainMenu(Group mainSceneGroup) {
        GridPane mainMenu = new GridPane();
        String mainMenuStyle = "-fx-background-color: transparent;" +
                "-fx-background-image: url('Other/Main_menu.png'); " +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;";
        mainMenu.setStyle(mainMenuStyle);
        mainMenu.setPrefWidth(545);
        mainMenu.setPrefHeight(750);
        mainMenu.setTranslateX(Constants.WIDTH / 2 - 545 / 2);
        mainMenu.setTranslateY(10);
        mainSceneGroup.getChildren().add(mainMenu);

        return mainMenu;
    }
}
