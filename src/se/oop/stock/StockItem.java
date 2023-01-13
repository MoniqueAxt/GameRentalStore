package se.oop.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class StockItem {
    private final String id;
    private String title;
    private boolean rented = false;
    private double dailyRentCost;
    private LocalDate dateRented = null;
    private final ArrayList<Review> reviewList;
    private double averageRating;

    StockItem(String name, double dailyRentCost, double averageRating) {
        this.id = UUID.randomUUID().toString();
        this.title = name;
        this.dailyRentCost = dailyRentCost;
        this.reviewList = new ArrayList<>();
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return id + " : " + title + " [Average rating: "
                + (getAverageRating() == -1 ? "n/a" : getAverageRating())
                + "]";
    }


    public ArrayList<Integer> getAllNumberRatings() {
        ArrayList<Integer> numberRatings = new ArrayList<>();
        for (Review review : reviewList) {
            numberRatings.add(review.getNumberRating());
        }

        return numberRatings;
    }

    public ArrayList<String> getAllTextReviews() {
        ArrayList<String> textRatings = new ArrayList<>();
        for (Review review : reviewList) {
            textRatings.add(review.getTextRating());
        }
        return textRatings;
    }

    /*********************************************************
     * Getters and Setters
     /*********************************************************/
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public double getDailyRentCost() {
        return dailyRentCost;
    }

    public void setDailyRentCost(double dailyRentCost) {
        this.dailyRentCost = dailyRentCost;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    public void setDateRented(LocalDate dateRented) {
        this.dateRented = dateRented;
    }

    public String getRentedString() {
        if (rented)
            return "unavailable";

        return "available";
    }

    public void setReview(Review review) {
        this.reviewList.add(review);
    }

    public boolean hasReviews() {
        return !reviewList.isEmpty();
    }

    private void calculateAverageRating() {
        double totalRating = 0;

        if (hasReviews()) {
            for (Review review : reviewList) {
                totalRating += review.getNumberRating();
            }
            averageRating = totalRating / reviewList.size();
        }
        averageRating = -1;
    }

    public double getAverageRating() {
        calculateAverageRating();
        return averageRating;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

}
