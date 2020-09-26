package game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import utils.ResourceUtils;

public class MenuButton extends Button {
    private int preferredWidth = 100;
    private int preferredHeight = 100;

    private final Font biggerFont = Font.loadFont(ResourceUtils.getResource("Fonts/SegoePrint.ttf"), 35);

    private String buttonReleasedStyle = "-fx-background-color: transparent;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-position: center;";
    private String buttonPressedStyle = "-fx-background-color: transparent;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-position: center;";


    /**
     *
     * @param text Text to be displayed on the button
     */
    public MenuButton(String text) {
        setAlignment(Pos.CENTER);
        setFont(biggerFont);

        if (text.equals("Load Level")) {
            setText(ResourceUtils.getLocalisedText("LoadLevel"));
            preferredWidth = 360;
            preferredHeight = 100;
            buttonReleasedStyle = buttonReleasedStyle.concat("-fx-background-image: url('Other/Load_level_button.png'); " +
                    "-fx-background-size: 360px 100px;");
            buttonPressedStyle = buttonPressedStyle.concat("-fx-background-image: url('Other/Load_level_button_hover.png'); " +
                    "-fx-background-size: 360px 100px;");
        } else if (text.equals("Load Plugin")) {
            setText(ResourceUtils.getLocalisedText("LoadPlugin"));
            preferredWidth = 360;
            preferredHeight = 100;
            buttonReleasedStyle = buttonReleasedStyle.concat("-fx-background-image: url('Other/Credits_button.png'); " +
                    "-fx-background-size: 360px 100px;");
            buttonPressedStyle = buttonPressedStyle.concat("-fx-background-image: url('Other/Credits_button_hover.png'); " +
                    "-fx-background-size: 360px 100px;");
        } else {
            setText(text);
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

    /**
     * Set the style of the pressed button.
     */
    private void setButtonPressedStyle() {
        setStyle(buttonPressedStyle);
        setTranslateY(getTranslateY() + 1);
    }

    /**
     * Set the style of the released (normal) button.
     */
    protected void setButtonReleasedStyle() {
        setStyle(buttonReleasedStyle);
        setTranslateY(getTranslateY() - 1);
    }

    /**
     * Initialize behavior when the user hovers or clicks the button.
     */
    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(event -> setEffect(new DropShadow()));

        setOnMouseExited(event -> setEffect(null));
    }

}
