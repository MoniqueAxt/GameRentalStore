package se.oop;

import se.oop.messagesystem.Message;
import se.oop.person.Employee;
import se.oop.person.customer.Customer;
import se.oop.stock.Game;
import se.oop.stock.Review;
import se.oop.stock.SongAlbum;
import se.oop.stock.StockItem;
import se.oop.transactions.RentTransaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public class GameRentalController {

    private final Vector<Customer> customerList;
    private final Vector<Employee> employeeList;
    private final Vector<StockItem> stockItemList;
    private final Vector<Customer> membershipRequests;
    private final ArrayList<String> gameGenres;
    private final ArrayList<RentTransaction> rentTransactions;
    private final int creditsForFreeRent = 5;
    private double totalRentProfit = 0;
    private Customer loggedinCustomer;

    public GameRentalController() {
        customerList = new Vector<>();
        employeeList = new Vector<>();
        stockItemList = new Vector<>();
        membershipRequests = new Vector<>();
        rentTransactions = new ArrayList<>();

        gameGenres = new ArrayList<>() {
            {
                add("Action");
                add("Adventure");
                add("Role-playing");
                add("Shooter");
                add("Simulation");
                add("Strategy");
                add("Sport");
                add("MMO");
            }
        };
    }


    /*********************************************************
     * StockItem methods
     /*********************************************************/

    // PRINT GAMES by genre
    public boolean searchGamesByGenre(String genre) {
        int nrFound = 0;

        for (StockItem item : stockItemList) {
            if (item instanceof Game && ((Game) item).getGenre().toLowerCase().equals(genre)) {
                System.out.println(item);
                nrFound++;
            }
        }
        return nrFound != 0;
    }

    // PRINT all games
    public void printAllGames() {
        boolean printDiscount = (loggedinCustomer != null);
        int nrItems = 0;

        for (StockItem item : stockItemList) {
            if (item instanceof Game) {
                String toPrint = "[" + stockItemList.indexOf(item) + "] " + item;

                if (printDiscount) {
                    toPrint += " [your price: "
                            + loggedinCustomer.applyDiscount(item.getDailyRentCost())
                            + "]";
                }
                System.out.println(toPrint);
                nrItems++;
            }
        }
        if (nrItems == 0)
            System.out.println("No games saved.");
    }

    // PRINT ALBUMS by year
    public boolean searchAlbumsByYear(int year) {
        int nrFound = 0;

        for (StockItem item : stockItemList) {
            if (item instanceof SongAlbum && ((SongAlbum) item).getYearReleased() == year) {
                System.out.println(item);
                nrFound++;
            }
        }
        return nrFound != 0;
    }

    // PRINT all albums
    public void printAllAlbums() {
        boolean printDiscount = (loggedinCustomer != null);
        int nrItems = 0;

        for (StockItem item : stockItemList) {
            if (item instanceof SongAlbum) {
                String toPrint = "[" + stockItemList.indexOf(item) + "] " + item;

                if (printDiscount) {
                    toPrint += " [your price: "
                            + loggedinCustomer.applyDiscount(item.getDailyRentCost())
                            + "]";
                }
                System.out.println(toPrint);
                nrItems++;
            }
        }
        if (nrItems == 0)
            System.out.println("No albums saved.");
    }

    // PRINT ALL stock items
    public void printAllStockItems() {
        boolean printDiscount = (loggedinCustomer != null);
        int nrItems = 0;

        for (StockItem item : stockItemList) {
            String toPrint = "[" + stockItemList.indexOf(item) + "] " + item.toString();

            if (printDiscount) {
                toPrint += " [your price: "
                        + loggedinCustomer.applyDiscount(item.getDailyRentCost())
                        + "]";
            }
            System.out.println(toPrint);
            nrItems++;
        }
        if (nrItems == 0)
            System.out.println("No albums saved.");
    }

    // PRINT RATINGS of items
    public void printItemRatingsDescending(String type) {
        // no type specified - print all stock items sorted by ratings DESC
        if (type.trim().isEmpty()) {
            for (StockItem item : stockItemList) {
                String averageRating = item.getAverageRating() == -1 ? "n/a" : String.valueOf(item.getAverageRating());
                System.out.println(averageRating + " : " + item.getTitle() + "(" + item.getClass() + ")");
            }
        }
        // print specific stock item type sorted by ratings DESC
        for (StockItem item : stockItemList) {
            String averageRating = item.getAverageRating() == -1 ? "n/a" : String.valueOf(item.getAverageRating());

            // GAME TYPE
            if (type.trim().equalsIgnoreCase("game")) {
                if (item instanceof Game) {
                    System.out.println(averageRating + " : " + item.getTitle());
                }
            }
            // ALBUM TYPE
            else if (type.trim().equalsIgnoreCase("album")) {
                if (item instanceof Game) {
                    System.out.println(averageRating + " : " + item.getTitle());
                }
            }
        }
    }

    // PRINT REVIEWS of a specific item
    public boolean printReviews(String id) {
        int count = 0;

        for (StockItem item : stockItemList) {
            if (item.getId().equals(id)) {
                System.out.println("\n\t **** Reviews for: " + item.getTitle() + "****");

                for (Review review : item.getReviewList()) {
                    System.out.println(review);
                    count++;
                }
                break;
            }
        }
        return count > 0;
    }


    // ADD ITEM
    public boolean addStockItem(StockItem item) {
        return stockItemList.add(item);
    }

    // REMOVE ITEM
    public boolean removeStockItem(String itemID) {
        for (StockItem item : stockItemList) {
            if (item.getId().equals(itemID)) {

                if (stockItemList.removeElement(item))
                    return true;
            }
        }
        return false;
    }

    // RENT ITEM

    /**
     * Rents a stock item: sets the item to unavailable/rented
     *
     * @param itemID ID of item to rent
     * @return String indicating if rent was successful, doesn't exist or unavailable for rent
     */
    public String rentStockItem(String itemID) {
        if (loggedinCustomer == null) {              /* modified for M2 */
            return "No customer logged in.";
        }

        if (loggedinCustomer.rentLimitReached()) {
            return "\nRent limit reached - return an item before renting a new one.";
        }

        for (StockItem stockItem : stockItemList) {
            String stockType = getItemType(stockItem);

            if (stockItem.getId().equals(itemID)) {
                if (!stockItem.isRented()) {
                    stockItem.setRented(true);
                    loggedinCustomer.incrementRentedItems();
                    stockItem.setDateRented(LocalDate.now());

                    return stockItem
                            + "\n" + stockType + " rented!";

                } else {
                    return "Item with id " + itemID + " is already rented";
                }
            }
        }
        return "Item with id " + itemID + " not found.";
    }

    // RETURN ITEM

    /**
     * Returns a game: sets the game to available and updates the totalRentProfit
     * Prompts user for a review
     *
     * @param itemID ID of the game to be returned
     * @param review
     * @return String message indicating success of return
     */
    public String returnItem(String itemID, LocalDate returnDate, Review review) {
        if (loggedinCustomer == null) /* modified for M2 */ {
            return "No customer logged in.";
        }

        int index = getItemIndex(itemID);
        StockItem stockItem = stockItemList.get(index);
        String stockType = getItemType(stockItem);

        // the game was rented successfully
        if (stockItem.isRented()) {
            stockItem.setRented(false);
            loggedinCustomer.decrementRentedItems();

            if (review != null)
                stockItem.setReview(review);

            double totalRentCost = 0;
            LocalDate dateRented = stockItem.getDateRented();
            int nrDaysRented = (Period.between(dateRented, returnDate).getDays());
            nrDaysRented = nrDaysRented == 0 ? 1 : nrDaysRented; // same day return counts as 1 day

            String output = "\n" + stockType + " returned! " + stockItem;

            // customer uses credits to pay /* modified for M2 */
            if (loggedinCustomer.getCredits() == creditsForFreeRent) {
                loggedinCustomer.resetCredits();
                output += ".\nRented for " + nrDaysRented + " days. Used credits to pay!";
            }
            // customer pays with money
            else {
                double dailyRent = loggedinCustomer.applyDiscount(stockItem.getDailyRentCost());
                totalRentCost = dailyRent * nrDaysRented;
                updateTotalRentProfit(dailyRent, nrDaysRented);
                output += "\nTotal rent cost is " + dailyRent + " x " + nrDaysRented + " days = " + totalRentCost + " SEK.";
            }

            loggedinCustomer.addCreditsOnRent();
            rentTransactions.add(new RentTransaction(loggedinCustomer.getId(), totalRentCost, stockItem.getId(), dateRented, returnDate));

            return output;

            // game with that ID was not rented
        } else {
            return "Item with that ID has not been rented out.";
        }

    }


    private String getItemType(StockItem item) {
        if (item instanceof Game)
            return "Game";

        else if (item instanceof SongAlbum)
            return "Album";

        return "Item";
    }

    /**
     * Checks the stock-item list to see if an item exists
     *
     * @param itemID ID of item to check
     * @return true if in the list, else false
     */
    public boolean itemExists(String itemID) {
        for (StockItem item : stockItemList) {
            if (item.getId().equals(itemID))
                return true;
        }
        return false;
    }

    /**
     * Gets the index of the stock-item object in the stock list
     *
     * @param itemID ID of the item
     * @return index of the item object
     */
    private int getItemIndex(String itemID) {
        if (itemExists(itemID)) {
            for (int i = 0; i < stockItemList.size(); i++) {
                if (stockItemList.get(i).getId().equals(itemID))
                    return i;
            }
        }
        return -1;
    }

    private StockItem getStockItem(String stockItemID) {
        for (StockItem item : stockItemList) {
            if (item.getId().equals(stockItemID)) {
                return item;
            }
        }
        return null;
    }

    private Customer getCustomer(String customerID) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }


    /*********************************************************
     * CustomerList methods
     /*********************************************************/
    public boolean addCustomer(Customer customer) {
        return customerList.add(customer);
    }

    /**
     * Removes a customer from the Customer list
     *
     * @param id ID of customer to be removed
     * @return true if customer was found and removed,
     * false if customer with that ID doesn't exist
     */
    public boolean removeCustomer(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {

                if (customerList.removeElement(customer))
                    return true;
            }
        }
        return false;
    }

    public void printCustomerList() {
        for (Customer customer : customerList) {
            System.out.println("[" + customerList.indexOf(customer) + "] " + customer.toString());
        }
    }

    public boolean customerLogin(String customerID, String password) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(customerID) && customer.getPassword().equals(password)) {
                loggedinCustomer = getCustomer(customerID); //new Customer(customer);
                return true;
            }
        }
        return false;
    }

    public void customerLogOut() {
        loggedinCustomer = null;
    }

    public Customer getLoggedinCustomer() {
        return loggedinCustomer;
    }

    public boolean requestMembershipUpgrade() {
        return membershipRequests.add(loggedinCustomer);
    }


    /************************************
     * Message methods
     /***********************************/
    public boolean sendMessage(String recipientID, String messageContent) {
        for (Customer customer : customerList) {
            if (recipientID.equals(customer.getId())) {

                String senderID = loggedinCustomer.getId();
                Message newMessage = new Message(senderID, messageContent, recipientID);
                loggedinCustomer.addMessageToOutbox(newMessage);
                customer.addMessageToInbox(newMessage);
                return true;
            }
        }
        return false;
    }

    public boolean printInboxMessages() {
        ArrayList<Message> inbox = loggedinCustomer.getInbox();

        if (inbox.isEmpty())
            return false;

        for (Message message : inbox) {
            String senderName = getCustomer(message.getSenderID()) == null ? "[sender name]" : getCustomer(message.getSenderID()).getName();

            System.out.println("Status: " + message.getReadStatus()
                    + "\nRecipient: " + message.getReceiverID() + " : " + loggedinCustomer.getName()
                    + "\nSender: " + message.getSenderID() + " : " + senderName
                    + "\nMessage: " + message.getContent()
            );
            message.setReadStatus(true);     //set status of all messages to read AFTER print
        }
        return true;
    }


    /*********************************************************
     * EmployeeList methods
     /*********************************************************/
    public boolean addEmployee(Employee newEmployee) {
        return employeeList.add(newEmployee);
    }

    public void printEmployeeList() {
        for (Employee employee : employeeList) {
            System.out.println(employee.toString());
        }
    }


    /**
     * Gets the net salary and salary bonus of all employees
     * and saves this data in a string array
     *
     * @return string array with employee salary data
     */
    public String[] getEmployeeSalaries() {
        String[] salaryArray = new String[employeeList.size()];

        for (Employee e : employeeList) {
            double grossSalary = e.getGrossSalary();
            double netSalary = e.getNetSalary();
            double bonus = e.getBonus();

            String toPrint = e.getName() + " (" + e.getAge() + ") : ";

            if (grossSalary == netSalary)
                toPrint += "Gross|Net: " + grossSalary + " SEK";

            else toPrint += "Gross: " + grossSalary + " Net: " + netSalary + " SEK";

            toPrint += " Bonus: " + bonus + " SEK" + " Total net: " + (netSalary + bonus);
            salaryArray[employeeList.indexOf(e)] = toPrint;
        }
        return salaryArray;
    }

    public void printMembershipRequests() {
        for (Customer customer : membershipRequests) {
            String print = customer.getId() + " : " + customer.getName()
                    + ". Membership: " + customer.getMembershipName();

            System.out.println(print);
        }
    }

    public void approveAllRequests() {
        for (Customer customer : membershipRequests) {
            customer.upgradeMembership();
            Message newMessage = new Message("GameRental", "Membership upgraded!", customer.getId());
            customer.addMessageToInbox(newMessage);
        }
        membershipRequests.clear(); // remove all requests
    }

    public boolean approveOneRequest(String selection) {
        for (Customer customer : membershipRequests) {
            if (customer.getId().equals(selection)) {
                customer.upgradeMembership();
                membershipRequests.removeElement(customer);
                Message newMessage = new Message("GameRental", "Membership upgraded!", customer.getId());
                customer.addMessageToInbox(newMessage);
                return true;
            }
        }
        return false;
    }

    /*********************************************************
     * Profit methods
     /*********************************************************/
    public double getTotalRentProfit() {
        return totalRentProfit;
    }

    private void updateTotalRentProfit(double dailyRent, int nrDays) {
        totalRentProfit += dailyRent * nrDays;
    }

    public void printAllTransactions() {
        System.out.println("\n**** Transactions (sorted by rent date) **** ");
        Collections.sort(rentTransactions);
        int count = 0;

        for (RentTransaction transaction : rentTransactions) {
            System.out.println(transaction.toString());
            count++;
        }
        if (count == 0)
            System.out.println("\nNo transactions yet!");
    }

    public void getMostProfitableItem() {
        if (stockItemList.isEmpty()) {
            System.out.println("No items in stock");
        } else {
            double highestProfit = 0;
            double currentProfit = 0;
            String highestProfitItemID = "";

            for (int i = 0; i < rentTransactions.size(); i++) {
                String id = rentTransactions.get(i).getItemID();

                for (RentTransaction transaction : rentTransactions) {
                    if (transaction.getItemID().equals(id)) {
                        currentProfit += transaction.getCost();
                    }
                }
                if (currentProfit > highestProfit) {
                    highestProfit = currentProfit;
                    highestProfitItemID = id;
                }
                currentProfit = 0;
            }
            StockItem item = getStockItem(highestProfitItemID);
            if (item != null) {
                System.out.println(item);
                System.out.println("\nTotal profit of this item: " + highestProfit + " SEK");
            }
        }
    }


    public void printRentedItemFrequency() {
        if (rentTransactions.isEmpty()) {
            System.out.println("No items have been rented.");
        } else if (stockItemList.isEmpty()) {
            System.out.println("No items in stock.");
        } else {
            Map<String, Long> occurrences =
                    rentTransactions.stream().collect(Collectors.groupingBy(RentTransaction::getItemID, Collectors.counting()));

            String stockItemID = occurrences.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            long nrTimesRented = occurrences.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();

            System.out.println("\n\t Most frequently rented item " + "[" + nrTimesRented + " times]");
            System.out.println(getStockItem(stockItemID).toString());
        }
    }

    public void printMostProfitableCustomer() {
        if (rentTransactions.isEmpty()) {
            System.out.println("No items have been rented yet.");
        }
        double highestSpending = 0;
        double currentSpending = 0;
        String mostProfitableCustomerID = "";

        for (int i = 0; i < rentTransactions.size(); i++) {
            String customerID = rentTransactions.get(i).getCustomerID();

            for (RentTransaction transaction : rentTransactions) {
                if (transaction.getCustomerID().equals(customerID)) {
                    currentSpending += transaction.getCost();
                }
            }
            if (currentSpending > highestSpending) {
                highestSpending = currentSpending;
                mostProfitableCustomerID = customerID;
            }
            currentSpending = 0;
        }
        Customer customer = getCustomer(mostProfitableCustomerID);
        if (customer != null) {
            System.out.println(customer);
            System.out.println("\nTotal spent by customer: " + highestSpending + " SEK,");
        }
    }

    public LocalDate getRentDate(String id) {
        for (StockItem item : stockItemList) {
            if (item.getId().equals(id)) {
                return item.getDateRented();
            }
        }
        return null;
    }


    public boolean exportRentTransactionHistory(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (RentTransaction transaction : rentTransactions) {
                String customerID = transaction.getCustomerID();
                String itemID = transaction.getItemID();
                String itemTitle = getStockItem(transaction.getItemID()).getTitle();
                String rentPaid = String.valueOf(transaction.getCost());

                fileWriter.write(customerID + "; " + itemID + "; " + itemTitle + "; " + rentPaid + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean importDataFromFile(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");

                switch (parts[0].toLowerCase()) {
                    case "employee": {
                        String name = parts[1].trim();
                        String birthYear = parts[2].trim();
                        String address = parts[3].trim();
                        String grossSalary = parts[4].trim();
                        employeeList.add(new Employee(name, Integer.parseInt(birthYear), address, Double.parseDouble(grossSalary)));
                    }
                    break;

                    case "customer": {
                        String name = parts[1].trim();
                        String membershipType = parts[2].trim();
                        customerList.add(new Customer(name, "", membershipType));
                    }
                    break;

                    case "game": {
                        String title = parts[1].trim();
                        String genre = parts[2].trim();
                        String rentCost = parts[3].trim();
                        String averageRating = parts[4].trim();
                        stockItemList.add(new Game(title, genre, Double.parseDouble(rentCost), Double.parseDouble(averageRating)));
                    }
                    break;

                    case "album": {
                        String title = parts[1].trim();
                        String artist = parts[2].trim();
                        String yearReleased = parts[3].trim();
                        String averageRating = parts[4].trim();
                        String rentCost = parts[5].trim();
                        stockItemList.add(new SongAlbum(title, artist, Integer.parseInt(yearReleased), Double.parseDouble(averageRating), Double.parseDouble(rentCost)));
                    }
                    break;
                }

            }

        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void addTransaction(RentTransaction transaction) {
        rentTransactions.add(transaction);
    }
}