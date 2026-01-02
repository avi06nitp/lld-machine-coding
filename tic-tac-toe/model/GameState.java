package model;

import enums.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final Board board;
    private final List<Player> players;
    private GameStatus status;
    private Player currentPlayer;
    private Player winner;
    private final List<Move> moveHistory;
    private int currentPlayerIndex;

    public GameState(Board board, List<Player> players) {
        this.board = board;
        this.players = new ArrayList<>(players);
        this.status = GameStatus.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.currentPlayer = players.get(currentPlayerIndex);
        this.moveHistory = new ArrayList<>();
        this.winner = null;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Move> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }

    public void addMove(Move move) {
        moveHistory.add(move);
    }

    public boolean isGameOver() {
        return status != GameStatus.IN_PROGRESS;
    }

    public int getMoveCount() {
        return moveHistory.size();
    }
}