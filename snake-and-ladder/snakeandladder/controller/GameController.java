package snakeandladder.controller;

import snakeandladder.enums.PlayerType;
import snakeandladder.factory.PlayerFactory;
import snakeandladder.models.Board;
import snakeandladder.models.Player;
import snakeandladder.strategy.WinningStrategy;

import java.util.*;

public class GameController {
    private Board board;
    private final int playerSize;
    private final int ladderSize;
    private final int snakeSize;
    private final Scanner scanner;
    private List<Player> players = new ArrayList<>();
    private final PlayerFactory playerFactory;
    private final WinningStrategy winningStrategy;

    public GameController(Board board, int ladderSize, int playerSize, int snakeSize, PlayerFactory playerFactory, WinningStrategy winningStrategy, Scanner scanner) {
        this.board = board;
        this.playerSize = playerSize;
        this.ladderSize = ladderSize;
        this.snakeSize = snakeSize;
        this.playerFactory = playerFactory;
        this.winningStrategy = winningStrategy;
        this.scanner = scanner;
    }

    public void initializeGame() {
        createSnakes(snakeSize);
        createLadders(ladderSize);
        addPlayers();
    }

    public void play() {
        int currIndex = 0;
        Map<Integer, Integer> snakes = board.getSnakes();
        Map<Integer, Integer> ladders = board.getLadders();
        Player currPlayer = players.get(currIndex);

        while (true) {
            System.out.println("\033[1;34m\n  ────────────── PLAYER POSITIONS ──────────────\033[0m");
            for (Player p : players) {
                System.out.println("\033[36m  " + p.getName() + " : Position " + p.getPosition() + "\033[0m");
            }

            System.out.println("\033[1;33m\n  ► " + currPlayer.getName() + "'s turn — press Enter to roll dice...\033[0m");
            scanner.nextLine();

            int diceOutcome = currPlayer.getDiceRollStrategy().roll();
            System.out.println("\033[1;35m  [DICE] Rolled: " + diceOutcome + "\033[0m");

            int currPosition = currPlayer.getPosition();
            int finalPosition = currPosition + diceOutcome;
            System.out.println("\033[1;34m  -> Moving from " + currPosition + " to " + finalPosition + "\033[0m");

            winningStrategy.setPlayerPosition(currPlayer, finalPosition, board);

            if (snakes.containsKey(currPlayer.getPosition())) {
                int fromPos = currPlayer.getPosition();
                int toPos = snakes.get(fromPos);
                System.out.println("\033[1;31m  [SNAKE] Bitten! Sliding from " + fromPos + " down to " + toPos + "\033[0m");
                currPlayer.setPosition(toPos);
            }
            if (ladders.containsKey(currPlayer.getPosition())) {
                int fromPos = currPlayer.getPosition();
                int toPos = ladders.get(fromPos);
                System.out.println("\033[1;32m  [LADDER] Climbed! Moving from " + fromPos + " up to " + toPos + "\033[0m");
                currPlayer.setPosition(toPos);
            }

            System.out.println("\033[36m  " + currPlayer.getName() + " is now at position " + currPlayer.getPosition() + "\033[0m");

            if (winningStrategy.playerWon(currPlayer, board)) {
                System.out.println("\033[1;36m");
                System.out.println("  ╔══════════════════════════════════════════════╗");
                System.out.println("  ║         GAME OVER — WE HAVE A WINNER!       ║");
                System.out.println("  ╚══════════════════════════════════════════════╝\033[0m");
                System.out.println("\033[1;32m  [WIN] " + currPlayer.getName() + " wins the game!\033[0m");
                break;
            }

            currIndex++;
            currPlayer = players.get((currIndex) % playerSize);
        }
    }

    private void createSnakes(int snakeSize) {
        System.out.println("\033[1;34m\n  ── Configure Snakes ──\033[0m");
        for (int i = 1; i <= snakeSize; i++) {
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter head (higher position) of Snake " + i + ":\033[0m");
                    int head = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("\033[1;33m  ► Enter tail (lower position) of Snake " + i + ":\033[0m");
                    int tail = Integer.parseInt(scanner.nextLine().trim());
                    board.addSnake(head, tail);
                    System.out.println("\033[1;32m  [+] Snake " + i + " added: " + head + " -> " + tail + "\033[0m");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\033[1;31m  [x] Invalid input — positions must be numbers.\033[0m");
                } catch (RuntimeException e) {
                    System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
                }
            }
        }
    }

    private void createLadders(int ladderSize) {
        System.out.println("\033[1;34m\n  ── Configure Ladders ──\033[0m");
        for (int i = 1; i <= ladderSize; i++) {
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter source (lower position) of Ladder " + i + ":\033[0m");
                    int source = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("\033[1;33m  ► Enter destination (higher position) of Ladder " + i + ":\033[0m");
                    int destination = Integer.parseInt(scanner.nextLine().trim());
                    board.addLadder(source, destination);
                    System.out.println("\033[1;32m  [+] Ladder " + i + " added: " + source + " -> " + destination + "\033[0m");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\033[1;31m  [x] Invalid input — positions must be numbers.\033[0m");
                } catch (RuntimeException e) {
                    System.out.println("\033[1;31m  [x] " + e.getMessage() + "\033[0m");
                }
            }
        }
    }

    private void addPlayers() {
        System.out.println("\033[1;34m\n  ── Add Players ──\033[0m");
        for (int i = 1; i <= playerSize; i++) {
            while (true) {
                try {
                    System.out.println("\033[1;33m  ► Enter name for Player " + i + ":\033[0m");
                    String name = scanner.nextLine().trim();
                    System.out.println("\033[1;33m  ► Enter type for " + name + " \033[2m[HUMAN / COMPUTER]\033[0m\033[1;33m:\033[0m");
                    PlayerType type = PlayerType.valueOf(scanner.nextLine().trim().toUpperCase());
                    Player player = playerFactory.createPlayer(name, type);
                    players.add(player);
                    System.out.println("\033[1;32m  [+] " + name + " (" + type + ") added.\033[0m");
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("\033[1;31m  [x] Invalid player type — use HUMAN or COMPUTER.\033[0m");
                }
            }
        }
    }
}
