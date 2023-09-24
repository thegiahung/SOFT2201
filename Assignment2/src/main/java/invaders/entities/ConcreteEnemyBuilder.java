package invaders.entities;
import invaders.physics.Vector2D;
public class ConcreteEnemyBuilder implements EnemyBuilder{
    private Vector2D position;
    private String projectileType;

    /**
     * @param position
     * @return get the ConcreateEnemyBuilder class which will be utilized later for creating Enemy
     */
    public ConcreteEnemyBuilder setPosition(Vector2D position) {
        this.position = position;
        return this;
    }

    /**
     * @param projectileType
     * @return get the ConcreateEnemyBuilder class which will be utilized later for creating Enemy
     */
    public ConcreteEnemyBuilder setProjectileType(String projectileType) {
        this.projectileType = projectileType;
        return this;
    }

    /**
     * @return give the Enemy object
     */
    public Enemy build() {
        return new Enemy(this.position, this.projectileType);
    }
}