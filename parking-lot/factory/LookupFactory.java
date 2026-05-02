package factory;

import enums.LookupType;
import models.Floor;
import registry.FloorRegistry;
import strategy.FarthestLookupStrategy;
import strategy.LookUpStrategy;
import strategy.NearestLookUpStrategy;

public class LookupFactory {

    public LookUpStrategy createLookUpStrategy(LookupType type, FloorRegistry floorRegistry) {
        switch (type) {
            case FARTHEST->{
                return new FarthestLookupStrategy(floorRegistry);
            }case NEAREST ->{
                return new NearestLookUpStrategy(floorRegistry);
            }
            default ->{
                throw new IllegalArgumentException("Unsupported LookupType: " + type);
            }
        }

    }
}
