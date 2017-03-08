package Lib_Program;

import BasicIO.ASCIIDataFile;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Adult
 * A Subclass of Item
 * Solely to seperate Adults from children
 */
public class Book extends Item {
    protected String author;


    public Book(String[] info){
        super(info);
        super.charge = 0.25;
        super.borrowLength = 21;
        this.author = info[4];
    }

    //Returns the Charge per day
    public double getCharge(){
        return charge;
    }
    //Returns the length of time borrowable
    public int getBorrowLength( ){
        return borrowLength;
    }
    //Returns the author
    public String getAuthor(){
        return author;
    }
    public String getInfo(){
        output = super.getInfo();
        output += " Author: " + getAuthor();
        return output;
    }
}
