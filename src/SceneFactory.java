import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SceneFactory implements ISceneFactory
{
    protected Stage stage;
    protected GraphicsContext gc;
    protected Group root;
    protected HashMap<String, Scene> sceneMap;

    protected ImageView IV_PLAY_MAIN = new ImageView("file:res/play.png");
    protected ImageView IV_SETTINGS_MAIN = new ImageView("file:res/settings.png");
    protected ImageView IV_DIFF_E = new ImageView("file:res/easydiff.png");
    protected ImageView IV_DIFF_N = new ImageView("file:res/normdiff.png");
    protected ImageView IV_DIFF_H = new ImageView("file:res/harddiff.png");

    public SceneFactory(Stage stage)
    {
        this.stage = stage;
    }

    public HashMap<String, Scene> initSceneMap()
    {
        sceneMap = new HashMap<String, Scene>();
        sceneMap.put("main", createScene("main"));
        sceneMap.put("settings", createScene("settings"));
        sceneMap.put("game", createScene("game"));

        return sceneMap;
    }

    public Scene createScene(String type)
    {
        //scene init
        Group root = new Group();
        this.root = root;
        Scene scene = new Scene(root, 1200, 800);
        scene.setFill(Color.DARKRED);

        switch(type)
        {
            case "settings":
                Text settingsheading = createHeading("Settings", 100, 100);
                Text settingsHeader = createSmallHeading("Please choose a difficulty:", 100, 300);
                Button easyButton = createButton(IV_DIFF_E, 100, 350, 'e');
                Button normButton = createButton(IV_DIFF_N, 100, 420, 'n');
                Button hardButton = createButton(IV_DIFF_H, 100, 490, 'h');

                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if(code.equals(KeyCode.Q))
                            stage.setScene(sceneMap.get("main"));
                    }
                });

                root.getChildren().addAll(settingsheading, settingsHeader, easyButton, normButton, hardButton);
                return scene;
            case "game":
                Player player = new Player();

                //define key press actions for scene
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if(code.isArrowKey() || code.isKeypadKey()) //move the player
                            player.setDirection(code);
                        else if(code.equals(KeyCode.P)) //pause tha game
                            Game.isActive(!Game.isActive());
                        else if(code.equals(KeyCode.Q)) { //quit to the menu (preserving the current game state)
                            Game.isActive(false);
                            stage.setScene(sceneMap.get("main"));
                        }
                        else if(code.equals(KeyCode.C)) //clear the player's answer
                            player.setAnswer();
                        else if(code.isDigitKey()) //add a digit to the player's answer
                            player.addToAnswer(code.getName());
                        else if(code.equals(KeyCode.PLUS) || code.equals(KeyCode.ADD))
                            player.setSign("+");
                        else if(code.equals(KeyCode.MINUS) || code.equals(KeyCode.SUBTRACT))
                            player.setSign("-");
                        else if(code.equals(KeyCode.SPACE)) //toggle the sign of the player
                            player.setSign(" ");
                        else
                            System.out.println(code.getName());

                    }
                });
                //define key release actions for scene
                scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if(code.isArrowKey() || code.isKeypadKey()) //set direction impetus to 0
                            player.setDirection();
                    }
                });

                Game.getInstance(root).addToAll(player);
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

    //buttons for changing external values
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

    //TODO: the checking of the existence of files here shouldn't be necessary, but maybe kept as a precaution?
    private void changeValue(char type)
    {
        try {
            //create the file if it does not exist
            File settings = new File(getMyDocs() + "\\settings.txt");
            if(settings.createNewFile())
                System.out.println("settings made");
            else
                System.out.println("settings exists");

            //write the value to my documents
            FileWriter fw = new FileWriter(settings.getAbsoluteFile());
            fw.write(type);
            System.out.println("wrote to settings");
            fw.close();

            //change the value to something recognisable
            String diffFull = "";
            switch (type) {
                case 'e':
                    diffFull = "easy";
                    break;
                case 'n':
                    diffFull = "normal";
                    break;
                case 'h':
                    diffFull = "hard";
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            //output the change to the current scene for the user
            Text textDiff = createSmallHeading("Difficulty changed to " + diffFull + ".", 400, 400);
            root.getChildren().removeIf(obj -> obj instanceof Text && ((Text) obj).getText().contains("Difficulty"));
            root.getChildren().add(textDiff);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //buttons for scene changes
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
                stage.setScene(sceneMap.get(destination));
                root = (Group) stage.getScene().getRoot();
                if(destination.equals("game"))
                    Game.reInit();
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

    /*Courtesy of user IvanRF (https://stackoverflow.com/questions/9677692/getting-my-documents-path-in-java)*/
    public static String getMyDocs()
    {
        return FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    }
}
