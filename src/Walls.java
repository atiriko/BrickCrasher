import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Walls extends StaticBody {
    private static final Shape shape = new BoxShape(15, 0.5f);

    //Initialise the outer walls at correct positions
    public Walls(World world, position pos) {
        super(world, shape);
        switch (pos){
            case TOP:
                setPosition(new Vec2(0,13));
                setName("topWall");
                break;
            case BOTTOM:
                setPosition(new Vec2(0,-13));
                setName("bottomWall");
                break;
            case RIGHT:
                setPosition(new Vec2(13.5f,0));
                rotateDegrees(90);
                setName("rightWall");
                break;
            case LEFT:
                setPosition(new Vec2(-13.5f,0));
                rotateDegrees(90);
                setName("leftWall");
                break;

        }
    }

    enum position{
        TOP,LEFT,RIGHT,BOTTOM
    }
}
