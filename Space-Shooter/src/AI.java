class AI implements Runnable {

    private static final int MAX_HEALTH = 3;
    private static boolean isPaused;
    private static int health = MAX_HEALTH;
    static boolean isRunning;

    AI() {
        isRunning = true;
        isPaused = false;
    }

    static void displayHealth() {
        System.out.println("\nHealth: " + health);
    }

    static void resetGame() {
        AI.health = MAX_HEALTH;
        AI.displayHealth();
        wipeGameObjects();
        setEnemies(Main.NUM_OF_ENEMIES);
        AI.isRunning = true;
    }

    private static void wipeGameObjects() {
        for (GameObject g : GameObject.gameObjects) {
            GameObject.root.getChildren().remove(g);
        }
        GameObject.gameObjects.clear();
    }

    private static void setEnemies(int num) {
        if (GameObject.gameObjects.isEmpty()) {
            Main.player = new Player();
            GameObject.root.getChildren().add(Main.player);
            for (int i = 0; i < num; i++) {
                GameObject.root.getChildren().add(new Enemy());
            }
        }
    }

    static void injurePlayer() {
        if (health > 0) {
            health--;
            displayHealth();
        } else {
            endGame();
        }
    }

    private static void endGame() {
        isRunning = false;
        System.out.println("You lose!");
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (!isPaused) {
                    for (GameObject gameObject : GameObject.gameObjects) {
                        if (gameObject.isEnemy || gameObject.isAlly) {
                            gameObject.movementBehavior.move(gameObject);
                        }
                        gameObject.collisionDetection(gameObject);
                    }
                }
                long sleepDuration = 25;
                Thread.sleep(sleepDuration);
            }
        } catch (Exception ignored) {
        }
    }

}
