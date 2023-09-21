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

    public void setState(BunkerState state) {
        this.currentState = state;
    }

    @Override
    public void takeDamage(double hits) {
        currentState.handleDamage();
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
        return Renderable.Layer.FOREGROUND;
    }

    public void setImage(String path) {
        this.image = new Image(new File(path).toURI().toString(), width, height, false, true);
        // Implement this method to set the bunker image
    }

    public BunkerState getCurrentState() {
        return currentState;
    }
}
