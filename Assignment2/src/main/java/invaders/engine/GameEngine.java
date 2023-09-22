package invaders.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import invaders.GameObject;
import invaders.entities.*;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;

	private Player player;
	private boolean left;
	private boolean right;
	private ConfigReader configReader = new ConfigReader().parse("src/main/resources/config.json");
	private List<Enemy> enemies;
	public int enemyProjectileCount = 0; // only 3 enemies' projectile allow on screen

	public GameEngine(String config){
		// read the config here
		// Read configuration (Hung's do)
		// Retrieve player section
		long playerSpeed = configReader.getPlayerSpeed();
		long playerLives = configReader.getPlayerLives();
		String playerColor = configReader.getPlayerColour();
		long playerPosX = configReader.getPlayerPositionX();
		long playerPosY = configReader.getPlayerPositionY();

		// Retrieve enemy section in a list
		enemies = configReader.readEnemyData();

		// Retrieve bunkers section in a list
		List<Bunkers> bunkers = new ArrayList<>();
		bunkers = configReader.readBunkerData();

		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();

		// Create enemies
		for (int i = 0; i < enemies.size(); i++) {
			renderables.add(enemies.get(i));
		}
		// Create bunkers
		for (int i = 0; i < bunkers.size(); i++) {
			renderables.add(bunkers.get(i));
		}
		// Create player
		player = new Player(new Vector2D(playerPosX, playerPosY));
		renderables.add(player);
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		// Extract json file for game size
		int gameSizeX = configReader.getGameSizeX();
		int gameSizeY = configReader.getGameSizeY();

		movePlayer();
		moveEnemies();

		for(GameObject go: gameobjects){
			go.update();
		}

		// Add enemies projectile to renderables list
		// Only three projectile available at a time
		// It need to be in the renderables list to make a shot
		addEnemyProjectile();

		// Add playerProjectile to renderables if it exists
		addPlayerProjectile();

		// If player's projectile or enemy's projectile go out of the screen/collide, then delete it
		deleteProjectile();

		// Delete Bunker/Enemy once hit projectile
		deleteObjectGame();

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			
			if (!(ro instanceof Projectile)) {
				if(ro.getPosition().getX() + ro.getWidth() >= gameSizeX) {
					ro.getPosition().setX((gameSizeX-1)-ro.getWidth());
				}

				if(ro.getPosition().getX() <= 0) {
					ro.getPosition().setX(1);
				}

				if(ro.getPosition().getY() + ro.getHeight() >= gameSizeY) {
					ro.getPosition().setY((gameSizeY-1)-ro.getHeight());
				}

				if(ro.getPosition().getY() <= 0) {
					ro.getPosition().setY(1);
				}
			}
		}
	}

	/**
	 * Add Enemy's projectile
	 */
	private void addEnemyProjectile() {
		if (enemyProjectileCount < 3) {
			int index = new Random().nextInt(enemies.size());
			if (renderables.contains(enemies.get(index))) {
				enemies.get(index).shoot();
				renderables.add(enemies.get(index).getEnemyProjectile());
				enemyProjectileCount += 1;
			}
		}
	}

	/**
	 * Delete Bunker/Enemy once it hit projectile
	 */
	private void deleteObjectGame() {
		List<Renderable> roToDelete = new ArrayList<>();
		Bunkers bunker_remove = null;
		for (Renderable ro1 : renderables) {
			if (ro1 instanceof Projectile projectile) {
				for (Renderable ro2 : renderables) {
					if (ro2 instanceof Bunkers bunkers) {
						if (bunkers.isColliding(projectile)) {
							roToDelete.add(ro1);
							player.setPlayerProjectile(null);
							bunker_remove = bunkers;
						}
					}

					if (ro2 instanceof Enemy enemy && projectile instanceof PlayerProjectile) {
						if (enemy.isColliding(projectile)) {
							roToDelete.add(ro2);
						}
					}
					if (ro2 instanceof  Player player && projectile instanceof EnemyFastProjectile && projectile instanceof EnemySlowProjectile) {
						if (player.isColliding(projectile)) {
							roToDelete.add(ro2);
						}
					}
				}
			}
		}

		for (Renderable ro1 : renderables) {
			if (ro1 instanceof Enemy enemy) {
				for (Renderable ro2 : renderables) {
					if (ro2 instanceof Bunkers bunkers) {
						if (bunkers.isColliding(enemy)) {
							roToDelete.add(ro2);
							bunker_remove = null;
						}
					}

					if (ro2 instanceof Player player) {
						if (enemy.isColliding(player)) {
							roToDelete.add(ro2);
						}
					}
				}
			}
		}

		// Remove the elements collected for deletion
		for (Renderable ro: roToDelete) {
			renderables.remove(ro);
			if (ro instanceof EnemyFastProjectile || ro instanceof EnemySlowProjectile) {
				enemyProjectileCount -=1;
			}
		}

		// Remove bunkers if it hit when in red state
		if (bunker_remove != null) {
			bunker_remove.takeDamage(2.0);
			if (bunker_remove.getCurrentState() == null) {
				renderables.remove(bunker_remove);
			}
		}
	}

	/**
	 * Delete Player's/Enemy's projectile in the game
	 */
	private void deleteProjectile() {
		Iterator<Renderable> it = renderables.iterator();
		while (it.hasNext()) {
			Renderable ro = it.next();
			if (ro instanceof PlayerProjectile) {
				((Moveable) ro).up();
				if (ro.getPosition().getY()  < 0) {
					it.remove();
					player.setPlayerProjectile(null);
				}
				continue;
			}
			if (ro instanceof EnemyFastProjectile enemyFastProjectile || ro instanceof EnemySlowProjectile enemySlowProjectile) {
				((Moveable) ro).down();
				if (ro.getPosition().getY() >= configReader.getGameSizeY()) {
					it.remove();
					enemyProjectileCount -=1;
				}
			}
		}
	}

	/**
	 * Add Player's projectile to the game
	 */
	private void addPlayerProjectile() {
		if (player.getPlayerProjectile() != null) {
			renderables.add(player.getPlayerProjectile());
		}
	}

	/**
	 * Make enemies move accross the screen
	 */
	private void moveEnemies() {
		// Make enemy move down and change direction together once it hit the wall
		boolean shouldChangeDirection = false;

		for (Renderable ro: renderables) {
			if (ro instanceof Enemy enemy) {
				enemy.moveSideways();
				if (enemy.shouldChangeDirection()) {
					shouldChangeDirection = true;
				}
			}
		}

		if (shouldChangeDirection) {
			for (Renderable ro: renderables) {
				if (ro instanceof Enemy enemy) {
					enemy.down();
					enemy.changeDirection();
				}
				shouldChangeDirection = false;
			}
		}
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		player.shoot();
		return true;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}


}
