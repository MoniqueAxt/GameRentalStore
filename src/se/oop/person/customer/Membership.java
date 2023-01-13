package se.oop.person.customer;

import java.util.UUID;

public abstract class Membership {
    protected String membershipName = "Regular";
    protected String membershipID;
    protected int discountPercentage;
    protected int rentLimit;
    protected int creditsOnRent;

    protected Membership() {
        this(0, 1, 0);
    }

    protected Membership(int discountPercentage, int rentLimit, int creditsOnRent) {
        this.membershipID = UUID.randomUUID().toString();
        this.discountPercentage = discountPercentage;
        this.rentLimit = rentLimit;
        this.creditsOnRent = creditsOnRent;
    }

    protected double applyDiscount(double originalPrice) {
        double factor = (100 - discountPercentage) / 100.00;
        return originalPrice * factor;
    }

    protected String getMembershipName() {
        return membershipName;
    }

    protected void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    protected String getMembershipID() {
        return membershipID;
    }

    protected void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    protected int getDiscountPercentage() {
        return discountPercentage;
    }

    protected void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    protected int getRentLimit() {
        return rentLimit;
    }


    protected void setRentLimit(int rentLimit) {
        this.rentLimit = rentLimit;
    }

    protected int getCreditsOnRent() {
        return creditsOnRent;
    }

    protected void setCreditsOnRent(int creditsOnRent) {
        this.creditsOnRent = creditsOnRent;
    }
}

class RegularMembership extends Membership {
    RegularMembership() {
        super();
    }
}

class SilverMembership extends Membership {
    SilverMembership() {
        super(10, 3, 1);
        this.setMembershipName("Silver");
    }
}

class GoldMembership extends Membership {
    GoldMembership() {
        super(15, 5, 2);
        this.setMembershipName("Gold");
    }
}

class PremiumMembership extends Membership {
    PremiumMembership() {
        super(25, 7, 3);
        this.setMembershipName("Premium");
    }
}