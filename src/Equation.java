import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class Equation extends Sprite
{
    private String BASE = "file:res/btnbase.png";
    protected int[] operands;
    protected char[] operators;
    protected Textual text;

    public Equation()
    {
        this.width = 150;
        this.height = 50;
        this.x = randomNumber(0, 1200-this.width); //prevent off screen spawning
        this.y = randomNumber(0, 100-this.height);
        this.dx = 0;
        this.dy = randomNumber(1, 4);
        //TODO: add to the number of operands and operators as difficulty increases
        this.operands = new int[]{randomInt(0, 9), randomInt(0, 9)};
        this.operators = new char[]{randomOperator()};
        setSprite(composeSprite());
        this.text = new Textual(x+5, y+5, toString());
    }

    private ImageView composeSprite()
    {
        return new ImageView(BASE);
    }

    /*
     * Courtesy of user awwsmm, https://stackoverflow.com/questions/7548841/round-a-double-to-3-significant-figures
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
        return (Math.random() * ((max - min))) + min;
    }

    //TODO: easy difficulty should have no division and any medium difficulty division should be whole numbers only
    private char randomOperator()
    {
        double operatorMax = Game.getDifficulty() == 'e' ? 3.0d : 4.0d; //if the difficulty is easy then deny the use of division
        double rand = randomNumber(0, operatorMax);
        //System.out.println("" + operatorMax + "\t" +  rand);
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

    public int getAnswer()
    {
        int total = operands[0];
        for(int i = 0; i < operators.length; i++)
        {
            switch(operators[i])
            {
                case '+':
                    total += operands[i+1];
                    break;
                case '-':
                    total -= operands[i+1];
                    break;
                case '*':
                    total *= operands[i+1];
                    break;
                case '/':
                    total /= operands[i+1];
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator found: " + i + "\t" + operators[i]);
            }
        }
        return total;
    }

    @Override
    public ArrayList<GameObject> getGameObjects()
    {
        return new ArrayList<GameObject>(Arrays.asList(this, this.text));
    }

    @Override
    public ArrayList<Node> getDisplayables()
    {
        return new ArrayList<Node>(Arrays.asList(this.sprite, this.text.getText()));
    }

    @Override
    public void update()
    {
        super.update();
        text.setContent(toString());
        text.x = x+50;
        text.y = y+40;
    }

    @Override
    public String toString() //create output string for any length of equation
    {
        String out = "" + operands[0];
        for(int i = 0; i < operators.length; i++)
            out = out.concat("" + operators[i] + operands[i+1]);

        return out;
    }
}
