import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{
    ArrayList<Object> listAll = new ArrayList<Object>();
    ArrayList<Object> listMenu = new ArrayList<Object>();
    SceneFactory sf;
    GraphicsContext gc;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        }
    };

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        sf = new SceneFactory(stage);
        stage.setScene(sf.createScene("main"));

        //stage init
        stage.setResizable(false);
        stage.setTitle("Maths Drop!");
        stage.show();

        //timer.start();
    }
}
