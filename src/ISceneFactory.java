import javafx.scene.Scene;

@FunctionalInterface
public interface ISceneFactory
{
    Scene createScene(String type);
}
