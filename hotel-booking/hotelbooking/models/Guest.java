package hotelbooking.models;

import hotelbooking.registry.GuestRegistry;

public class Guest {
    private static long idCounter = 0;
    public final long id;
    private final String name;
    private final String phone;
    private final String email;

    private Guest( String name, String phone, String email) {
        this.id = ++idCounter;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public static Guest createGuest(String name, String phone, String email) {
        Guest guest= new Guest(name, phone, email);
        GuestRegistry.addGuest(guest);
        return guest;
    }

    //Getters and Setters
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }

}
