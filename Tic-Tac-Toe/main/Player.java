package main;

import javafx.scene.paint.Color;

public class Player {

    private boolean[] playerHistory;
    private Color color;
    public boolean playerWon = false;
    public int totalWins = 0;

    public Player(Color color) {

        this.color = color;

        playerHistory = new boolean[9];

    }

    public void paintMove(int index) {

        Main.rectangles[index].setFill(color);

        playerHistory[index] = true;

        isWinner();

    }

    public void isWinner() {

        boolean methodOne = (playerHistory[0] && playerHistory[1] && playerHistory[2]);

        methodOne = (methodOne || (playerHistory[3] && playerHistory[4] && playerHistory[5]));

        methodOne = (methodOne || (playerHistory[6] && playerHistory[7] && playerHistory[8]));

        boolean methodTwo = (playerHistory[0] && playerHistory[3] && playerHistory[6]);

        methodTwo = (methodTwo || (playerHistory[1] && playerHistory[4] && playerHistory[7]));

        methodTwo = (methodTwo || (playerHistory[2] && playerHistory[5] && playerHistory[8]));

        boolean methodThree = (playerHistory[0] && playerHistory[4] && playerHistory[8]);

        methodThree = (methodThree || (playerHistory[2] && playerHistory[4] && playerHistory[6]));

        if (methodOne || methodTwo || methodThree) {

            playerWon = true;

            AI.endGame();

        }

    }

    public void flushHistory() {

        for (int i = 0; i < 9; i++) {

            playerHistory[i] = false;

        }

        playerWon = false;

    }


}
