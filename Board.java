/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author matthew
 */
public class Board implements IBoard{

 
    
   private ArrayList<String> gameData;
   private ArrayList<String> dictionaryData;
   private ArrayList<Die> diceData;    
   private ArrayList<String> diceGame;
   
   // shakes all of the dice so each displays a random letter
   @Override
    public ArrayList shakeDice() 
    {
        // freqArray will hold whether or not a die has been seen before
        int[] freqArray = new int[NUMBER_OF_DICE];
        int counter = 0, index;
        diceGame = new ArrayList<>();
        
        Random random = new Random();
       // populates freqArray with a default of 0
        for(int i = 0; i < NUMBER_OF_DICE; i++)
        {
            freqArray[i] = 0;
        }
        
        // randomly selects a die and makes sure each is visited only once
        while(counter < NUMBER_OF_DICE)
        {
            index = random.nextInt(NUMBER_OF_DICE);
            if(freqArray[index] != 0)
                ;
           // adds the letter returned from rollDie to the array list
           // and updates the counter and frequency array
            else
            {
              diceGame.add(diceData.get(index).rollDie());
              counter++;
              freqArray[index]++;
            }
        }
        
        return diceGame;
    }
    // formats proper output for the board showing the result of all dice being
    // shaken
    public void displayBoard()
    {
        System.out.println("Boggle board");
        for(int i = 0; i < NUMBER_OF_DICE; i++)
        {
            if( (i+1) % 4 == 0)
                System.out.println(diceGame.get(i));
            else
                System.out.print(diceGame.get(i) + " ");
         }
    }

    // Adds letters to each side of every die
    @Override
    public void populateDice() 
    {
        Die die;
        int diceCounter = 0;
        
        // creates 16 new dice
        for(int i = 0; i < NUMBER_OF_DICE; i++)
        {
            die = new Die();
            
            // adds letters to each side of the die
            for(int j = 0; j < Die.NUMBER_OF_SIDES; j++)
            {
               die.addLetter(getGameData().get(diceCounter));
               diceCounter++;
               
            
            }
            // formats output of each die
            System.out.print("Die " + i + ": ");
            die.displayLetters();
            System.out.println();
            diceData.add(die);
        }
        
        
    }
   // Board constructor 
   public Board(ArrayList<String> boggle, ArrayList<String> dictionary)
   {
       gameData = boggle;
       dictionaryData = dictionary;
       diceData = new ArrayList();
       diceGame = new ArrayList<>();
       
   }

    /**
     * @return the gameData
     */
    public ArrayList<String> getGameData() {
        return gameData;
    }

    /**
     * @param gameData the gameData to set
     */
    public void setGameData(ArrayList<String> gameData) {
        this.gameData = gameData;
    }

    /**
     * @return the dictionaryData
     */
    public ArrayList<String> getDictionaryData() {
        return dictionaryData;
    }

    /**
     * @param dictionaryData the dictionaryData to set
     */
    public void setDictionaryData(ArrayList<String> dictionaryData) {
        this.dictionaryData = dictionaryData;
    }

    /**
     * @return the DiceData
     */
    public ArrayList<Die> getdiceData() {
        return diceData;
    }

    /**
     * @param DiceData the DiceData to set
     */
    public void setdiceData(ArrayList<Die> DiceData) {
        this.diceData = DiceData;
    }

       /**
     * @return the diceGame
     */
    public ArrayList<String> getDiceGame() {
        return diceGame;
    }
    
}
