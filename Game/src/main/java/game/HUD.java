package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.ResourceUtils;
import utilsAPI.Constants;

public class HUD {
    private final int OBJECT_HEIGHT = 46;
    private final GraphicsContext gc;
    private final World world;

    private final Image heart;
    private final Image heartNone;
    private final Image star;
    private final Color textColor = Color.BLACK;
    private final Color messageOutlineColor = Color.rgb(89, 61, 37);
    private final Color messageBodyColor = Color.rgb(187, 200, 126);
    private final Font scoreFont = Font.loadFont(ResourceUtils.getResource("Fonts/SegoeScriptBold.ttf"), OBJECT_HEIGHT);
    private final Font messageFont = Font.loadFont(ResourceUtils.getResource("Fonts/SegoeScript.ttf"), 20);
    private Image hero1Small;
    private Image hero2Small;
    private String message;
    private double messageTimeLeft;

    /**
     * @param g Graphics context
     * @param w World in which the level happens
     */
    public HUD(GraphicsContext g, World w) {
        gc = g;
        world = w;
        heart = new Image("Other/Heart.png", 0, OBJECT_HEIGHT, true, true);
        heartNone = new Image("Other/Heart_none.png", 0, OBJECT_HEIGHT, true, true);
        star = new Image("Other/StarHUD.png", 0, OBJECT_HEIGHT - 6, true, true);
        if (world.getHero1() != null) {
            hero1Small = new Image(world.getHero1().getHUDImageSource(), 0, OBJECT_HEIGHT + 4, true, true);
        }
        if (world.getHero2() != null) {
            hero2Small = new Image(world.getHero2().getHUDImageSource(), 0, OBJECT_HEIGHT + 4, true, true);
        }
    }

    /**
     * Set a message to be displayed for specified time.
     * @param msg message
     * @param time time (in second) how long the message will be displayed
     */
    public void setMessage(String msg, double time) {
        message = msg;
        messageTimeLeft = time;
    }

    /**
     * @return Message that is displayed
     */
    public String getMessage() {
        if (message == null || messageTimeLeft <= 0) {
            return "";
        } else {
            return message;
        }
    }

    /**
     *
     * @return how long (in seconds) the message will be displayed
     */
    public double getMessageTimeLeft() {
        return messageTimeLeft;
    }

    /**
     * Draw whole HUD.
     * @param delta how long it's been since the last call of the method
     */
    public void draw(double delta) {
        drawHeroes();
        drawScore();
        drawMessage(delta);
    }

    /**
     * Draw heroes with their lives in HUD.
     */
    private void drawHeroes() {
        // draw hero1 bar
        if (world.getHero1() != null) {
            gc.drawImage(hero1Small, 15, 8);

            int fullHearts = world.getHero1().getCurrentLives();
            int emptyHearts = world.getHero1().getMaxLives() - fullHearts;
            int currentHeart = 0;
            while (currentHeart < fullHearts) {
                gc.drawImage(heart, 15 + hero1Small.getWidth() + 10 + currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
            while (currentHeart < fullHearts + emptyHearts) {
                gc.drawImage(heartNone, 15 + hero1Small.getWidth() + 10 + currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
        }

        // draw hero2 bar
        if (world.getHero2() != null) {
            gc.drawImage(hero2Small, Constants.WIDTH - 15 - hero2Small.getWidth(), 8);

            int fullHearts = world.getHero2().getCurrentLives();
            int emptyHearts = world.getHero2().getMaxLives() - fullHearts;
            int currentHeart = 0;
            while (currentHeart < fullHearts) {
                gc.drawImage(heart, Constants.WIDTH - 15 - hero2Small.getWidth() - 10 - heart.getWidth() - currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
            while (currentHeart < fullHearts + emptyHearts) {
                gc.drawImage(heartNone, Constants.WIDTH - 15 - hero2Small.getWidth() - 10 - heart.getWidth() - currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
        }
    }

    /**
     * Draw current/max number of stars in HUD.
     */
    private void drawScore() {
        // score
        int score = world.getScore();
        int maxScore = world.getMaxScore();
        String scoreToDisplay = score + "/" + maxScore;

        // get text width
        Text scoreText = new Text(scoreToDisplay);
        scoreText.setFont(scoreFont);
        double textWidth = scoreText.getLayoutBounds().getWidth();

        gc.setFill(textColor);
        gc.setFont(scoreFont);
        gc.fillText(scoreToDisplay, (Constants.WIDTH - (textWidth + 12 + star.getWidth())) / 2, OBJECT_HEIGHT);
        gc.drawImage(star, (Constants.WIDTH - (textWidth + 12 + star.getWidth())) / 2 + textWidth + 12, 10);
    }

    /**
     * Draw message in HUD if there is any current message to display.
     *
     * @param delta time in seconds since the last update
     */
    private void drawMessage(double delta) {
        if (message != null && messageTimeLeft > 0) {
            messageTimeLeft -= delta;

            // get message width
            Text messageText = new Text(message);
            messageText.setFont(messageFont);
            double messageWidth = messageText.getLayoutBounds().getWidth();

            gc.setFill(messageOutlineColor);
            gc.fillRoundRect((Constants.WIDTH - messageWidth - 22 - 4) / 2, 64, messageWidth + 22 + 4, 50, 15, 15);

            gc.setFill(messageBodyColor);
            gc.fillRoundRect((Constants.WIDTH - messageWidth - 22) / 2, 64 + 2, messageWidth + 22, 50 - 4, 15, 15);

            gc.setFill(textColor);
            gc.setFont(messageFont);
            gc.fillText(message, (Constants.WIDTH - messageWidth) / 2, 64 + 33);
        }
    }
}
