import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Textual extends GameObject
{
    String content;
    Text text;

    public Textual(double x, double y, String content) {
        super(x, y);
        this.content = content;
        this.text = new Text(x, y, content);
        text.setFill(Color.BLUE);
        text.setFont(getDefaultFont());
        text.setEffect(getInnerShadowEffect());
    }

    private Font getDefaultFont()
    {
        return Font.font("Comic Sans MS", 32);
    }

    private InnerShadow getInnerShadowEffect()
    {
        InnerShadow is = new InnerShadow();
        is.setOffsetX(2.0f);
        is.setOffsetY(2.0f);
        is.setColor(Color.WHITE);

        return is;
    }

    public Text getText()
    {
        return text;
    }

    public void setContent(String text)
    {
        this.content = text;
    }

    public String getContent()
    {
        return this.content;
    }

    @Override
    public ArrayList<GameObject> getGameObjects()
    {
        return new ArrayList<GameObject>(Arrays.asList(this));
    }

    @Override
    public ArrayList<Node> getDisplayables()
    {
        return new ArrayList<Node>(Arrays.asList(this.text));
    }

    @Override
    public void update() {
        text.setX(x);
        text.setY(y);
        text.setText(content);
    }
}
