import city.cs.engine.UserView;
import city.cs.engine.World;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;

public class GameView extends UserView {
    private Image background;
    private GameWorld world;

    //Initialise the game view with background image named "background.png".
    public GameView(GameWorld w, int width, int height) {
        super(w, width, height);
        world = w;
        background = new ImageIcon("data/background.png").getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g){
        g.setColor(Color.WHITE);
        g.drawString("Score: " + world.getScore(), 10, 490);
    }
}
