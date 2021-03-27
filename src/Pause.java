import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Pause {
    private JPanel mainPanel;
    private JButton resumeButton;
    private JButton settingsButton;
    private JButton mainMenuButton;
    private JButton saveButton;

    public Pause(Game game, GameWorld world){
        resumeButton.setFocusable(false);
        settingsButton.setFocusable(false);
        mainMenuButton.setFocusable(false);
        mainPanel.setBackground(Color.BLACK);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    world.Save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ResumeGame();

            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowSettings();

            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowMainMenu();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
