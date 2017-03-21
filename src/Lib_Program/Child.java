package Lib_Program;

import BasicIO.ASCIIDataFile;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Child
 * A Subclass of Patron
 * Solely to seperate Children of Adults
 */
public class Child extends Patron {
    public Child(String[] info){
        super(info);
    }
    public boolean checkoutItem(Item checkout){
        if(getNumItems()>=5){
            errorMessage = "Too many items out";
            return false;
        }else{
            if(checkout instanceof Game) {
                return super.checkoutItem(checkout);
            }else{
                errorMessage ="cannot checkout on child card";
                return false;
            }
        }

    }
}
