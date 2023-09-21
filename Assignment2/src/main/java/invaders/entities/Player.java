package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Player implements Moveable, Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;

    private final double width = 25;
    private final double height = 30;
    private final Image image;
    private Projectile playerProjectile; // New variable to keep track of active projectile
    private final double projectileOffset = 10;

    public Player(Vector2D position){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = position;
        this.playerProjectile = null; // Initialize player projectile as null (hung)
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1);
    }

    public void shoot(){
        // todo
        // Check if there is no active player projectile
        if (playerProjectile == null && this.isAlive()) {
            // Create a Slow Straight Projectile at the player's position
            playerProjectile = ProjectileFactory.createProjectile("slow_straight", new Vector2D(this.getPosition().getX() + projectileOffset, this.getPosition().getY() - projectileOffset), ProjectileOwner.PLAYER);
            // Add the projectile to gameobjects list in GameEngine

        }
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public Projectile getPlayerProjectile() {
        return playerProjectile;
    }

    public void setPlayerProjectile(PlayerProjectile playerProjectile) {
        this.playerProjectile = playerProjectile;
    }
}
