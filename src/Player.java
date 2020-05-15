import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Sprite
{
    final String SPRITE_PLAYER = getClass().getResource("player.png").toString();
    Textual sign;
    Textual answer;

    public Player()
    {
        this(600, 600, 160, 80);
        sign = new Textual(x, y, "+");
        answer = new Textual(x, y, "0");
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
            case KP_LEFT:
            case LEFT:
                this.dx = -1 * SPRITE_MOVEMENT_DELTA_DEFAULT;
                break;
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

    public int getAnswer()
    {
        return Integer.parseInt(this.sign.getContent() + this.answer.getContent());
    }

    public void setAnswer()
    {
        this.sign.setContent("+");
        this.answer.setContent("0");
    }

    public void addToAnswer(String s)
    {
        if(this.answer.getContent().equals("0") || Integer.parseInt(this.answer.getContent()) >=100)
            this.answer.setContent(s);
        else
            this.answer.setContent(this.answer.getContent() + s);
    }

    public void setSign(String s)
    {
        if(s.equals(" "))
        {
            if (this.sign.getContent().equals("+"))
                this.sign.setContent("-");
            else
                this.sign.setContent("+");
        }
        else
            this.sign.setContent(s);
    }

    @Override
    public ArrayList<GameObject> getGameObjects() {
        return new ArrayList<GameObject>(
                Arrays.asList(this, this.sign, this.answer));
    }

    @Override
    public ArrayList<Node> getDisplayables()
    {
        return new ArrayList<Node>(
                Arrays.asList(this.sprite, this.sign.getText(), this.answer.getText()));
    }

    @Override
    public void update()
    {
        super.update();

        if(this.x < 0)
            this.x = 0;
        else if(this.x > 1200 - this.width)
            this.x = 1200 - this.width;

        sign.x = x + 20;
        sign.y = y + 50;
        answer.x = x + 80;
        answer.y = y + 50;
    }
}
