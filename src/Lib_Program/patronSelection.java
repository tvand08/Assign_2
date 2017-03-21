package Lib_Program;

import javax.swing.*;


/**
 * Created by Trevor Vanderee on 2017-03-16.
 */
public class patronSelection extends Thread {
    private JList patronList, itemList;
    private DefaultListModel itemListModel;
    private Patron patron, temp;
    private int[] currentState;
    private JScrollPane itemPane;
    private JTextField fine;
    public patronSelection(JList patronList, JList itemList, int[] currentState, JScrollPane itemPane,JTextField fine){
        //run til prog close
        this.setDaemon(true);
        this.patronList = patronList;
        this.currentState = currentState;
        this.itemPane = itemPane;
        this.fine =fine;
        this.itemList= itemList;
    }
    //Run thread for  updating selected patron items
    public void run() {
        while (true) {
            temp = (Patron)patronList.getSelectedValue();
            //check if selection has changed
            if (temp != patron) {
                patron = temp;
                //Check for state
                if(currentState[0]==1) {

                    if (patron != null) {
                        itemListModel = patron.getlistModel();
                        itemList.setModel(itemListModel);
                    } else {
                        itemPane.setViewportView(null);
                    }}
                }else if(currentState[0]==2){
                if(patron!=null) {
                    fine.setText("$" + patron.getFine());
                }else{
                    fine.setText("");
                }
            }

            try {
                sleep(250);
            } catch (InterruptedException e) {
            }
        }
    }
}
