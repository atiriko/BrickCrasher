import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Settings {


    private JPanel mainPanel;
    private JButton backButton;
    private JCheckBox turnSoundOnOffCheckBox;
    private JCheckBox invertControlsCheckBox;
    private JLabel settingsLabel;

    public void setBackToMain(boolean backToMain) {
        this.backToMain = backToMain;
    }

    private boolean backToMain = true;

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public boolean isInverted() {
        return isInverted;
    }

    private boolean isSoundOn = true;
    private boolean isInverted = false;



    public Settings(Game game){
        backButton.setFocusable(false);
        turnSoundOnOffCheckBox.setFocusable(false);
        invertControlsCheckBox.setFocusable(false);
        mainPanel.setBackground(Color.black);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(backToMain){
                    game.BackToMainMenu();
                }else{
                    game.BackToPause();
                }
            }
        });
        turnSoundOnOffCheckBox.setSelected(true);
        turnSoundOnOffCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    isSoundOn = true;
                }else{
                    isSoundOn = false;
                }
            }
        });
        invertControlsCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    isInverted = true;
                }else{
                    isInverted = false;
                }
            }
        });


    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
