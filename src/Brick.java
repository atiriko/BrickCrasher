import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import java.awt.*;

public class Brick extends StaticBody {

    static float width = 4f;
    static float height = 1.5f;
    private int level = 0;
    private static final Shape shape = new BoxShape(width/2, height/2);


    /***
     * Initialise a brick with its position and level
     */
    public Brick(World world, float x, float y, int Level) {
        super(world, shape);
        setPosition(new Vec2(x,y));
        level = Level;
        switch (Level){
            case 0:
                setFillColor(Color.YELLOW);
                addImage(new BodyImage("data/brickBroken.png",height));
                break;
            case 1:
                setFillColor(Color.GREEN);
                addImage(new BodyImage("data/brickCracked.png", height));
                break;
            case 2:
                setFillColor(Color.BLUE);
                addImage(new BodyImage("data/brick.png", height));
                break;
            case 3:
                setFillColor(Color.RED);
                addImage(new BodyImage("data/redmetal.png", height));
                break;

        }
        setName("brick");


    };
    public static float getWidth() {
        return width;
    }


    public static float getHeight() {
        return height;
    }


    public static void setHeight(float height) {
        Brick.height = height;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
