package com.shell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class StandardShellIO implements ShellIO  {
    private PrintStream out =System.out;
    private InputStream in = System.in;
    private PrintStream errorOut=System.err;

    public void writeLine(String line) {
        out.println(line);
    }
    public void writeError(String error)
    {
        errorOut.println(error);
    }
    public String readLine()  
    {
        String input=null;
        try(BufferedReader bf= new BufferedReader(new InputStreamReader(in))) 
        {
            input=bf.readLine();
        }
        catch(Exception e)
        {
            errorOut.println("Exception occured:"+e.getMessage());
        }
       
        return input;
    }
    
}
