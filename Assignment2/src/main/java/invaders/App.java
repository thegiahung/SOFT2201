package invaders;

import invaders.engine.ConfigReader;
import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;

import java.util.Map;

public class App extends Application {
    private ConfigReader configReader = new ConfigReader().parse("src/main/resources/config.json");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Map<String, String> params = getParameters().getNamed();

        GameEngine model = new GameEngine("/resources/config.json");
        // Extract GameWindow size from json file
        int gameSizeX = configReader.getGameSizeX(); // Assuming you have a getter for game size X in ConfigReader
        int gameSizeY = configReader.getGameSizeY(); // Assuming you have a getter for game size Y in ConfigReader

        // Create GameWindow
        GameWindow window = new GameWindow(model, gameSizeX, gameSizeY);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
