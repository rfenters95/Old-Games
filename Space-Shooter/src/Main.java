import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    static final int NUM_OF_ENEMIES = 25;
    static Player player;
    private final double WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage, getScene(getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT));
        AI.displayHealth();
    }

    private Parent getRoot() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color:Black;");
        GameObject.getInfo(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        player = new Player();
        Enemy[] enemies = new Enemy[NUM_OF_ENEMIES];
        for (int i = 0; i < NUM_OF_ENEMIES; i++) {
            enemies[i] = new Enemy();
            root.getChildren().add(enemies[i]);
        }
        root.getChildren().add(player);
        return root;
    }

    private Scene getScene(Parent root, double width, double height) {
        Scene scene = new Scene(root, width, height);
        GameObject.thread = new Thread(new AI());
        GameObject.thread.start();
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    player.shoot();
                    break;
                case LEFT:
                    player.changeDirection(false);
                    player.movementBehavior.move(player);
                    break;
                case RIGHT:
                    player.changeDirection(true);
                    player.movementBehavior.move(player);
                    break;
                case UP:
                    AI.resetGame();
                    break;
            }
        });
        return scene;
    }

    private void setStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Space Shooter");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> shutdownApp(primaryStage));
        primaryStage.show();
    }

    private void shutdownApp(Stage primaryStage) {
        AI.isRunning = false;
        GameObject.thread = null;
        primaryStage.close();
    }

}
