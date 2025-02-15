package beadando1;

import beadando1.board.Board;
import beadando1.player.*;
import beadando1.tile.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    /**
     * Parses the input file and creates a set up Board object
     * @param filename The path to the file to be parsed
     * @return A Board object created from the parsed input
     * @throws FileNotFoundException If the file at the provided path can't be found
     * @throws InvalidInputException If there was an error detected during the parsing process
     */
    public static Board initBoard(String filename) throws FileNotFoundException, InvalidInputException {
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Integer> diceThrows = new ArrayList<>();

        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));

        int tilesCount;
        try {
            tilesCount = sc.nextInt();
            if (tilesCount < 1) {
                throw new InvalidInputException("Provide at least 1 tile");
            }
        } catch (NoSuchElementException e) {
            throw new InvalidInputException("Provide the tiles count as a positive integer.");
        }

        try {
            for (int i = 0; i < tilesCount; i++) {
                switch (sc.next()) {
                    case "P":
                        tiles.add(new Property());
                        break;
                    case "U":
                        int uval = sc.nextInt();
                        if (uval <= 0) throw new InvalidInputException("Provide a positive integer as the tile value.");
                        tiles.add(new Utility(uval));
                        break;
                    case "F":
                        int fval = sc.nextInt();
                        if (fval <= 0) throw new InvalidInputException("Provide a positive integer as the tile value.");
                        tiles.add(new Fortune(fval));
                        break;
                    default:
                        throw new InvalidInputException("Invalid tile type found.");
                }
            }
        } catch (InputMismatchException e) {
            throw new InvalidInputException("Provide a positive integer as the tile value.");
        } catch (NoSuchElementException e) {
            throw new InvalidInputException("Not enough tiles provided.");
        }

        int playerCount;
        try {
            playerCount = sc.nextInt();
            if (playerCount < 1) {
                throw new InvalidInputException("Provide at least 1 player.");
            }
        } catch (NoSuchElementException e) {
            throw new InvalidInputException("Provide the player count as a positive integer.");
        }
        try {
            for (int i = 0; i < playerCount; i++) {
                String name = sc.next();
                switch (sc.next()) {
                    case "C":
                        players.add(new Careful(name));
                        break;
                    case "T":
                        players.add(new Tactical(name));
                        break;
                    case "G":
                        players.add(new Greedy(name));
                        break;
                    default:
                        throw new InvalidInputException("Invalid player type found.");
                }
            }
        } catch (InputMismatchException e) {
            throw new InvalidInputException("Provide correct player data.");
        } catch (NoSuchElementException e) {
            throw new InvalidInputException("Not enough players provided.");
        }

        try {
            while (sc.hasNext()) {
                int diceValue = sc.nextInt();
                if (diceValue < 1 || diceValue > 6) {
                    throw new InvalidInputException("Dice values have to be between 1 and 6.");
                }
                diceThrows.add(diceValue);
            }
        } catch (InputMismatchException e) {
            throw new InvalidInputException("Invalid dice values.");
        }

        if (diceThrows.isEmpty()) {
            return new Board(tiles, players);
        }

        return new Board(tiles, players, diceThrows);
    }

    public static void main(String[] args) {

        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        if (args.length == 0) {
            System.err.println("Provide an input file.");
            return;
        }

        Board b;
        String filename= args[0];

        try {
            b = initBoard(filename);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Exiting. Restart program with correct input.");
            return;
        }

        Player winner = b.playGame();
        System.out.println("\nWinner!");
        System.out.println(String.format("%s: %d", winner.name, winner.getCapital()));
        System.out.println(String.format("Properties: %s", winner.getProperties()));
    }
}
