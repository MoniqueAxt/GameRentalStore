package se.oop;

import se.oop.exceptions.CannotBeEmptyException;
import se.oop.exceptions.CannotBeZeroException;
import se.oop.exceptions.ReturnDateException;
import se.oop.person.Employee;
import se.oop.person.Manager;
import se.oop.person.customer.Customer;
import se.oop.stock.Game;
import se.oop.stock.Review;
import se.oop.stock.SongAlbum;
import se.oop.stock.StockItem;
import se.oop.transactions.RentTransaction;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static final String TRANSACTION_HISTORY_FILENAME = "GAMESTORE_transactions.txt";
    static final int GAME = 1;
    static final int ALBUM = 2;
    static GameRentalController gameRentalController;
    static Scanner keyboard;

    public static void main(String[] args) {
        gameRentalController = new GameRentalController();
        createTestCases();
        String mainInput = "";

        while (!mainInput.equalsIgnoreCase("X")) {
            keyboard = new Scanner(System.in);
            gameRentalController.customerLogOut();
            mainInput = printMainMenu();

            switch (mainInput) {
                case "M": {
                    System.out.print("Enter password: ");
                    String password = keyboard.nextLine();

                    if (password.equals(Manager.getPassword())) {
                        printManagerMenu();
                    } else
                        printUserMessage("\n\t --- Incorrect password --- \n");
                }
                break;

                case "E": {
                    System.out.print("Enter password: ");
                    String password = keyboard.nextLine();

                    if (password.equals(Employee.getPassword())) {
                        printEmployeeMenu();
                    } else
                        printUserMessage("\n\t --- Incorrect password --- \n");
                }
                break;

                case "C": {
                    gameRentalController.printCustomerList();
                    System.out.print("Enter ID: ");
                    String id = keyboard.nextLine();
                    System.out.print("Enter password: ");
                    String password = keyboard.nextLine();

                    boolean success = gameRentalController.customerLogin(id, password);

                    if (success) {
                        printUserMessage("\nSuccessful login!");
                        printCustomerMenu();
                    } else {
                        printUserMessage("\nID or password incorrect.");
                    }
                }
                break;

                case "X":
                case "x":
                    break;
                default: {
                    printUserMessage("\n\t --- Invalid selection ---");
                }
            }
        }

        keyboard.close();
        System.out.println("\n\nGoodbye!");
    }

    /*********************************************************
     * Main menu
     /*********************************************************/
    public static String printMainMenu() {

        System.out.println("Main Menu"
                + "\nWelcome to Video Game Store, your good old game rental system. The competition has no steam to keep up!\n"
                + "\nPlease specify your role by entering one of the options given:"
                + "\nEnter \"M\" for Manager"
                + "\nEnter \"E\" for Employee"
                + "\nEnter \"C\" for Customer"
                + "\nEnter \"X\" to exit system"
        );

        return keyboard.nextLine();
    }

    /*********************************************************
     * Manager Menu
     /*********************************************************/
    public static void printManagerMenu() {
        int input = -1;

        while (input != 0) {
            System.out.println(
                    "\nManager Screen - Type one of the options below:"
                            + "\n1. Add an employee"
                            + "\n2. View all employees"
                            + "\n3. View employee salaries/bonuses"
                            + "\n----------------------------------"
                            + "\n4. Print transaction history"
                            + "\n5. Print most profitable item"
                            + "\n6. Print rented items frequency"
                            + "\n7. Print most profitable customer"
                            + "\n----------------------------------"
                            + "\n8. Export data to file"
                            + "\n9. Import data from file"
                            + "\n----------------------------------"
                            + "\n0. Return to the main menu"
            );

            input = keyboard.nextInt();
            keyboard.nextLine();    // discard newline

            switch (input) {
                // add employee
                case 1: {
                    System.out.print("\nCreating an Employee. Please type the Employee's:");
                    int count = 0;
                    int maxTries = 3;
                    String name = "";
                    double grossSalary = -1;
                    boolean returnToMain = false;

                    // get name
                    while (true) {
                        try {
                            System.out.print("\nName: ");
                            name = keyboard.nextLine().trim();

                            if (name.isEmpty()) {
                                throw new CannotBeEmptyException("\nInvalid data. Employee name cannot be empty.");
                            }
                            count = 0;
                            break;

                        } catch (CannotBeEmptyException e) {
                            System.out.println(e.getMessage() == null ? "Invalid data. Employee name cannot be empty." : e.getMessage());

                            if (++count == maxTries) {
                                printUserMessage("\nMaximum tries reached. Returning to the main menu.");
                                returnToMain = true;
                                break;
                            }
                        }
                    }

                    // get salary
                    if (!returnToMain) {
                        count = maxTries = 3;

                        while (true) {
                            try {
                                System.out.print("Gross Salary: ");
                                grossSalary = keyboard.nextDouble();
                                keyboard.nextLine();    // discard the line-break

                                if (grossSalary < 0) {
                                    throw new CannotBeEmptyException("Invalid data. Employee salary cannot be negative.");
                                }
                                break;

                            } catch (CannotBeEmptyException e) {
                                System.out.println(e.getMessage() == null ? "Invalid data. Employee salary cannot be negative." : e.getMessage());

                                if (++count == maxTries) {
                                    printUserMessage("\nMaximum tries reached. Returning to the main menu.");
                                    returnToMain = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!returnToMain) {
                        System.out.print("Birth Year: ");
                        int birthYear = keyboard.nextInt();
                        keyboard.nextLine();    // discard the line-break

                        System.out.print("Address: ");
                        String address = keyboard.nextLine();

                        String msg;
                        if (gameRentalController.addEmployee(new Employee(name, birthYear, address, grossSalary)))
                            msg = "\n\t--- Employee added! ---";
                        else
                            msg = "\n\t--- Could not add Employee! ---";

                        printUserMessage(msg);
                    }
                }
                break;

                // print all employees
                case 2: {
                    gameRentalController.printEmployeeList();
                    printUserMessage("");
                }
                break;

                // view employee salaries
                case 3: {
                    String[] salaryArray = gameRentalController.getEmployeeSalaries();
                    for (String s : salaryArray)
                        System.out.println(s);

                    printUserMessage("");
                }
                break;

                // print transaction history
                case 4: {
                    gameRentalController.printAllTransactions();
                    printUserMessage("");
                }
                break;

                // print most profitable item
                case 5: {
                    System.out.println("\n\tMost profitable item: ");
                    gameRentalController.getMostProfitableItem();
                    printUserMessage("");
                }
                break;

                // print most frequently rented item
                case 6: {
                    System.out.println("\n\tRent item frequency: ");
                    gameRentalController.printRentedItemFrequency();
                    printUserMessage("");
                }
                break;

                // print most profitable customer
                case 7: {
                    System.out.println("\n\tMost profitable customer: ");
                    gameRentalController.printMostProfitableCustomer();
                    printUserMessage("");
                }
                break;

                // export rent transaction data to file
                case 8: {
                    System.out.println("\n\tExporting transaction data to file ...");
                    boolean exportSuccess;

                    exportSuccess = gameRentalController.exportRentTransactionHistory(TRANSACTION_HISTORY_FILENAME);

                    String msg = "Error - couldn't export!";
                    if (exportSuccess) {
                        msg = "Export successful!";
                    }
                    printUserMessage(msg);
                }
                break;

                // import data from file
                case 9: {
                    System.out.print("\nName of file: ");
                    String filename = keyboard.nextLine();

                    boolean success = gameRentalController.importDataFromFile(filename);

                    String msg = "Error - couldn't import!";
                    if (success) {
                        msg = "Import successful!";
                    }
                    printUserMessage(msg);
                }
                break;

                case 0:
                    break;

                default:
                    printUserMessage("\n\t --- Invalid selection ---");

            }
        }
    }

    /*********************************************************
     * Employee Menu
     /*********************************************************/
    public static void printEmployeeMenu() {
        int mainInput = -1;

        while (mainInput != 0) {

            System.out.println(
                    "\nEmployee Screen - Type one of the options below:"
                            + "\n1. Register a stock item"
                            + "\n2. Remove a stock item"
                            + "\n3. View all stock items"
                            + "\n----------------------------------"
                            + "\n4. Register a customer"
                            + "\n5. Remove a customer"
                            + "\n6. View membership requests"
                            + "\n----------------------------------"
                            + "\n7. Show total rent profit"
                            + "\n----------------------------------"
                            + "\n0. Return to the main menu"
            );

            mainInput = keyboard.nextInt();
            keyboard.nextLine();    //discard newline

            switch (mainInput) {

                // register a stock item
                /* Modified for M2 by creating superclass StockItem and subclassing Game/SongAlbum */
                /* Modified for M3 by adding Exception class and using try-catch blocks */
                case 1: {
                    System.out.println("\nSelect type of stock item"
                            + "\n1. Game"
                            + "\n2. Song Album");

                    final int type = keyboard.nextInt();
                    keyboard.nextLine();
                    String msg = "";
                    StockItem item = null;

                    switch (type) {
                        case GAME:
                            item = createGame();
                            break;
                        case ALBUM:
                            item = createAlbum();
                            break;

                        default:
                            msg = "\n\t --- Invalid selection ---";
                    }

                    if (item != null) {
                        if (gameRentalController.addStockItem(item)) {
                            System.out.println(item);
                            msg = "Item added!";
                        } else {
                            msg = "Item could not be added!";
                        }
                    }

                    printUserMessage(msg);

                }
                break;

                // remove a stock item
                /* Modified for M2 by creating superclass StockItem and subclassing Game/SongAlbum */
                case 2: {
                    gameRentalController.printAllStockItems();
                    System.out.print("\nWhich item should be removed? ID: ");
                    String itemID = keyboard.nextLine();

                    String msg;

                    if (gameRentalController.removeStockItem(itemID.trim()))   //remove leading/trailing white-space
                        msg = "Item removed!";
                    else
                        msg = "Item with id " + itemID + " not found.";

                    printUserMessage(msg);
                }
                break;

                // print all stock items
                /* Modified for M2 by creating superclass StockItem and subclassing Game/SongAlbum */
                case 3: {
                    gameRentalController.printAllStockItems();
                    printUserMessage("");
                }
                break;

                // add customer
                case 4: {
                    System.out.print(
                            "\nCreating a Customer. Please type the Customer's:"
                                    + "\nName: ");
                    String customerName = keyboard.nextLine();

                    System.out.print("\nLog-in password: ");
                    String password = keyboard.nextLine();

                    /* modified for M2 */
                    System.out.print("\nMembership type [regular | silver | gold | premium]: ");
                    String membershipType = keyboard.nextLine();
                    Customer newCustomer = new Customer(customerName, password, membershipType);

                    String msg;

                    if (gameRentalController.addCustomer(newCustomer)) {
                        System.out.println(newCustomer);
                        msg = "Customer added!";
                    } else
                        msg = "Could not add customer!";

                    printUserMessage(msg);
                }
                break;

                // remove customer
                case 5: {
                    gameRentalController.printCustomerList();
                    System.out.print("\nWhich customer should be removed? ID: ");
                    String customerId = keyboard.nextLine();

                    String msg;

                    if (gameRentalController.removeCustomer(customerId.trim()))   // remove leading/trailing white-space
                        msg = "Customer removed!";
                    else
                        msg = "Customer with id " + customerId + " not found.";

                    printUserMessage(msg);
                }
                break;

                // view membership requests
                case 6: {
                    System.out.print("\n\t*** Membership Requests ***");
                    gameRentalController.printMembershipRequests();

                    System.out.println("\nEnter ID to approve a specific membership upgrade or enter 0 to approve all.");
                    String selection = keyboard.nextLine();

                    if (selection.equals("0")) {
                        gameRentalController.approveAllRequests();
                    } else {
                        String msg;
                        if (gameRentalController.approveOneRequest(selection.trim()))  // remove leading/trailing white-space
                            msg = "Upgrade done!";
                        else
                            msg = "Upgrade couldn't be done! The customer ID doesn't exist.";

                        printUserMessage(msg);
                    }

                }
                break;

                // calculate profit
                case 7: {
                    double profit = gameRentalController.getTotalRentProfit();
                    printUserMessage("Total rent profit: " + profit + " SEK");
                }
                break;

                case 0:
                    break;

                default:
                    printUserMessage("\n\t --- Invalid selection ---");
            }
        }
    }

    /* Modified for M3 - added Exception throwing */
    private static StockItem createAlbum() {
        System.out.print("\nCreating an Album. Please type the item's:");


        String albumName = "", genre = "";
        double rentFee = -1;
        int count = 0, maxTries = 3;
        boolean nameValid = false, rentFeeValid = false;

        while (true) {
            try {
                // if game-name is invalid, throw exception
                if (!nameValid) {
                    System.out.print("\nTitle: ");
                    albumName = keyboard.nextLine();
                    nameValid = !albumName.isEmpty();

                    if (!nameValid)
                        throw new CannotBeEmptyException("Invalid data. Album name cannot be empty.”");

                    count = 0;  // reset counter if exception is not thrown
                }

                // if game rent-fee is invalid, throw exception
                if (!rentFeeValid) {
                    System.out.print("\nDaily Rent Fee: ");
                    rentFee = keyboard.nextDouble();
                    keyboard.nextLine();    // discard linebreak
                    rentFeeValid = rentFee > 0;

                    if (!rentFeeValid)
                        throw new CannotBeZeroException("Invalid data. Album daily rent fee cannot be negative.");

                    count = 0;  // reset counter if exception is not thrown
                }
                break;  // break out of loop if no exceptions were thrown (i.e. all data valid)

            } catch (CannotBeEmptyException | CannotBeZeroException e) {
                if (!rentFeeValid)
                    System.out.println(e.getMessage() == null ? "Invalid data. Album daily rent fee cannot be negative." : e.getMessage());

                else if (!nameValid)
                    System.out.println(e.getMessage() == null ? "Invalid data. Album name cannot be empty." : e.getMessage());

                if (++count == maxTries) {
                    System.out.println("\nMaximum tries reached. Returning to previous menu.");
                    return null;
                }
            }
        }

        System.out.print("\nArtist: ");
        String artist = keyboard.nextLine();

        System.out.print("\nYear Released: ");
        int yearReleased = keyboard.nextInt();
        keyboard.nextLine();    // discard linebreak

        System.out.print("\nUser rating: ");
        double userRating = keyboard.nextDouble();
        keyboard.nextLine();    // discard linebreak

        return new SongAlbum(albumName, artist, yearReleased, userRating, rentFee);
    }

    /* Modified for M3 - added Exception throwing */
    private static StockItem createGame() {
        System.out.print("\nCreating a Game. Please type the game's:");

        String gameName = "", genre = "";
        double rentFee = -1;
        int count = 0, maxTries = 3;
        boolean nameValid = false, rentFeeValid = false;

        while (true) {
            try {
                // if game-name is invalid, throw exception
                if (!nameValid) {
                    System.out.print("\nTitle: ");
                    gameName = keyboard.nextLine();
                    nameValid = !gameName.isEmpty();

                    if (!nameValid)
                        throw new CannotBeEmptyException("Invalid data. Game name cannot be empty.”");

                    count = 0;
                }

                // if game rent-fee is invalid, throw exception
                if (!rentFeeValid) {
                    System.out.print("\nDaily Rent Fee: ");
                    rentFee = keyboard.nextDouble();
                    keyboard.nextLine();    // discard linebreak
                    rentFeeValid = rentFee > 0;

                    if (!rentFeeValid)
                        throw new CannotBeZeroException("Invalid data. Game daily rent fee cannot be negative.");

                    count = 0;
                }
                break;  // break out of loop if no exceptions were thrown (i.e. all data valid)

            } catch (CannotBeEmptyException | CannotBeZeroException e) {
                if (!rentFeeValid)
                    System.out.println(e.getMessage() == null ? "Invalid data. Game daily rent fee cannot be negative." : e.getMessage());

                else if (!nameValid)
                    System.out.println(e.getMessage() == null ? "Invalid data. Game name cannot be empty." : e.getMessage());

                if (++count == maxTries) {
                    System.out.println("\nMaximum tries reached. Returning to previous menu.");
                    return null;
                }
            }
        }

        System.out.print("\nGenre: ");
        genre = keyboard.nextLine();

        return new Game(gameName, genre, rentFee);
    }

    /*********************************************************
     * Customer Menu
     /*********************************************************/
    public static void printCustomerMenu() {
        int input = -1;

        while (input != 0) {
            System.out.println("\n\t## Logged in as: " + gameRentalController.getLoggedinCustomer().getName()
                    + " - " + gameRentalController.getLoggedinCustomer().getMembershipName() + " ##");

            /* Modified menu for M2 */
            System.out.println(
                    "\nCustomer Screen - Type one of the options below:"
                            + "\n1. Rent a game / song album"
                            + "\n2. Return a game / song album"
                            + "\n3. Search for an item"
                            + "\n4. View highest rated items"
                            + "\n5. Print reviews of an item"
                            + "\n----------------------------------"
                            + "\n6. Request membership / membership upgrade"
                            + "\n7. Print account info"
                            + "\n----------------------------------"
                            + "\n8. Send a message"
                            + "\n9. View inbox"
                            + "\n----------------------------------"
                            + "\n0. Return to the main menu"
            );

            input = keyboard.nextInt();
            keyboard.nextLine();

            switch (input) {
                // rent stock item
                case 1: {
                    System.out.println("Would you like to rent a game or song album?");
                    System.out.println("\n1. Game"
                            + "\n2. Song album"
                            + "\n3. Not specified");

                    int selection = keyboard.nextInt();
                    keyboard.nextLine();
                    String itemID = "";

                    switch (selection) {
                        // print games
                        case GAME: {
                            gameRentalController.printAllGames();
                        }
                        break;

                        // print song album
                        case ALBUM: {
                            gameRentalController.printAllAlbums();
                        }
                        break;

                        default: {
                            gameRentalController.printAllStockItems();
                        }
                    }

                    System.out.println("Credits available: " + gameRentalController.getLoggedinCustomer().getCredits());
                    System.out.print("\nEnter the ID of the item you'd like to rent: ");
                    itemID = keyboard.nextLine();
                    printUserMessage(gameRentalController.rentStockItem(itemID).trim()); // remove leading/trailing white-space

                }
                break;

                // return stock item
                case 2: {
                    System.out.println("Would you like to rent a game or song album?");
                    System.out.println("\n1. Game"
                            + "\n2. Song album"
                            + "\n3. Not specified");

                    int selection = keyboard.nextInt();
                    keyboard.nextLine();

                    switch (selection) {
                        case GAME: {
                            gameRentalController.printAllGames();
                        }
                        break;

                        case ALBUM: {
                            gameRentalController.printAllAlbums();
                        }
                        break;

                        default: {
                            gameRentalController.printAllStockItems();
                        }

                        System.out.print("\nWhich item do you want to return? ID: ");
                        String id = keyboard.nextLine();
                        String message = "";
                        LocalDate returnDate = null;
                        LocalDate rentDate;

                        if (gameRentalController.itemExists(id)) {
                            int count = 0, maxTries = 0;
                            boolean returnToMain = false;

                            // check the return date is valid
                            while (true) {
                                try {
                                    System.out.print("\nDate returned? [YYYY-MM-DD] : ");
                                    returnDate = LocalDate.parse(keyboard.nextLine());
                                    rentDate = gameRentalController.getRentDate(id);

                                    if (returnDate.compareTo(rentDate) < 0) {       // if return date is BEFORE rent date
                                        throw new ReturnDateException("Invalid operation. Upon returning an item, the number of days rented must be positive.");
                                    }
                                    break;  // break out of loop if no exception thrown

                                } catch (ReturnDateException e) {
                                    System.out.println(e.getMessage() == null ?
                                            "Invalid operation. Upon returning an item, the number of days rented must be positive."
                                            : e.getMessage());

                                    if (++count == maxTries) {
                                        printUserMessage("\nMaximum tries reached. Returning to the main menu.");
                                        returnToMain = true;
                                        break;
                                    }
                                }
                            }

                            // if return date is valid, get further input
                            if (!returnToMain) {
                                Review review = getReview();
                                message = gameRentalController.returnItem(id, returnDate, review);

                            }
                        } else {
                            message = "Item with that ID not found.";
                        }
                        printUserMessage(message);
                    }
                }
                break;

                // search for an item
                case 3: {
                    System.out.println("Would you like to search for a game or song album?");
                    System.out.println("\n1. Game"
                            + "\n2. Song album");

                    int selection = keyboard.nextInt();
                    keyboard.nextLine();
                    String msg = "";

                    switch (selection) {
                        case GAME -> {
                            System.out.print("What genre of game do you want to search for?: ");
                            String genre = keyboard.nextLine();
                            boolean itemsFound = gameRentalController.searchGamesByGenre(genre);

                            if (!itemsFound)
                                msg = "\nNo games in genre: " + genre;

                        }
                        case ALBUM -> {
                            System.out.print("What year do you want to search for?: ");
                            int year = keyboard.nextInt();
                            keyboard.nextLine();
                            boolean itemsFound = gameRentalController.searchAlbumsByYear(year);

                            if (!itemsFound)
                                msg = "\nNo albums stocked that were released in" + year;
                        }
                        default -> {
                            printUserMessage("\n\t --- Invalid selection ---");
                        }
                    }
                    printUserMessage(msg);
                }
                break;

                // view highest rated
                case 4: {
                    System.out.println("Would you like to view the ratings of games or albums?");
                    System.out.println("\n1. Games"
                            + "\n2. Song albums"
                            + "\n3. All items");

                    int selection = keyboard.nextInt();
                    keyboard.nextLine();

                    switch (selection) {
                        case GAME -> {
                            System.out.print("\n\t*** Game Ratings ***");
                            gameRentalController.printItemRatingsDescending("game");
                        }
                        case ALBUM -> {
                            System.out.print("\n\t*** Album Ratings ***");
                            gameRentalController.printItemRatingsDescending("album");
                        }
                        case 3 -> {
                            gameRentalController.printItemRatingsDescending("");
                        }
                        default -> {
                            {
                                printUserMessage("\n\t --- Invalid selection ---");
                            }
                            printUserMessage("");
                        }
                    }
                }
                break;

                // print reviews of an item
                case 5: {
                    gameRentalController.printAllStockItems();
                    System.out.println("Enter ID of item to see its reviews: ");
                    String id = keyboard.nextLine();

                    if (gameRentalController.itemExists(id)) {
                        boolean itemsFound = gameRentalController.printReviews(id);

                        String msg = "\nNo reviews yet.";
                        if (itemsFound)
                            msg = "";
                        printUserMessage(msg);
                    } else {
                        printUserMessage("Item with that ID not found");
                    }
                }
                break;

                // request membership upgrade
                case 6: {
                    if (gameRentalController.getLoggedinCustomer().getMembershipName().equalsIgnoreCase("premium")) {
                        printUserMessage("Membership cannot be upgraded further.");
                    } else {

                        boolean success = gameRentalController.requestMembershipUpgrade();
                        String msg;

                        if (success) {
                            msg = "Request sent!";
                        } else {
                            msg = "Couldn't send request! Contact store for assistance.";
                        }
                        printUserMessage(msg);
                    }
                }
                break;

                // print account info
                case 7: {
                    printUserMessage(gameRentalController.getLoggedinCustomer().getAccountInfo());
                }
                break;

                // send a message
                case 8: {
                    gameRentalController.printCustomerList();
                    System.out.println("\nEnter ID of recipient: ");
                    String recipientID = keyboard.nextLine();

                    System.out.println("\nEnter message: ");
                    String messageContent = keyboard.nextLine();

                    boolean success = gameRentalController.sendMessage(recipientID, messageContent);

                    String msg;
                    if (success) {
                        msg = "Message sent!";
                    } else {
                        msg = "Couldn't send message! Recipient ID not found.";
                    }
                    printUserMessage(msg);
                }
                break;

                // view inbox messages
                case 9: {
                    boolean hasMessages = gameRentalController.printInboxMessages();
                    String msg = "No messages";

                    if (hasMessages) {
                        msg = "";
                    }
                    printUserMessage(msg);

                }
                break;

                case 0:
                    break;

                default: {
                    printUserMessage("\n\t --- Invalid selection ---");
                }
            }
        }
    }

    private static Review getReview() {
        System.out.print("\nAdd review? (y/n): ");
        String input = keyboard.nextLine();

        if (input.equalsIgnoreCase("y")) {
            System.out.print("\nYour rating [1-5]: ");
            int rating = keyboard.nextInt();
            keyboard.nextLine();

            System.out.println("\nYour written review (press Enter to skip): ");
            String textReview = keyboard.nextLine();
            return new Review(rating, textReview, gameRentalController.getLoggedinCustomer().getId());
        } else {
            return null;
        }
    }

    private static void printUserMessage(String msg) {
        if (!msg.isEmpty())
            System.out.println(msg);

        System.out.println("\tPress Enter to continue...");
        keyboard.nextLine();
    }


    /*********************************************************
     * Test cases
     /*********************************************************/
    public static void createTestCases() {
        // add Customers
        Customer customer = new Customer("Dwayne Johnson", "dwayne123", "Silver");
        Customer customer2 = new Customer("Ryan Reynolds", "ryan123", "Premium");
        Customer customer3 = new Customer("Billie Eilish", "billie123", "Gold");
        Customer customer4 = new Customer("Susan Sarandon", "susan123", "silver");

        gameRentalController.addCustomer(customer);
        gameRentalController.addCustomer(customer2);
        gameRentalController.addCustomer(customer3);
        gameRentalController.addCustomer(customer4);

        // add Employees
        // (String name, int birthYear, String address, double grossSalary)
        Employee employee = new Employee("Mora Kovach", 1995, "Kyrkagatan 20, Göteborg", 62000.00);
        Employee employee2 = new Employee("Enola Holmes", 2000, "Vasagatan 88, Göteborg", 100000.00);
        Employee employee3 = new Employee("Jonas Henrikson", 1985, "Fiskgatan 32, Göteborg", 150000.00);

        gameRentalController.addEmployee(employee);
        gameRentalController.addEmployee(employee2);
        gameRentalController.addEmployee(employee3);

        // add Games
        // (String name, String genre, double rentCost)
        Game game = new Game("Fortnite", "Battle Royale", 40.00);
        Game game2 = new Game("Cyberpunk 2077", "RPG", 60.00);
        Game game3 = new Game("Minecraft", "Sandbox", 24.50);
        Game game4 = new Game("Wasteland 3", "RPG", 55.00);

        gameRentalController.addStockItem(game);
        gameRentalController.addStockItem(game2);
        gameRentalController.addStockItem(game3);
        gameRentalController.addStockItem(game4);

        // add SongAlbums
        SongAlbum album1 = new SongAlbum("Hollywood's Bleeding", "Post Malone", 2019, 4, 34);
        SongAlbum album2 = new SongAlbum("Thriller", "Micheal Jackson", 1982, 5, 24);
        SongAlbum album3 = new SongAlbum("Hybrid Theory", "Linkin Park ", 2000, 4, 24);
        SongAlbum album4 = new SongAlbum("1989", "Taylor Swift", 2014, 2, 44);

        gameRentalController.addStockItem(album1);
        gameRentalController.addStockItem(album2);
        gameRentalController.addStockItem(album3);
        gameRentalController.addStockItem(album4);


        RentTransaction transaction1 = new RentTransaction(customer.getId(), game.getDailyRentCost(), game.getId(), LocalDate.parse("2020-10-01"), LocalDate.parse("2020-10-06"));
        RentTransaction transaction2 = new RentTransaction(customer.getId(), album2.getDailyRentCost(), album2.getId(), LocalDate.parse("2020-06-17"), LocalDate.parse("2020-06-17"));
        RentTransaction transaction3 = new RentTransaction(customer2.getId(), game2.getDailyRentCost(), game2.getId(), LocalDate.parse("2020-01-01"), LocalDate.parse("2020-02-01"));
        RentTransaction transaction4 = new RentTransaction(customer3.getId(), album4.getDailyRentCost(), album4.getId(), LocalDate.parse("2020-05-11"), LocalDate.parse("2020-05-13"));
        RentTransaction transaction5 = new RentTransaction(customer2.getId(), game.getDailyRentCost(), game.getId(), LocalDate.parse("2020-07-01"), LocalDate.parse("2020-08-06"));
        gameRentalController.addTransaction(transaction1);
        gameRentalController.addTransaction(transaction2);
        gameRentalController.addTransaction(transaction3);
        gameRentalController.addTransaction(transaction4);
        gameRentalController.addTransaction(transaction5);


    }

}
