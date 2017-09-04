/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author matthew
 */
public class ReadDataFile implements IReadDataFile
{
    private Scanner input;
    private String dataFile;
    private ArrayList<String> data;
    
    // constructs the object, saves filename to datafile and instantiates data
    public ReadDataFile(String fileName)
    {
        dataFile = fileName;
        data = new ArrayList<String>();
    }
    
    // reads the files and adds the information from the files
    @Override
    public void populateData()
    {
        // opens the file and reads each string in the file
        try
        {
            URL url = getClass().getResource(dataFile);
            File file = new File(url.toURI());
            
            input = new Scanner(file);
            
            while(input.hasNext())
            {
                getData().add(input.next());
            }
        }
        catch(IOException | URISyntaxException ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        
        finally
        {
            if(input != null)
                input.close();
        }
    }

    /**
     * @return the data
     */
    public ArrayList<String> getData() {
        return data;
    }
}
