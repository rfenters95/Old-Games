import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GameObject extends Circle {

    static double window_width, window_height;
    static Pane root;
    static Thread thread;
    static Collection<GameObject> gameObjects = new ArrayList<>();
    double position_x, position_y;
    boolean isEnemy;
    boolean isAlly; // Only used to keep bullet moving
    boolean isActive;
    double velocity_x, velocity_y;
    MovementBehavior movementBehavior;
    private double[] edges = new double[4];

    public GameObject() {
        setRadius(15);
    }

    private static void destroyObject(GameObject o) {
        root.getChildren().remove(o);
        gameObjects.remove(o);
    }

    static void getInfo(Pane pane, double width, double height) {
        root = pane;
        window_width = width;
        window_height = height;
    }

    void collisionDetection(GameObject currentGameObject) {
        if (currentGameObject.isEnemy) {
            currentGameObject.setEdgesEnemy(currentGameObject);
        } else {
            currentGameObject.setEdgesAlly(currentGameObject);
        }
        for (GameObject nextObject : gameObjects) {
            if (nextObject.isEnemy) {
                nextObject.setEdgesEnemy(nextObject);
            } else {
                nextObject.setEdgesAlly(nextObject);
            }
            if (((currentGameObject.isEnemy && !nextObject.isEnemy) || (!currentGameObject.isEnemy && nextObject.isEnemy)) && currentGameObject.isActive && nextObject.isActive) {
                zoneDetection(currentGameObject, nextObject);
            }
        }
    }

    private void setEdgesEnemy(GameObject gameObject) {
        gameObject.edges[0] = position_x - getRadius(); // left
        gameObject.edges[1] = position_x + getRadius(); // right
        gameObject.edges[2] = position_y - getRadius(); // top
        gameObject.edges[3] = position_y + getRadius(); // bottom
    }

    private void setEdgesAlly(GameObject gameObject) {
        gameObject.edges[0] = position_x - getRadius(); // left
        gameObject.edges[1] = position_x + getRadius(); // right
        gameObject.edges[2] = position_y + getRadius(); // top
        gameObject.edges[3] = position_y - getRadius(); // bottom
    }

    private void zoneDetection(GameObject current, GameObject next) {
        boolean x_conflict = false, y_conflict = false;
        // this is problem
        if ((current.edges[0] <= next.edges[0] && current.edges[1] >= next.edges[0]) || ((current.edges[1] >= next.edges[1] && current.edges[0] <= next.edges[1])))
            x_conflict = true;
        // (c_top is higher up than n_top AND c_bottom is lower than n_top) OR (c_bottom is lower than n_bottom AND c_top is higher than n_top) OR (c_top is lower than n_top AND c_top is higher than n_bottom)
        if ((current.edges[2] <= next.edges[2] && current.edges[3] >= next.edges[2]) || (current.edges[3] >= next.edges[3] && current.edges[2] <= next.edges[2]) || (current.edges[2] >= next.edges[2] && current.edges[2] <= next.edges[3]))
            y_conflict = true;
        if (x_conflict && y_conflict) {
            // This works, but it creates new problem(s)!
            Platform.runLater(() -> {
                destroyObject(current);
                destroyObject(next);
            });

            /*

            // This is the old way

            current.setVisible(false);

            next.setVisible(false);

            current.isActive = false;

            next.isActive = false;

            */
        }
    }

    void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

}
