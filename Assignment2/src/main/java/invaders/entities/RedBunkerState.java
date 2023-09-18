package invaders.entities;

import javafx.scene.image.Image;

import java.io.File;

public class RedBunkerState extends BunkerState {
    private Image image = new Image(new File("src/main/resources/bunker.png").toURI().toString(), 25, 30, true, true);;
    Bunkers bunkers;

    public RedBunkerState(Bunkers bunkers) {
        this.bunkers = bunkers;
    }

    @Override
    public void handleDamage() {
        bunkers.setState(null);
        System.out.println("this call 1 time");
    }
}