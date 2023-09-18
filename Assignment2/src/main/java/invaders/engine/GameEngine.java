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
	public int enemyProjectileCount = 0;

	public GameEngine(String config){
		// read the config here
		// Read configuration (Hung's do)
		// Retrieve player section
		long playerSpeed = configReader.getPlayerSpeed();
		long playerLives = configReader.getPlayerLives();
		String playerColor = configReader.getPlayerColour();
		long playerPosX = configReader.getPlayerPositionX();
		long playerPosY = configReader.getPlayerPositionY();

		// Retrieve enemy section
		enemies = configReader.readEnemyData();

		// Retrieve bunkers section
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
		player = new Player(new Vector2D(playerPosX, playerPosY)); //		player = new Player(new Vector2D(200, 380));
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

		if (enemyProjectileCount < 3) {
			int index = new Random().nextInt(enemies.size());
			enemies.get(index).shoot();
			renderables.add(enemies.get(index).getEnemyProjectile());
			enemyProjectileCount += 1;
		}



		for(GameObject go: gameobjects){
			go.update();
		}

		// Add playerProjectile to renderables if it exists: Hung
		if (player.getPlayerProjectile() != null) {
			renderables.add(player.getPlayerProjectile());
		}

		// If player's projectile go out of the screen, then delete it
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
			System.out.println("check");
			if (ro instanceof Enemy enemy) {
				System.out.println("check1");
				if (enemy.getEnemyProjectile() != null) {
					System.out.println("check2");
					if (enemy.getEnemyProjectile().getPosition().getY() >= gameSizeY) {
						System.out.println("check3fadssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
						it.remove();
						enemy.setEnemyProjectile(null);

						enemyProjectileCount -=1;
						System.out.println(enemyProjectileCount);
					}
				}
			}
			if (ro instanceof Projectile) {
				((Moveable) ro).down();
			}
		}

		//
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

					if (ro2 instanceof Enemy enemy) {
						if (enemy.isColliding(projectile)) {
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

		if (bunker_remove != null) {
			bunker_remove.takeDamage(2.0);
			if (bunker_remove.getCurrenState() == null) {
				renderables.remove(bunker_remove);
			}
		}

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
