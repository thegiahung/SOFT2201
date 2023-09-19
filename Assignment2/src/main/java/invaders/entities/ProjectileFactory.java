package invaders.entities;

import invaders.physics.Vector2D;

public class ProjectileFactory {

    public static Projectile createProjectile(String type, Vector2D position, ProjectileOwner projectileOwner) {
        Projectile projectile = null;

        if (projectileOwner == ProjectileOwner.PLAYER) {
            return new PlayerProjectile(position, 1);
        }

        switch (type) {
            case "fast_straight":
//                projectile = new EnemyFastProjectile(position, 2);
                projectile = EnemyProjectileContext.context(new EnemyFastProjectile(position, 2));
                break;
            case "slow_straight":
//                projectile = new EnemySlowProjectile(position, 1);
                projectile = EnemyProjectileContext.context(new EnemySlowProjectile(position, 1));
                break;
        }
        return projectile;
    }
}

enum ProjectileOwner {ENEMY, PLAYER};
