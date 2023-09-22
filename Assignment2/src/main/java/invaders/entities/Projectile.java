package invaders.entities;

import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

public interface Projectile extends Renderable, Collider, Moveable {
}
