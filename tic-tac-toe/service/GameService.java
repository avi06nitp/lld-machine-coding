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
        Player currentPlayer=player1;

        while(gameStatus!=GameStatus.FINISHED && gameStatus!=GameStatus.DRAW){
            MoveStrategy moveStrategy=currentPlayer.getStrategy();
            moveStrategy.move(board, currentPlayer);
            board.printBoard();
            System.out.println();

            if(winnerDetectionService.getWinner()==GameStatus.FINISHED){
                gameStatus=GameStatus.FINISHED;
                System.out.println(currentPlayer.getName()+" wins!");
                break;
            }else if (winnerDetectionService.getWinner()==GameStatus.DRAW){
                gameStatus=GameStatus.DRAW;
                System.out.println("Both players played optimally, and no one wins!");
                break;
            }
            if(currentPlayer==player1){
                currentPlayer=player2;
            }
            else if(currentPlayer==player2){
                currentPlayer=player1;
            }

        }
    }
}
