package snakeandladder.factory;

import snakeandladder.enums.GameWinningStrategy;
import snakeandladder.strategy.ExactLandingStrategy;
import snakeandladder.strategy.ExceedsLandingStrategy;
import snakeandladder.strategy.WinningStrategy;

public class WinningStrategyFactory {

    public WinningStrategy createWinningStrategy(GameWinningStrategy strategy) {
        switch (strategy) {
            case EXACT:{
                return new ExactLandingStrategy();

            }case EXCEEDS:{
                return new ExceedsLandingStrategy();
            }
            default:{
                throw new IllegalArgumentException("Unsupported strategy: " + strategy);
            }
        }

    }
}
