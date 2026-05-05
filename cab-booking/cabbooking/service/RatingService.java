package cabbooking.service;

import cabbooking.exception.InvalidRatingException;
import cabbooking.models.Ride;


import java.util.Scanner;

public class RatingService {

    private final Scanner scanner;

    public RatingService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void giveVehicleRating(Ride ride) {
        System.out.println("Please rate the driver on scale of 1-5!");
        double rating = scanner.nextDouble();
        if (rating < 1 || rating > 5) throw new InvalidRatingException("Rating must be between 1 and 5.");
        ride.getVehicle().setRating(rating);
    }

    public void giveRiderRating(Ride ride) {
        System.out.println("Please rate your ride on scale of 1-5!");
        double rating = scanner.nextDouble();
        if (rating < 1 || rating > 5) throw new InvalidRatingException("Rating must be between 1 and 5.");
        ride.getRider().setRating(rating);
    }
}
