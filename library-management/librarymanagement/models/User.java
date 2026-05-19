package librarymanagement.models;

import librarymanagement.enums.MembershipType;
import librarymanagement.registry.UserRegistry;
import librarymanagement.strategy.FeeCalculationStrategy;
import librarymanagement.strategy.PremiumFeeMemebershipCalculationStrategy;
import librarymanagement.strategy.StandardFeeMembershipCalculationStrategy;

import java.awt.print.Book;
import java.util.Collections;
import java.util.List;

public class User {
    private static int idCounter=1;
    private final int id;
    private final String name;
    private final String email;
    private String phoneNumber;
    private MembershipType membershipType;
    private int deviceId;
    private final FeeCalculationStrategy feeCalculationStrategy;
    private List<RentedBooks> books= Collections.emptyList();


    private User(String name, String email, MembershipType membershipType, FeeCalculationStrategy feeCalculationStrategy) {
        this.feeCalculationStrategy = feeCalculationStrategy;
        this.id = idCounter;
        idCounter++;
        this.name = name;
        this.email = email;
        this.membershipType = membershipType;

    }

    //factory to create user
    public static User createUser(String name, String email, MembershipType membershipType) {
        switch (membershipType) {
            case STANDARD -> {
                User user=  new User(name, email, MembershipType.STANDARD,new StandardFeeMembershipCalculationStrategy());
                UserRegistry.registerUser(user);
                return user;
            }case PREMIUM -> {
                User user=  new User(name,email,MembershipType.PREMIUM,new PremiumFeeMemebershipCalculationStrategy());
                UserRegistry.registerUser(user);
                return user;
            }
            default -> {
                throw new RuntimeException("Unsupported MembershipType");
            }
        }

    }

     public int getId() {
            return id;
     }
     public String getName() {
            return name;
     }
     public String getEmail() {
            return email;
     }
     public String getPhoneNumber() {
            return phoneNumber;
     }
     public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
     }
     public MembershipType getMembershipType() {
            return membershipType;
     }
     public void setMembershipType(MembershipType membershipType) {
            this.membershipType = membershipType;
     }

     public FeeCalculationStrategy getFeeCalculationStrategy() {
        return feeCalculationStrategy;
     }
     public int getDeviceId() {
        return deviceId;
     }
     public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
     }


}
