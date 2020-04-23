import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneFactory implements ISceneFactory
{
    protected Stage stage;
    protected GraphicsContext gc;

    protected ImageView IV_PLAY_MAIN = new ImageView("file:res/play.png");
    protected ImageView IV_SETTINGS_MAIN = new ImageView("file:res/settings.png");

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
                root.getChildren().addAll(settingsheading);
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
        txt.setFont(Font.font("Comic Sans MS", 60));
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
