import javafx.scene.image.ImageView;

public class Equation extends Sprite
{
    private String BASE = "file:res/btnbase.png";
    protected int[] operands;
    protected char[] operators;

    public Equation()
    {
        this.x = randomNumber(0, 1200);
        this.y = 0;
        this.dx = 0;
        this.dy = randomNumber(2, 5);
        this.operands = new int[]{randomInt(0, 9), randomInt(0, 9)};
        this.operators = new char[]{randomOperator()};
        this.width = 150;
        this.height = 50;
        setSprite(composeSprite());
    }

    private ImageView composeSprite()
    {
        return new ImageView(BASE);
    }

    /*
     * Courtesy of user awwsmm on stackoverflow.com
     * -> https://stackoverflow.com/questions/7548841/round-a-double-to-3-significant-figures
     */
    private static double sfRound(double value, int nSigDig, int dir)
    {
        double intermediate = value/Math.pow(10,Math.floor(Math.log10(Math.abs(value)))-(nSigDig-1));

        if      (dir > 0) intermediate = Math.ceil(intermediate);
        else if (dir< 0)  intermediate = Math.floor(intermediate);
        else              intermediate = Math.round(intermediate);

        return intermediate * Math.pow(10,Math.floor(Math.log10(Math.abs(value)))-(nSigDig-1));
    }

    private int randomInt(double min, double max)
    {
        return (int) sfRound(randomNumber(min, max), 1, 0);
    }

    private double randomNumber(double min, double max)
    {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    //TODO: easy difficulty should have no division and any medium difficulty division should be whole numbers only
    private char randomOperator()
    {
        double operatorMax = Game.getDifficulty() == 'e' ? 2.0d : 3.0d;
        double rand = randomNumber(0, operatorMax);
        System.out.println("" + operatorMax + rand);
        if(rand >= 0.0d && rand < 1.0d)
        {
            return '+';
        }
        else if(rand >= 1.0d && rand < 2.0d)
        {
            return '-';
        }
        else if(rand >= 2.0d && rand < 3.0d)
        {
            return '*';
        }
        else if(rand >= 3.0d && rand <=4.0d)
        {
            return '/';
        }

        return 'E';
    }

    @Override
    public void update()
    {
        super.update();

        if(this.y > 800)
            this.y = 800;
    }

    @Override
    public String toString()
    {
        return "Equation: " + operands[0] + operators[0] + operands[1];
    }
}
