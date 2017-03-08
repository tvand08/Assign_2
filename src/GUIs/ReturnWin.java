package GUIs;

import BasicIO.BasicForm;
import Lib_Program.DataBase;
import Lib_Program.Item;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: ReturnWin
 * Operation with GUI to return
 * an item to the library
 */
public class ReturnWin {
    private BasicForm form;
    private DataBase dataBase;
    private Item item;
    private String itemNum, transaction;
    private boolean found;
    private double charge;
    private int nextAction, copy,date;


    public ReturnWin(DataBase dataBase){
        this.dataBase = dataBase;
        makeForm();
        nextAction = form.accept("Ok","Cancel");

        if(nextAction== 0){
            readForm();
            makeReturn();

        }
    }
    //Initializes form
    private void makeForm(){
        form = new BasicForm();

        //Input
        form.addTextField("itemNum","Item #");
        form.addTextField("copy","Copy");
        form.addTextField("date","Date");

        //Output
        form.addTextField("due","Due");
        form.setEditable("due",false);
        form.addTextField("fine","Fine");
        form.setEditable("fine",false);
    }
    //Reads information from the form
    private void readForm(){
        itemNum = form.readString("itemNum");
        //the copy number
        copy = form.readInt("copy");
        //date creation
        date = form.readInt("date");

    }
    //Return Operation
    private void makeReturn(){
        found = false;
        item = dataBase.findItem(itemNum, copy);
        if(item==null){
            System.out.println("Item Not Found");
            form.accept();
            return;
        }else  if(item.checkAvailability()){
            form.writeString("Item is not checked out");
            form.accept();
            return;
        }
        form.writeInt("due",item.getDueDate());
        //Gets the overdue Charges
        charge = item.returnItem(date);
        item.getLoanedTo().updateFine(charge);
        item.getLoanedTo().returnItem(item);
        form.writeDouble("fine",charge, NumberFormat.getCurrencyInstance());
        //For output to data file
        transaction = "Item: "+ item.getNumber() + "  returned on "+ date;
        //if there was a fine: add to transaction
        if(charge>0){
            transaction += "      Fine charged: $" + charge;
        }
        form.accept();
    }
    //Returns the transaction for the output file
    public String getTransaction(){
        return transaction;
    }
    public void closeForm(){
        form.close();
    }
}
