
import enums.PlayerType;
import exceptions.InvalidBoardSize;
import factory.PlayerFactory;
import models.Board;
import models.Player;
import service.BoardValidation;
import service.GameService;
import service.InputValidationService;
import service.WinnerDetectionService;

import java.util.Scanner;

public class TicTacToeGame {
    public static void main(String[] args) {



       Scanner scanner = new Scanner(System.in);
       System.out.println("Welcome to Tic-Tac-Toe Game!");
       while (true) {
           BoardValidation boardValidation = new BoardValidation();
           Board board;
           int boardSize;
           while(true){
               try{
                   System.out.println("Please enter the board size (between 3 to 9: ");
                   boardSize = Integer.parseInt(scanner.nextLine());
                   boardValidation.validBoard(boardSize);
                   board = new Board(boardSize);
                   break;
               }catch (InvalidBoardSize e){
                   System.out.println("Invalid board size");
                   System.out.println("Please try again in range");
               }
           }

           PlayerFactory playerFactory = new PlayerFactory();
           System.out.println("Please enter first player name: ");
           String playerName = scanner.nextLine();
           System.out.println("Please enter first player symbol: ");
           char symbol = scanner.nextLine().charAt(0);
           System.out.println("Please enter "+playerName+"'s type (HUMAN/COMPUTER): ");
           PlayerType playerType = PlayerType.valueOf(scanner.nextLine().toUpperCase());
           System.out.println("Please enter second player name: ");
           String player2name = scanner.nextLine();
           System.out.println("Please enter second player symbol: ");
           char symbol2 = scanner.nextLine().charAt(0);
           System.out.println("Please enter "+player2name+"'s type (HUMAN/COMPUTER): ");

           WinnerDetectionService winnerDetectionService = new WinnerDetectionService(board);
           GameService gameService = new GameService(winnerDetectionService);

           PlayerType player2Type = PlayerType.valueOf(scanner.nextLine().toUpperCase());
           InputValidationService inputValidationService = new InputValidationService();

           Player player= playerFactory.createPlayer(playerName,symbol,playerType, inputValidationService);
           Player player2= playerFactory.createPlayer(player2name,symbol2,player2Type, inputValidationService);

           gameService.play(player,player2,board);

           System.out.println("Wanna play again (Y/N");
           String answer = scanner.nextLine();
           if(answer.equals("N")){
               break;
           }

       }

    }
}