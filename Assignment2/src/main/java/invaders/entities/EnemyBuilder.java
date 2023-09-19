package invaders.entities;

import invaders.physics.Vector2D;

public interface EnemyBuilder {
    EnemyBuilder setPosition(Vector2D position);
    EnemyBuilder setProjectileType(String projectileType);
    Enemy build();
}