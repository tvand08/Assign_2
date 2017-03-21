package Lib_Program;

import javax.swing.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * Created by Trevor Vanderee on 2017-03-01.
 */
public class DataBase implements Serializable {

    static final long serialVersionUID = 42L;

    private LinkedList<Patron> people;
    private LinkedList<Item> items;

    public DataBase (LinkedList<Patron> p ,LinkedList<Item> i){
        this.people = p;
        this.items = i;
    }

    /**
     * find the Patron with the given ID
     * @param ID: The desired ID
     * @return Patron with num ID
     */
    public Patron findPatron(String ID){
       for(Patron p : people){
            if(p.getNumber().equals(ID)){
                //Begin Output
                return p;
                //End Output
            }
        }
        return null;
    }

    /**
     * Find the Item with the given itemNum and copyNum
     * @param itemNum: The designated ID
     * @param copy: The copy number
     * @return: Item found
     */
    public Item findItem(String itemNum, int copy){
        for(Item h: items){
            if(h.getNumber().equals(itemNum)&& h.getCopy()==copy){
                //check if available
                return h;

            }
        }
        return null;
    }

    /**
     * get the list of items
     * @return: list of items
     */
    public DefaultListModel getItemListModel( ){
        DefaultListModel listModel = new DefaultListModel();
        int i =0;
        for (Item item: items) {
            if(item.isAvailable) {
                listModel.add(i, item);
                i++;
            }

        }
        return listModel;
    }

    /**
     * get the list of patrons
     * @return: List or patrons
     */
    public DefaultListModel getPatronListModel( ){
        DefaultListModel listModel = new DefaultListModel();
        int i =0;
        for (Patron patron: people) {
            listModel.add(i,patron);
            i++;
        }
        return listModel;
    }
}
