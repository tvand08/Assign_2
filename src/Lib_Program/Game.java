package Lib_Program;

import BasicIO.ASCIIDataFile;

/**
 * Student: Trevor Vanderee
 * ID: 5877022
 * Class: Game
 * A Subclass of Media
 */
public class Game extends Media {

    protected char system;
    public Game(String[] info){
        super(info);
        this.system = info[5].charAt(0);
        super.charge = 2.00;
        super.borrowLength = 3;
    }

    //Returns the game system
    public char getSystem( ){
        return system;
    }
    public String getInfo(){
        super.getInfo();
        output += " System: " + getSystem();
        return output;
    }
}
