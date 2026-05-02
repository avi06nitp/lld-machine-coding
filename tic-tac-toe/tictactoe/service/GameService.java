package tictactoe.service;

import tictactoe.enums.GameStatus;
import tictactoe.models.Board;
import tictactoe.models.Player;
import tictactoe.strategy.MoveStrategy;


public class GameService {

    private  GameStatus gameStatus;
    private final WinnerDetectionService winnerDetectionService;

    public GameService( WinnerDetectionService winnerDetectionService) {
        this.winnerDetectionService = winnerDetectionService;
        this.gameStatus=GameStatus.IN_PROGRESS;
    }

    public void play(Player player1, Player player2, Board board) {
        Player currentPlayer = player1;

        while (gameStatus != GameStatus.FINISHED && gameStatus != GameStatus.DRAW) {
            MoveStrategy moveStrategy = currentPlayer.getStrategy();
            moveStrategy.move(board, currentPlayer);
            board.printBoard();

            if (winnerDetectionService.getWinner() == GameStatus.FINISHED) {
                gameStatus = GameStatus.FINISHED;
                System.out.println("\033[1;36m");
                System.out.println("  ╔══════════════════════════════════════════════╗");
                System.out.println("  ║         GAME OVER — WE HAVE A WINNER!       ║");
                System.out.println("  ╚══════════════════════════════════════════════╝\033[0m");
                System.out.println("\033[1;32m  [WIN] " + currentPlayer.getName() + " wins!\033[0m\n");
                break;
            } else if (winnerDetectionService.getWinner() == GameStatus.DRAW) {
                gameStatus = GameStatus.DRAW;
                System.out.println("\033[1;33m  [DRAW] Both players played optimally — it's a draw!\033[0m\n");
                break;
            }

            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }
    }
}
