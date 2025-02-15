package beadando3.logic.labyrinth;

import beadando3.lib.MazeGenerator.Direction;
import beadando3.lib.MazeGenerator.Maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The main Logic component of the game
 */
public class Labyrinth {

    private Tile[][] tiles;
    private Point playerPosition;
    private Point dragonPosition;
    private Direction dragonDirection;
    private boolean gameOver;
    private Point exit;
    /**
     * The time passed since the start of the run
     */
    public int time;

    private int score = 0;

    /**
     * Initialise the labyrinth based on the Maze model
     * @param maze The maze to base the labyrinth off of
     */
    public Labyrinth(Maze maze) {
        newLabyrinth(maze);
    }

    /**
     * Recreates a Labyrithn based off of a Maze
     * @param maze The maze to base the labyrinth off of
     */
    public void newLabyrinth(Maze maze) {
        int size = maze.getSize();
        Random rand = new Random();
        dragonDirection = Direction.getRandomDirection(1,0,2);
        playerPosition = new Point(0, size - 1);
        exit = new Point(size - 1, 0);
        dragonPosition = new Point(rand.nextInt(size), rand.nextInt(size));
        convertMaze(maze);
        addLoops( 6, size);
        gameOver = false;
    }

    /**
     * Getter for the score prop
     * @return The player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score back to 0
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Getter for the gameOver prop
     * @return Whether the run has ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Getter for the player's position
     * @return The player's position
     */
    public Point getPlayerPosition() {
        return new Point(playerPosition);
    }

    /**
     * Getter for the dragon's position
     * @return The dragon's position
     */
    public Point getDragonPosition() {
        return new Point(dragonPosition);
    }

    private void convertMaze(Maze maze) {
        int size = maze.getSize();
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile();
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                convertNode(maze.maze[i][j]);
            }
        }

    }

    private void convertNode(Maze.MazeNode node) {
        Maze.MazeNode nextNode = node.nextNode;
        if (nextNode == null) {
            return;
        }

        Tile currTile = tiles[node.y][node.x];
        Direction dir;
        if (node.x == nextNode.x) {
            if (node.y == nextNode.y - 1) {
                dir = Direction.DOWN;
            } else {
                dir = Direction.UP;
            }
        } else {
            if (node.x == nextNode.x - 1) {
                dir = Direction.RIGHT;
            } else {
                dir = Direction.LEFT;
            }
        }

        int yOffset = dir.getYOffset();
        int xOffset = dir.getXOffset();

        Tile.connect(currTile, tiles[node.y + yOffset][node.x + xOffset], dir);
    }

    private void addLoops(int n, int size) {
        ArrayList<Direction> allDirs = new ArrayList<>(Arrays.asList(Direction.values()));
        Random random = new Random();
        for (int i = 0; i < n; i++) {

            // Choose a random tile
            int randX = random.nextInt(size);
            int randY = random.nextInt(size);

            ArrayList<Direction> possibleDirs = (ArrayList<Direction>) allDirs.clone();
            if (randY == 0) {
                possibleDirs.remove(Direction.UP);
            } else if (randY == size - 1) {
                possibleDirs.remove(Direction.DOWN);
            }
            if (randX == 0) {
                possibleDirs.remove(Direction.LEFT);
            } else if (randX == size - 1) {
                possibleDirs.remove(Direction.RIGHT);
            }

            Tile tile = tiles[randY][randX];

            possibleDirs.removeIf(tile::hasNeighbour);
            if (possibleDirs.isEmpty()) {
                i--;
                continue;
            }
            Direction dir = possibleDirs.get(random.nextInt(possibleDirs.size()));
            int yOffset = dir.getYOffset();
            int xOffset = dir.getXOffset();

            Tile.connect(tile, tiles[randY + yOffset][randX + xOffset], dir);
        }
    }

    /**
     * Getter for the tiles prop
     * @return The Tiles of the Labyrinth
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Handles a move attempt by the player
     * @param dir The direction of the attempted move
     * @return Whether the move was valid
     */
    public boolean movePlayer(Direction dir) {
        Tile currTile = tiles[playerPosition.y][playerPosition.x];
        if (currTile.hasNeighbour(dir)) {
            playerPosition.x += dir.getXOffset();
            playerPosition.y += dir.getYOffset();
            checkGameOver();
            return true;
        }
        return false;
    }

    /**
     * Handles the move by the dragon. Will change direction if hitting a wall
     */
    public void moveDragon() {
        Tile cTile = tiles[dragonPosition.y][dragonPosition.x];
        if (cTile.hasNeighbour(dragonDirection)) {
            dragonPosition.x += dragonDirection.getXOffset();
            dragonPosition.y += dragonDirection.getYOffset();
        } else {
            dragonDirection = Direction.getRandomDirection(dragonPosition.x, dragonPosition.y, tiles.length);
            moveDragon();
        }
        checkGameOver();
    }

    private void checkGameOver() {
        Tile playerTile = tiles[playerPosition.y][playerPosition.x];
        for (Direction dir : Direction.values()) {
            if (playerTile.hasNeighbour(dir)) {
                if (dragonPosition.x == playerPosition.x + dir.getXOffset() && dragonPosition.y == playerPosition.y + dir.getYOffset()) {
                    gameOver = true;
                }
            }
        }
    }

    /**
     * Check if the labyrinth has been escaped successfully
     * @return Whether the labyrinth has been solved
     */
    public boolean hasWon() {
        boolean res = playerPosition.x == exit.x && playerPosition.y == exit.y;
        if (res) {
            score++;
        }
        return res;
    }

}
