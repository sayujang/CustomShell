package com.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class StandardShellIO implements ShellIO  {
    private PrintWriter writer;
    private BufferedReader bf;
    private PrintWriter errorOut;
    StandardShellIO(OutputStream out, InputStream in, OutputStream errorOut)
    {
        this.writer=new PrintWriter(out,true); //keep true to autoflush no need writer.flush()
        this.bf=new BufferedReader(new InputStreamReader(in));
        this.errorOut=new PrintWriter(errorOut,true);
    }

    public void writeLine(String line) {
        writer.printf(line);
    }
    public void writeError(String error)
    {
        errorOut.printf(error);
    }
    public String readLine()  
    {
        try
        {
            return bf.readLine();
        }
        catch(IOException e)
        {
            errorOut.printf("I/O exception occured:"+e.getMessage());
            return null;
        }
       
        
    }
    
}
