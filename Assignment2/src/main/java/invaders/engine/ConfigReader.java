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
}
