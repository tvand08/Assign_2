package Lib_Program;
import java.io.*;
import java.util.LinkedList;
import BasicIO.*;
import GUIs.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Student: Trevor Vanderee
 * Number: 5877022
 * Assignment 1
 * COSC 2P05: programming languages
 * Date: 2017-01-25
 *
 * Class: Library
 * This is the main class from
 *  which the library is
 *  initialized
 */
public class Library implements Serializable{
    //Input Files
    private File file;
    private FileInputStream inputStream;
    private ObjectInputStream objectInputStream;
    private ASCIIOutputFile out;
    private DataBase dataBase;
    public Window window;
    private String transaction;
    private int nextAction;
    private boolean again;

    public Library( ){

        //Initialize System
        libraryInit();
        //Create Window
        window = new Window();
        //Run System
        runProgram();
        window.closeForm();
        out.close();
        //Exit
        System.exit(0);
    }

    //Initializes all library assets
    private void libraryInit() {
        file = new File(".//output.ser");
        inputStream = null;
        objectInputStream = null;
        try{
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            dataBase = (DataBase)objectInputStream.readObject();
            inputStream.close();
            objectInputStream.close();
        }catch (FileNotFoundException noFile){
            System.out.println("Failure: File Not Found");
            chooseFile();
        }catch(IOException ioE){
            System.out.println("Failure: Object not Read");
            ioE.printStackTrace();
            System.exit(1);
        }catch(ClassNotFoundException noClass) {
            System.out.println("Failure: No Such Class");
            noClass.printStackTrace();
            System.exit(1);
        }
        out = new ASCIIOutputFile();
    }

    //to begin Library program
    private void runProgram(){
        //Set boolean for loop
        again = true;
        /*
        Loop allows users to return to main
         screen and call another operation
         */
        while(again) {
            window.popup();
            nextAction = window.getNextAction();
            switch (nextAction) {
                //For Borrow Operation
                case 0: BorrowWin borrow = new BorrowWin(dataBase);
                        transaction = borrow.getTransaction();
                        if(transaction!=null) {
                            out.writeString(transaction);
                            out.newLine();
                        }
                        borrow.closeForm();
                    break;
                //For Return Operation
                case 1: ReturnWin itemReturn = new ReturnWin(dataBase);
                    transaction = itemReturn.getTransaction();
                    if(transaction!=null) {
                        out.writeString(transaction);
                        out.newLine();

                    }
                        itemReturn.closeForm();
                        break;
                //For Pay operation
                case 2: PayWin payWin = new PayWin(dataBase);
                    transaction = payWin.getTransaction();
                    if(transaction!=null) {
                        out.writeString(transaction);
                        out.newLine();

                    }
                        payWin.closeForm();
                        break;
                //To Check what books have been loaned to a patron
                case 3: LoanedWin loanedWin = new LoanedWin(dataBase);
                        loanedWin.closeForm();
                        break;
                //To Check Information of Patron
                case 4: PatronWin patronWin = new PatronWin(dataBase);
                        patronWin.closeForm();

                    break;
                //To check item information
                case 5: InventoryWin inventoryWin = new InventoryWin(dataBase);
                        inventoryWin.closeForm();
                    break;
                //To exit
                case 6: again = false;
                    break;
                //default
                default: again = false;
                    break;
            }
        }
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
    private void chooseFile( ) {// method to open up a file dialog and return the
        // file. returns null if no file is chosen
        File f = null;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open File");
        int rVal = fc.showOpenDialog(null);// gets the button pressed
        if (rVal == JFileChooser.APPROVE_OPTION) {// if the button pressed is
            // the open button
            f = fc.getSelectedFile();// set the file to the file chosen
        }
        if(f == null){
            System.out.println("Error No File found");
            System.exit(1);
        }else{
            try{
                inputStream = new FileInputStream(f);
                objectInputStream = new ObjectInputStream(inputStream);
                dataBase = (DataBase)objectInputStream.readObject();
                inputStream.close();
                objectInputStream.close();
            }catch (FileNotFoundException noFile){
                System.out.println("Failure: File Not Found");
                System.exit(1);
            }catch(IOException ioE){
                System.out.println("Failure: Object not Read");
                ioE.printStackTrace();
                System.exit(1);
            }catch(ClassNotFoundException noClass) {
                System.out.println("Failure: No Such Class");
                noClass.printStackTrace();
                System.exit(1);
            }
        }
    }
    public static void main(String[] args){Library library = new Library();}
}
