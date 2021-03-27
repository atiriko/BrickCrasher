
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/***
 * This is the class that holds the game world
 */
public class GameWorld extends World {

    private boolean isGameRunning = false;
    private int currentLevel = 0;
    private int numberOfBricks = 0;
    private int numberOfLives = 3;
    private int numberOfBalls = 1;


    private Level level;
    private PlayerBrick playerBrick = new PlayerBrick(this);
    private final Walls leftWall = new Walls(this, Walls.position.LEFT);
    private final Walls rightWall = new Walls(this, Walls.position.RIGHT);
    private final Walls topWall = new Walls(this, Walls.position.TOP);
    private final Walls bottomWall = new Walls(this, Walls.position.BOTTOM);
    private final Body[] hearthBody = new Body[4];
    private final Brick[] bricks = new Brick[11];
    private final Ball[] balls = new Ball[4];
    private Random random = new Random();
    private int ballHitThePlayer = 0;
    private int initialNumberOfBricksAtLevel = 0;
    private ScoreView scoreView = new ScoreView(0);
    private int score = 0;
    private Settings settings;
    Game game;

    /***
     * Initialises a new world with settings
     * @param settings takes a Settings object for the settings of the world
     * @param game takes a Game object
     */
    public GameWorld(Settings settings, Game game)  {
        GetLevel();
        this.game = game;
        this.settings = settings;
        ShowLives();


    }

    /***
     * Initialises a new world with settings and at a level
     * @param settings takes a Settings object for the settings of the world
     * @param level takes an integer and starts the world at that level
     * @param game takes a Game object
     */
    public GameWorld(Settings settings, int level, Game game)  {
        currentLevel = level;
        GetLevel();
        this.settings = settings;
        this.game = game;
        ShowLives();


    }

    /***
     * Initialises a new world from saved string
     * @param settings takes a Settings object for the settings of the world
     * @param data is a comma delimited String containing balls position, balls velocity
     *             , player score, number of lives left and current level information.
     * @param game takes a Game object
     */
    public GameWorld(Settings settings, String data, Game game){
        String[] properties = data.split(",");

        balls[0] = new Ball(this, playerBrick.getPosition());

        score = Integer.parseInt(properties[1]);
        numberOfLives = Integer.parseInt(properties[2]);
        balls[0].setPosition(new Vec2(Float.parseFloat(properties[3]), Float.parseFloat(properties[4])));
        balls[0].setLinearVelocity(new Vec2(Float.parseFloat(properties[5]), Float.parseFloat(properties[6])));
        currentLevel = Integer.parseInt(properties[9]);

        level = new Level(this, currentLevel);
        this.settings = settings;
        this.game = game;
        initialNumberOfBricksAtLevel = numberOfBricks;
        ShowLives();
        isGameRunning = true;
    }

    /***
     * Uptade the number of lives to Number
     * @param Number
     */
    public void UpdateNumberOfLivesTo(int Number){
        ResetLives();
        numberOfLives = Number;
        ShowLives();
    }

    /***
     * Shows lives on the screen
     */
    public void ShowLives(){
        for(int i=0; i<numberOfLives; i++){
            HealthIcon healthIcon = new HealthIcon(new StaticBody(this),i);
            hearthBody[i] = healthIcon.getBody();
        }

    }

    /***
     * Resets all the lives
     */
    public void ResetLives(){
        for(int i=0; i<numberOfLives; i++){
            hearthBody[i].destroy();
        }

    }

    /***
     * Saves current state of the game as a comma delimited String containing balls position, balls velocity
     *      *             , player score, number of lives left and current level information.
     * @throws IOException
     */
    public void Save() throws IOException{
            FileWriter writer = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
                writer = new FileWriter("data/"+now+".txt");
                writer.write(numberOfBricks +
                        "," + score +
                        "," + numberOfLives +
                        "," + balls[0].getPosition().x +
                        "," + balls[0].getPosition().y +
                        "," + balls[0].getLinearVelocity().x +
                        "," + balls[0].getLinearVelocity().y +
                        "," + playerBrick.getPosition().x +
                        "," + playerBrick.getPosition().y +
                        "," + currentLevel
                );
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

    /***
     *  This gets called when the game starts and when the player proceeds to next level.
     */
    public void GetLevel(){

        balls[0] = new Ball(this, playerBrick.getPosition());
        level = new Level(this, currentLevel);
        initialNumberOfBricksAtLevel = numberOfBricks;

    }

    /***
     * Calculates what happens when the ball hits the bottom wall
     */
    public void BallHitTheBottomWall(){
        numberOfBalls--;
        if (numberOfBalls == 0) {
            UpdateNumberOfLivesTo(numberOfLives - 1);
            if (numberOfLives == 0) {
                //If player has no lives left restart the game
                playSound("data/GameOver.wav");
                game.ShowPlayerDied();
                score = 0;

                //level.RestartTheGame(this);
            } else {
                playSound("data/LostHealth.wav");
            }
            isGameRunning = false;
            playerBrick.setPosition(new Vec2(0, -10));
            balls[0] = new Ball(this, playerBrick.getPosition());
            numberOfBalls = 1;
        }
    }

    /***
     * Proceeds to the next level
     */
    public void NextLevel(){
        for (Ball ball : balls) {
            if(ball != null) {
                ball.destroy();
            }
        }

        isGameRunning = false;
        playerBrick.setPosition(new Vec2(0,-10));
        currentLevel++;
        GetLevel();
    }

    /***
     * Drops an upgrade if the player hit the ball more than 1/4 of the number of bricks
     * @param brick
     */
    public void DropPowersIfNecessary(Brick brick){
        if(ballHitThePlayer >= initialNumberOfBricksAtLevel/4 && numberOfBricks != 1){
            Upgrade upgrade = new Upgrade(this, brick.getPosition(), playerBrick);
            ballHitThePlayer = 0;

        }
    }

    /***
     * Returns player back to original size
     */
    public void ResetPlayerSize(){
        playerBrick.destroy();
        Vec2 playerPosition = playerBrick.getPosition();
        playerBrick = new PlayerBrick(this, playerPosition);
    }

    /***
     * Makes the player 1.5 times wider
     */
    public void LongerPlayer(){
        float playerWidth = playerBrick.getWidth();
        Vec2 playerPosition = playerBrick.getPosition();
        playerBrick.destroy();
        playerBrick = new PlayerBrick(this, playerWidth* 1.5f, playerPosition, true);
    }

    /***
     * Makes the player 1.5 times thinner
     */
    public void SmallerPlayer() {
        float playerWidth = playerBrick.getWidth();
        Vec2 playerPosition = playerBrick.getPosition();
        playerBrick.destroy();
        playerBrick = new PlayerBrick(this, playerWidth / 1.5f, playerPosition, false);
    }

    /***
     * Spawns three more balls
     */
    public void TripleBall(){
        for(int i = 1; i<4; i++) {
            balls[i] = new Ball(this, this.balls[0].getPosition());
            balls[i].Bounce(Direction.TOP);
            numberOfBalls++;
        }

    }

    /***
     * Decides what to do depending on the bricks level, when the ball hits a brick.
     * @param brick is the brick that's been hit.
     */
    public void CalculateWhatHappensToBrickOnCollision(Brick brick){
        score = score + ((initialNumberOfBricksAtLevel - numberOfBricks) * numberOfLives + currentLevel);
        switch ( brick.getLevel()){
            case 0:
                DropPowersIfNecessary(brick);
                playSound("data/BrickBreak.wav");
                brick.destroy();
                numberOfBricks--;
                break;
            case 1:
                playSound("data/BrickBreak.wav");
                brick.setLevel(0);
                brick.removeAllImages();
                brick.addImage(new BodyImage("data/brickBroken.png", Brick.getHeight()));
                break;
            case 2:
                playSound("data/BrickBreak.wav");
                brick.setLevel(1);
                brick.removeAllImages();
                brick.addImage(new BodyImage("data/brickCracked.png", Brick.getHeight()));
                break;
            case 3:
                playSound("data/metal.wav");
                break;

        }

    }

    /***
     * Starts the ball at the start of the game
     */
    public void StartGame(){
        if (!isGameRunning){
            balls[0].startBall();
            isGameRunning = true;
        }

    }
    enum Direction{
        TOP,BOTTOM,LEFT,RIGHT,UNKNOWN
    }

    /***
     *  Calculates the impact direction between two body using their positions.
     * @param BodyPosition position of the body that the ball hits
     * @param BallPosition position of the ball
     * @return
     */
    public Direction CalculateImpactDirection(Vec2 BodyPosition, Vec2 BallPosition){
        float width = Brick.getWidth();
        float height = Brick.getHeight();

        if (BallPosition.x >= BodyPosition.x-width/2 && BallPosition.x <= BodyPosition.x+width/2 && BallPosition.y <= BodyPosition.y){
            return Direction.BOTTOM;
        }
        else if (BallPosition.x >= BodyPosition.x-width/2 && BallPosition.x <= BodyPosition.x+width/2 && BallPosition.y >= BodyPosition.y){
            return Direction.TOP;
        }
        else if (BallPosition.y >= BodyPosition.y-height/2 && BallPosition.y <= BodyPosition.y+height/2 && BallPosition.x <= BodyPosition.x){
            return Direction.LEFT;
        }
        else if (BallPosition.y >= BodyPosition.y-height/2 && BallPosition.y <= BodyPosition.y+height/2 && BallPosition.x >= BodyPosition.x){
            return Direction.RIGHT;
        }
        else{
            return Direction.UNKNOWN;
        }
    }

    /***
     * Moves the player at the direction
     * @param direction
     */
    public void MovePlayer(PlayerBrick.direction direction){
        switch (direction) {
            case LEFT:
                playerBrick.MovePlayer(PlayerBrick.direction.LEFT);
                break;

            case RIGHT:
                playerBrick.MovePlayer(PlayerBrick.direction.RIGHT);
                break;

        }
    }

    /***
     * Stops the player
     */
    public void StopPlayer(){
       playerBrick.StopPlayer();

    }

    /***
     * sets all the balls speeds
     * @param speed it is float for the speed value
     */
    public void setBallSpeed(float speed){
        for (Ball ball : balls) {
            if (ball != null){
                ball.setSpeed(speed);

            }
        }
    }
    public float getBallSpeed(){
        return balls[0].getSpeed();
    }
    public boolean isGameRunning() {
        return isGameRunning;
    }
    void IncrementBallHitThePlayer(){
        ballHitThePlayer++;
    }


    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }
    void IncrementNumberOfBricks(){
        numberOfBricks++;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getNumberOfBricks() {
        return numberOfBricks;
    }
    int getScore(){
        return score;
    }


    public void setNumberOfBricks(int numberOfBricks) {
        this.numberOfBricks = numberOfBricks;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }
    public Level getLevel() {
        return level;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }
    public int getInitialNumberOfBricksAtLevel() {
        return initialNumberOfBricksAtLevel;
    }

    public void setInitialNumberOfBricksAtLevel(int initialNumberOfBricksAtLevel) {
        this.initialNumberOfBricksAtLevel = initialNumberOfBricksAtLevel;
    }

    /***
     * Plays a sound file. The file needs to be .wav file
     * @param soundFile name of the file.
     */
    public void playSound(String soundFile) {
        if (settings.isSoundOn()) {
            File f = new File("./" + soundFile);
            AudioInputStream audioIn = null;
            try {
                audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(audioIn);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clip.start();
        }
    }
}
