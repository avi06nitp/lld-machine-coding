package hotelbooking.models;


import hotelbooking.enums.RoomType;
import hotelbooking.registry.RoomRegistry;
import hotelbooking.strategy.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private static long idCounter = 0;
    private final long  id;
    private final RoomType type;
    private final PricingStrategy pricingStrategy;
    private Set<Reservation> reservations= ConcurrentHashMap.newKeySet();

    private Room(RoomType type, PricingStrategy pricingStrategy) {
        this.id = ++idCounter;
        this.type = type;
        this.pricingStrategy = pricingStrategy;
    }

    public static Room createRoom(RoomType type, PricingStrategy pricingStrategy) {
        switch (type) {
            case STANDARD -> {
                Room room = new Room(type, new StandardPricingStrategy());
                RoomRegistry.addRoom(room);
                return room;
            }
            case DELUXE -> {
                Room room = new Room(type, new DeluxePricingStrategy());
                RoomRegistry.addRoom(room);
                return room;
            }
            case SUITE -> {
                Room room=new Room(type,new SuitePricingStrategy());
                RoomRegistry.addRoom(room);
                return room;
            }
            case STUDIO -> {
                Room room=new Room(type,new StudioPricingStartegy());
                RoomRegistry.addRoom(room);
                return room;
            }
            default -> throw new IllegalArgumentException("Unsupported room type: " + type);

        }
    }

    //Getters and Setters;
    public long getId() {
        return id;
    }
    public RoomType getType() {
        return type;
    }
    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }
    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

}
