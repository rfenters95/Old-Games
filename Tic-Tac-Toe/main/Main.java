package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/*
    Created By: Reed Fenters
    On 6 / 9 / 2015
*/

public class Main extends Application {

    public static GridPane gridPane;
    public static StackPane[] stackPanes = new StackPane[9];
    public static Rectangle[] rectangles = new Rectangle[9];
    public static Label label;
    public static final String TITLE = "Tic Tac Toe";
    public static final String END_GAME_STATUS = "Game Over";
    public static final String CLEAR = "";
    public static Stage mStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        mStage = primaryStage;

        AI.initGame();

        Scene scene = new Scene(gridPane);

        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {

                case UP:

                    AI.resetGame();

                    break;

                case DOWN:

                    AI.scoreBoard();

                    break;

                case SPACE:

                    AI.freshRestart();

                    break;

            }

        });

        primaryStage.setTitle(TITLE);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);

        primaryStage.show();

    }

}
