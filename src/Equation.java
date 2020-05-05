import javafx.scene.image.ImageView;

public class Equation extends Sprite
{
    protected double[] operands;
    protected char[] operators;

    public Equation()
    {
        this.x = randomNumber(0, 1200);
        this.y = 0;
        this.dx = 0;
        this.dy = randomNumber(2, 5);
        this.operands = new double[]{randomNumber(0, 9), randomNumber(0, 9)};
        this.operators = new char[]{randomOperator()};
        this.width = 150;
        this.height = 50;
        setSprite(composeSprite());
    }

    private ImageView composeSprite()
    {
        return new ImageView("file:res/btnbase.png");
    }

    private double randomNumber(double min, double max)
    {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    private char randomOperator()
    {
        double rand = randomNumber(0,4);
        if(rand > 0 && rand <= 1)
        {
            return '+';
        }
        else if(rand > 1 && rand <= 2)
        {
            return '-';
        }
        else if(rand > 2 &&  rand <= 3)
        {
            return '*';
        }
        else
        {
            return '/';
        }
    }

    @Override
    public void update()
    {
        super.update();

        if(this.y > 800)
            this.y = 800;
    }
}
