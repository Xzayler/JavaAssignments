package beadando1.board;

import beadando1.player.Player;
import beadando1.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private int turn = 0;
    private int bankruptPlayerCount = 0;
    private final Random random = new Random();
    private final ArrayList<Integer> diceThrows = new ArrayList<>();

    /**
     * Creates a new Board object, the dice throws will be random
     * @param tiles The Tiles on the board
     * @param players The Players playing on the Board
     */
    public Board(List<Tile> tiles, List<Player> players) {
        if (tiles.size() <= 0) throw new IllegalArgumentException("Provided tiles list can't be empty");
        if (players.size() <= 0) throw new IllegalArgumentException("Provided players list can't be empty");
        this.tiles.addAll(tiles);
        this.players.addAll(players);
    }

    /**
     * Creates a new Board object, the dice throws are predetermined
     * @param tiles The Tiles on the board
     * @param players The Players playing on the Board
     * @param dicethrows The predetermined dice throws
     */
    public Board(List<Tile> tiles, List<Player> players, List<Integer> dicethrows) {
        this(tiles,players);
        this.diceThrows.addAll(dicethrows);
    }

    private int throwDice() {
        if (diceThrows.isEmpty()) {
            return random.nextInt(6)+1;
        } else {
            return diceThrows.get(turn);
        }
    }

    private Player getRichest() {
        Player richest = players.get(0);
        Player curr;
        for (int i = 1; i < players.size(); i++) {
            curr = players.get(i);
            if (curr.getCapital() > richest.getCapital()) {
                richest = curr;
            }
        }
        return richest;
    }

    /**
     * Simulates the game, using either predetermined dice throws or random values,
     * depending on the constructor used.
     * @return The winner of the game
     */
    public Player playGame() {
        Player currPlayer = null;
        while (players.size() > 1 && (diceThrows.isEmpty() || diceThrows.size() > turn)) {
            if (turn > 1000) {
                System.out.println("\nThe game has been running for over 1000 turns, is this an infinite loop?");
                System.out.println("There is more than 1 player left.");
                System.out.println("Setting currently richest player as winner...");
                return getRichest();
            }
            currPlayer = players.get((turn - bankruptPlayerCount) % players.size());
            // move the player
            currPlayer.stepBy(throwDice());
            // Have the player take its turn
            Tile currTile = tiles.get(currPlayer.getPosition() % tiles.size());
            currTile.passToPlayer(currPlayer);

            // If bankrupt, remove Player from game
            if (currPlayer.isBankrupt()) {
                currPlayer.resetOwnedTiles();
                players.remove(currPlayer);
                bankruptPlayerCount++;
            }
            turn++;
        }
        if (players.size() != 1) {
            System.out.println("\nThe game isn't over yet. There is more than 1 player left.");
            System.out.println("Setting currently richest player as winner...");
            return getRichest();
        } else if (diceThrows.size() > turn) {
            System.out.println("\nThere are still some dice throws left but there is only one player remaining.");
            System.out.println("Outputting results anyways.");
        }
        return players.get(0);
    }

}
