import javafx.scene.paint.Color;

class Player extends GameObject {

    private final GameObject self = this;

    Player() {
        isEnemy = false;
        isActive = true;
        position_x = window_width / 2;
        setCenterX(position_x);
        double PADDING_BOTTOM = 20;
        position_y = window_height - getRadius() - PADDING_BOTTOM;
        setCenterY(position_y);
        setFill(Color.CYAN);
        velocity_x = 25;
        addGameObject(this);
        movementBehavior = new PlayerMovement();
    }

    void changeDirection(boolean input) {
        if (input && velocity_x < 0 || !input && velocity_x > 0) {
            velocity_x *= -1;
        }
    }

    void shoot() {
        root.getChildren().add(new Bullet(self));
    }

}
