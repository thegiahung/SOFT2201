package invaders.engine;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Set<EntityView> entitiesToDelete = new HashSet<>(); // Hung
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model, int width, int height){
		this.width = width;
        this.height = height;
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();

    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }

    private void draw(){
        model.update();

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }

            // Hung
            for (EntityView entityView : entityViews) {
                if (entityView.isMarkedForDelete()) {
                    entitiesToDelete.add(entityView); // Mark for deletion
                }
            }
        }

        for (EntityView entityView: entityViews) {
            boolean isfound = false;
            for (Renderable ro : renderables) {
                if (entityView.matchesEntity(ro)) {
                    isfound = true;
                    break;
                }
            }
            if (!isfound) {
                entitiesToDelete.add(entityView);
            }
        }

        // Hung: Remove entities marked for deletion
        for (EntityView entityView : entitiesToDelete) {
            pane.getChildren().remove(entityView.getNode());
            entityViews.remove(entityView);
        }



        entityViews.removeIf(EntityView::isMarkedForDelete);
        entitiesToDelete.clear(); // Hung: Clear the set after deletion
    }

	public Scene getScene() {
        return scene;
    }
}
