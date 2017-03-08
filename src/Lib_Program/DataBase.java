package Lib_Program;

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
    public Item findItem(String itemNum, int copy){
        for(Item h: items){
            if(h.getNumber().equals(itemNum)&& h.getCopy()==copy){
                //check if available
                return h;

            }
        }
        return null;
    }
}
