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
public class Die implements IDie
{   
    // creates a new ArrayList for the side of a die
    private ArrayList<String> dieSide = new ArrayList<String>();
    
    // returns the letter from a random side of a die
    @Override
    public String rollDie() 
    {
       Random rand = new Random();
       
       return dieSide.get(rand.nextInt(6)); 
        
    }
    
    // adds a letter to the side of the die
    @Override
    public void addLetter(String letter) {
        dieSide.add(letter);
    }
    
    // prints the letters on that side of the die
    @Override
    public void displayLetters() {
       for(String side: dieSide)
       {
           System.out.print(side + " ");
       }
    }
    
 
    
}
