package GUIs;

import BasicIO.BasicForm;
import Lib_Program.*;

import javax.xml.crypto.Data;
import java.util.LinkedList;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: InventoryWin
 * Operation with GUI to display
 * Information on an Item
 */
public class InventoryWin{

    private BasicForm form;
    private String itemNum, output;
    private Item item;
    private boolean found;
    private int copy;
    private DataBase dataBase;
    public InventoryWin(DataBase dataBase){
        this.dataBase = dataBase;
        makeForm();
        if(form.accept("Ok","Cancel")==0) {
            readForm();
            findInfo();
            found = false;
        }
    }
    //Creates form
    private void makeForm(){
        form = new BasicForm();
        form.addTextField("itemNum","Item #:");
        form.addTextField("copy","Copy");
        form.addTextArea("output");

    }
    //Read information from form
    private void readForm(){
        itemNum = form.readString("itemNum");
        copy = form.readInt("copy");

    }
    //Item Display Operation
    private void findInfo() {
        //Find Item

        item = dataBase.findItem(itemNum, copy);
        if (item == null) {
            form.writeString("output", "Item does not exist");
            form.accept();
            return;
        } else {
            form.writeString("output", item.getInfo());
            //End Output
            form.accept();
        }
    }

    public void closeForm(){
        form.close();
    }
}
