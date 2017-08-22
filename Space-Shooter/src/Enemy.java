import javafx.scene.paint.Color;

class Enemy extends GameObject {

    Enemy() {
        isEnemy = true;
        isActive = true;
        movementBehavior = new EnemyMovement();
        setInitPositions();
        velocity_y = 1.75;
        setFill(Color.MAGENTA);
        addGameObject(this);
    }

    private void setInitPositions() {
        position_x = (Math.random() * (window_width - (4 * getRadius()))) + (2 * getRadius());
        position_y = ((Math.random() * -50 * getRadius()) + (-2 * getRadius()));
        setCenterX(position_x);
        setCenterY(position_y);
        setVisible(true);
    }

}
