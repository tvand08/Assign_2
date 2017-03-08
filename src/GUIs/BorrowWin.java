package GUIs;

import BasicIO.BasicForm;
import Lib_Program.*;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * Student: Trevor Vanderee
 * Id #: 5877022
 * Class: BorrowWin
 * Description: A Class to process Borrowing
 *  operations
 */

public class BorrowWin {
    private BasicForm form;
    private Patron person;
    private Item itemOut;
    private String patNum,itemNum, transaction;
    private int nextAction, age,date,copy;
    private boolean itemFound, patronFound;
    private char rating;
    private DataBase dataBase;
    public BorrowWin(DataBase dataBase){
        this.dataBase = dataBase;
        makeForm();
        nextAction = form.accept("Ok","Cancel");
        if(nextAction == 0){
            if(readForm()){
                findInfo();
            }else{
                form.writeString("console","Incorrect Date Format");
            }
        }

    }
    //Creates the form
    private void makeForm(){
        form = new BasicForm();
        form.addTextField("patronNum","Patron #");
        form.addTextField("itemNum","Item #");
        form.addTextField("copy","Copy");
        form.addTextField("date","Date [mm/dd/yy]: ");
        form.addTextField("due","Due Date");
        form.setEditable("due",false);
        form.addTextField("fine","Fine");
        form.setEditable("fine",false);
        form.addTextArea("console");
        form.setEditable("console",false);

    }
    //Reads information from the form
    private boolean readForm(){

        //The patrons id
        patNum = form.readString("patronNum");

        //the book id number
        itemNum = form.readString("itemNum");

        //the copy number
        copy = form.readInt("copy");

        date = form.readInt("date");
        //if date is negative
        if(date<0){
            form.writeString("Improper date (Integer)");
            form.accept();
            return false;
        }


        return true;
    }
    //Finds the designated specifications
    private void findInfo(){


        //Begin Item Search
        itemOut = dataBase.findItem(itemNum,copy);
        //If Item DNE
        if(itemOut == null){
            form.writeString("console","Item Not Found");
            form.accept();
            return;
        }
        //End Item Search
        person = dataBase.findPatron(patNum);
        if(person == null){
            form.writeString("console","Patron Not Found");
            form.accept();
            return;
        }
        if(itemOut.checkAvailability()) {
            checkoutItem();
        }
    }
    //Does Checkout Operation
    private void checkoutItem(){
        //If person has a fine deny
        if(person.getFine()>0){
            form.writeString("console","Outstanding Fine");
            form.writeDouble("fine",person.getFine(), NumberFormat.getCurrencyInstance());
            form.accept();
            return;
        }
        //Child Constraints
        if(person instanceof Child){
            //Children may not borrow games
            if(itemOut instanceof Game){
                form.writeString("console","Child Patrons may not borrow games.");
                form.accept();
                return;
            }
            //Children may have a maximum of 5 items out
            if(person.getNumItems()>=5){
                form.writeString("console","You have too many items out.");
                form.accept();
                return;
            }
        }

        //Age Constraints
        if(itemOut instanceof Media){
            rating = ((Media) itemOut).getRating();
            age = person.getAge();
            switch(rating){
                case 'F':
                    break;
                case 'P':
                    if(!(age>=14)){
                        form.writeString("console","You are too young");
                        form.accept();
                        return;
                    }
                    break;
                case 'R':
                    if(!(age>=18)){
                        form.writeString("console","You are too young");
                        form.accept();
                        return;
                    }
                    break;
                case 'X':
                    if(!(age>=21)){
                        form.writeString("console","You are too young");
                        form.accept();
                        return;
                    }
                    break;
            }
            //Proceed to checkout
            person.checkoutItem(itemOut);
            itemOut.borrow(date, person);
            //Information display
            form.writeString("console","Item Checked Out");
            form.writeInt("due",itemOut.getDueDate());
            transaction = "Item: " + itemNum + "   Loaned to " + person.getNumber() +
                    "  due: " + itemOut.getDueDate();
        }else {
            //Proceed to checkout for book
            person.checkoutItem(itemOut);
            itemOut.borrow(date, person);
            //Information display
            form.writeString("console","Item Checked Out");
            form.writeInt("due",itemOut.getDueDate());
            transaction = "Item: " + itemNum + "   Loaned to " + person.getNumber() +
                    "  due: " + itemOut.getDueDate();
        }
        form.accept();
        //Form is closed from Library
    }
    //Close the Form
    public void closeForm(){
        form.close();
    }
    //Returns the transaction for output file
    public String getTransaction(){
        return transaction;
    }
}
