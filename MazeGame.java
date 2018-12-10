import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Maze Game
 *
 * INFO1103 Assignment 2
 * 2017 Semester 1
 *
 * The Maze Game.
 * In this assignment you will be designing a maze game.
 * You will have a maze board and a player moving around the board.
 * The player can step left, right, up or down.
 * However, you need to complete the maze within a given number of steps.
 *
 * As in any maze, there are walls that you cannot move through. If you try to
 * move through a wall, you lose a life. You have a limited number of lives.
 * There is also gold on the board that you can collect if you move ontop of it.
 *
 * Please implement the methods provided, as some of the marks are allocated to
 * testing these methods directly.
 *
 * @author YOU :)
 * @date April, 2017
 *
 */
public class MazeGame {
    /* You can put variables that you need throughout the class up here.
     * You MUST INITIALISE ALL of these class variables in your initialiseGame
     * method.
     */

    // A sample variable to show you can put variables here.
    // You would initialise it in initialiseGame method.
    // e.g. Have the following line in the initialiseGame method.
    // sampleVariable = 1;
    static int numLives;
    static int numSteps;
    static int gold;
    static char[][] board;
    static String fileName;
    static int[] playerPosition;
    static int[] goalPosition;


    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", load the default
     * game configuration.
     *
     * NOTE: Please also initialise all of your class variables.
     *
     * @args configFileName The name of the game configuration file to read from.
     * @throws IOException If there was an error reading in the game.
     *         For example, if the input file could not be found.
     */
    public static void initialiseGame(String configFileName) throws IOException {
        // TODO: Implement this method.
        fileName = configFileName;

        if (fileName.equals("DEFAULT")){
          numLives = 3;
          numSteps = 20;
          gold = 0;

            board = new char[][]{
            {'#', '#', '#', '#'},
            {'@', '#', '#', '#'},
            {' ', ' ', '#', '#'},
            {'#', ' ', ' ', '#'},
            {'#', '#', ' ', '#'},
            {' ', ' ', '3', '#'},
            {' ', '#', '#', '#'},
            {'&', '#', ' ', ' '},
            {'4', ' ', ' ', ' '},
            {'#', '#', ' ', '#'} };

        playerPosition = new int[2];
        goalPosition = new int[2];

        playerPosition[0] = 7;
        playerPosition[1] = 0;

        goalPosition[0] = 1;
        goalPosition[1] = 0;

        }

        else{
          Scanner inputFile = null;

          try{
            inputFile = new Scanner(new File(fileName));
          }
          catch(IOException e){
            throw new IOException(e);
          }

          String[] tmpArray;
          tmpArray = inputFile.nextLine().split(" ");
          numLives = Integer.parseInt(tmpArray[0]);
          numSteps = Integer.parseInt(tmpArray[1]);
          gold = Integer.parseInt(tmpArray[2]);
          int numRows = Integer.parseInt(tmpArray[3]);

          String currentLine = inputFile.nextLine();
          int lengthOfLine = currentLine.length();

          board = new char[lengthOfLine][numRows];
          playerPosition = new int[2];
          goalPosition = new int[2];

          for (int x = 0; x < lengthOfLine; x++){
            if (currentLine.charAt(x) == '&'){
              playerPosition[0] = x;
              playerPosition[1] = 0;
            }
            else if (currentLine.charAt(x) == '@'){
              goalPosition[0] = x;
              goalPosition[1] = 0;
            }
            board[x][0] = currentLine.charAt(x);
          }

          for (int y = 1; y < numRows; y++){
            currentLine = inputFile.nextLine();
            for (int x = 0; x < lengthOfLine; x++){
              if (currentLine.charAt(x) == '&'){
                playerPosition[0] = x;
                playerPosition[1] = y;
              }
              else if (currentLine.charAt(x) == '@'){
                goalPosition[0] = x;
                goalPosition[1] = y;
              }
              board[x][y] = currentLine.charAt(x);
            }
          }
        }
    }

    /**
     * Save the current board to the given file name.
     * Note: save it in the same format as you read it in.
     * That is:
     *
     * <number of lives> <number of steps> <amount of gold> <number of rows on the board>
     * <BOARD
     * @args toFileName The name of the file to save the game configuration to.
     * @throws IOException If there was an error writing the game to the file.
     */
    public static void saveGame(String toFileName) throws IOException {
        // TODO: Implement this method.
        PrintWriter outputFile;
        try{
          outputFile = new PrintWriter(toFileName);
        }
        catch(IOException e){
          throw new IOException();
        }
        outputFile.println(numberOfLives() + " " + numberOfStepsRemaining() + " " + amountOfGold() + " " + board[0].length);
        for (int y = 0; y < board[0].length; y++){
          for (int x = 0; x < board.length; x++){
            outputFile.print(board[x][y]);
          }
          outputFile.println();
        }
        System.out.println("Successfully saved the current game configuration to '" + toFileName + "'.");
        outputFile.close();
    }

    /**
     * Gets the current x position of the player.
     *
     * @return The players current x position.
     */
    public static int getCurrentXPosition() {
        // TODO: Implement this method.
        return playerPosition[0];
    }

    /**
     * Gets the current y position of the player.
     *
     * @return The players current y position.
     */
    public static int getCurrentYPosition() {
        // TODO: Implement this method.
        return playerPosition[1];
    }

    /**
     * Gets the number of lives the player currently has.
     *
     * @return The number of lives the player currently has.
     */
    public static int numberOfLives() {
        // TODO: Implement this method.
        return numLives;
    }

    /**
     * Gets the number of remaining steps that the player can use.
     *
     * @return The number of steps remaining in the game.
     */
    public static int numberOfStepsRemaining() {
        // TODO: Implement this method.
        return numSteps;
    }

    /**
     * Gets the amount of gold that the player has collected so far.
     *
     * @return The amount of gold the player has collected so far.
     */
    public static int amountOfGold() {
        // TODO: Implement this method.
        return gold;
    }


    /**
     * Checks to see if the player has completed the maze.
     * The player has completed the maze if they have reached the destination.
     *
     * @return True if the player has completed the maze.
     */
    public static boolean isMazeCompleted() {
        // TODO: Implement this method.
        if (getCurrentXPosition() == goalPosition[0] && getCurrentYPosition() == goalPosition[1]){
          return true;
        }
        else{
          return false;
        }
    }

    /**
     * Checks to see if it is the end of the game.
     * It is the end of the game if one of the following conditions is true:
     *  - There are no remaining steps.
     *  - The player has no lives.
     *  - The player has completed the maze.
     *
     * @return True if any one of the conditions that end the game is true.
     */
    public static boolean isGameEnd() {
        // TODO: Implement this method.
        boolean result = false;
        if (numberOfStepsRemaining() <= 0 || numberOfLives() <= 0 || isMazeCompleted()){
          result = true;
        }

        return result;
    }

    /**
     * Checks if the coordinates (x, y) are valid.
     * That is, if they are on the board.
     *
     * @args x The x coordinate.
     * @args y The y coordinate.
     * @return True if the given coordinates are valid (on the board),
     *         otherwise, false (the coordinates are out or range).
     */
    public static boolean isValidCoordinates(int x, int y) {
        // TODO: Implement this method.
        boolean result = true;
        if (x < 0 || x >= board.length){
          result = false;
        }

        if (y < 0 || y >= board[0].length){
          result = false;
        }

        return result;
    }

    /**
     * Checks if a move to the given coordinates is valid.
     * A move is invalid if:
     *  - It is move to a coordinate off the board.
     *  - There is a wall at that coordinate.
     *  - The game is ended.
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     * @return True if the move is valid, otherwise false.
     */
    public static boolean canMoveTo(int x, int y) {
        // TODO: Implement this method.
        boolean result = true;
        if (!(isValidCoordinates(x, y))){
          result = false;
        }
        else if (board[x][y] == '#'){
          result = false;
        }
        else if (isGameEnd()){
          result = false;
        }

        return result;
    }

    /**
     * Move the player to the given coordinates on the board.
     * After a successful move, it prints "Moved to (x, y)."
     * where (x, y) were the coordinates given.
     *
     * If there was gold at the position the player moved to,
     * the gold should be collected and the message "Plus n gold."
     * should also be printed, where n is the amount of gold collected.
     *
     * If it is an invalid move, a life is lost.
     * The method prints: "Invalid move. One life lost."
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     */
    public static void moveTo(int x, int y) {
        // TODO: Implement this method.
        if (canMoveTo(x, y)){
          System.out.println("Moved to (" + x + ", " + y + ").");
          if (Character.isDigit(board[x][y])){
            System.out.println("Plus " + Character.getNumericValue(board[x][y]) + " gold.");
            gold += Character.getNumericValue(board[x][y]);
          }
          board[getCurrentXPosition()][getCurrentYPosition()] = '.';
          board[x][y] = '&';
          playerPosition[0] = x;
          playerPosition[1] = y;
          numSteps--;
        }
      else{
          System.out.println("Invalid move. One life lost.");
          numLives--;
          numSteps--;
        }
    }

    /**
     * Prints out the help message.
     */
    public static void printHelp() {
        // TODO: Implement this method.
        System.out.println("Usage: You can type one of the following commands.");
        System.out.println("help         Print this help message.");
        System.out.println("board        Print the current board.");
        System.out.println("status       Print the current status.");
        System.out.println("left         Move the player 1 square to the left.");
        System.out.println("right        Move the player 1 square to the right.");
        System.out.println("up           Move the player 1 square up.");
        System.out.println("down         Move the player 1 square down.");
        System.out.println("save <file>  Save the current game configuration to the given file.");
    }

    /**
     * Prints out the status message.
     */
    public static void printStatus() {
        // TODO: Implement this method.
        System.out.println("Number of live(s): " + numberOfLives());
        System.out.println("Number of step(s) remaining: " + numberOfStepsRemaining());
        System.out.println("Amount of gold: " + amountOfGold());
    }

    /**
     * Prints out the board.
     */
    public static void printBoard() {
        // TODO: Implement this method.

        for (int y = 0; y < board[0].length; y++){
          for (int x = 0; x < board.length; x++){
            System.out.print(board[x][y]);
          }
          System.out.println();
        }
    }

    /**
     * Performs the given action by calling the appropriate helper methods.
     * [For example, calling the printHelp() method if the action is "help".]
     *
     * The valid actions are "help", "board", "status", "left", "right",
     * "up", "down", and "save".
     * [Note: The actions are case insensitive.]
     * If it is not a valid action, an IllegalArgumentException should be thrown.
     *
     * @args action The action we are performing.
     * @throws IllegalArgumentException If the action given isn't one of the
     *         allowed actions.
     */
    public static void performAction(String action) throws IllegalArgumentException {
        // TODO: Implement this method.
        if (action.trim().equals("")){
          //do nothing
        }
        else if (action.equalsIgnoreCase("help")){
          printHelp();
        }
        else if (action.equalsIgnoreCase("board")){
          printBoard();
        }
        else if (action.equalsIgnoreCase("status")){
          printStatus();
        }
        else if (action.equalsIgnoreCase("left")){
          moveTo((getCurrentXPosition() - 1), getCurrentYPosition());
        }
        else if (action.equalsIgnoreCase("right")){
          moveTo((getCurrentXPosition() + 1), getCurrentYPosition());
        }
        else if (action.equalsIgnoreCase("up")){
          moveTo(getCurrentXPosition(), (getCurrentYPosition() - 1));
        }
        else if (action.equalsIgnoreCase("down")){
          moveTo(getCurrentXPosition(), (getCurrentYPosition() + 1));
        }
        else{
          String[] splitArgs = action.split(" ");
          if (splitArgs[0].equalsIgnoreCase("save")){
            if (splitArgs.length == 2){
              try{
                saveGame(splitArgs[1]);
              }
              catch(IOException e){
                System.out.println("Error: Could not save the current game configuration to '" + splitArgs[1] + "'.");
              }
            }

            else{
              throw new IllegalArgumentException();
            }
          }

          else{
            throw new IllegalArgumentException();
          }
        }

    }

    /**
     * The main method of your program.
     *
     * @args args[0] The game configuration file from which to initialise the
     *       maze game. If it is DEFAULT, load the default configuration.
     */
    public static void main(String[] args) {

      if (args.length < 1){
        System.out.println("Error: Too few arguments given. Expected 1 argument, found 0.");
        System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
        return;
      }

      else if (args.length > 1){
        System.out.println("Error: Too many arguments given. Expected 1 argument, found " + args.length + ".");
        System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
        return;
      }

      else{
        try{
          initialiseGame(args[0]);
        }
        catch(IOException e){
          System.out.println("Error: Could not load the game configuration from 'does_not_exist.txt'.");
          return;
        }

        Scanner input = new Scanner(System.in);
        String command = null;

        while (input.hasNextLine()){
          try{
            command = input.nextLine();
            performAction(command);
            if (isMazeCompleted()){
              System.out.println("Congratulations! You completed the maze!");
              System.out.println("Your final status is:");
              printStatus();
              return;
            }
            if (isGameEnd()){
              if (numberOfStepsRemaining() == 0 && numberOfLives() == 0){
                System.out.println("Oh no! You have no lives and no steps left.");
              }
              else if (numberOfStepsRemaining() == 0){
                System.out.println("Oh no! You have no steps left.");
              }
              else{
                System.out.println("Oh no! You have no lives left.");
              }
              System.out.println("Better luck next time!");
              return;
            }
          }
          catch(IllegalArgumentException e){
            System.out.println("Error: Could not find command '" + command + "'.");
            System.out.println("To find the list of valid commands, please type 'help'.");
          }
        }
        System.out.println("You did not complete the game.");
        return;
      }

        // Run your program (reading in from args etc) from here.
    }

}
