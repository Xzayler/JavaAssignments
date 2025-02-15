package beadando2.controller;

import beadando2.gui.Frame;
import beadando2.gui.Grid;
import beadando2.gui.Label;
import beadando2.logic.Logic;
import beadando2.logic.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Controller {
    private final Color PREY_COLOR = Color.GREEN;
    private final Color HUNTER_COLOR = Color.RED;

    private Logic logic;
    private final Frame frame;

    private Point selectedPoint = null;

    /**
     * Creates the main Controller object
     */
    public Controller() {
        int gridSize = 3;
        this.frame = new Frame(gridSize,this);
        newGame(gridSize);
        frame.setVisible(true);
    }

    private void newGame(int gridSize) {
        this.logic = new Logic(gridSize);
        frame.getLabel().setSteps(gridSize*4);

        final Grid grid = frame.getGrid();
        grid.newGame(gridSize);

        int m = gridSize / 2;
        Point p = new Point(m,m);
        grid.setCellColor(p, PREY_COLOR);
        Point middle = p.getLocation();

        int max = gridSize - 1;
        Point[] positions = new Point[4];

        p = new Point(0,0);
        grid.setCellColor(p, HUNTER_COLOR);
        positions[0] = p.getLocation();

        p = new Point(max,0);
        grid.setCellColor(p, HUNTER_COLOR);
        positions[1] = p.getLocation();

        p = new Point(max,max);
        grid.setCellColor(p, HUNTER_COLOR);
        positions[2] = p.getLocation();

        p = new Point(0,max);
        grid.setCellColor(p, HUNTER_COLOR);
        positions[3] = p.getLocation();

        logic.initialisePieces(middle, positions);
        frame.pack();
    }

    private void newGameDialog() {
        final Integer[] gameSize = new Integer[]{3,5,7};
        final Object dialogRes = JOptionPane.showInputDialog(frame, "Choose the new grid's size", "New Game", JOptionPane.QUESTION_MESSAGE, null,  gameSize, gameSize[1]);
        if (dialogRes == null) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else {
            newGame((int) dialogRes);
        }

    }

    /**
     * The listener assigned to each grid cell.
     */
    public class CellListener implements ActionListener {

        private final Point pos;

        /**
         * Creates a CellListener object
         * @param x The x position, within the grid, of the cell this listener is assigned to
         * @param y The y position, within the grid, of the cell this listener is assigned to
         */
        public CellListener(int x, int y) {
            pos = new Point(x, y);
        }

        /**
         * This function is responsible for reacting to cell clicks, by both updating the model,
         * and updating the view based on the feedback in the model.
         * @param e The ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedPoint == null) {
                if (logic.isCurrentPiece(pos)) {
                    selectedPoint = pos.getLocation();
                }
            } else {
                Move move = logic.makeMove(selectedPoint, pos.getLocation());
                selectedPoint = null;
                if (move == null) { return; }
                Grid grid = frame.getGrid();
                grid.setCellColor(move.previousPos, null);
                grid.setCellColor(move.currentPos, (move.turn ? PREY_COLOR : HUNTER_COLOR ));
                if (!move.turn) {
                    Label label = frame.getLabel();
                    label.setSteps(logic.getSteps());
                    if (logic.isPreyTrapped()) {
                        JOptionPane.showMessageDialog(frame, "Hunters Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        newGameDialog();
                    } else {
                        if (logic.getSteps() == 0) {
                            JOptionPane.showMessageDialog(frame, "Prey Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                            newGameDialog();
                        }
                    }
                }
            }
        }
    }

}
