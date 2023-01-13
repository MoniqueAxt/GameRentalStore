package se.oop.transactions;

import java.io.Serializable;
import java.time.LocalDate;

public class RentTransaction implements Comparable<RentTransaction>, Serializable {

    private String customerID;
    private double cost;
    private String itemID;
    transient private LocalDate dateRented;
    transient private LocalDate dateReturned;


    public RentTransaction(String customerID, double cost, String itemID, LocalDate dateRented, LocalDate dateReturned) {
        this.customerID = customerID;
        this.cost = cost;
        this.itemID = itemID;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
    }


    @Override
    public String toString() {
        return "[" + dateRented + "  -  " + dateReturned + " ] Item: " + itemID + " rented by " + customerID;
    }

    @Override
    public int compareTo(RentTransaction transaction) {
        return this.dateRented.compareTo(transaction.dateRented);
    }

    /*********************************************************
     * Getters and setters
     /*********************************************************/
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    public void setDateRented(LocalDate dateRented) {
        this.dateRented = dateRented;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }
}
