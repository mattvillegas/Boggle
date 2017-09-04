/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

/**
 *
 * @author matthew
 */

import core.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class BoggleUi 
{
  
    private Board myBoard;
    private JFrame frame;
    private JMenu menu;
    private JMenuItem newgame;
    private JMenuItem exit;
    private JMenuBar menuBar;
    private JButton[][] buttons;
    private JPanel words;
    private JLabel curWord;
    private JButton submit;
    private JPanel bogglePanel;
    private JPanel gameInfo;
    private JTextPane enterWord;
    private JScrollPane scroll;
    private JLabel time;
    private JButton shake;
    private JLabel score;
    private Timer timer; 
    private int minutes = 3; 
    private int seconds = 0; 
    private resetGame reset; 
    private exitGame exitListener;
    private timerListener startTimer;
    private int labelScore = 0;
    private ArrayList<JButton> usedButtons = new ArrayList<>();
    private StyledDocument striketext;
    private StyleContext context = new StyleContext();;
    private Style nonStrike, strikeStyle;
    private ArrayList<String> computerWords = new ArrayList<>();
    private ArrayList<String> submitted = new ArrayList<>();
    private  int scoreArray[] = {0,0,1,1,3,5,7,11};
   
    
    
    public BoggleUi(Board inputBoard)
    {
      myBoard = inputBoard;
      reset = new resetGame();
      exitListener = new exitGame();
      startTimer = new timerListener();
      initComponents();
    }
    
    // initializes Frame settings and calls methods to add components to the
    // frame
    private final void initComponents()
    {
       
        timer = new Timer(1000, startTimer);
        frame = new JFrame();
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Boggle");
        strikeStyle = context.addStyle("strike", null);
        
        frame.setLayout(new BorderLayout());
       
        setMenuBar();
     
        setBottomPanel();
               
        setBogglePanel();
        
        setInfoPanel();
        
        
        frame.setJMenuBar(menuBar);
        frame.add(words, BorderLayout.SOUTH);
        frame.add(bogglePanel, BorderLayout.CENTER);
        frame.add(gameInfo, BorderLayout.EAST);
        
        frame.setVisible(true);
        frame.pack();
        
        timer.start();
    }
    
    // initializes the menu bar for the frame and adds action listeners to 
    // the exit and new game menu items
    private void setMenuBar()
    {
        menuBar = new JMenuBar();
        menu = new JMenu("Boggle");
        menuBar.add(menu);
        newgame = new JMenuItem("New Game");
        newgame.addActionListener(reset);
        exit = new JMenuItem("Exit");
        exit.addActionListener(exitListener);
        menu.add(newgame);
        menu.add(exit);
    }
    
    // initializes the panel containing the current score, word, 
    // and the submit button
    private void setBottomPanel()
    {
        words = new JPanel(new FlowLayout());
        words.setPreferredSize(new Dimension(200,200));
        words.setMinimumSize(new Dimension(100,100));
        words.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        curWord = new JLabel();
        curWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
        curWord.setPreferredSize(new Dimension(200,100));
        words.add(curWord);
        
        submit = new JButton("Submit Word");
        submit.setPreferredSize(new Dimension(200,100));
        submit.addActionListener(new SubmitWord());
        words.add(submit);
        
        score = new JLabel();
        score.setBorder(BorderFactory.createTitledBorder("Score"));
        score.setPreferredSize(new Dimension(200,100));
        score.setText("0");
        words.add(score);
        
    }
    
    // initializes panel that contains the grid of 16 letters as buttons
    // and populates each button
    private void setBogglePanel()
    {
        bogglePanel = new JPanel(new GridLayout(4,4));
        bogglePanel.setPreferredSize(new Dimension(450,450));
        bogglePanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        buttons = new JButton[Board.GRID][Board.GRID];
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++)
            {
                buttons[row][col] = new JButton();
                buttons[row][col].setText(myBoard.getDiceGame().get(4*row+col));
                buttons[row][col].setPreferredSize(new Dimension(100,100));
                buttons[row][col].addActionListener(new ButtonListener());
                bogglePanel.add(buttons[row][col]);
                
            }
    }
    
    // initializes panel that contains words the user can enter
    // as well as displays remaining time and lets a user shake dice
    private void setInfoPanel()
    {
        gameInfo = new JPanel();
        gameInfo.setPreferredSize(new Dimension(300,300));
        
        enterWord = new JTextPane();
        enterWord.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        enterWord.setEditable(false);
        striketext = (StyledDocument) enterWord.getDocument();
        scroll = new JScrollPane(enterWord);
        scroll.setPreferredSize(new Dimension(200,300));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        gameInfo.add(scroll);
        
        time = new JLabel("3:00", SwingConstants.CENTER);
        time.setMinimumSize(new Dimension(300,100));
        time.setPreferredSize(new Dimension(200,60));
        time.setBorder(BorderFactory.createTitledBorder("Time left"));
        time.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        gameInfo.add(time);
        
        shake = new JButton("Shake Dice");
        shake.setPreferredSize(new Dimension(200,75));
        shake.addActionListener(reset);
        gameInfo.add(shake);
    }


    // handles what happens when the user wants to submit a word they have found
    private class SubmitWord implements ActionListener
    {
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
          String enteredWord = curWord.getText();
          // checks to see if the word is in the dictionary
          if(myBoard.getDictionaryData().contains(enteredWord.toLowerCase()))
          {
              // case for if word has already been used
              if(submitted.contains(enteredWord))
                  JOptionPane.showMessageDialog(null, "You have already submitted that word!");
              
              // award appropriate points based on word length
              else
              {
                int wordLen = enteredWord.length();
                if(wordLen <= 2)
                   JOptionPane.showMessageDialog(null, "That word is too short");
                else
                {
                  if(wordLen >= 8)
                      labelScore += scoreArray[7];
                  else
                      labelScore+= scoreArray[wordLen-1];
                  score.setText(""+labelScore);
                  submitted.add(enteredWord);
                  nonStrike = context.addStyle("nostrike", null);
                  StyleConstants.setStrikeThrough(nonStrike, false);
                  try
                  {
                  striketext.insertString(enterWord.getText().length(), 
                                          enteredWord + "\n", nonStrike);
                  }
                  catch(BadLocationException ex)
                  {
                     System.out.println("Bad location");
                  }
                }
              }
          }
              
          else
              JOptionPane.showMessageDialog(null, "That is not a valid word");
          
          curWord.setText("");
          
          // reenables all buttons after a word has been entered
          for(int i = 0; i < Board.GRID; i++)
              for(int j = 0; j < Board.GRID; j++)
                  buttons[i][j].setEnabled(true);
          usedButtons.clear();
        }
        
    }
    
    
    // handles disabling of buttons to only show valid selections when a 
    // button is pressed
    private class ButtonListener implements ActionListener
    {
        private JButton tempButton;
        @Override
        public void actionPerformed(ActionEvent e) {
          tempButton = (JButton) e.getSource();
          
          curWord.setText(curWord.getText() + tempButton.getText());
          
          // calls checkRange method to figure out what buttons to reenable
          // and adds the current button to an array so that it cannot be 
          // reused in the same word
          for(int i = 0; i < Board.GRID; i++)
              for(int j = 0; j < Board.GRID; j++)
                  if(buttons[i][j] == tempButton)
                  {
                      checkRange(i-1,j-1);
                      usedButtons.add(buttons[i][j]);
                  }
          //turns off the buttons that have already been used
           for(int i = 0; i < Board.GRID; i++)
              for(int j = 0; j < Board.GRID; j++)
                  if(usedButtons.contains(buttons[i][j]))
                      buttons[i][j].setEnabled(false);
                  
        }
        
        // method calculates which buttons can be enabled by checking its location
         
        private void checkRange(int i, int j)
        {
            // disables every button
            for(int row = 0; row < Board.GRID; row++)
                for(int col = 0; col < Board.GRID; col++)
                    buttons[row][col].setEnabled(false);
            
            // checks diffeent cases for button positions
            if(i < 0 || j < 0)
            {
                int originI = i+1, originJ = j+1;
                if(originI + 1 < 4)
                    buttons[originI+1][originJ].setEnabled(true);
                if(originI + 1< 4 && originJ + 1 < 4 )
                    buttons[originI + 1][originJ + 1].setEnabled(true);
                if(originJ + 1 < 4)
                    buttons[originI][originJ+1].setEnabled(true);
                if(originJ - 1 >=0)
                    buttons[originI][originJ-1].setEnabled(true);
                if(originI - 1 >=0 && originJ -1 >= 0)
                    buttons[originI-1][originJ-1].setEnabled(true);
                if(originI-1>=0)
                    buttons[originI-1][originJ].setEnabled(true);
                if(originI -1 >= 0 && originJ +1 <4)
                    buttons[originI - 1][originJ+1].setEnabled(true);
                if(originI + 1 < 4 && originJ - 1>= 0)
                    buttons[originI+1][originJ -1].setEnabled(true);
            }
           // enables buttons in a ring around the current button
            else
                for(int r = i; r < i+3; r++)
                    for(int k = j; k < j+3; k++)
                        if(r<4 && k<4)
                            buttons[r][k].setEnabled(true);
            
        }
        
    }
  
    // This class handles the resetting of the timer and repainting of the game
    // board. Additionally it changes all of the letters on the dice buttons 
    // and resets the score.
    private class resetGame implements ActionListener
    {
        private ArrayList<String> newDiceArray = new ArrayList<String>();
        
        @Override
        public void actionPerformed(ActionEvent e) 
        {
        
        // repopulates the buttons with new letter values
        newDiceArray = myBoard.shakeDice();
        
       
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++)
            {
                buttons[row][col].setText(newDiceArray.get(4*row+col));
                bogglePanel.add(buttons[row][col]);
            }
        
        
        
        
          for(int i = 0; i < Board.GRID; i++)
              for(int j = 0; j < Board.GRID; j++)
                  buttons[i][j].setEnabled(true);
          
        // resets the score, scroll pane, and timer
    
          enterWord.setFont(new Font("Times New Roman", Font.PLAIN, 14));
          enterWord.setText("");
          Style defaultStyle = StyleContext.getDefaultStyleContext(). getStyle(StyleContext.DEFAULT_STYLE);
          enterWord.setCharacterAttributes(defaultStyle, true);
          StyleConstants.setStrikeThrough(strikeStyle, false);
          striketext = new DefaultStyledDocument();
          striketext = (DefaultStyledDocument) enterWord.getStyledDocument();
          striketext.setCharacterAttributes(0, striketext.getLength(), defaultStyle, true);
          usedButtons.clear();
          submitted.clear();
          computerWords.clear();
          score.setText("0");
          curWord.setText("");
          frame.revalidate();
          frame.repaint();
          time.setText("3:00");
          timer.stop();
          minutes = 3;
          seconds = 0;
          labelScore = 0;
          timer.start();
         
        }
        
    }
    
    // prompts the user asking if they want to exit and closes if yes is entered
    private class exitGame implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
          int exitCheck =   JOptionPane.showConfirmDialog(null, 
                                  "Confirm to exit Boggle", 
                                  "Exit?", 
                                  JOptionPane.YES_NO_OPTION); 
          if(exitCheck == JOptionPane.YES_OPTION)
              System.exit(0);
          
          
         
        }
        
    }
    
    // sets the timer to decrement every second and formats output 
    private class timerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // handles end of the game, strikes through words and has the computer
            // randomly choose how many words they found and decrements points 
            if(minutes == 0 && seconds == 1)
            {
                time.setText("0:00");
                int arrayLen = submitted.size();
               
                context = new StyleContext();
                strikeStyle = context.addStyle("strike", null);
                nonStrike = context.addStyle("nostrike", null);
                StyleConstants.setStrikeThrough(strikeStyle, true);
                Random rand = new Random();
                JOptionPane.showMessageDialog(null, "Game Over!\nThe computer is comparing words.");
                int wordsFound = rand.nextInt(arrayLen+1);
                enterWord.setText("");
                int counter = wordsFound;
                while(counter > 0)
                {
                   String chosenWord = submitted.get(rand.nextInt(arrayLen));
                   if(computerWords.contains(chosenWord))
                       continue;
                   else
                   {
                    computerWords.add(chosenWord);
                    counter--;
                   }
                        
                }
                int deductedPoints = 0;
                
                for(int i = 0; i < submitted.size(); i++)
                     if(computerWords.contains(submitted.get(i)) == false)
                        enterWord.setText(enterWord.getText() + submitted.get(i) + "\n");
               
                for(int i = 0 ; i < computerWords.size(); i++)
                {
                    try{
                    striketext.insertString(enterWord.getText().length(), computerWords.get(i) + "\n", strikeStyle);
                    }
                    
                    catch(BadLocationException f)
                    {
                        System.out.println("Did not insert properly");
                    }
                    
                    deductedPoints += scoreArray[computerWords.get(i).length() - 1];
                }
                
                
                
                timer.stop();
                score.setText("" + (labelScore - deductedPoints));
                JOptionPane.showMessageDialog(null, "The computer found " + wordsFound + " of Player's " + arrayLen);
              
                
               
            }
            if(seconds == 0)
            {
                seconds = 60;
                minutes--;
            }
            seconds--;
            
            if(seconds < 10)
                time.setText(minutes + ":"+ "0" + seconds);
            else
                time.setText(minutes + ":" + seconds);
                
        }
        
    }
   
}
