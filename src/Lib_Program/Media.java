package Lib_Program;

import BasicIO.ASCIIDataFile;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Media
 * This class is used for
 * keeping the rating in one class
 */
public class Media extends Item {
    protected char rating;

    public Media(String[] info){
        super(info);
        this.rating = info[4].charAt(0);
        //Sanitize rating input
        switch(rating){
            case 'F': break;
            case 'P': break;
            case 'R': break;
            case 'X': break;
            default:
                System.out.println("Error: Improper rating");
                System.exit(1);
        }

    }

    public String getInfo(){
        super.getInfo();
        output += " Rating: " + getRating();
        return output;
    }
    public char getRating(){
        return rating;
    }
}
