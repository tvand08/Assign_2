package GUIs;

import BasicIO.BasicForm;
import Lib_Program.DataBase;
import Lib_Program.Patron;
import java.text.NumberFormat;
import java.util.LinkedList;


/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: PayWIn
 * Operation with GUI to pay
 * an outstanding fine
 */
public class PayWin {
    private BasicForm form;
    private LinkedList<Patron> people;
    private String patNum, transaction;
    private DataBase dataBase;
    private Patron person;
    private double paid,bal;
    private int nextAction;

    public PayWin(DataBase dataBase){
        this.dataBase = dataBase;
        makeForm();
        nextAction = form.accept("Ok","Cancel");
        if(nextAction == 0){
            readForm();
        }
    }

    private void makeForm() {
        form = new BasicForm();
        form.addTextField("patNum", "Patron #");
        form.addTextField("pay","Amount Paid");
        form.addTextArea("bal","Current Balance");
        form.setEditable("bal",false);
    }

    //Pay Fine Operation
    private void readForm(){
        //Read information
        patNum = form.readString("patNum");
        paid = form.readDouble("pay");
        //Find Patron
        person = dataBase.findPatron(patNum);
        if(person==null){
            form.writeString("console","Patron Not Found");
            form.accept();
            return;
        }
        //Fine pay transaction
        bal = person.updateFine(-1*paid);
        form.writeString("bal","Current Balance");
        form.writeDouble("bal",bal, NumberFormat.getCurrencyInstance());
        //transaction for output file
        transaction = "Patron "+patNum + "  paid $"+ paid;
        form.accept();
    }
    //Returns the output transaction
    public String getTransaction(){
        return transaction;
    }
    public void closeForm(){
        form.close();
    }
}
