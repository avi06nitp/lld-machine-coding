package parkinglot.factory;

import parkinglot.enums.LookupType;
import parkinglot.models.Floor;
import parkinglot.registry.FloorRegistry;
import parkinglot.strategy.FarthestLookupStrategy;
import parkinglot.strategy.LookUpStrategy;
import parkinglot.strategy.NearestLookUpStrategy;

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
