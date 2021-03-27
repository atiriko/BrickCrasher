/***
 *
 * Holds a three dimensional array of numbers. These numbers represent the level of each brick.
 * Level 0 is breakable with one hit, level 1 is breakable with 2 hits and so on.
 * Level 3 is unbreakable and level 5 represents a space in the grid.
 * This way each level can be represented with 2 dimensional arrays and this makes designing
 * new levels very quickly.
 */
public class Level {


    int [][][] levels = new int[10][][];

    public Brick[] getBricks() {
        return bricks;
    }

    private final Brick[] bricks = new Brick[63];


    int[][] level0 =
            {{ 5, 5, 5, 5, 5, 5, 5 },
            {  0, 0, 0, 0, 0, 0, 0 },
            {  5, 5, 5, 5, 5, 5, 5 },
            {  5, 5, 5, 5, 5, 5, 5 },
            {  5, 0, 5, 0, 5, 0, 5 },
            {  5, 5, 5, 5, 5, 5, 5 },
            {  5, 5, 5, 5, 5, 5, 5 },
            {  5, 5, 5, 5, 5, 5, 5 }
    };

    int[][] level1 =
                    {{ 5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  2, 3, 2, 3, 2, 3, 2 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 }
            };
    int[][] level2 =
                    {{ 3, 3, 3, 3, 3, 3, 3 },
                    {  2, 0, 0, 0, 0, 0, 2 },
                    {  5, 0, 0, 0, 0, 2, 5 },
                    {  5, 5, 2, 0, 2, 5, 5 },
                    {  5, 5, 5, 2, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 },
                    {  5, 5, 5, 5, 5, 5, 5 }
            };
    int[][] level3 =
                  { { 3, 3, 3, 3, 3, 3, 3 },
                    { 3, 3, 3, 2, 3, 3, 3 },
                    { 3, 3, 2, 0, 2, 3, 3 },
                    { 3, 2, 0, 0, 0, 2, 3 },
                    { 2, 0, 0, 0, 0, 0, 2 },
                    { 5, 5, 5, 5, 5, 5, 5 },
                    { 5, 5, 5, 5, 5, 5, 5 },
                    { 5, 5, 5, 5, 5, 5, 5 }
            };
    int[][] level4 =
                  { { 0, 5, 5, 5, 5, 5, 5 },
                    { 0, 0, 5, 5, 5, 5, 5 },
                    { 0, 0, 0, 5, 5, 5, 5 },
                    { 0, 0, 0, 0, 5, 5, 5 },
                    { 0, 0, 0, 0, 0, 5, 5 },
                    { 2, 2, 2, 2, 2, 2, 5 },
                    { 5, 5, 5, 5, 5, 5, 5 },
                    { 5, 5, 5, 5, 5, 5, 5 }
            };
    int[][] level5 =
                  { { 5, 5, 5, 0, 5, 5, 5 },
                    { 5, 5, 0, 0, 0, 5, 5 },
                    { 5, 0, 0, 0, 0, 0, 5 },
                    { 0, 0, 0, 2, 0, 0, 0 },
                    { 5, 2, 0, 0, 0, 2, 5 },
                    { 5, 5, 2, 0, 2, 5, 5 },
                    { 5, 5, 5, 2, 5, 5, 5 },
                    { 5, 5, 5, 5, 5, 5, 5 }
            };

    /***
     * Places the bricks at correct positions for each level using the information
     * from the level parameter which is a 2d array
     * @param world in witch the level is created
     * @param level information as 2d array
     */
    public Level(GameWorld world, int level){
        levels[0] = level0;
        levels[1] = level1;
        levels[2] = level2;
        levels[3] = level3;
        levels[4] = level4;
        levels[5] = level5;



        int numberOfBricks = 0;
        for(int i = 0; i < 8; i++){

            for(int j = 0; j < 7; j++){
                numberOfBricks++;

                if (levels[level][i][j] != 5){
                     bricks[numberOfBricks] = new Brick(world, -11.5f+j*3.8f,12f-i*1.4f, levels[level][i][j]);

                    if(levels[level][i][j] != 3) {
                        world.IncrementNumberOfBricks();
                    }
                }
            }
        }
    }

    /***
     * Restart the game
     * @param world
     */
    public void RestartTheGame(GameWorld world){
        for (Brick brick : bricks) {
            if (brick != null) {
                brick.destroy();
            }
        }
        world.setNumberOfBricks(0);
        world.setGameRunning(false);
        world.setCurrentLevel(0);
        world.GetLevel();
        world.UpdateNumberOfLivesTo(3);
    }
}
