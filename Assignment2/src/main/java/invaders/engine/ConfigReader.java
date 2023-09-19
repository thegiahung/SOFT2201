package invaders.engine;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import invaders.entities.Enemy;
import invaders.entities.Bunkers;
import invaders.entities.ConcreteEnemyBuilder;
import invaders.entities.EnemyBuilder;
import invaders.physics.Vector2D;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigReader {
	private static JSONObject jsonObject;

	/**
	 * You will probably not want to use a static method/class for this.
	 * 
	 * This is just an example of how to access different parts of the json
	 * 
	 * @param path The path of the json file to read
	 */
	public ConfigReader parse(String path) {

		JSONParser parser = new JSONParser();
		ConfigReader configReader = new ConfigReader();
		try {
			Object object = parser.parse(new FileReader(path));

			// convert Object to JSONObject
			 jsonObject = (JSONObject) object;

//			// reading the Game section:
//			JSONObject jsonGame = (JSONObject) jsonObject.get("Game");
//
//			// reading a coordinate from the nested section within the game
//			// note that the game x and y are of type Long (i.e. they are integers)
//			Long gameX = (Long) ((JSONObject) jsonGame.get("size")).get("x");
//			Long gameY = (Long) ((JSONObject) jsonGame.get("size")).get("y");
//
//			// reading the Player section
//			JSONObject jsonPlayer = (JSONObject) jsonObject.get("Player");
//			String colour = (String) (jsonPlayer.get("colour")); // player colour state
//			Long speed = (Long) (jsonPlayer.get("speed")); // player speed
//			Long lives = (Long) (jsonPlayer.get("lives")); // player lives
//			Long playerX = (Long) ((JSONObject) jsonPlayer.get("position")).get("x"); // X position of player
//			Long playerY = (Long) ((JSONObject) jsonPlayer.get("position")).get("y"); // Y position of player
//
//			// reading the "Enemies" array:
//			JSONArray jsonEnemies = (JSONArray) jsonObject.get("Enemies");
//
//			// reading from the array:
//			for (Object obj : jsonEnemies) {
//				JSONObject jsonEnemy = (JSONObject) obj;
//
//				// the enemy position is a double
//				Double positionX = (Double) ((JSONObject) jsonEnemy.get("position")).get("x");
//				Double positionY = (Double) ((JSONObject) jsonEnemy.get("position")).get("y");
//
//				String projectileStrategy = (String) jsonEnemy.get("projectile");
//			}
//
//			// Reading the Bunkers section
//			JSONArray jsonBunkers = (JSONArray) jsonObject.get("Bunkers");
//
//			for (Object bunkerObject : jsonBunkers) {
//				JSONObject jsonBunker = (JSONObject) bunkerObject;
//
//				// Getting position and size for each bunker
//				JSONObject position = (JSONObject) jsonBunker.get("position");
//				Long bunkerX = (Long) position.get("x");
//				Long bunkerY = (Long) position.get("y");
//
//				JSONObject size = (JSONObject) jsonBunker.get("size");
//				Long SizeX = (Long) size.get("x");
//				Long SizeY = (Long) size.get("y");
//			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return configReader;
	}

	public int getGameSizeX() {
		return ((Long) ((JSONObject) ((JSONObject) jsonObject.get("Game")).get("size")).get("x")).intValue();
	}

	public int getGameSizeY() {
		return ((Long) ((JSONObject) ((JSONObject) jsonObject.get("Game")).get("size")).get("y")).intValue();
	}

	public String getPlayerColour() {
		return (String) ((JSONObject) jsonObject.get("Player")).get("colour");
	}

	public Long getPlayerSpeed() {
		return (Long) ((JSONObject) jsonObject.get("Player")).get("speed");
	}

	public Long getPlayerLives() {
		return (Long) ((JSONObject) jsonObject.get("Player")).get("lives");
	}

	public Long getPlayerPositionX() {
		return (Long) ((JSONObject) ((JSONObject) jsonObject.get("Player")).get("position")).get("x");
	}

	public Long getPlayerPositionY() {
		return (Long) ((JSONObject) ((JSONObject) jsonObject.get("Player")).get("position")).get("y");
	}

	// Extract Enemy information
	public List<Enemy> readEnemyData() {
		List<Enemy> enemies = new ArrayList<>();
		JSONArray jsonEnemies = (JSONArray) jsonObject.get("Enemies");

		for (Object obj : jsonEnemies) {
			JSONObject jsonEnemy = (JSONObject) obj;
			Long enemyXpos = (Long) ((JSONObject) jsonEnemy.get("position")).get("x");
			Long enemyYpos = (Long) ((JSONObject) jsonEnemy.get("position")).get("y");
			String projectileStrategy = (String) jsonEnemy.get("projectile");

			// Create an Enemy object and add it to the list
			Vector2D enemyPosition = new Vector2D(enemyXpos, enemyYpos);
			EnemyBuilder enemyBuilder = new ConcreteEnemyBuilder();
			Enemy enemy = new ConcreteEnemyBuilder().setPosition(enemyPosition).setProjectileType(projectileStrategy).build();
			enemies.add(enemy);
		}

		return enemies;
	}

	// Extract Bunkers information
	public List<Bunkers> readBunkerData() {
		List<Bunkers> bunkers = new ArrayList<>();
		JSONArray jsonBunkers = (JSONArray) jsonObject.get("Bunkers");

		for (Object bunkerObject : jsonBunkers) {
			JSONObject jsonBunker = (JSONObject) bunkerObject;
			Long bunkerX = (Long) ((JSONObject) jsonBunker.get("position")).get("x");
			Long bunkerY = (Long) ((JSONObject) jsonBunker.get("position")).get("y");
			Long bunkerSizeX = (Long) ((JSONObject) jsonBunker.get("size")).get("x");
			Long bunkerSizeY = (Long) ((JSONObject) jsonBunker.get("size")).get("y");
			// Process bunker data here
			Vector2D bunkerPosition = new Vector2D(bunkerX, bunkerY);
			Bunkers bunker = new Bunkers(bunkerPosition, bunkerSizeX, bunkerSizeY);
			bunkers.add(bunker);
		}
		return bunkers;
	}

//	/**
//	 * Your main method will probably be in another file!
//	 *
//	 * @param args First argument is the path to the config file
//	 */
//	public static void main(String[] args) {
//		// if a command line argument is provided, that should be used as the path
//		// if not, you can hard-code a default. e.g. "src/main/resources/config.json"
//		// this makes it easier to test your program with different config files
//		String configPath;
//		if (args.length > 0) {
////			configPath = args[0];
//			configPath = "src/main/resources/" + args[0];
//		} else {
//			configPath = "src/main/resources/config.json";
//		}
//		// parse the file:
//		ConfigReader.parse(configPath);
//	}

}
