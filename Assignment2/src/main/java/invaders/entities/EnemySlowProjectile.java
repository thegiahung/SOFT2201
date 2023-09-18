package invaders.entities;

import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class EnemySlowProjectile implements Projectile, Renderable, Moveable, Collider {
    private Vector2D position;
    private double speed;
    private final double width = 5;
    private final double height = 10;
    private Image image;

    public EnemySlowProjectile(Vector2D position, double speed) {
        this.position = position;
        this.speed = speed;
        this.image = new Image(new File("src/main/resources/projectile.png").toURI().toString(), width, height, true, true);
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

    @Override
    public void up() {

    }

    @Override
    public void down() {
        this.getPosition().setY(this.getPosition().getY() + this.getSpeed());
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    public double getSpeed() {
        return speed;
    }
}
