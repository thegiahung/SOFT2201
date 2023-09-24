package invaders.entities;

import invaders.physics.Vector2D;

public interface EnemyBuilder {
    /**
     * @param position
     * @return EnemyBuilder class
     */
    EnemyBuilder setPosition(Vector2D position);

    /**
     * @param projectileType
     * @return EnemyBuilder class
     */
    EnemyBuilder setProjectileType(String projectileType);

    /**
     * @return give the Enemy object
     */
    Enemy build();
}