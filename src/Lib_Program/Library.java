package Lib_Program;
import java.io.*;

import BasicIO.*;


import javax.swing.*;


/**
 * Student: Trevor Vanderee
 * Number: 5877022
 * Assignment 2
 * COSC 2P05: programming languages
 * Date: 2017-03-17
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
    private DataBase dataBase;
    public Window window;

    public Library( ){

        //Initialize System
        libraryInit();
        //Create Window
        window = new Window(dataBase);
        //Listener for JFrame Close
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(window,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        exitProgram();
                    System.exit(0);
                }
            }
        });
    }

    //Initializes all library assets
    private void libraryInit() {
        file = new File(".//output.ser");
        inputStream = null;
        objectInputStream = null;
        //Try To read files
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


    }

    //to Exit Library program
    private void exitProgram(){
        window.closeLog();
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

    //Choose File
    private void chooseFile( ) {// method to open up a file dialog and return the file
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
