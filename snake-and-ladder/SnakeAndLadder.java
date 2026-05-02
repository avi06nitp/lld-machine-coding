import controller.GameController;
import enums.GameWinningStrategy;
import exceptions.InvalidBoardSize;
import exceptions.InvalidLadderCount;
import exceptions.InvalidPlayerSize;
import factory.PlayerFactory;
import factory.WinningStrategyFactory;
import models.Board;
import service.ValidationService;
import strategy.WinningStrategy;

import java.util.Scanner;

public class SnakeAndLadder {
    public static void main(String[] args) {

        ValidationService service = new ValidationService();
        System.out.println("Welcome to SnakeAndLadder!");

        Scanner scanner = new Scanner(System.in);


        int boardSize=0;
        while(true) {
            try{
                System.out.println("Please enter the Board size between [100,500]:(default = 100) ");
                boardSize=Integer.parseInt(scanner.nextLine());
                service.validateBoardSize(boardSize);
                break;
            }catch (InvalidBoardSize e){
                System.out.println(e.getMessage());
            }

        }
        Board board = new Board(boardSize,service);
        int playerSize=0;
        while(true) {
           try {
               System.out.println("Please enter no. of Players in range [2,5]: ");
               playerSize= Integer.parseInt(scanner.nextLine());
               service.validatePlayerSize(playerSize);
               break;
           }catch (InvalidPlayerSize e){
               System.out.println(e.getMessage());
           }
        }
        int ladderSize=0;
        while(true) {
            try {
                System.out.println("Please enter no. of Ladders in range [2,5]: ");
                ladderSize= Integer.parseInt(scanner.nextLine());
                service.validateSnakeandLadderCountSize(ladderSize);
                break;
            }catch (InvalidLadderCount e){
                System.out.println(e.getMessage());
            }
        }
        int snakeSize=0;
        while(true) {
            try {
                System.out.println("Please enter no. of Snakes in range [2,5]: ");
                snakeSize= Integer.parseInt(scanner.nextLine());
                service.validateSnakeandLadderCountSize(snakeSize);
                break;
            }catch (InvalidLadderCount e){
                System.out.println(e.getMessage());

            }
        }
        PlayerFactory factory=new PlayerFactory();
        System.out.println("Please enter winning strategy: [EXACT, EXCEEDS]: ");
        WinningStrategyFactory winningStrategyFactory=new WinningStrategyFactory();
        GameWinningStrategy gamewinningStrategy= GameWinningStrategy.valueOf(scanner.nextLine());
        WinningStrategy winningStrategy=winningStrategyFactory.createWinningStrategy(gamewinningStrategy);
        GameController gameController=new GameController(board, ladderSize,playerSize,snakeSize, factory, winningStrategy,  scanner);
        gameController.initializeGame();
        gameController.play();

    }
}
