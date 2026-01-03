package com.shell.Commands;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.shell.Shell.Context;

public class Pwd implements ShellCommand{
    public void execute(List<String> arguments, List<String> options,OutputStream stream, Context context) 
    {
        PrintStream ps =(PrintStream)stream;
        ps.println(context.getCwd().toString());
    }
    
}
