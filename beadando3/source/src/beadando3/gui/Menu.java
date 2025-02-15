package beadando3.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * A displayed Menu with some options
 */
public class Menu extends JMenuBar{

    /**
     * A menu to create a new game, view scores or exit the game
     * @param newGame The action performed when selecting the new game menu item
     * @param viewScores The action performed when selecting the Scores menu item
     */
    public Menu(ActionListener newGame, ActionListener viewScores) {
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem scoresItem = new JMenuItem("Scores");

        newItem.addActionListener(newGame);
        exitItem.addActionListener(e -> System.exit(0));
        scoresItem.addActionListener(viewScores);

        fileMenu.add(newItem);
        fileMenu.add(exitItem);
        fileMenu.add(scoresItem);

        add(fileMenu);
    }
}
