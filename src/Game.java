import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game
{
    static Game instance = null;
    static char difficulty;
    static boolean isGameActive;
    int score;
    Group root;
    ArrayList<GameObject> goList = new ArrayList<GameObject>();
    AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(isActive()) {
                //pre-update
                if(goList.size() <= 10) {
                    Equation eqn = new Equation();
                    addToAll(eqn);
                }

                //on-update
                for (GameObject obj : goList) {
                    obj.update();
                }

                //post-update
                checkCollisions();
            }
        }
    };

    //TODO: time and space complexity can probably be greatly reduced here by creating definitions beforehand and reducing nested for loops?
    public void checkCollisions()
    {
        ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
        for(GameObject obj : goList) //for each relevant object in the game
        {
            if (obj instanceof Player)
            {
                Rectangle playerBounds = ((Player) obj).getBounds(); //get the current bounds/dimensions of the player
                for (GameObject collTest : goList)
                {
                    Rectangle collBounds = null;
                    if(collTest instanceof Sprite)
                        collBounds = ((Sprite) collTest).getBounds(); //get the current bounds/dimensions of the sprite
                    if (collTest instanceof Equation && playerBounds.intersects(collBounds.getBoundsInParent())) //check if the bounds of the player and the sprite overlap (collision)
                    {
                        toRemove.add(collTest);
                        System.out.println("Collision with player: removed " + collTest);
                        score += 1;
                        for(Object text : root.getChildren())
                            if(text instanceof Text)
                            {
                                //on collision of the player and an equation
                                ((Text) text).setText("Score: " + score);
                            }
                    }
                }
            }
            else if(obj instanceof Equation)
            {
                if(((Sprite) obj).getSprite().getLayoutY() > 800) //check if the equation is out of stage-bounds, delete if so
                    toRemove.add(obj);
            }
        }
        for(GameObject obj : toRemove) //remove each necessary object
            removeFromAll(obj);
    }

    public void addToAll(GameObject obj)
    {
        if(obj instanceof Sprite)
            root.getChildren().add(((Sprite) obj).getSprite());
        else if(obj instanceof Textual)
            root.getChildren().add(((Textual) obj).getText());

        goList.add(obj);
    }

    public void removeFromAll(GameObject obj)
    {
        goList.remove(obj);
        if(obj instanceof Sprite)
            root.getChildren().remove(((Sprite) obj).getSprite());
        else if(obj instanceof Textual)
            root.getChildren().remove(((Textual) obj).getText());
    }

    public void addToSpriteList(Sprite s)
    {
        goList.add(s);
    }

    public ArrayList<GameObject> getSpriteList()
    {
        return goList;
    }

    public static char getDifficulty()
    {
        return difficulty;
    }

    public static void isActive(boolean state)
    {
        isGameActive = state;
    }

    public static boolean isActive()
    {
        return isGameActive;
    }

    private static void readDifficulty()
    {
        File settings = new File(SceneFactory.getMyDocs() + "\\settings.txt");
        Scanner scanner = null;
        FileWriter fw = null;
        try {
            scanner = new Scanner(settings);
            difficulty = scanner.next().charAt(0); //set the difficulty to the one stored
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scanner == null) {
                try {
                    fw = new FileWriter(settings);
                    fw.write("n"); //if there is no current difficulty, make one
                    fw.close();
                } catch(IOException e) {
                    e.printStackTrace();
                } finally {
                    difficulty = 'n';
                    System.out.println("Default difficulty n set");
                }
            }
        }
        System.out.println("Current difficulty: " + difficulty);
    }

    //update values on reentry to the game to prevent mid game changes to make the game easier
    public static void reInit()
    {
        isActive(true);
        readDifficulty();
    }

    private Game(Group group)
    {
        this.root = group;
        readDifficulty();
        score = 0;
        root.getChildren().add((new Textual(100, 100, "Score: 0")).getText());
        isActive(false);
        gameTimer.start();
    }

    public static Game getInstance(Group group)
    {
        if(instance == null)
            instance = new Game(group);

        return instance;
    }
}
