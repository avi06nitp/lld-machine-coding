package strategy;

import models.Board;
import models.Move;
import models.Player;
import service.GameService;
import service.InputValidationService;
import exceptions.InvalidInputExeption;

import java.util.Scanner;

public class HumanMoveStrategy implements MoveStrategy {
    Scanner scanner = new Scanner(System.in);
    private final InputValidationService inputValidationService;



    public HumanMoveStrategy(InputValidationService inputValidationService) {
        this.inputValidationService = inputValidationService;
    }


    @Override
    public void move(Board board, Player player) {
        boolean isValid=false;
        while (!isValid) {
            try {
                Move move=takeMoveInputs(board, player);
                int row = move.getRow();
                int col = move.getCol();
                inputValidationService.validateInput(row,col,board);
                board.setCell(row,col, player.getSymbol());
                isValid=true;

            }catch (InvalidInputExeption e) {
                System.out.println("Invalid input: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }

    }

    private Move takeMoveInputs(Board board,Player player) {
        System.out.println(player.getName()+" ,please enter a your row for you move: ");
        int row = scanner.nextInt();
        System.out.println(player.getName()+" ,please enter a your column for you move: ");
        int column = scanner.nextInt();
        return new Move(row,column, board);
    }
}
