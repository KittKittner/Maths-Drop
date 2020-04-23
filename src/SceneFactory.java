import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class SceneFactory implements ISceneFactory
{
    protected Stage stage;
    protected GraphicsContext gc;

    protected ImageView IV_PLAY_MAIN = new ImageView("file:res/play.png");
    protected ImageView IV_SETTINGS_MAIN = new ImageView("file:res/settings.png");
    protected ImageView IV_DIFF_E = new ImageView("file:res/easydiff.png");

    public SceneFactory(Stage stage)
    {
        this.stage = stage;
    }

    public Scene createScene(String type)
    {
        //scene init
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800);
        scene.setFill(Color.BLACK);
        Canvas canvas = new Canvas(1200, 800);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        switch(type)
        {
            case "settings":
                Text settingsheading = createHeading("Settings", 100, 100);
                Text settingsHeader = createSmallHeading("Please choose a difficulty:", 100, 300);
                Button easyButton = createButton(IV_DIFF_E, 100, 350, 'e');

                root.getChildren().addAll(settingsheading, settingsHeader, easyButton);
                return scene;
            case "game":
                Text playHeading = createHeading("Time to play!", 100, 100);

                root.getChildren().addAll(playHeading);
                return scene;
            case "main":
            default:
                Text mainHeading = createHeading("Welcome to Maths Drop!", 100, 100);
                Button playbtn = createButton(IV_PLAY_MAIN, 450, 350, 256, 64, "game");
                Button settingsbtn = createButton(IV_SETTINGS_MAIN, 1064, 668, 128, 128, "settings");

                root.getChildren().addAll(mainHeading, playbtn, settingsbtn);
                return scene;
        }
    }

    //buttons for changing setting values
    private Button createButton(ImageView iv, int x, int y, char type)
    {
        Button btn = new Button();
        btn.setGraphic(iv);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setBackground(null);

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeValue(type);
            }
        });

        return btn;
    }

    private void changeValue(char type)
    {
        try {
            FileWriter fw = new FileWriter("settings.txt");
            switch (type) {
                case 'e':
                    System.out.println("difficulty set to easy");
                    fw.write('e');
                    fw.close();
                    break;
                case 'n':
                    System.out.println("difficulty set to normal");
                    fw.write('n');
                    fw.close();
                    break;
                case 'h':
                    System.out.println("difficulty set to hard");
                    fw.write('h');
                    fw.close();
                    break;
                default:
                    System.out.println("nothing");
                    fw.close();
                    break;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //butons for scene creation
    private Button createButton(ImageView iv, int x, int y, String destination)
    {
        Button btn = new Button();
        btn.setGraphic(iv);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setBackground(null);

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(createScene(destination));
            }
        });

        return btn;
    }

    private Button createButton(ImageView iv, int x, int y, int width, int height, String destination)
    {
        iv.setFitWidth(width);
        iv.setFitHeight(height);

        return createButton(iv, x, y, destination);
    }

    private Text createHeading(String s, int x, int y)
    {
        Text txt = new Text(x, y, s);
        txt.setFont(Font.font("Comic Sans MS", 64));
        txt.setEffect(getInnerShadowEffect());

        return txt;
    }

    private Text createSmallHeading(String s, int x, int y)
    {
        Text txt = new Text(x, y, s);
        txt.setFont(Font.font("Comic Sans MS", 32));
        txt.setEffect(getInnerShadowEffect());

        return txt;
    }

    private InnerShadow getInnerShadowEffect()
    {
        InnerShadow is = new InnerShadow();
        is.setOffsetX(2.0f);
        is.setOffsetY(2.0f);
        is.setColor(Color.WHITE);

        return is;
    }

}
