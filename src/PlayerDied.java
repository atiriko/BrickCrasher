import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerDied {


    private JPanel mainPanel;
    private JTextField playerName;
    private JButton saveHighScoreButton;
    private JButton restartButton;
    private JButton mainMenuButton;
    private JLabel gameOverLabel;

    public PlayerDied(Game game,  int Score) {
        mainPanel.setBackground(Color.black);
        playerName.setText("Your Name?");
        playerName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (playerName.getText().equals("Your Name?")) {
                    playerName.setText("");
                    playerName.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (playerName.getText().isEmpty()) {
                    playerName.setForeground(Color.GRAY);
                    playerName.setText("Your Name?");
                }
            }
        });
        saveHighScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                try {
                    SaveHighScore(playerName.getText(), Score);
                    game.RestartGame();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.RestartGame();
            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowMainMenuFromDead();
            }
        });

    }
    void SaveHighScore(String name, int score) throws IOException{

        FileWriter writer = null;
        try {
            writer = new FileWriter("data/HighScores.txt", true);
            writer.write(name + ": " + score + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
