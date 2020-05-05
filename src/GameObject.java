import javafx.scene.Parent;

public abstract class GameObject extends Parent
{
    protected double x, y;

    public GameObject(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract void update();
}
