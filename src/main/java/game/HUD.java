package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private final Font scoreFont = new Font("Segoe Script Bold", OBJECT_HEIGHT);
    private final Font messageFont = new Font("Segoe Script", 20);
    private Image hero1Small;
    private Image hero2Small;
    private String message;
    private double messageTimeLeft;

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

    public void draw(double delta) {
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
            gc.drawImage(hero2Small, Game.WIDTH - 15 - hero2Small.getWidth(), 8);

            int fullHearts = world.getHero2().getCurrentLives();
            int emptyHearts = world.getHero2().getMaxLives() - fullHearts;
            int currentHeart = 0;
            while (currentHeart < fullHearts) {
                gc.drawImage(heart, Game.WIDTH - 15 - hero2Small.getWidth() - 10 - heart.getWidth() - currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
            while (currentHeart < fullHearts + emptyHearts) {
                gc.drawImage(heartNone, Game.WIDTH - 15 - hero2Small.getWidth() - 10 - heart.getWidth() - currentHeart * (heart.getWidth() + 10), 10);
                currentHeart++;
            }
        }

        // score
        int score = world.getScore();
        int maxScore = world.getMaxScore();
        String scoreToDisplay = score + "/" + maxScore;


        // get text width
        Text scoreText = new Text(scoreToDisplay);
        scoreText.setFont(scoreFont);
        double textWidth = scoreText.getLayoutBounds().getWidth();

        gc.setFill(textColor);
        gc.setImageSmoothing(true);
        gc.setFont(scoreFont);
        gc.fillText(scoreToDisplay, (Game.WIDTH - (textWidth + 12 + star.getWidth())) / 2, OBJECT_HEIGHT);
        gc.drawImage(star, (Game.WIDTH - (textWidth + 12 + star.getWidth())) / 2 + textWidth + 12, 10);


        // message
        if (message != null && messageTimeLeft > 0) {
            messageTimeLeft -= delta;

            // get message width
            Text messageText = new Text(message);
            messageText.setFont(messageFont);
            double messageWidth = messageText.getLayoutBounds().getWidth();

            gc.setFill(messageOutlineColor);
            gc.fillRoundRect((Game.WIDTH - messageWidth - 22 - 4) / 2, 64, messageWidth + 22 + 4, 50, 15, 15);

            gc.setFill(messageBodyColor);
            gc.fillRoundRect((Game.WIDTH - messageWidth - 22) / 2, 64 + 2, messageWidth + 22, 50 - 4, 15, 15);

            gc.setFill(textColor);
            gc.setFont(messageFont);
            gc.fillText(message, (Game.WIDTH - messageWidth) / 2, 64 + 33);
        }
    }
}
