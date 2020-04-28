import javafx.scene.Node;

public abstract class GameObject extends Node
{
    protected double x, y;

    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract void update();
}
