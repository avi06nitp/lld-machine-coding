package controller;

import enums.PlayerType;
import factory.PlayerFactory;
import models.Board;
import models.Player;
import strategy.WinningStrategy;

import java.util.*;

public class GameController {
    private Board board;
    private final int  playerSize;
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
        // Add Snakes;
        createSnakes(snakeSize);
        createLadders(ladderSize);
        addPlayers();

    }

   public void play(){
        int currIndex=0;
        Map<Integer,Integer>snakes=board.getSnakes();
        Map<Integer,Integer>ladders=board.getLadders();
       Player currPlayer=players.get(currIndex);
        while(true){
            System.out.println(currPlayer.getName()+ "'s turn. Press any key to continue");
            scanner.nextLine();
            int diceOutcome=currPlayer.getDiceRollStrategy().roll();
           int  currPosition=currPlayer.getPosition();
           int finalPosition=currPosition+diceOutcome;
           winningStrategy.setPlayerPosition(currPlayer,finalPosition,board);
       if(snakes.containsKey(currPlayer.getPosition())){
           currPlayer.setPosition(snakes.get(currPlayer.getPosition()));
       }if(ladders.containsKey(currPlayer.getPosition())){
           currPlayer.setPosition(ladders.get(currPlayer.getPosition()));
            }
           if(winningStrategy.playerWon(currPlayer,board)){
               System.out.println(currPlayer.getName()+" won the game!");
               break;
           }
            currIndex++;
           currPlayer=players.get((currIndex)%playerSize);

        }
   }

    private void createSnakes(int snakeSize) {

        for(int i=1;i<=snakeSize;i++){
            System.out.println("Please enter head of snake"+ i);
            int head=  scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter tail of snake"+ i);
            int tail= scanner.nextInt();
            board.addSnake(head,tail);
            scanner.nextLine();

        }
    }

    private void createLadders(int ladderSize) {

        for(int i=1;i<=ladderSize;i++){
            System.out.println("Please enter source of ladder"+ i);
            int source= scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter destination of ladder"+ i);
            int destination= scanner.nextInt();
            scanner.nextLine();
            board.addLadder(source,destination);
        }
    }

    private void addPlayers() {
        for(int i=1;i<=playerSize;i++){
            System.out.println("Please enter name of player"+ i);
            String name= scanner.nextLine();
            System.out.println("Please enter type of player"+ i);
            PlayerType type= PlayerType.valueOf(scanner.nextLine());
            Player player=playerFactory.createPlayer(name,type);
            players.add(player);
        }
    }
}
