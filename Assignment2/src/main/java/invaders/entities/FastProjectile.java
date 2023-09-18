package invaders.entities;

import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class FastProjectile implements Projectile, Renderable, Moveable, Collider {
    private Vector2D position;
    private double speed;
    private final double width = 25;
    private final double height = 30;
    private final Image image;
//    private double damage;

    public FastProjectile(Vector2D position, double speed) {
        this.position = position;
        this.speed = speed;
        this.image = new Image(new File("src/main/resources/projectile.png").toURI().toString(), width, height, true, true);
//        this.damage = damage;
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
        this.getPosition().setY(this.getPosition().getY() - this.getSpeed());
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        return;
    }

    @Override
    public void right() {
        return;
    }

    public double getSpeed() {
        return speed;
    }
}
