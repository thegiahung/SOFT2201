package invaders.entities;

import javafx.scene.image.Image;

import java.io.File;

public class GreenBunkerState extends BunkerState {
    private String image = "src/main/resources/yellow_bunker.png";
    private Bunkers bunkers;

    public GreenBunkerState(Bunkers bunkers) {
        this.bunkers = bunkers;
    }

    /**
     * Use the State pattern to change the color
     */
    @Override
    public void handleDamage() {
        bunkers.setState(new YellowBunkerState(bunkers));
        bunkers.setImage(image);
    }
}