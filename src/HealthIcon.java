import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class HealthIcon extends GhostlyFixture {
private static final CircleShape circleShape = new CircleShape(0);

    //Initialise a health icon
    public HealthIcon(Body body, int numberOfLives) {
        super(body, circleShape);
      body.addImage(new BodyImage("data/heart.png"));
      body.setPosition(new Vec2(10 - (numberOfLives*2),-11.5f));
    }

}

