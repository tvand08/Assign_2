package Lib_Program;
import java.io.Serializable;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Item
 * Contains the information relevant
 * for every Item type
 */
public class Item implements Serializable{
    static final long serialVersionUID = 42L;
    protected int minAge = 0;
    protected Patron loanedTo;
    protected String number;
    protected String title;
    protected String output;
    protected boolean isAvailable;
    protected int borrowLength, overdue, dueDate, copy;
    protected double charge, overCharge;

    public Item(String[] info){
        this.number = info[1];
        this.copy= Integer.parseInt(info[2]);
        this.title = info[3];
        isAvailable = true;

    }
    //Returns if the Item is available
    public boolean checkAvailability(){
        return isAvailable;
    }
    //Returns the Item Number
    public String getNumber(){
        return number;
    }
    //Returns the copy
    public int getCopy( ){
        return copy;
    }
    //Returns the title
    public String getTitle( ){
        return title;
    }
    //Checks out the Item
    public void borrow(int date, Patron person){
        loanedTo = person;
        isAvailable = false;
        dueDate = date;
        dueDate= dueDate + getBorrowLength();
    }
    //Returns the item to the library
    public double returnItem(int date){
        overdue =0;
        isAvailable = true;

        overdue = date-dueDate;
        if(overdue>0){
            overCharge = overdue * getCharge();
        }else{
            overCharge = 0;
        }
        return overCharge;

    }
    //Returns the amount of time the item may be borrowed
    public int getBorrowLength( ){
        return borrowLength;
    }
    //Returns the charge per day
    public double getCharge( ){
        return charge;
    }
    //Returns the patron who currently holds the item
    public Patron getLoanedTo(){
        return loanedTo;
    }
    //Returns the date the item is due
    public int getDueDate(){
        return dueDate;
    }
    //Returns Item Information
    public String getInfo(){
        output = "";
        output="Item: "+getNumber();
        output += " Copy: " + getCopy();
        output += " Title: " + getTitle();
        if (checkAvailability()) {
            output += " available";
        }else{
            output += " on loan";
        }
        return output;
    }
    public int getRating(){
        return minAge;
    }
    //Override outputs string id
    @Override
    public String toString(){
        return number;
    }
    //Returns the fine
    public double getFine(){
        return overCharge;
    }
}
