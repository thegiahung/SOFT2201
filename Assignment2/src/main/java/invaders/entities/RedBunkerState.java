package invaders.entities;

import javafx.scene.image.Image;

import java.io.File;

public class RedBunkerState extends BunkerState {
    private Bunkers bunkers;

    public RedBunkerState(Bunkers bunkers) {
        this.bunkers = bunkers;
    }

    /**
     * Use the State pattern to change the color
     */
    @Override
    public void handleDamage() {
        bunkers.setState(null);
    }
}