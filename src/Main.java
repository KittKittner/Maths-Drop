import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application
{
    HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
    SceneFactory sf;


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //scenes init
        sf = new SceneFactory(stage);
        sceneMap = sf.initSceneMap();


        //stage init
        stage.setScene(sceneMap.get("main"));
        stage.setResizable(false);
        stage.setTitle("Maths Drop!");
        stage.show();
    }
}
