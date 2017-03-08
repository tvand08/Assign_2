package Lib_Program;

import BasicIO.ASCIIDataFile;

/**
 *Student: Trevor Vanderee
 * ID: 5877022
 * Class: Video
 * A Subclass of Media
 *
 */
public class Video extends  Media {

    //Constructor
    public Video(String[] info){
        super(info);
        super.borrowLength = 2;
        super.charge = 0.50;
    }

}
