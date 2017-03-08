package GUIs;

import BasicIO.BasicForm;
import Lib_Program.*;

import javax.xml.crypto.Data;
import java.util.LinkedList;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: LoanedWin
 * Operation with GUI to display
 * a Patrons Checkouts
 */

public class LoanedWin {
    private String patronNum,output;
    private BasicForm form;
    private boolean found;
    private DataBase dataBase;
    private Patron person;

    public LoanedWin(DataBase dataBase){
        this.dataBase = dataBase;
        makeForm();
        findPatron();
    }
    //Creates Form
    private void makeForm(){
        form = new BasicForm();
        form.addTextField("patronNum","ID #");
        form.addTextArea("output");
    }
    //Finds the designated patron
    private void findPatron(){
        form.accept();
        //read info
        patronNum = form.readString("patronNum");
        found= false;
        //Find Patron
        person = dataBase.findPatron(patronNum);
        //If Patron DNE
        if(person == null){
            form.writeString("output","Sorry, we can't find this patron");
            form.accept();
        }
        printList(person.getItemsOut());
    }
    //Displays all items in list
    private void printList(LinkedList<Item> out){
        //If there are no items
        if(out.isEmpty()){
            form.writeString("output","No items on loan");
            form.accept();
            return;
        }
        //For every item in list
        for(Item item: out){
            //Begin Output
            output="Item: ";
            output += item.getNumber();
            output += " Copy: " + item.getCopy();
            output += " Title: " + item.getTitle();
            //For availability
            if(item.checkAvailability()){
                output += " available";
            }else{
                output += " on loan";
            }

            if(item instanceof Book){
                output += " Author: "+((Book) item).getAuthor();
            }else if(item instanceof Video){
                output += " Rating: " + ((Video) item).getRating();
            }else if(item instanceof Game){
                output += " Rating: " + ((Game) item).getRating();
                output += " System: "+((Game) item).getSystem();
            }
            form.writeString("output",output);
            form.newLine("output");
            //End Output
        }
        form.accept();

    }
    public void closeForm(){
        form.close();
    }
}
