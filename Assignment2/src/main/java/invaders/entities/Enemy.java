package invaders.entities;

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

    public Enemy(Vector2D position, String projectileType){
        this.image = new Image(new File("src/main/resources/enemy.png").toURI().toString(), width, height, false, true);
        this.position = position;
        this.projectileType = projectileType;
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
//        this.position.setX(this.position.getX() - 1);
    }

    @Override
    public void right() {
//        this.position.setX(this.position.getX() + 1);
    }

    public void shoot(){
        // Check if there is enemy still alive before shooting
        if (isAlive()) {
            enemyProjectile = ProjectileFactory.createProjectile(projectileType, new Vector2D(this.getPosition().getX() + projectileXOffset, this.getPosition().getY() + projectileYOffset), ProjectileOwner.ENEMY);
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
        return Renderable.Layer.FOREGROUND;
    }

    public Projectile getEnemyProjectile() {
        return enemyProjectile;
    }

    public void setEnemyProjectile(Projectile enemyProjectile) {
        this.enemyProjectile = enemyProjectile;
    }
}
