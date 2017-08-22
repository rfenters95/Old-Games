import javafx.scene.paint.Color;

class Bullet extends GameObject {

    Bullet(GameObject parent) {
        isEnemy = false;
        isAlly = true;
        isActive = true;
        movementBehavior = new BulletMovement();
        position_x = parent.position_x;
        position_y = parent.position_y;
        setCenterX(position_x);
        setCenterY(position_y);
        setRadius(5);
        velocity_y = 10;
        setFill(Color.YELLOWGREEN);
        addGameObject(this);
    }

}
