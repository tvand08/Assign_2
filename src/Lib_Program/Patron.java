package Lib_Program;

import BasicIO.ASCIIDataFile;

import javax.swing.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.LinkedList;


/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Patron
 * Holds all the information needed
 * for all patrons
 */
public class Patron implements Serializable {
    static final long serialVersionUID = 42L;
    protected String name;
    protected String number;
    protected int age;
    private double fine;
    private LinkedList<Item> items;
    protected String errorMessage, transaction;

    public Patron(String[] info){

        this.number = info[1];
        this.name =  info[2];
        this.age = Integer.parseInt(info[3]);

        fine = 0.00;
        items = new LinkedList<>();
    }
    //Adds an Item to the patrons checkout
    public boolean checkoutItem(Item checkout){
        if(getAge()>= checkout.minAge){
            if(getFine()>0.0) {
                errorMessage = "Outstanding fine";
                return false;
            }else {
                items.add(checkout);
                transaction = getNumber() + " checked out " + checkout.getNumber() + ", due "+ checkout.getDueDate();
                return true;
            }
        }else{
            errorMessage = "You are too Young";
            return false;
        }
    }
    //Returns the number of items currently checked out by the patron
    public int getNumItems(){
        return items.size();
    }
    //Returns the items the patron possesses
    public LinkedList<Item> getItemsOut(){
        return items;
    }
    //Returns the patrons id number
    public String getNumber( ){
        return number;
    }
    //Returns the Patrons name
    public String getName( ){
        return name;
    }
    //Returns the Patrons age
    public int getAge( ){
        return age;
    }
    //Returns the Patrons outstanding fine
    public double getFine(){
        return fine;
    }
    //Updates the Patrons fine
    public double updateFine(double add){
        fine+=add;
        if(add<0){
            transaction = getNumber() + "paid: $" + add*-1;
        }
        return fine;
    }
    //Remove an Item from the Patrons possession
    public void returnItem(Item i){
        items.remove(i);
        transaction = getNumber() + " returned " + i.getNumber() + ", Fine: $" + i.getCharge();
    }
    //Return Patron Information
    public String getInfo( ){
        String output;
        output = "ID: " + getNumber();
        output+= "  Name:"+ getName();
        output+= "  Age:"+ getAge();
        output += "  Items Out: " + getNumItems();
        output += "  Balance: " + getFine();
        return output;
    }

    public DefaultListModel getlistModel( ) {
        DefaultListModel listModel = new DefaultListModel();
        int i = 0;
        for (Item item : items) {
            listModel.add(i, item);
            i++;
        }
        return listModel;
    }
        @Override
    public String toString(){
        return number;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
    public String getTransaction(){
        return transaction;
    }
}
