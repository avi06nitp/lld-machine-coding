package service;

import exception.GameException;

public class ValidationService {

    public void validateBoardSize(int size) throws GameException {
        if (size < 3) {
            throw new GameException("Board size must be at least 3x3");
        }
        if (size > 10) {
            throw new GameException("Board size cannot exceed 10x10");
        }
    }

    public void validatePlayerName(String name) throws GameException {
        if (name == null || name.trim().isEmpty()) {
            throw new GameException("Player name cannot be empty");
        }
        if (name.length() > 20) {
            throw new GameException("Player name cannot exceed 20 characters");
        }
    }

    public void validateSymbol(char symbol) throws GameException {
        if (Character.isWhitespace(symbol)) {
            throw new GameException("Symbol cannot be a whitespace character");
        }
        if (!Character.isLetterOrDigit(symbol) && !"!@#$%^&*".contains(String.valueOf(symbol))) {
            throw new GameException("Symbol must be alphanumeric or one of: !@#$%^&*");
        }
    }
}