/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boggle;

import core.Board;
import inputOutput.ReadDataFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import userInterface.BoggleUi;

/**
 *
 * @author matthew
 */
public class Boggle {

    /**
     * @param args the command line arguments
     */
    
    // stores game file information
    private static ArrayList<String> gameData = new ArrayList();
    
    // stores dictionary file information
    private static ArrayList<String> dictionaryFile = new ArrayList();
    
    // saves name of BoggleData file
    private static String gameFileName = new String("../data/BoggleData.txt");
    
    // saves name of dictionary file 
    private static String dictionaryName = new String("../data/Dictionary.txt");
    
    
    public static void main(String[] args) 
    {
       
    //changes look and feel from default to Nimbus  
    try
    {
        
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
       
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }




        // Prints out game welcome message to console
        System.out.println("Welcome to Boggle!");
        // Displays welcome message in a GUI
        JOptionPane.showMessageDialog(null, "Let's Play Boggle!");
        
        // creates new ReadDataFile object that reads in the BoggleData file
        ReadDataFile data = new ReadDataFile(gameFileName);
        data.populateData();
        
        // creates an object that reads in the Dictionary data file
        ReadDataFile dictionary = new ReadDataFile(dictionaryName);
        dictionary.populateData();
        
        // creates a new board object using the data from the boggle and
        // dictionary
        Board board = new Board(data.getData(), dictionary.getData());
        board.populateDice();
        
        gameData = board.shakeDice();
        // prints number of entries
        System.out.println("There are " + dictionary.getData().size() + " entries in the dictionary");
        // outputs the board to the console
        board.displayBoard();
        
        // creates a new UI object to display the game
        BoggleUi myBoardUI = new BoggleUi(board);
    }
    
}
