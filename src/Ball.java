import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.util.Random;

public class Ball extends DynamicBody{

    private static float ballRadius = 0.5f;
    private float speed = 10f;
    private static final Shape ballShape = new CircleShape(ballRadius);
    private Random random = new Random();

    /***
     * Initialise a ball
     * @param world in witch the ball is initialised
     * @param position at witch the ball is initialised
     */

    public Ball(GameWorld world, Vec2 position) {
        super(world, ballShape);
        setPosition(new Vec2(position.x,position.y+ballRadius/2));
        this.setName("ball");
        setGravityScale(0);
        addImage(new BodyImage("data/ball.png", 1));

        this.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                if(collisionEvent.getOtherBody().getName() != null){
                    //Listen to collisions between the ball and the walls and move the ball accordingly
                    switch (collisionEvent.getOtherBody().getName()){
                        case "topWall":
                            world.playSound("data/wall.wav");
                            Bounce(GameWorld.Direction.BOTTOM);
                            break;
                        case "leftWall":
                            world.playSound("data/wall.wav");
                            Bounce(GameWorld.Direction.RIGHT);
                            break;
                        case "rightWall":
                            world.playSound("data/wall.wav");
                            Bounce(GameWorld.Direction.LEFT);
                            break;
                        case "bottomWall":
                            destroy();
                           world.BallHitTheBottomWall();
                            break;
                        case "playerBrick":
                            if(world.isGameRunning()) {
                                world.playSound("data/PlayerBrick.wav");
                                Bounce(GameWorld.Direction.TOP);
                                world.IncrementBallHitThePlayer();
                            }
                        default:
                            break;
                    }
                    //Listens to collisions between the ball and the bricks and bounce the ball accordingly.
                    if(collisionEvent.getOtherBody().getName().equals("brick")){
                        Vec2 bodyPos = collisionEvent.getOtherBody().getPosition();
                        Vec2 ballPos = getPosition();


                        //Calculates the impact direction of the collision
                        switch (world.CalculateImpactDirection(bodyPos, ballPos)) {
                            case TOP -> Bounce(GameWorld.Direction.TOP);
                            case BOTTOM -> Bounce(GameWorld.Direction.BOTTOM);
                            case LEFT -> Bounce(GameWorld.Direction.LEFT);
                            case RIGHT -> Bounce(GameWorld.Direction.RIGHT);
                            case UNKNOWN -> Bounce(GameWorld.Direction.UNKNOWN);
                        }

                        world.CalculateWhatHappensToBrickOnCollision((Brick) collisionEvent.getOtherBody());


                        //Go to next level if no bricks left
                        if (world.getNumberOfBricks() == 0){
                            destroy();
                           world.NextLevel();
                        }

                    }
                }
            }
        });

    }


    /***
     * Starts the ball to a random direction when the player starts the game
     */
    public void startBall(){
        boolean initialVelocity = random.nextBoolean( ) ;
        if(initialVelocity){
            setLinearVelocity(new Vec2(speed,speed));

        }else{
            setLinearVelocity(new Vec2(speed*-1,speed));

        }
    }

    /***
     *  Sets the linear velocity of the ball to correct Vec2 depending on the direction
     * @param direction witch the ball is going to bounce towards
     */
    public void Bounce(GameWorld.Direction direction){
        switch (direction) {
            case TOP -> this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed));
            case BOTTOM -> this.setLinearVelocity(new Vec2(this.getLinearVelocity().x, speed*-1));
            case LEFT -> this.setLinearVelocity(new Vec2(speed*-1, this.getLinearVelocity().y));
            case RIGHT -> this.setLinearVelocity(new Vec2(speed, this.getLinearVelocity().y));
            case UNKNOWN -> startBall();
        }
        if (this.getLinearVelocity().x >= speed * -1 && this.getLinearVelocity().x <= 0){
            if (this.getLinearVelocity().y >= speed * -1 && this.getLinearVelocity().y <= 0){
                this.setLinearVelocity(new Vec2(speed*-1, speed*-1));
            }else{
                this.setLinearVelocity(new Vec2(speed*-1, speed));
            }
        }else {
            if (this.getLinearVelocity().y >= speed * -1 && this.getLinearVelocity().y <=0){
                this.setLinearVelocity(new Vec2(speed, speed*-1));
            }else{
                this.setLinearVelocity(new Vec2(speed, speed));
            }
        }
    }
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius(float ballRadius) {
        this.ballRadius = ballRadius;
    }

}
