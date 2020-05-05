import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Player extends Sprite
{
    final String SPRITE_PLAYER = "file:res/earthmain.png";

    public Player()
    {
        this(600, 600, 160, 80);
    }

    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);
        setSprite(new ImageView(SPRITE_PLAYER));
        setDirection();
    }

    //the player can only move side to side, or along the x-axis
    public void setDirection(KeyCode code)
    {
        switch(code)
        {
            case A:
            case KP_LEFT:
            case LEFT:
                this.dx = -1 * SPRITE_MOVEMENT_DELTA_DEFAULT;
                break;
            case D:
            case KP_RIGHT:
            case RIGHT:
                this.dx = SPRITE_MOVEMENT_DELTA_DEFAULT;
                break;
            default:
                this.dx = 0;
                break;
        }
    }
    public void setDirection()
    {
        this.dx = 0;
    }

    @Override
    public void update()
    {
        super.update();

        if(this.x < 0)
            this.x = 0;
        else if(this.x > 1200 - this.width)
            this.x = 1200 - this.width;
    }
}
