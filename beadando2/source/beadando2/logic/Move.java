package beadando2.logic;

import java.awt.*;

public class Move {
    /**
     * Whether it was a prey or hunter which moved.
     * true means it was the prey's turn,
     * false means it was a hunter's.
     */
    public boolean turn;
    /**
     * The origin point of the piece's move.
     */
    public Point previousPos;
    /**
     * The destination point of the piece's move.
     */
    public Point currentPos;

    /**
     * Creates a Move object.
     * @param turn Whether it was a prey or hunter which moved.
     * @param previousPos The origin point of the piece's move.
     * @param currentPos The destination point of the piece's move.
     */
    public Move(boolean turn, Point previousPos, Point currentPos) {
        this.turn = turn;
        this.previousPos = previousPos;
        this.currentPos = currentPos;
    }
}
