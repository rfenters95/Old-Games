import javafx.scene.paint.Color;

public class BulletMovement implements MovementBehavior {

    @Override
    public void move(GameObject gameObject) {
        gameObject.position_y -= gameObject.velocity_y;
        if (gameObject.position_y + gameObject.getRadius() >= 0) {
            gameObject.setCenterY(gameObject.position_y);
        } else {
            gameObject.isActive = false;
            gameObject.setFill(Color.BLACK);
        }
    }

}
