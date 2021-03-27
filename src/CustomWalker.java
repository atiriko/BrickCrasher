import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class CustomWalker extends DynamicBody {
    private final CustomWalker.Legs legs;
    private boolean walking;

    /** Creates a custom walker class extending Dynamic Body. I used this for my player to be
     * able to change its mass so that it wouldn't start falling down everytime the ball hits it.*/

     public CustomWalker(World world) {
        super(world);
        this.setAngularVelocity(0);
        this.legs = new CustomWalker.Legs();
        this.walking = false;
    }

    public CustomWalker(World world, Shape shape) {
        this(world);
        SolidFixture fixture = new SolidFixture(this, shape, 900.0F);
        fixture.setFriction(0.0F);
    }

    public void jump(float speed) {
        Vec2 v = this.getLinearVelocity();
        if (Math.abs(v.y) < 0.01F) {
            this.setLinearVelocity(new Vec2(v.x, speed));
        }

    }

    public void startWalking(float speed) {
        if (!this.walking) {
            this.getWorld().addStepListener(this.legs);
            this.walking = true;
        }

        this.legs.setSpeed(speed);
    }

    public void stopWalking() {
        if (this.walking) {
            this.getWorld().removeStepListener(this.legs);
            this.walking = false;
        }

    }

    public void destroy() {
        this.stopWalking();
        super.destroy();
    }

    private class Legs implements StepListener {
        private float speed = 0.0F;

        public Legs() {
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public void preStep(StepEvent e) {
            Vec2 v = CustomWalker.this.getLinearVelocity();
            CustomWalker.this.setLinearVelocity(new Vec2(this.speed, v.y));
        }

        public void postStep(StepEvent e) {
           // CustomWalker.this.setAngularVelocity(0);
        }
    }
}

