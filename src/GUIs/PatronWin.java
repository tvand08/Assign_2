package GUIs;

import BasicIO.BasicForm;
import Lib_Program.Patron;
import java.text.NumberFormat;
import java.util.LinkedList;
import Lib_Program.DataBase;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: PatronWin
 * Operation with GUI to display
 * information about a Patron
 */
public class PatronWin{

    private BasicForm form;
    private int nextAction;
    private boolean found;
    private String patNum,output;
    private Patron person;

    public PatronWin(DataBase dataBase){
        makeForm();
        nextAction = form.accept("OK","Cancel");
        if(nextAction==0){
            printInfo(dataBase);
        }

    }
    //Creates the form
    private void makeForm(){
        form = new BasicForm();
        form.addTextField("patNum","Id #");
        form.addTextArea("output");
    }
    //Display Patron Information
    private void printInfo(DataBase dataBase){
        //Read Info
        patNum = form.readString("patNum");
        found = false;
        //Find the Patron
        person = dataBase.findPatron(patNum);
        //If Patron DNE
        if(person == null){
            form.writeString("output","Patron Not Found");
        }else{
            output = person.getInfo();
            form.writeString("output",output);
            form.writeDouble("output",person.getFine(), NumberFormat.getCurrencyInstance());
        }
        form.accept();
    }
    public void closeForm(){
            form.close();
    }

}
