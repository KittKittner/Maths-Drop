import javafx.scene.shape.Rectangle;

public class Sprite extends GameObject
{
    double width, height;


    public Sprite(double x, double y, double width, double height)
    {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {

    }
}
