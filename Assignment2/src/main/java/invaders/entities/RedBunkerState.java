package invaders.entities;

import javafx.scene.image.Image;

import java.io.File;

public class RedBunkerState extends BunkerState {
    private Bunkers bunkers;

    public RedBunkerState(Bunkers bunkers) {
        this.bunkers = bunkers;
    }

    @Override
    public void handleDamage() {
        bunkers.setState(null);
    }
}