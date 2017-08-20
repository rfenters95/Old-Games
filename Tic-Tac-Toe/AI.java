package com.company;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AI {

    private static boolean statusGameOver = false;
    private static boolean displayScore = false;
    private static boolean[] moveValid = new boolean[9];
    private static boolean turnCount = true;
    private static final Player PLAYER_ONE = new Player(Color.RED);
    private static final Player PLAYER_TWO = new Player(Color.BLUE);
    private static int totalTurns = 1;
    private static final int SIZE = 150;
    private static final int GAP = 10;
    private static int drawGame = 0;

    public static void Action(int index) {

        Media soundEffect;
        MediaPlayer mediaPlayer;

        if (!statusGameOver && !moveValid[index]) {

            moveValid[index] = true;

            if (displayScore) {

                scoreBoard();

            }

            if (turnCount) {

                PLAYER_ONE.paintMove(index);

            } else {

                PLAYER_TWO.paintMove(index);

            }

            switchPlayer();

        } else {

            try {

                soundEffect = new Media(AI.class.getResource("/res/error.mp3").toString());

                mediaPlayer = new MediaPlayer(soundEffect);

                mediaPlayer.play();

            } catch (Exception e) {

                System.out.println("Invalid Move!");

            }

        }

    }

    public static void switchPlayer() {

        turnCount = (!turnCount);

        if (totalTurns == 9) {

            if (!PLAYER_ONE.playerWon && !PLAYER_TWO.playerWon) {

                endGame();

            }

        } else {

            totalTurns++;

        }

    }

    public static void resetGame() {

        Main.mStage.setTitle(Main.TITLE);

        Main.label.setText(Main.CLEAR);

        for (Rectangle r : Main.rectangles) {

            r.setFill(Color.IVORY);

        }

        statusGameOver = false;

        displayScore = false;

        for (int i = 0; i < 9; i++) {

            moveValid[i] = false;

            PLAYER_ONE.flushHistory();

            PLAYER_TWO.flushHistory();

        }

        turnCount = true;

        totalTurns = 1;

    }

    public static void endGame() {

        statusGameOver = true;

        if (PLAYER_ONE.playerWon) {

            Main.mStage.setTitle(Main.TITLE + " - Player 1 Wins");

            PLAYER_ONE.totalWins++;

        } else if (PLAYER_TWO.playerWon) {

            Main.mStage.setTitle(Main.TITLE + " - Player 2 Wins");

            PLAYER_TWO.totalWins++;

        } else {

            drawGame++;

            Main.mStage.setTitle(Main.TITLE + " - Draw(" + drawGame + ")");

        }

        Main.label.setText(Main.END_GAME_STATUS);

    }

    public static void scoreBoard() {

        if (!displayScore) {

            final String MESSAGE = "Player 1: " + PLAYER_ONE.totalWins + "\nPlayer 2: " + PLAYER_TWO.totalWins;

            Main.label.setText(MESSAGE);

        } else {

            Main.label.setText(Main.CLEAR);

            if (statusGameOver) {

                Main.label.setText(Main.END_GAME_STATUS);

            }
        }

        displayScore = (!displayScore);

    }

    public static void initGame() {

        Main.gridPane = new GridPane();

        Main.gridPane.setPadding(new Insets(GAP));

        Main.gridPane.setVgap(GAP);

        Main.gridPane.setHgap(GAP);

        Main.label = new Label("");

        Main.label.setTextFill(Color.GREY);

        for (int i = 0, r = 0; i < 9; r++) {

            for (int c = 0; c < 3; c++, i++) {

                Main.rectangles[i] = new Rectangle(SIZE, SIZE, Color.IVORY);

                Main.rectangles[i].setStroke(Color.BLACK);

                final int finalI = i;

                Main.rectangles[i].setOnMouseClicked(e -> Action(finalI));

                Main.stackPanes[i] = new StackPane(Main.rectangles[i]);

                Main.gridPane.getChildren().add(Main.stackPanes[i]);

                GridPane.setConstraints(Main.stackPanes[i], c, r);

            }

        }

        Main.stackPanes[4].getChildren().add(Main.label);

    }

    public static void resetScore() {

        PLAYER_ONE.totalWins = 0;

        PLAYER_TWO.totalWins = 0;

        drawGame = 0;

    }

    public static void freshRestart() {

        resetGame();

        resetScore();

    }

}
