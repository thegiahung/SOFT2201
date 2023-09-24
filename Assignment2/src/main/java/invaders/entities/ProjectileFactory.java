package invaders.entities;

import invaders.physics.Vector2D;

public class ProjectileFactory {

    /**
     * This is a factory method that return the correct type Projectile to the correct class
     * @param type
     * @param position
     * @param projectileOwner
     * @return Projectile
     */
    public static Projectile createProjectile(String type, Vector2D position, ProjectileOwner projectileOwner) {
        Projectile projectile = null;

        if (projectileOwner == ProjectileOwner.PLAYER) {
            return new PlayerProjectile(position, 1);
        }

        switch (type) {
            case "fast_straight":
                projectile = new EnemyFastProjectile(position, 2);
                break;
            case "slow_straight":
                projectile = new EnemySlowProjectile(position, 1);
                break;
        }
        return projectile;
    }
}

/**
 * This is enum where we know which type of projectile will return in Factory method
 */
enum ProjectileOwner {ENEMY, PLAYER};
