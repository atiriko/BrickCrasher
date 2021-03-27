import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelectPanel extends JPanel {
    public LevelSelectPanel(Level levels, Game game){
        int numberOfLevels = levels.levels.length/2;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        for(int i=0; i < numberOfLevels; i++){
            JButton levelButton = new JButton(String.valueOf(i+1));
            this.add(levelButton);

            levelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.StartGameAt(Integer.parseInt(levelButton.getText()));
                }
            });
        }

        this.setBackground(Color.black);
    }
}
