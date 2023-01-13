package se.oop.person.customer;

import se.oop.messagesystem.Message;
import se.oop.messagesystem.MessageBox;
import se.oop.person.Person;

import java.util.ArrayList;

public class Customer extends Person {
    private final MessageBox messageBox;
    private String password;
    private Membership membership;
    private int nrRentedItems;
    private int credits;


    public Customer(String name, String password, String membershipType) {
        super(name);

        Membership membership = switch (membershipType.toLowerCase()) {
            case "silver" -> new SilverMembership();
            case "gold" -> new GoldMembership();
            case "premium" -> new PremiumMembership();
            default -> new RegularMembership();
        };
        this.password = password;
        this.membership = membership;
        nrRentedItems = credits = 0;
        messageBox = new MessageBox(super.getId());
    }

    /* shallow copy */
    public Customer(Customer customer) {
        super(customer.getName());
        this.password = customer.password;
        this.membership = customer.membership;
        this.nrRentedItems = customer.nrRentedItems;
        this.credits = customer.credits;
        this.messageBox = customer.messageBox;
    }

    public double applyDiscount(double originalPrice) {
        return this.membership.applyDiscount(originalPrice);
    }

    public double getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getAccountInfo() {
        return super.toString() + ". Membership: " + getMembershipName()
                + "\nRented items: " + nrRentedItems + "/" + membership.rentLimit + ". Credits: " + credits;
    }

    public void addMessageToInbox(Message inboxMessage) {
        messageBox.addMessageToInbox(inboxMessage);
    }

    public void addMessageToOutbox(Message outboxMessage) {
        messageBox.addMessageToOutbox(outboxMessage);
    }

    public ArrayList<Message> getInbox() {
        return messageBox.getInbox();
    }

    /*********************************************************
     * Getters and Setters
     /*********************************************************/


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMembershipName() {
        return membership.membershipName;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public void upgradeMembership() {
        if (!(membership instanceof PremiumMembership)) {

            if (membership instanceof RegularMembership) {
                membership = new SilverMembership();
            } else if (membership instanceof SilverMembership) {
                membership = new GoldMembership();
            } else if (membership instanceof GoldMembership) {
                membership = new PremiumMembership();
            }
        }
    }

    public int getNrRentedItems() {
        return nrRentedItems;
    }

    public boolean rentLimitReached() {
        return nrRentedItems == membership.getRentLimit();
    }

    public void incrementRentedItems() {
        nrRentedItems++;
    }

    public void decrementRentedItems() {
        nrRentedItems--;
    }

    public void addCreditsOnRent() {
        this.credits += membership.getCreditsOnRent();
    }

    public void resetCredits() {
        this.credits = 0;
    }
}
