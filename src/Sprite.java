import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Sprite extends GameObject
{
    final double SPRITE_MOVEMENT_DELTA_DEFAULT = 5;
    double width, height, dx, dy;
    ImageView sprite;

    public Sprite()
    {
        super(0, 0);
    }

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

    public ImageView getSprite()
    {
        return sprite;
    }

    public void setSprite(ImageView iv)
    {
        this.sprite = iv;
        iv.setFitWidth(this.width);
        iv.setFitHeight(this.height);
    }

    @Override
    public ArrayList<GameObject> getGameObjects() {
        return new ArrayList<GameObject>(Arrays.asList(this));
    }

    @Override
    public ArrayList<Node> getDisplayables() {
        return new ArrayList<Node>(Arrays.asList(this.sprite));
    }

    @Override
    public void update() {
        this.x += dx;
        this.y += dy;
        sprite.setLayoutX(this.x);
        sprite.setLayoutY(this.y);
    }
}
