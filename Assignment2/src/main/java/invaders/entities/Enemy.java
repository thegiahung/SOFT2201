package invaders.entities;

import invaders.engine.ConfigReader;
import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Enemy implements Moveable, Damagable, Renderable, Collider {
    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;

    private final double width = 25;
    private final double height = 30;
    private final Image image;
    private final String projectileType;
    private final double projectileXOffset = 10;
    private final double projectileYOffset = 30;
    private Projectile enemyProjectile;
    public boolean moveRight = true;
    private final double SPEED = 0.2;
    private double accelerator = 0;

    public Enemy(Vector2D position, String projectileType){
        this.image = new Image(new File("src/main/resources/enemy.png").toURI().toString(), width, height, false, true);
        this.position = position;
        this.projectileType = projectileType;
    }

    /**
     * Handle logic when get hit by projectile
     * @param amount
     */
    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    /**
     * @return the health of Enemy
     */
    @Override
    public double getHealth() {
        return this.health;
    }

    /**
     * @return if the bunkers still alive then return true
     */
    @Override
    public boolean isAlive() {
        return this.health > 0;
    }


    /**
     * move up
     */
    @Override
    public void up() {
        return;
    }

    /**
     * move down
     */
    @Override
    public void down() {
        this.position.setY(this.position.getY() + 10 + accelerator);
    }

    /**
     * move left
     */
    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1 - accelerator);
    }

    /**
     * move right
     */
    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1 + accelerator);
    }

    /**
     * move left or right depend on direction on variable moveRight
     */
    public void moveSideways() {
        if (moveRight) {
            right();
        } else {
            left();
        }
    }

    /**
     * shoot the projectile only if it is alive
     */
    public void shoot(){
        // Check if there is enemy still alive before shooting
        if (isAlive()) {
            enemyProjectile = ProjectileFactory.createProjectile(projectileType, new Vector2D(this.getPosition().getX() + projectileXOffset, this.getPosition().getY() + projectileYOffset), ProjectileOwner.ENEMY);
        }
    }

    /**
     * @return get the image of the enemy
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * @return give the width of the image
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * @return give the width of the image
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * @return give the position
     */
    @Override
    public Vector2D getPosition() {
        return position;
    }

    /**
     * @return give the Layer
     */
    @Override
    public Layer getLayer() {
        return Renderable.Layer.FOREGROUND;
    }

    /**
     * @return give the type of the enemy's projectile
     */
    public Projectile getEnemyProjectile() {
        return enemyProjectile;
    }

    /**
     * Set the enemy projectile
     * @param enemyProjectile
     */
    public void setEnemyProjectile(Projectile enemyProjectile) {
        this.enemyProjectile = enemyProjectile;
    }

    /**
     * @return boolean type to know whether the enemy hit the wall or not
     */
    public boolean shouldChangeDirection() {
        ConfigReader configReader = new ConfigReader();
        return (moveRight && this.getPosition().getX() + width >= configReader.getGameSizeX() - 1) ||
                (!moveRight && this.getPosition().getX() <= 1);
    }

    /**
     * Change the direction of moving
     */
    public void changeDirection() {
        moveRight = !moveRight;
    }

    /**
     * This function only call when enemy is killed by Player's projectile so it move faster
     */
    public void increaseSpeed() {
        accelerator += SPEED;
    }
}
