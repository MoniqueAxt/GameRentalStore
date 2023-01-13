# Video Game Rental System

## Description

A simple console-based application that simulates renting video games from a retail store.
This project is intended to illustrate fundamental OOP concepts as well basic as use of the Java library.
It uses an [controller class](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java) to interface between [Main](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/Main.java) and the relevant classes.

## Application

This menu-run program allows customers, employees and managers to log-in to the system and execute various tasks.

### Interface Menus


<details>
<summary>Main menu</summary>

```
Welcome to Video Game Store, your good old game rental system.

Please specify your role by entering one of the options given:
Enter "M" for Manager
Enter "E" for Employee
Enter "C" for Customer
Enter "X" to exit system
```
</details>

 <details>
        <summary>Employee & Manager menus</summary>
<table>
  <tr>
    <td>

Employee menu

```
Employee Screen - Type one of the options below:
1. Register a stock item
2. Remove a stock item
3. View all stock items
----------------------------------
4. Register a customer
5. Remove a customer
6. View membership requests
----------------------------------
7. Show total rent profit
----------------------------------
0. Return to the main menu
```

</td>
<td>

Manager menu

```
Manager Screen - Type one of the options below:
1. Add an employee
2. View all employees
3. View employee salaries/bonuses
----------------------------------
4. Print transaction history
5. Print most profitable item
6. Print rented items frequency
7. Print most profitable customer
----------------------------------
8. Export data to file
9. Import data from file
----------------------------------
0. Return to the main menu
```

</td>
  </tr>
</table>
</details>

### Customer menu
```
	## Logged in as: Ryan Reynolds - Premium ##

Customer Screen - Type one of the options below:
1. Rent a game / song album
2. Return a game / song album
3. Search for an item
4. View highest rated items
5. Print reviews of an item
----------------------------------
6. Request membership / membership upgrade
7. Print account info
----------------------------------
8. Send a message
9. View inbox
----------------------------------
0. Return to the main menu
```

### Feature: Messaging system
<sub>_See : [Message classes](https://github.com/MoniqueAxt/GameRentalStore/tree/master/src/se/oop/messagesystem)
and [code](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java#L429-L460)_</sub>

<details>
 <summary>Sending messages</summary>

```
## Logged in as: Ryan Reynolds - Premium ##

Customer Screen - Type one of the options below:
1. Rent a game / song album
2. Return a game / song album
3. Search for an item
4. View highest rated items
5. Print reviews of an item
----------------------------------
6. Request membership / membership upgrade
7. Print account info
----------------------------------
8. Send a message
9. View inbox
----------------------------------
0. Return to the main menu
>> 8
   
Enter ID of recipient:
>> 196cd5fb-792a-4e05-b354-25ed06269e2a

Enter message:
>> Can I borrow Fortnite??

Message sent!
Press Enter to continue...
```
</details>

<details>
 <summary>Receiving messages</summary>

```
## Logged in as: Dwayne Johnson - Silver ##

Customer Screen - Type one of the options below:
1. Rent a game / song album
2. Return a game / song album
3. Search for an item
4. View highest rated items
5. Print reviews of an item
----------------------------------
6. Request membership / membership upgrade
7. Print account info
----------------------------------
8. Send a message
9. View inbox
----------------------------------
0. Return to the main menu
   >> 9
   
   Status: UNREAD
   Recipient: 196cd5fb-792a-4e05-b354-25ed06269e2a : Dwayne Johnson
   Sender: 78a6f52a-4d3f-4ffe-bb5c-5a02e3aa65c4 : Ryan Reynolds   
   Message: Can I borrow Fortnite??
   
   Press Enter to continue...
   ```
</details>

### Feature: Transactions of games and song albums

<sub>_See: [RentTransactions.java](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/transactions/RentTransaction.java), [Stock](https://github.com/MoniqueAxt/GameRentalStore/tree/master/src/se/oop/stock) and [GameRentalController.java](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java)_</sub>

Renting and returning <sub>_[code](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java#L216-L310)_ </sub>
> Games and songs can be rented and returned by Customers. The dates of the rental and the return are used to calculate
> the cost.

Searching, sorting and storing rent history

> Games and songs can be sorted and searched based on ratings or year.
> A manager can view and store a complete rental history (item, cost, renter, profit).

### Feature: Membership system

Requests, upgrades and approvals <sub>_[Membership.java](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/person/customer/Membership.java)_</sub>

> Memberships are granted to customers based on their level of spending. Customers can
> request an upgrade, and Employees can approve these requests. Responses are sent to the customer's inbox.

### Feature: Import and export data

File management <sub>_[code](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java#L646-L714)_</sub>

> Files to import can include mixed content, including Employee data, stock items, and Customers.
> Transaction history can be exported to file.


### Feature: Finances and stats

Profit <sub>_[code](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/GameRentalController.java#L538-L643)_ </sub>

> Managers can view the most profitable items, the most profitable customers and the rent-frequency of each stock item.

Salaries

> Managers can view the calculated net salary of employees, taxed at 30%. Salary bonuses are rewarded based on the age
> range of the Employee.

### Feature: Review games and song albums
<sub>_See: [Review.java](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/stock/Review.java)_
and _[review creation](https://github.com/MoniqueAxt/GameRentalStore/blob/master/src/se/oop/Main.java#L899-L914)_</sub>

> Games and songs can be rated and reviewed by a customer who has rented the item. The average rating of each stock item
> is also available.
