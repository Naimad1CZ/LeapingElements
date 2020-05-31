package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class MenuButton extends Button {
    private int preferredWidth = 100;
    private int preferredHeight = 100;

    private final Font biggerFont = new Font("Segoe Print", 35);

    private String buttonReleasedStyle = "-fx-background-color: transparent;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-position: center;";
    private String buttonPressedStyle = "-fx-background-color: transparent;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-position: center;";

    public MenuButton(String text) {
        setText(text);
        setAlignment(Pos.CENTER);
        setFont(biggerFont);

        if (text.equals("Load Level")) {
            preferredWidth = 360;
            preferredHeight = 100;
            buttonReleasedStyle = buttonReleasedStyle.concat("-fx-background-image: url('Other/Load_level_button.png'); " +
                    "-fx-background-size: 360px 100px;");
            buttonPressedStyle = buttonPressedStyle.concat("-fx-background-image: url('Other/Load_level_button_hover.png'); " +
                    "-fx-background-size: 360px 100px;");
        } else if (text.equals("Credits")) {
            preferredWidth = 360;
            preferredHeight = 100;
            buttonReleasedStyle = buttonReleasedStyle.concat("-fx-background-image: url('Other/Credits_button.png'); " +
                    "-fx-background-size: 360px 100px;");
            buttonPressedStyle = buttonPressedStyle.concat("-fx-background-image: url('Other/Credits_button_hover.png'); " +
                    "-fx-background-size: 360px 100px;");
        } else {
            // we have a level number
            preferredWidth = 100;
            preferredHeight = 100;
            buttonReleasedStyle = buttonReleasedStyle.concat("-fx-background-image: url('Other/Level_button.png'); " +
                    "-fx-background-size: 100px 100px;");
            buttonPressedStyle = buttonPressedStyle.concat("-fx-background-image: url('Other/Level_button_hover.png'); " +
                    "-fx-background-size: 100px 100px;");
        }

        setPrefWidth(preferredWidth);
        setPrefHeight(preferredHeight);
        setStyle(buttonReleasedStyle);

        initializeButtonListeners();
    }

    private void setButtonPressedStyle() {
        setStyle(buttonPressedStyle);
        setTranslateY(getTranslateY() + 1);
    }

    protected void setButtonReleasedStyle() {
        setStyle(buttonReleasedStyle);
        setTranslateY(getTranslateY() - 1);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }

}
