package snakeandladder.models;

import snakeandladder.service.ValidationService;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private int size;
    private Map<Integer,Integer>snakes;
    private Map<Integer,Integer>ladders;
    private final ValidationService validationService;


    //Default Constructor
    public Board(ValidationService validationService) {
        this.validationService = validationService;
        this.size = 100;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    // Parameterized Constructor
    public Board(int size, ValidationService validationService) {
        this.size = size;
        this.validationService = validationService;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    // Add snakes and ladders
    public void addLadder(int source, int destination) {
        validationService.validateLadder(source, destination, this);
        ladders.put(source, destination);
    }
    public void addSnake(int head, int tail) {
        validationService.validateSnake(head,tail,this);
        snakes.put(head,tail);
    }

    public Map<Integer,Integer> getSnakes() {
        return Map.copyOf(snakes);
    }
    public Map<Integer,Integer> getLadders() {
        return Map.copyOf(ladders);
    }

    public int getSize() {
        return size;
    }

}
