import city.cs.engine.*;
import org.jbox2d.common.Vec2;
public class PlayerBrick extends CustomWalker {

    private static final float  x = 0;
    private static final float  y = -10;
    private static final float playerSpeed = 8;
    private static float halfWidth = 2.5f;
    private static Shape playerShape = new BoxShape(halfWidth, 0.5f);
    private World world;


    /***
     * Initialise the player brick
     * @param world in witch the player is created
     */
    public PlayerBrick(World world) {
        super(world, playerShape);
        this.world = world;
        setPosition(new Vec2(x,y));
        setGravityScale(0);
        setName("playerBrick");
        addImage(new BodyImage("data/PlayerBrick.png"));

    }

    /***
     * Initialise the player brick at a position
     * @param world in witch the player is created
     * @param position at witch the player is created
     */
    public PlayerBrick(World world, Vec2 position) {
        super(world, playerShape);
        this.world = world;
        setPosition(position);
        setGravityScale(0);
        setName("playerBrick");
        addImage(new BodyImage("data/PlayerBrick.png"));



    }

    /***
     * Initialise a longer or shorter player brick at a position
     * @param world in witch the player is created
     * @param width of the player
     * @param position at witch the player is created
     * @param isLong whether it is long or not
     */
    public PlayerBrick(World world, float width, Vec2 position, boolean isLong) {
        super(world, new BoxShape(width/2, 0.5f));
        this.world = world;
        setPosition(position);
        setGravityScale(0);
        setName("playerBrick");
        if (isLong){
            addImage(new BodyImage("data/PlayerBrickLong.png"));
        }else{
            addImage(new BodyImage("data/PlayerBrickSmall.png"));

        }



    }

    /***
     * Stops player, resets its y position and stops it from spinning
     */
    void StopPlayer(){
        this.stopWalking();
        this.setLinearVelocity(new Vec2(0,0));
        this.setAngleDegrees(0);
        this.setAngularVelocity(0);
        this.setPosition(new Vec2(this.getPosition().x, -10));

    }

    enum direction{
        LEFT,RIGHT
    }

    /***
     * Move the player towards the direction
     * @param direction
     */
    void MovePlayer(direction direction){
        switch (direction) {
            case LEFT:
                this.startWalking(playerSpeed * -1);
                break;

            case RIGHT:
                this.startWalking(playerSpeed);
                break;

        }
    }
    void setWidth(float width){
        halfWidth = width/2;
        playerShape = new BoxShape(halfWidth, 0.5f);

    }
    float getWidth(){
        return halfWidth*2;
    }


}
