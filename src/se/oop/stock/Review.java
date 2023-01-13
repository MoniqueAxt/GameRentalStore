package se.oop.stock;

public class Review {
    private int numberRating;
    private String textRating;
    private final String reviewerID;

    public Review(int numberRating, String textRating, String reviewerID) {
        this.numberRating = numberRating;
        this.textRating = textRating;
        this.reviewerID = reviewerID;
    }

    public Review(int numberRating, String reviewerID) {
        this.numberRating = numberRating;
        this.reviewerID = reviewerID;
    }


    @Override
    public String toString() {
        return numberRating + "/5 : " + textRating;
    }

    protected int getNumberRating() {
        return numberRating;
    }

    protected void setNumberRating(int numberRating) {
        this.numberRating = numberRating;
    }

    protected String getTextRating() {
        return textRating;
    }

    protected void setTextRating(String textRatings) {
        this.textRating = textRatings;
    }
}
