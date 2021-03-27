import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class HighScores {
    private JList<String> highSoreList;
    private JLabel highScoresLabel;
    private JButton backButton;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    public HighScores(Game game){
        mainPanel.setBackground(Color.black);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            for(String score: GetHighScores()){
                    listModel.addElement(score);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        highSoreList.setModel(listModel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.ShowMainMenu();
            }
        });


    }
    String[] GetHighScores() throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader("data/HighScores.txt");
            reader = new BufferedReader(fr);

            List<String> Scores = new ArrayList<String>();
            String line = reader.readLine();

            int i = 1;
            while (line != null) {
                Scores.add(line);
                line = reader.readLine();
                i++;
            }
            Scores.sort(new Comparator<String>() {
                public int compare(String s1, String s2) {
                    return extractInt(s1) - extractInt(s2);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            Collections.reverse(Scores);

            return Scores.toArray(new String[0]);

        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
