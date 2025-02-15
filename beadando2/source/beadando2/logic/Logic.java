package beadando2.logic;

import java.awt.*;

public class Logic {
    private final int gridSize;
    private final Point prey;
    private final Point[] hunters;
    // true = prey's turn, false = hunter's turn
    private boolean turn = true;
    private int steps;

    /**
     * Creates a Logic object
     * @param gridSize The length of one of the sides of a square grid.
     */
    public Logic(int gridSize) {
        this.gridSize = gridSize;
        this.prey = new Point();
        this.hunters = new Point[4];
        for (int i = 0; i < 4; i++) {
            hunters[i] = new Point();
        }
        this.steps = gridSize * 4;
    }

    /**
     * Sets the positions of the prey and hunter pieces
     * @param p The prey piece's position
     * @param positions The 4 positions of the hunters. Must be a list of at least length 4.
     */
    public void initialisePieces(Point p, Point[] positions) {
        prey.setLocation(p);
        for (int i = 0; i < positions.length; i++) {
            hunters[i].setLocation(positions[i]);
        }
    }

    /**
     *
     * @return The number of steps remaining for the game to end.
     */
    public int getSteps() {
        return steps;
    }

    private Point getHunterAt(Point p) {
        for (Point hunter : hunters) {
            if (hunter.equals(p)) {
                return hunter;
            }
        }
        return null;
    }

    /**
     * Determines whether it's the selected piece's turn or not
     * @param p The position of the piece
     * @return Whether it's the selected piece's turn
     */
    public boolean isCurrentPiece(Point p) {
        if (turn) {
            return prey.equals(p);
        } else {
            return getHunterAt(p) != null;
        }
    }

    private boolean isValidMove(Point from, Point to) {
        if (from.x == to.x && from.y == to.y) {
            return false;
        }

        if (Math.abs(from.x - to.x) + Math.abs(from.y - to.y) != 1) {
            return false;
        }

        Point hunter = getHunterAt(to);
        return hunter == null && !prey.equals(to);
    }

    /**
     * Calculates the validity of the move made by the user, modifies it's inner state,
     * then sends back the information relevant to the move made in a Move object.
     * @param from The starting point of the player's move.
     * @param to The destination point of the player's move.
     * @return null if the move was invalid and there was no state change,
     * or a Move object containing the information relevant to the player's move.
     */
    public Move makeMove(Point from, Point to) {
        Move m = null;
        if (turn) {
            if (prey.equals(from) && isValidMove(from, to)) {
                Move move = new Move(true, from, to);
                prey.setLocation(to);
                turn = false;
                m = move;
            }
        } else {
            Point hunter = getHunterAt(from);
            if (hunter == null) { return null; }
            if (isValidMove(from, to)) {
                Move move = new Move(false, from, to);
                hunter.setLocation(to);
                turn = true;
                m = move;
                steps--;
            }
        }
        return m;
    }

    /**
     * Checks if prey is unable to move
     * @return Wether prey is unable to move
     */
    public boolean isPreyTrapped() {
        int[] offsetsX = {0,1,0,-1};
        int[] offsetsY = {-1,0,1,0};
        for (int i = 0; i < 4; i++) {
            Point pos = prey.getLocation();
            pos.translate(offsetsX[i], offsetsY[i]);
            if (!(pos.x < 0 || pos.x >= gridSize || pos.y < 0 || pos.y >= gridSize || getHunterAt(pos) != null)) {
                return false;
            }
        }
        return true;
    }
}
