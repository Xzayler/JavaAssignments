package beadando3.lib.MazeGenerator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * An abstract representation of a perfect maze used for maze creation
 */
public class Maze {

    /**
     * The nodes representing a possible intersection within the maze
     */
    public MazeNode[][] maze;
    private MazeNode origin = null;

    /**
     * Generates a square maze of the given size
     * @param size The height and width of the maze
     */
    public Maze(int size) {
        maze = new MazeNode[size][size];
        initMaze(size);
        for (int i = 0; i < 4; i++) {
            scrambleMaze();
        }
    }

    /**
     * Returns the height and width of the maze
     * @return The length of an edge of the maze
     */
    public int getSize() {
        return maze.length;
    }

    private void initMaze(int size) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = new MazeNode(j,i);
            }
        }

        for (int j = 0; j < size-1; j++) {
            maze[0][j].nextNode = (maze[0][j+1]);
        }

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size-i-1; j++) {
                maze[i][j].nextNode = (maze[i][j+1]);
            }
            for (int j = size-i-1; j < size; j++) {
                maze[i][j].nextNode = (maze[i-1][j]);
            }
        }
        origin = maze[0][size-1];
    }

    private void scrambleMaze() {
        int l = maze.length;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                MazeNode node = maze[i][j];
                // 1. Set node as origin
                changeOrigin(node);

                // 2. Choose a random neighbour

                Direction dir = Direction.getRandomDirection(j,i,l);
                int yOffset = dir.getYOffset();
                int xOffset = dir.getXOffset();

                MazeNode nextNode = maze[i+yOffset][j+xOffset];

                node.nextNode = nextNode;

                // 3. Neighbour becomes origin
                origin = nextNode;

                // 4. Remove pointer of origin
                nextNode.nextNode = null;


            }
        }
    }

    private void changeOrigin(MazeNode newOrig) {
        if (origin == newOrig) {
            return;
        }
        reversePath(newOrig);
        newOrig.nextNode = null;
        origin = newOrig;
    }

    private void reversePath(MazeNode node) {
        MazeNode next = node.nextNode;
        if (next != origin) {
            reversePath(next);
        }
        next.nextNode = node;
    }

    /**
     * The representation of an intersection within the maze
     */
    public class MazeNode {
        /**
         * The next node linked to the current one
         */
        public MazeNode nextNode = null;
        /**
         * The x grid-coordinate of the node
         */
        public int x;
        /**
         * The y grid-coordinate of the node
         */
        public int y;

        /**
         * Creates an isolated node
         * @param x The x coordinate
         * @param y The y coordinate
         */
        public MazeNode(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
