import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application
{
    HashMap<String, Scene> mapScene = new HashMap<String, Scene>();
    ArrayList<Node> listAll = new ArrayList<Node>();
    ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
    SceneFactory sf;
    GraphicsContext gc;
    AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            //gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            for(GameObject obj : spriteList)
            {
                //pre-update    ^^^^^
                obj.update();
                //post-update   vvvvv
                checkCollisions();
            }
        }
    };

    public void checkCollisions()
    {
        for(Sprite obj : spriteList)
            if(obj instanceof Player)
            {
                Rectangle playerBounds = obj.getBounds();
                for(Sprite collTest : spriteList) {
                    Rectangle collBounds = collTest.getBounds();
                    if (playerBounds.intersects(collBounds.getBoundsInParent())) {
                        System.out.println("removed " + collTest);
                        spriteList.remove(collTest);
                    }
                }
            }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //scenes init
        sf = new SceneFactory(stage);
        mapScene = sf.initSceneMap();


        //stage init
        stage.setScene(mapScene.get("main"));
        stage.setResizable(false);
        stage.setTitle("Maths Drop!");
        stage.show();

        gameTimer.start();
    }
}
