package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class Bunkers implements Damagable, Renderable, Collider {
    private final Vector2D position;
    private final Animator anim = null;
    private double health;
    private double width;
    private double height;
    private Image image;
    private BunkerState currentState;

    public Bunkers(Vector2D position, double width, double height){
        this.width = width;
        this.height = height;
        this.image = new Image(new File("src/main/resources/bunker.png").toURI().toString(), width, height, false, true);
        this.position = position;
        this.currentState = new GreenBunkerState(this);
    }

    /**
     * Chnage the current state color of the bunker
     * @param state
     */
    public void setState(BunkerState state) {
        this.currentState = state;
    }

    /**
     * Handle logic when get hit by projectile
     * @param hits
     */
    @Override
    public void takeDamage(double hits) {
        currentState.handleDamage();
    }

    /**
     * @return the health of Bunkers
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
     * @return get the image of the bunkers
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * @return give the width of the bunkers image
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * @return give the height of the bunkers image
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * @return give the position of bunker in x-y co-ordinate
     */
    @Override
    public Vector2D getPosition() {
        return position;
    }

    /**
     * @return get the layer for
     */
    @Override
    public Layer getLayer() {
        return Renderable.Layer.FOREGROUND;
    }

    /**
     * Set the image for the bunker
     * @param path
     */
    // Set the bunker image
    public void setImage(String path) {
        this.image = new Image(new File(path).toURI().toString(), width, height, false, true);
    }

    /**
     * @return get the current state of the bunker
     */
    public BunkerState getCurrentState() {
        return currentState;
    }
}
