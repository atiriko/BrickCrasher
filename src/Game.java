import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Game implements KeyListener {

    private GameView gameView;

    private final JFrame frame = new JFrame("Brick Crasher");
    private MainMenu mainMenu = new MainMenu(this);
    private Settings settings = new Settings(this);
    private GameWorld world = new GameWorld(settings, this);
    private Pause pause = new Pause(this, world);
    private HighScores highScores = new HighScores(this);
    private JPanel highScoresPanel = highScores.getMainPanel();
    private PlayerDied playerDied = new PlayerDied(this,0);
    private LevelSelect levelSelect = new LevelSelect(world.getLevel(), this);
    private JPanel levelsPanel = levelSelect.getMainPanel();
    private JPanel settingsPanel = settings.getMainPanel();
    private JPanel mainMenuPanel = mainMenu.getMainPanel();
    private Boolean isPaused = false;

    /***
     * Initialise a new Game
     */
    public Game() {
        // add the view to a frame (Java top level window)


        frame.add(mainMenuPanel);

        frame.setPreferredSize(new Dimension(525, 525));



        //add key listener to the frame
        frame.addKeyListener(this);


        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        frame.setFocusable(true);

        // uncomment this to make a debugging view
        //JFrame debugView = new DebugViewer(world, 500, 500);

        // start our game world simulation!
    }
    /***
     * Starts the game
     */
    public void StartGame(){
        world = new GameWorld(settings, this);
        // make a view
        gameView = new GameView(world, 525, 500);
        gameView.setZoom(20);

        // uncomment this to draw a 1-metre grid over the view
        // view.setGridResolution(1);

        // add some mouse actions
        gameView.addMouseListener(new MouseHandler(gameView));

        frame.remove(mainMenuPanel);
        frame.add(gameView);
        frame.revalidate();
        world.start();

    }
    /***
     * Restarts the game
     */
    public void RestartGame(){
        world = new GameWorld(settings, this);
        // make a view
        gameView = new GameView(world, 525, 500);
        gameView.setZoom(20);

        // uncomment this to draw a 1-metre grid over the view
        // view.setGridResolution(1);

        // add some mouse actions
        gameView.addMouseListener(new MouseHandler(gameView));

        frame.remove(playerDied.getMainPanel());
        frame.add(gameView);
        frame.revalidate();
        world.start();

    }

    /***
     * Starts the game at a specific level
     * @param level is to level to start at
     */
    public void StartGameAt(int level){
        world = new GameWorld(settings, level-1, this);
        // make a view
        gameView = new GameView(world, 525, 500);
        gameView.setZoom(20);

        // add some mouse actions
        gameView.addMouseListener(new MouseHandler(gameView));

        frame.remove(levelsPanel);
        frame.add(gameView);
        frame.revalidate();
        world.start();

    }
    /***
     * initialises the game with saved data
     * @param data saved data
     */
    public void LoadGame(String data){
        world = new GameWorld(settings, data, this);
        // make a view
        gameView = new GameView(world, 525, 500);
        gameView.setZoom(20);

        // add some mouse actions
        gameView.addMouseListener(new MouseHandler(gameView));

        frame.remove(mainMenuPanel);
        frame.add(gameView);
        frame.revalidate();
        world.start();
    }


    /***
     * Shows game over page
     */
    public void ShowPlayerDied(){
        world.stop();
        playerDied = new PlayerDied(this, world.getScore());
        frame.add(playerDied.getMainPanel());
        frame.revalidate();
        gameView.setVisible(false);

    }
    /***
     * Shows high scores page
     */
    public void ShowHighScores(){
        frame.remove(mainMenuPanel);
        frame.add(highScoresPanel);
        frame.repaint();
        frame.revalidate();
    }
    /***
     * Shows settings page
     */
    public void ShowSettings(){
        frame.remove(mainMenuPanel);
        frame.remove(pause.getMainPanel());
        frame.add(settingsPanel);
        frame.repaint();
        frame.revalidate();
    }
    /***
     * Shows main menu page
     */
    public void ShowMainMenu(){
        settings.setBackToMain(true);
        if (world != null && world.isRunning()){
            frame.remove(gameView);
            world.stop();
        }
        frame.remove(highScoresPanel);
        frame.remove(pause.getMainPanel());
        frame.add(mainMenuPanel);
        frame.repaint();

    }
    /***
     * Shows main menu from game over page
     */
    public void ShowMainMenuFromDead(){
        settings.setBackToMain(true);
        if (world != null && world.isRunning()){
            frame.remove(gameView);
            world.stop();
        }
        frame.remove(playerDied.getMainPanel());
        frame.add(mainMenuPanel);
        frame.repaint();
    }
    /***
     * Show level selection page
     */
    public void ShowLevelSelect(){
        frame.remove(mainMenuPanel);
        frame.add(levelsPanel);
        frame.repaint();
        frame.revalidate();
    }

    /***
     * Pauses the game
     */
    public void PauseGame(){
        settings.setBackToMain(false);
        pause = new Pause(this, world);
        world.stop();
        frame.add(pause.getMainPanel());
        frame.revalidate();
        gameView.setVisible(false);
    }

    /***
     * from settings page goes back to pause page
     */
    public void BackToPause(){
        frame.remove(settingsPanel);
        frame.add(pause.getMainPanel());
        frame.repaint();

    }
    /***
     * from main menu page goes back to pause page
     */
    public void BackToMainMenu(){
        frame.remove(levelsPanel);
        frame.remove(settingsPanel);
        frame.add(mainMenuPanel);
        frame.repaint();

    }

    /***
     * Resumes the game from pause menu
     */
    public void ResumeGame(){
        frame.remove(pause.getMainPanel());
        gameView.setVisible(true);
        world.start();


    }

    /** Run the game. */
     public static void main(String[] args) {

        new Game();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /***
     * Listen to key presses
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
            if(settings.isInverted()){
                world.MovePlayer(PlayerBrick.direction.RIGHT);

            }else{
                world.MovePlayer(PlayerBrick.direction.LEFT);
            }
        }
        if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D'){
            if(settings.isInverted()){
                world.MovePlayer(PlayerBrick.direction.LEFT);

            }else{
                world.MovePlayer(PlayerBrick.direction.RIGHT);
            }
        }
        if (e.getKeyChar() == ' '){
            world.StartGame();
        }
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE){
            isPaused = !isPaused;
            if (isPaused){
                PauseGame();
            }else{
                ResumeGame();

            }


        }


    }

    /***
     * Listen to key releases
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
           world.StopPlayer();
        }
        if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D'){
            world.StopPlayer();
        }
    }
}
