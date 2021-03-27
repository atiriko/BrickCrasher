import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import java.awt.*;
import java.util.Random;

public class Upgrade extends DynamicBody {
    /**Under Construction*/
    private static final Shape shape = new CircleShape(0f);
    private static UpgradeType upgradeType = UpgradeType.ExtraLife;
    private GameWorld gameWorld;
    private PlayerBrick playerBrick;
    Random random = new Random();


    //  slow,  speed,  bigger player brick, smaller player brick,
    // laser player brick, glue player brick,  triple ball, safety net
    // extra life
    enum UpgradeType{
        SlowBall, FastBall, LongerPlayer, SmallerPlayer, Laser, Glue, TripleBall, SafetyNet, ExtraLife
  }
    public Upgrade(GameWorld w, Vec2 position, PlayerBrick playerBrick) {
        super(w, shape);
        gameWorld = w;
        setPosition(position);
        setName("upgrade");
        setGravityScale(0.5f);
        this.playerBrick = playerBrick;
        //RandomUpgradeType()
        switch (RandomUpgradeType()){
            case SlowBall:
                addImage(new BodyImage("data/SlowBall.png", 2));
                upgradeType = UpgradeType.SlowBall;
                break;
            case FastBall:
                addImage(new BodyImage("data/FastBall.png", 2));
                upgradeType = UpgradeType.FastBall;
                break;
            case LongerPlayer:
                addImage(new BodyImage("data/LongerPlayer.png", 2));
                upgradeType = UpgradeType.LongerPlayer;
                break;
            case SmallerPlayer:
                addImage(new BodyImage("data/SmallerPlayerd.png", 2));
                upgradeType = UpgradeType.SmallerPlayer;
                break;
            case Laser:
                addImage(new BodyImage("data/PlayerLaser.png", 2));
                upgradeType = UpgradeType.Laser;
                break;
            case Glue:

                break;
            case TripleBall:
                addImage(new BodyImage("data/ThreeBalls.png", 2));
                upgradeType = UpgradeType.TripleBall;
                break;
            case ExtraLife:
                addImage(new BodyImage("data/ExtraLife.png", 2));
                upgradeType = UpgradeType.ExtraLife;
                break;
            case SafetyNet:

                break;
        }


        addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                if(collisionEvent.getOtherBody().getName().equals("bottomWall")){
                    destroy();
                }
                if(collisionEvent.getOtherBody().getName().equals("playerBrick")){
                    destroy();
                    switch (upgradeType){
                        case ExtraLife -> ExtraLife();
                        case FastBall -> FastBall();
                        case SlowBall -> SlowBall();
                        case LongerPlayer -> LongerPlayer();
                        case SmallerPlayer -> SmallerPlayer();
                        case TripleBall -> gameWorld.TripleBall();
                    }
                }
            }
        });

    }
    UpgradeType RandomUpgradeType(){
        switch (random.nextInt(6)){
            case 1:
                return UpgradeType.SlowBall;
            case 2:
                return UpgradeType.FastBall;
            case 3:
                return UpgradeType.LongerPlayer;
            case 4:
                return UpgradeType.SmallerPlayer;
            case 5:
                return UpgradeType.TripleBall;
            case 6:
                return UpgradeType.ExtraLife;
            default:
                return UpgradeType.ExtraLife;
        }
    }
    void FastBall(){
        float ballSpeed = gameWorld.getBallSpeed();
        gameWorld.setBallSpeed(ballSpeed*2);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameWorld.setBallSpeed(ballSpeed);
                    }
                },
                4000
        );

    }
    void SlowBall(){
        float ballSpeed = gameWorld.getBallSpeed();
        gameWorld.setBallSpeed(ballSpeed/2);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameWorld.setBallSpeed(ballSpeed);
                    }
                },
                4000
        );

    }
    void LongerPlayer(){
        gameWorld.LongerPlayer();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameWorld.ResetPlayerSize();
                    }
                },
                4000
        );

    }
    void SmallerPlayer(){
        gameWorld.SmallerPlayer();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameWorld.ResetPlayerSize();
                    }
                },
                4000
        );

    }
    void ExtraLife(){
        gameWorld.UpdateNumberOfLivesTo(gameWorld.getNumberOfLives()+1);
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

}
