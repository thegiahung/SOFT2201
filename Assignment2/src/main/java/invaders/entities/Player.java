package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Player implements Moveable, Damagable, Renderable, Collider {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 3;

    private final double width = 25;
    private final double height = 30;
    private final Image image;
    private Projectile playerProjectile; // New variable to keep track of active projectile
    private final double projectileOffset = 10; // Adjust the position of projectile to make it look fit with the image

    public Player(Vector2D position){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = position;
        this.playerProjectile = null; // Initialize player projectile as null (hung)
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
     * @return the health of Player
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
     * Move up
     */
    @Override
    public void up() {
        return;
    }

    /**
     * Move down
     */
    @Override
    public void down() {
        return;
    }

    /**
     * Move left
     */
    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1);
    }

    /**
     * Move right
     */
    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1);
    }

    /**
     * Create the projectile to shoot
     */
    public void shoot(){
        // Check if there is no active player projectile
        if (playerProjectile == null && this.isAlive()) {
            // Create a Slow Straight Projectile at the player's position
            playerProjectile = ProjectileFactory.createProjectile("slow_straight", new Vector2D(this.getPosition().getX() + projectileOffset, this.getPosition().getY() - projectileOffset), ProjectileOwner.PLAYER);
        }
    }

    /**
     * @return get the image
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
     * @return get the projectile of the player
     */
    public Projectile getPlayerProjectile() {
        return playerProjectile;
    }

    /**
     * Set the projectile of player to a new type
     * @param playerProjectile
     */
    public void setPlayerProjectile(PlayerProjectile playerProjectile) {
        this.playerProjectile = playerProjectile;
    }

}
