public class EnemyMovement implements MovementBehavior {

    @Override
    public void move(GameObject gameObject) {
        gameObject.position_y += gameObject.velocity_y;
        if (gameObject.position_y + gameObject.getRadius() <= GameObject.window_height) {
            gameObject.setCenterY(gameObject.position_y);
        } else {
            if (gameObject.isActive) {
                AI.injurePlayer();
                gameObject.isActive = false;
            }
        }
    }

}
