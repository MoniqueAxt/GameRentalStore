package se.oop.person;

import java.util.Calendar;

public class Employee extends Person {
    private static final String password = "password123";
    private int birthYear;
    private String address;
    private double grossSalary;


    public Employee(String name, int birthYear, String address, double grossSalary) {
        super(name);
        this.birthYear = birthYear;
        this.address = address;
        this.grossSalary = grossSalary;
    }

    public static String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + birthYear + "(" + getAge() + "): " + grossSalary + "SEK.";
    }

    /**
     * Calculates the net salary from gross salary
     *
     * @return net salary
     */
    public double getNetSalary() {
        return (grossSalary < 100000) ? grossSalary : grossSalary * 0.7;
    }

    /**
     * Calculates salary bonus of an employee based on age
     *
     * @return bonus of the employee
     */
    public double getBonus() {
        int age = getAge();

        if (age < 22)
            return 0;

        else if (age > 22 && age < 30)
            return 6000;

        else
            return 7500;
    }

    public void changeMembership() {

    }

    /*********************************************************
     * Getters and Setters
     /*********************************************************/

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - birthYear;
    }


    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

}
