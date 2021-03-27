import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelect {

    private JPanel mainPanel;
    private JPanel selectorPanel;
    private JButton backButton;

    public LevelSelect(Level level, Game game){
        mainPanel.setBackground(Color.black);
        LevelSelectPanel levelSelectPanel = new LevelSelectPanel(level, game);

        selectorPanel.add(levelSelectPanel, BorderLayout.CENTER);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.BackToMainMenu();
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
