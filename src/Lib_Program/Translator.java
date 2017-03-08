package Lib_Program;

import BasicIO.ASCIIDataFile;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by Trevor Vanderee on 2017-03-01.
 */
public class Translator {

    private LinkedList<Patron> people;
    private LinkedList<Item> items;
    private BufferedReader catBR, patBR;
    private FileReader patFR, catFR;
    private File catFile, patFile;
    String catLine, patLine;
    String[] info;
    char cType, pType;


    public Translator( ) {
        //List of Patrons
        people = new LinkedList<>();
        patFile = new File(".//Patrons.txt");

        try {
            patFR = new FileReader(patFile);
            patBR = new BufferedReader(patFR);
            patLine = patBR.readLine();
            while(patLine!=null) {
                info = patLine.split("\t");
                if (info[0].charAt(0) == 'a') {

                    people.add(new Adult(info));
                } else if (info[0].charAt(0) == 'c') {

                    people.add(new Child(info));
                } else {
                    //To ensure proper patron type
                    System.out.println("an Error has occured: Improper patron type");
                    System.exit(2);
                }
                patLine = patBR.readLine();
            }
            //close readers
            patBR.close();
            patFR.close();

        } catch (FileNotFoundException noPatFile) {
            System.out.println("Failure: file not found");
            noPatFile.printStackTrace();
            System.exit(1);
        } catch (IOException ioE) {
            System.out.println("Failure: IOException");
            ioE.printStackTrace();
            System.exit(1);
        }
        //List of Items
        items = new LinkedList<>();
        catFile = new File(".//Catalog.txt");

        try {
            catFR = new FileReader(catFile);
            catBR = new BufferedReader(catFR);
            catLine = catBR.readLine();

            while(catLine!=null){
                cType = catLine.charAt(0);
                info = catLine.split("\t");

                switch (cType) {
                    //Book
                    case 'b':
                        items.add(new Book(info));
                        break;
                    //Video
                    case 'v':
                        items.add(new Video(info));
                        break;
                    //Game
                    case 'g':
                        items.add(new Game(info));
                        break;
                    //Default: error
                    default:
                        System.out.println("an Error has occured: Improper patron type");
                        System.exit(2);
                        break;
                }
                catLine = catBR.readLine();
            }
        }catch(FileNotFoundException noCatFile){
            System.out.println("Failure: file not found");
            noCatFile.printStackTrace();
            System.exit(1);
        }catch(IOException ioE){
            System.out.println("Failure: IOException");
            ioE.printStackTrace();
            System.exit(1);
        }

        DataBase db = new DataBase(people, items);
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        File outFile = null;
        try{
            outFile = new File(".//output.ser");
            fout = new FileOutputStream(outFile);
            System.out.println("Success: File Found");
        }catch(FileNotFoundException noFile){
            System.out.println("Failure: File Not Found");
            noFile.printStackTrace();
            System.exit(1);
        }
        try{
            oos = new ObjectOutputStream(fout);
            oos.writeObject(db);
            System.out.println("Success: Object Written");
        }catch(IOException ioE){
            System.out.println("Failure: IO error");
            ioE.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    public static void main(String[] args){Translator translator = new Translator();}
}
