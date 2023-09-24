package invaders.entities;

import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class PlayerProjectile implements Projectile, Renderable, Moveable, Collider {
    private Vector2D position;
    private double speed;
    private final double width = 5;
    private final double height = 10;
    private final Image image;

    public PlayerProjectile(Vector2D position, double speed) {
        this.position = position;
        this.speed = speed;
        this.image = new Image(new File("src/main/resources/projectile.png").toURI().toString(), width, height, true, true);
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
     * Move up
     */
    @Override
    public void up() {
        this.getPosition().setY(this.getPosition().getY() - this.getSpeed());
    }

    /**
     * Move down
     */
    @Override
    public void down() {

    }

    /**
     * Move left
     */
    @Override
    public void left() {

    }

    /**
     * Move right
     */
    @Override
    public void right() {

    }

    /**
     * @return get the speed player projectile
     */
    public double getSpeed() {
        return speed;
    }
}
