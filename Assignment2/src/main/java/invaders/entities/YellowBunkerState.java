package invaders.entities;

import javafx.scene.image.Image;

import java.io.File;

public class YellowBunkerState extends BunkerState {
    private String image = "src/main/resources/red_bunker.png";
    private Bunkers bunkers;

    public YellowBunkerState(Bunkers bunkers) {
        this.bunkers = bunkers;
    }

    @Override
    public void handleDamage() {
        bunkers.setState(new RedBunkerState(bunkers));
        bunkers.setImage(image);
    }
}