import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

public class ScoreView extends JLabel{
    ScoreView(int Score){
        this.setText("Score: " + Score);
        this.setForeground(Color.RED);
        this.setBackground(Color.BLACK);

    }
}
