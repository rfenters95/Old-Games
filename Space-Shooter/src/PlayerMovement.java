public class PlayerMovement implements MovementBehavior {

    @Override
    public void move(GameObject gameObject) {
        if (((gameObject.position_x < (GameObject.window_width - (2 * gameObject.getRadius()))) && gameObject.velocity_x > 0) || ((gameObject.position_x > (2 * gameObject.getRadius())) && gameObject.velocity_x < 0)) {
            gameObject.position_x += gameObject.velocity_x;
            gameObject.setCenterX(gameObject.position_x);
        }
    }

}
