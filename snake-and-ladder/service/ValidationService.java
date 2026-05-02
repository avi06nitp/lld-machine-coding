package service;

import exceptions.*;
import models.Board;
import java.util.Map;

public class ValidationService {

    public boolean validateSnake(int head, int tail, Board board) {
        Map<Integer,Integer> ladders = board.getLadders();
        Map<Integer,Integer> snakes = board.getSnakes();
        if(head<=tail) {
            throw new InvalidSnakeException("Snake configuration is invalid. Head mist be greater than tail");
        }
        if(head>board.getSize() ||head<= 1){
           throw new InvalidSnakeException("Head must be within range [2,"+board.getSize()+"]");
        }
        if (tail>board.getSize() ||tail< 1){
           throw new InvalidSnakeException("Tail must be within board size");
        }
        if(ladders.containsKey(head) ){
            throw new AlreadyOccupiedWithLadder("Ladder already occupied the head of snake");
        }
        if( snakes.containsKey(head) ){
            throw new AlreadyOccupiedWithSnake("Snake has already occupied the source of the ladder");
        }
        return true;
    }

    public boolean validateLadder(int source, int destination, Board board) {
        Map<Integer,Integer> snakes = board.getSnakes();
        Map<Integer,Integer> ladders = board.getLadders();
        if(destination<=source) {
            throw new InvalidLadderException("Source must be within range [2,"+board.getSize()+"]");
        }if(source>=board.getSize() ||source<= 1){
            throw new InvalidLadderException("Source must be within board size");
        }
        if(destination>board.getSize() ||destination<= 2){
            throw new InvalidLadderException("Destination must be within board size");
        }
        if(snakes.containsKey(source)){
            throw new AlreadyOccupiedWithSnake("Snake has already occupied the source of the ladder");
        }if(ladders.containsKey(source)){
            throw new AlreadyOccupiedWithLadder("Ladder already occupied the head of snake");
        }
        return true;
    }

    public boolean validateBoardSize(int size) {
        if(size<100 || size>500){
            throw new InvalidBoardSize("Board size must be greater than range [100,500]");
        }return true;
    }

    public boolean validatePlayerSize(int size) {
        if(size<2 || size>5){
            throw new InvalidPlayerSize("Players size no. of players should be [2,5]");
        }
        return true;
    }
    public boolean validateSnakeandLadderCountSize(int ladderSize) {
        if(ladderSize<2 || ladderSize>5){
            throw new InvalidLadderCount("Ladder size no. of ladders should be [2,5]");
        }
        return true;
    }
}