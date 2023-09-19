package invaders.entities;
import invaders.physics.Vector2D;
public class ConcreteEnemyBuilder implements EnemyBuilder{
    private Vector2D position;
    private String projectileType;

    public ConcreteEnemyBuilder setPosition(Vector2D position) {
        this.position = position;
        return this;
    }

    public ConcreteEnemyBuilder setProjectileType(String projectileType) {
        this.projectileType = projectileType;
        return this;
    }

    public Enemy build() {
        return new Enemy(this.position, this.projectileType);
    }
}