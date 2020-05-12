import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;

public abstract class GameObject extends Parent
{
    protected double x, y;

    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract ArrayList<GameObject> getGameObjects();
    public abstract ArrayList<Node> getDisplayables();
    public abstract void update();
}
