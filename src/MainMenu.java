import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MainMenu {
    private JButton newGameButton;
    private JButton levelsButton;
    private JButton settingsButton;
    private JButton quitButton;
    private JPanel mainPanel;
    private JLabel brickCrasherLabel;
    private JButton loadGameButton;
    private JButton highScoresButton;

    public MainMenu(Game game){
        newGameButton.setFocusable(false);
        levelsButton.setFocusable(false);
        settingsButton.setFocusable(false);
        loadGameButton.setFocusable(false);

        mainPanel.setBackground(Color.BLACK);

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fc.showOpenDialog(mainPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            game.LoadGame(data);
                        }
                        myReader.close();
                    } catch (FileNotFoundException f) {
                        System.out.println("An error occurred.");
                        f.printStackTrace();
                    }
                } else {
                    System.out.println("User Cancelled");

                }
            }
        });
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.StartGame();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowSettings();
            }
        });
        levelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowLevelSelect();
            }
        });
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowHighScores();
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

}
