package GUIs;

import BasicIO.BasicForm;


/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Window
 * The Main window for moving to other windows
 */
public class Window {
    private BasicForm form;
    private int nextAction;

    public Window(){
        form = new BasicForm();
    }
    public int getNextAction(){
        return nextAction;
    }
    public void closeForm(){
        form.close();
    }
    //Reopen Window
    public void popup(){
        form.show();
        nextAction = form.accept("Borrow", "Return", "Pay Fine", "Loaned", "Patron", "Items", "Quit");
        form.hide();

    }
}
