package service;

import enums.GameStatus;
import exception.GameException;
import exception.InvalidPlayerException;
import model.*;
import strategy.WinningStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameService {
    private final WinningStrategy winningStrategy;

    public GameService(WinningStrategy winningStrategy) {
        this.winningStrategy = winningStrategy;
    }

    public GameState initializeGame(Board board, List<Player> players) throws GameException {
        validatePlayers(players);
        return new GameState(board, players);
    }

    public void executeMove(GameState gameState, Move move) {
        gameState.getBoard().makeMove(move);
        gameState.addMove(move);
    }

    public void checkGameStatus(GameState gameState, Move lastMove) {
        if (winningStrategy.checkWinner(gameState.getBoard(), lastMove)) {
            gameState.setStatus(GameStatus.WON);
            gameState.setWinner(gameState.getCurrentPlayer());
        } else if (gameState.getBoard().isFull()) {
            gameState.setStatus(GameStatus.DRAW);
        }
    }

    public void playTurn(GameState gameState) {
        Player currentPlayer = gameState.getCurrentPlayer();
        Move move = currentPlayer.makeMove(gameState.getBoard());

        executeMove(gameState, move);
        checkGameStatus(gameState, move);

        if (!gameState.isGameOver()) {
            gameState.switchPlayer();
        }
    }

    private void validatePlayers(List<Player> players) throws GameException {
        if (players == null || players.size() < 2) {
            throw new InvalidPlayerException("Game requires at least 2 players");
        }

        Set<Character> symbols = new HashSet<>();
        for (Player player : players) {
            if (!symbols.add(player.getSymbol())) {
                throw new InvalidPlayerException(
                    String.format("Duplicate symbol '%c' found. Each player must have a unique symbol.",
                        player.getSymbol())
                );
            }
        }
    }

    public void resetGame(GameState gameState) {
        gameState.getBoard().reset();
        gameState.setStatus(GameStatus.IN_PROGRESS);
        gameState.setWinner(null);
    }
}