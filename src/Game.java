import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Game
{
    static Game instance = null;
    boolean isGameActive = false;
    int score;
    Group root;
    ArrayList<Node> listAll = new ArrayList<Node>();
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

                for (GameObject obj : goList) {
                    obj.update();
                }

                //post-update
                checkCollisions();
            }
            else
                setActive(false);
        }
    };

    public void checkCollisions()
    {
        for(GameObject obj : goList)
        {
            if (obj instanceof Player)
            {
                Rectangle playerBounds = ((Player) obj).getBounds();
                for (GameObject collTest : goList)
                {
                    Rectangle collBounds = null;
                    if(collTest instanceof Sprite)
                        collBounds = ((Sprite) collTest).getBounds();
                    if (collTest instanceof Equation && playerBounds.intersects(collBounds.getBoundsInParent()))
                    {
                        removeFromAll(collTest);
                        score += 1;
                        for(Object text : root.getChildren())
                            if(text instanceof Text)
                            {
                                ((Text) text).setText("Score: " + score);
                            }
                    }
                }
            }
            else if(obj instanceof Equation)
            {
                if(((Sprite) obj).getSprite().getLayoutY() > 800)
                    removeFromAll(obj);
            }
        }
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
        listAll.add(s);
    }

    public ArrayList<GameObject> getSpriteList()
    {
        return goList;
    }


    public void setActive(boolean state)
    {
        isGameActive = state;
    }

    public boolean isActive()
    {
        return isGameActive;
    }

    private Game(Group group)
    {
        this.root = group;
        score = 0;
        root.getChildren().add((new Textual(100, 100, "Score: 0")).getText());
        setActive(true);
        //gameTimer.start();
    }

    public static Game getInstance(Group group)
    {
        if(instance == null)
            instance = new Game(group);

        return instance;
    }
}
