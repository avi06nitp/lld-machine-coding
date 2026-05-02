package factory;

import enums.GameWinningStrategy;
import strategy.ExactLandingStrategy;
import strategy.ExceedsLandingStrategy;
import strategy.WinningStrategy;

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
