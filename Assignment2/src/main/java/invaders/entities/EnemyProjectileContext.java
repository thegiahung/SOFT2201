package invaders.entities;

public class EnemyProjectileContext {
    private Projectile projectile;

    public static Projectile context(Projectile projectile) {
        return projectile;
    }
}
