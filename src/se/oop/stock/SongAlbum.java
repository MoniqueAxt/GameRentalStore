package se.oop.stock;

public class SongAlbum extends StockItem {
    private String artist;
    private int yearReleased;


    public SongAlbum(String title, String artist, int yearReleased, double averageRating, double dailyRentCost) {
        super(title, dailyRentCost, averageRating);
        this.artist = artist;
        this.yearReleased = yearReleased;

    }

    @Override
    public String toString() {
        return super.toString() + " - by" + artist + ". Released in " + yearReleased
                + ". Price: " + super.getDailyRentCost() + " SEK. Status: " + super.getRentedString();
    }


    /*********************************************************
     * Getters and Setters
     /*********************************************************/


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

}
