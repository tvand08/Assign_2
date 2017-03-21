package Lib_Program;

import BasicIO.ASCIIDataFile;
import BasicIO.ASCIIOutputFile;
import Lib_Program.DataBase;
import Lib_Program.Item;
import Lib_Program.Patron;
import Lib_Program.patronSelection;
import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.control.SelectionModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Trevor Vanderee on 2017-03-14.
 */
public class Window extends JFrame implements ActionListener{
    private static final int WIDTH = 600;
    private static final int HEIGHT = 350;
    //Status constants
    private static final int BORROW = 0;
    private static final int RETURN = 1;
    private static final int PAY = 2;
    /*
    Current State is a single element
    array. The reason for the array is
    to allow an integer to be passed by
    reference rather than value
     */
    private int[] currentState;
    //Objects
    private DataBase dataBase;
    private ASCIIOutputFile out;
    //JFrame items
    private JList patronList, itemList;
    private JLabel title,patTitle, itemTitle, dateTitle, dueTitle, fineTitle, paidTitle;
    private JScrollPane itemPane, patronPane;
    private JTextField date, due, fine, paid;
    private JButton okButton, cancelButton;
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem borrowTab, returnTab, payTab, quitTab;
    private DefaultListModel patronListModel, itemListModel;

    public Window(DataBase dataBase) {
        this.dataBase = dataBase;
        out = new ASCIIOutputFile();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                createGui();
            }
        });
    }

    private void createGui() {
        currentState = new int[1];
        currentState[0] = 0;
        //Get Lists from DataBase
        patronListModel = dataBase.getPatronListModel();
        itemListModel = dataBase.getItemListModel();
        //Add Lists to scroll panes
        itemPane = new JScrollPane();
        itemList = new JList(itemListModel);
        itemPane.setViewportView(itemList);
        patronList = new JList(patronListModel);
        patronPane = new JScrollPane();
        patronPane.setViewportView(patronList);

        //Titles for Lists
        patTitle = new JLabel("Patron");
        itemTitle = new JLabel("Item");

        //Create Menu items
        borrowTab = new JMenuItem("Borrow");
        returnTab = new JMenuItem("Return");
        payTab = new JMenuItem("Pay");
        quitTab = new JMenuItem("Quit");

        //Create Menu Bar
        bar = new JMenuBar();
        menu = new JMenu("File");
        menu.add(borrowTab);
        borrowTab.addActionListener(this);
        menu.add(returnTab);
        returnTab.addActionListener(this);
        menu.add(payTab);
        payTab.addActionListener(this);
        menu.addSeparator();
        menu.add(quitTab);
        quitTab.addActionListener(this);
        bar.add(menu);

        //Header Title
        title = new JLabel("Please Select an Operation");

        //Create JTextFields
        date = new JTextField();
        due = new JTextField();
        due.setEditable(false);
        fine = new JTextField();
        fine.setEditable(false);
        paid = new JTextField();
        new patronSelection(patronList, itemList, currentState, itemPane, fine).start();

        //Create Labels for JTextFields
        dateTitle = new JLabel("Date:");
        dueTitle = new JLabel("Due:");
        fineTitle = new JLabel("Fine:");
        paidTitle = new JLabel("Paid:");

        //Create Buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        //Set the window's title.
        setTitle("Sample Title: Area of a Rectangle");
        Container pane = getContentPane();

        //Set the Absolute layout.
        pane.setLayout(null);
        //Add Items to pane
        pane.add(bar); /* Menu Bar*/
        pane.add(title);/*Header Title*/
        pane.add(itemTitle); /*Item List label*/
        pane.add(itemPane); /*Item List*/
        pane.add(patTitle); /*Patron list Label*/
        pane.add(patronPane); /*Patron List*/
        pane.add(date); /*Current Date text Field*/
        pane.add(dateTitle);/*Date Label*/
        pane.add(due);  /*Due date text Field*/
        pane.add(dueTitle);/*Due Label*/
        pane.add(fine); /*Fine Text Field*/
        pane.add(fineTitle);/*Fine Label*/
        pane.add(paid); /*Paid text Field*/
        pane.add(paidTitle);/*Paid Label*/
        pane.add(okButton); /*Ok button*/
        pane.add(cancelButton); /*Cancel Button*/
        //Add listeners to buttons
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        //Get Pane corner coordinates
        Insets insets = pane.getInsets();
        int leftEdge = insets.left;
        int rightEdge = leftEdge + WIDTH;
        int topEdge = insets.top;
        int bottomEdge = topEdge + HEIGHT;
        int bodyStart = insets.top + 70;

        //Set Menu Bar pos
        bar.setBounds(leftEdge, insets.top, WIDTH, 20);

        //Set title Pos
        title.setBounds(leftEdge + WIDTH / 2, topEdge + 20, 50, 20);

        //Set JList pos
        patTitle.setBounds(leftEdge, bodyStart + 75, 40, 10);
        patronPane.setBounds(leftEdge + 43, bodyStart, 90, 150);
        itemTitle.setBounds(leftEdge + 150, bodyStart + 75, 25, 10);
        itemPane.setBounds(leftEdge + 180, bodyStart, 85, 150);

        //Set Text Fields pos
        date.setBounds(rightEdge - 180, bodyStart, 80, 30);
        due.setBounds(rightEdge - 180, bodyStart + 40, 80, 30);
        fine.setBounds(rightEdge - 180, bodyStart + 80, 80, 30);
        paid.setBounds(rightEdge - 180, bodyStart + 120, 80, 30);


        //Set TextField Labels pos
        dateTitle.setBounds(rightEdge - 240, bodyStart, 40, 30);
        dueTitle.setBounds(rightEdge - 240, bodyStart + 40, 40, 30);
        fineTitle.setBounds(rightEdge - 240, bodyStart + 80, 40, 30);
        paidTitle.setBounds(rightEdge - 240, bodyStart + 120, 40, 30);



        //Set Button Pos
        okButton.setBounds(leftEdge + 250, bottomEdge - 100, 60, 30);
        cancelButton.setBounds(leftEdge + 320, bottomEdge - 100, 90, 30);
        setTitle("Library");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Set Starting state to borrow
        borrowWin();
    }

    /**
     *Action Event Listener
     * @param e: The Action event
     */
    public void actionPerformed(ActionEvent e){
        //get Action and proceed to next action
        String action = e.getActionCommand();
        switch (action){
            case "Borrow":
                borrowWin();
                break;
            case "Return":
                returnWin();
                break;
            case "Pay":
                payWin();
                break;
            case "OK":
                completeTransaction();
                break;
            case "Cancel":
                due.setText("");
                date.setText("");
                break;
            case "Quit":
                exitProgram();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    /**
     * Switch to Borrow Elements
     * Change Status to borrow
     */
    private void borrowWin( ){
        //Make elements visible or not visible
        title.setText("Borrow");
        itemPane.setVisible(true);
        date.setVisible(true);
        dateTitle.setVisible(true);
        paid.setVisible(false);
        paidTitle.setVisible(false);
        due.setVisible(true);
        dueTitle.setVisible(true);
        fine.setVisible(false);
        fineTitle.setVisible(false);
        //Change current state to borrow
        currentState[0] = BORROW;
        itemList.setModel(dataBase.getItemListModel());

    }

    /**
     *Switch to Return Elements
     * Change Status to return
     */
    private void returnWin( ){
        title.setText("Return");
        itemPane.setVisible(true);
        paid.setVisible(false);
        paid.setText("");
        paidTitle.setVisible(false);
        date.setVisible(true);
        date.setText("");
        dateTitle.setVisible(true);
        due.setVisible(true);
        due.setText("");
        dueTitle.setVisible(true);
        fine.setVisible(true);
        fine.setText("");
        fineTitle.setVisible(true);
        currentState[0] = RETURN;
        //change
        itemList.clearSelection();
        patronList.setSelectedIndex(0);
        itemList.setModel(((Patron)patronList.getSelectedValue()).getlistModel());

    }

    /**
     * Switch to Pay elements
     * change status to pay
     */
    private void payWin( ){
        title.setText("Pay Fine");
        date.setVisible(false);
        dateTitle.setVisible(false);
        paid.setVisible(true);
        paidTitle.setVisible(true);
        due.setVisible(false);
        dueTitle.setVisible(false);
        fine.setVisible(true);
        fineTitle.setVisible(true);
        currentState[0] = PAY;
        itemPane.setVisible(false);
        itemTitle.setVisible(false);
    }

    /**
     * Exucutes a transaction
     * Borrow
     * Return
     * Pay
     */
    private void completeTransaction(){
        switch(currentState[0]){
            case BORROW:
                borrow();
                break;
            case RETURN:
                returnItem();
                break;
            case PAY:
                payFine();
                break;
            default:
                break;
        }
    }

    /**
     * Execute Borrow
     * Checkout Item
     */
    private void borrow( ){
        //Get Selected Values from lists
        Item item = (Item)itemList.getSelectedValue();
        Patron patron = (Patron)patronList.getSelectedValue();
        //Checking for Errors
        if(item==null){
            JOptionPane.showMessageDialog(this,"No item selected");
            return;
        }
        if(patron==null){
            JOptionPane.showMessageDialog(this,"No patron Selected");
            return;
        }
        int dateNum = -1;
        try {
            dateNum = Integer.parseInt(date.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Invalid date");
            return;
        }
        //Checkout Item
        if(patron.checkoutItem(item)) {
            item.borrow(dateNum, patron);
            JOptionPane.showMessageDialog(this,"Item Checked out");
        }else{
            JOptionPane.showMessageDialog(this, patron.getErrorMessage());
        }

        out.writeString(patron.getTransaction());
        out.newLine();
        due.setText(""+item.getDueDate());
        itemList.setModel(dataBase.getItemListModel());
    }

    /**
     * Execute Return
     * Return item to Library
     */
    private void returnItem(){
        //get Item Value
        Item item = (Item)itemList.getSelectedValue();
        //Check For errors
        if(item==null){
            JOptionPane.showMessageDialog(this,"No item selected");
            return;
        }
        Patron patron = (Patron)patronList.getSelectedValue();
        if(patron==null){
            JOptionPane.showMessageDialog(this,"No patron Selected");
            return;
        }
        int dateNum = -1;
        try {
            dateNum = Integer.parseInt(date.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Invalid Date");
            return;
        }

        //Execute Checkout
        double fee = item.returnItem(dateNum);
        patron.updateFine(fee);
        patron.returnItem(item);
        fine.setText("$" + fee);
        due.setText(""+item.getDueDate());
        JOptionPane.showMessageDialog(this,"Item Returned");
        //write out transaction
        out.writeString(patron.getTransaction());
        out.newLine();
        itemList.setModel(patron.getlistModel());
        itemPane.setViewportView(itemList);
    }

    /**
     * Execute Payment
     */
    private void payFine(){
        Patron p = (Patron)patronList.getSelectedValue();
        if(p==null){
            JOptionPane.showMessageDialog(this,"No patron Selected");
            return;
        }
        double pay = 0;
        try {
             pay = Double.parseDouble(paid.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Invalid Payment");
            return;
        }
        double nFine = p.updateFine(-1*pay);
        fine.setText("$"+nFine);
        JOptionPane.showMessageDialog(this,"Payment received");
        paid.setText("");
        out.writeString(p.getTransaction());
        out.newLine();
    }

    /**
     * Exit Sequence
     */
    private void exitProgram(){
        closeLog();
        File file = new File(".//output.ser");
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try{
            fout = new FileOutputStream(file);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(dataBase);
            System.out.println("Success: Object Written");
        }catch(IOException ioE){
            System.out.println("Failure: IO error");
            ioE.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    /**
     * Close output file
     */
    public void closeLog(){
        out.writeString("Saving Database, Closing.");
        out.newLine();
        out.close();
    }
}

