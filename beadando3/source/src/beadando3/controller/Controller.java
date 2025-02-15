package beadando3.controller;

import beadando3.gui.*;
import beadando3.lib.MazeGenerator.Direction;
import beadando3.lib.MazeGenerator.Maze;
import beadando3.lib.sql.Db;
import beadando3.logic.labyrinth.Labyrinth;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    private static int MAZE_SIZE = 8;
    private final Frame frame;
    private final Grid grid;
    private final Labyrinth labyrinth;
    private final Overlay overlay;
    private final Timer timer;
    private final Db db = new Db();
    private final JLabel label;

    /**
     * Creates the main Controller object
     * Sets up and initialises GUI Elements and the event handlers
     */
    public Controller() {
        labyrinth = new Labyrinth(new Maze(MAZE_SIZE));
        grid = new Grid(labyrinth.getTiles());
        grid.setPlayer(labyrinth.getPlayerPosition(), new Point());
        grid.setDragon(labyrinth.getDragonPosition(), new Point());
        overlay = new Overlay();
        overlay.setCenter(labyrinth.getPlayerPosition());

        Menu menu = new Menu(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.time = 0;
                label.setText("0:00");
                restartMap();
            }
        }, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet res = db.viewScores();
                try {
                    ArrayList<String> names = new ArrayList<>();
                    ArrayList<Integer> scores = new ArrayList<>();
                    while (res.next()) {
                        names.add(res.getString("name"));
                        scores.add(res.getInt("score"));
                    }

                    new ScorePopUp(frame, names, scores);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        label = new JLabel("0:00");

        frame = new Frame(grid, overlay, menu, label);
        frame.setFocusable(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Direction dir;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        dir = Direction.UP;
                        break;
                    case KeyEvent.VK_S:
                        dir = Direction.DOWN;
                        break;
                    case KeyEvent.VK_A:
                        dir = Direction.LEFT;
                        break;
                    case KeyEvent.VK_D:
                        dir = Direction.RIGHT;
                        break;
                    default:
                        return;
                }
                Point prevPos = labyrinth.getPlayerPosition();
                if (labyrinth.movePlayer(dir)) {
                    Point playerPos = labyrinth.getPlayerPosition();
                    grid.setPlayer(playerPos, prevPos);
                    overlay.setCenter(playerPos);
                }
                runGameOver();
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point prevPos = labyrinth.getDragonPosition();
                labyrinth.moveDragon();
                grid.setDragon(labyrinth.getDragonPosition(), prevPos);
                runGameOver();
                int time = ++labyrinth.time;
                label.setText(time / 60 + ":"+ String.format("%02d", (time % 60)) );
            }
        });

        // Stop timer when app is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                }
            }
        });

        timer.start();

        frame.setVisible(true);
    }

    private void restartMap() {
        timer.stop();
        labyrinth.newLabyrinth(new Maze(MAZE_SIZE));
        grid.newGame(labyrinth.getTiles());
        overlay.setCenter(labyrinth.getPlayerPosition());
        grid.setPlayer(labyrinth.getPlayerPosition(), new Point());
        grid.setDragon(labyrinth.getDragonPosition(), new Point());
        timer.restart();
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    private void finishRun() {
        timer.stop();
        frame.setFocusable(false);
        int score = labyrinth.getScore();

        String name = JOptionPane.showInputDialog(frame, "Your Score is " + score, "Game Over", JOptionPane.QUESTION_MESSAGE);

        if (name != null && !name.isEmpty()) {
            db.setHighscore(name, score);
        }

        labyrinth.resetScore();
        labyrinth.time = 0;
        label.setText("0:00");
        restartMap();
    }



    private void runGameOver() {
        if (labyrinth.isGameOver()) {
            finishRun();
        } else if (labyrinth.hasWon()) {
            restartMap();
        }
    }
}
