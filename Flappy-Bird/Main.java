import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    private final String TITLE = "Flappy Bird";
    private final double WINDOW_WIDTH = 320;
    private final int FALL_SPEED = 2;
    private final double WINDOW_HEIGHT = 480;
    private final long sleepTime = 10;
    private final double PIPE_SPACE = 150;
    private final int NUM_OF_RECT = 4;
    private final double RECT_WIDTH = 75;
    private final double JUMP_HEIGHT = 65;
    private double circleX, circleY, circleR, initCircleX = 50, initCircleY = 100;
    private Stage stage;
    private double rectX;
    private boolean isGameRunning, isGamePaused;
    private Circle circle;
    private boolean[] validRect = new boolean[NUM_OF_RECT];
    private Rectangle[] rectangles, invertRectangles;
    private double GAP_SPACE = 150;
    private Thread autoMagic;
    private Label scoreDisplay;
    private StackPane stack;
    private int score = 0, bestScore = 0;
    private Pane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new Pane();
        stage = primaryStage;
        scoreDisplay = new Label(String.valueOf(score));
        scoreDisplay.setTextFill(Color.WHITE);
        scoreDisplay.setFont(new Font(30));
        scoreDisplay.setAlignment(Pos.CENTER);
        stack = new StackPane(scoreDisplay);
        stack.setPadding(new Insets(10, 0, 0, 0));
        stack.setMinWidth(WINDOW_WIDTH);
        stack.setMaxWidth(WINDOW_WIDTH);
        circleX = initCircleX;
        circleY = initCircleY;
        circleR = 15;
        circle = new Circle(circleX, circleY, circleR, Color.GREENYELLOW);
        rectangles = new Rectangle[NUM_OF_RECT];
        invertRectangles = new Rectangle[NUM_OF_RECT];
        for (int i = 0; i < NUM_OF_RECT; i++) {
            validRect[i] = true;
            rectangles[i] = new Rectangle(RECT_WIDTH, randHeight(), Color.BLACK);
            invertRectangles[i] = new Rectangle(RECT_WIDTH, (WINDOW_HEIGHT - (rectangles[i].getHeight() + GAP_SPACE)), Color.BLACK);
            rectX = 300 + (i * (PIPE_SPACE + RECT_WIDTH));
            rectangles[i].setX(rectX);
            invertRectangles[i].setX(rectX);
            rectangles[i].setY(WINDOW_HEIGHT - rectangles[i].getHeight());
            invertRectangles[i].setY(0);
            pane.getChildren().add(rectangles[i]);
            pane.getChildren().add(invertRectangles[i]);
        }
        pane.getChildren().add(circle);
        pane.getChildren().add(stack);
        isGameRunning = true;
        pane.setStyle("-fx-background-color: #00CCFF");
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        autoMagic = initThread();
        autoMagic.start();
        scene.setOnMousePressed(e -> {
            if (isGameRunning) {
                circleY -= JUMP_HEIGHT;
                circle.setCenterY(circleY);
            }
        });
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    if (isGameRunning) {
                        playerJump();
                    }
                    break;
                case UP:
                    restart();
                    break;
                case ESCAPE:
                    isGameRunning = false;
            }
        });
        primaryStage.setTitle(TITLE);
        primaryStage.setOnCloseRequest(e -> killGame(primaryStage));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void playerJump() {
        circleY -= JUMP_HEIGHT;
        circle.setCenterY(circleY);
    }

    public void playerFall() {
        circleY += FALL_SPEED;
        circle.setCenterY(circleY);
    }

    public void collisionCheck(int index) {
        boolean top, left, right, ceiling, ground, bottom;
        bottom = circleY <= invertRectangles[index].getHeight();
        ceiling = (circleY <= 0);
        ground = (circleY >= WINDOW_HEIGHT);
        top = (circleY + circleR) >= rectangles[index].getY();
        left = (circleX + circleR) >= rectangles[index].getX();
        right = (circleX + circleR) <= (rectangles[index].getX() + rectangles[index].getWidth());
        if ((top && left && right) || (bottom && left && right) || ceiling || ground) {
            endGame();
        }
    }

    public void scoreCheck(int index) {
        double centRect = rectangles[index].getX() + (rectangles[index].getWidth() / 2);
        if (circleX > centRect && validRect[index]) {
            score++;
            validRect[index] = false;
            updateScore();
        }
    }

    public void updateScore() {
        scoreDisplay.setText(String.valueOf(score));
    }

    public void relocateRect(Rectangle r, int index) {
        if ((r.getX() + r.getWidth()) <= 0) {
            double x = (PIPE_SPACE * 4) + (RECT_WIDTH * 3);
            double h = randHeight();
            double y = WINDOW_HEIGHT - h;
            validRect[index] = true;
            r.setX(x);
            r.setHeight(h);
            r.setY(y);
            invertRectangles[index].setX(x);
            invertRectangles[index].setHeight(WINDOW_HEIGHT - (h + GAP_SPACE));
        }
    }

    public void resetPipes() {
        for (int i = 0; i < NUM_OF_RECT; i++) {
            rectX = 200 + (i * (PIPE_SPACE + RECT_WIDTH));
            rectangles[i].setX(rectX);
            rectangles[i].setY(WINDOW_HEIGHT - rectangles[i].getHeight());
        }
    }

    public void resetCircle() {
        circleX = initCircleX;
        circleY = initCircleY;
        circle.setCenterX(circleX);
        circle.setCenterY(circleY);
    }

    public double randHeight() {
        double h;
        do {
            h = new Random().nextInt((int) (WINDOW_HEIGHT * .75));
        } while (h < WINDOW_HEIGHT * .1);
        return h;
    }

    public Thread initThread() {
        return new Thread(() -> {
            while (isGameRunning) {
                Platform.runLater(() -> {
                    playerFall();
                    for (int i = 0; i < NUM_OF_RECT; i++) {
                        collisionCheck(i);
                        scoreCheck(i);
                        rectX = rectangles[i].getX();
                        rectX--;
                        rectangles[i].setX(rectX);
                        invertRectangles[i].setX(rectX);
                        relocateRect(rectangles[i], i);
                    }
                });
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            do {
                Platform.runLater(() -> {
                    stage.setTitle(TITLE + " - GAMEOVER");
                });
                circleY += 5;
                circle.setCenterY(circleY);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while ((circleY + circleR) <= WINDOW_HEIGHT);
            autoMagic = null;
        });
    }

    public void endGame() {
        isGameRunning = false;
        if (score > bestScore) {
            bestScore = score;
        }
        String msg = "Current: " + score + " | Best: " + bestScore;
        scoreDisplay.setText(msg);
    }

    public void restart() {
        resetCircle();
        Platform.runLater(() -> {
            stage.setTitle(TITLE);
        });
        score = 0;
        for (int i = 0; i < NUM_OF_RECT; i++) {
            validRect[i] = true;
        }
        updateScore();
        resetPipes();
        if (!isGameRunning) {
            isGameRunning = true;
        }
        if (autoMagic == null) {
            autoMagic = initThread();
            autoMagic.start();
        }
    }

    public void killGame(Stage stage) {
        endGame();
        stage.close();
    }
    
}
