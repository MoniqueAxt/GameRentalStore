package se.oop.stock;

public class Game extends StockItem {
    private String genre;

    public Game(String name, String genre, double rentCost, double userRatings) {
        super(name, rentCost, userRatings);
        this.genre = genre;
    }

    public Game(String name, String genre, double rentCost) {
        this(name, genre, rentCost, 0);

    }

    @Override
    public String toString() {
        String toString = super.toString() + " (" + this.genre + ")"
                + ". " + super.getDailyRentCost() + " SEK"
                + ". Status: " + super.getRentedString();
        if (super.isRented())
            toString += ". Date rented: " + super.getDateRented();

        return toString;
    }


    /*********************************************************
     * Getters and Setters
     /*********************************************************/


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
