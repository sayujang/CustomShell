package com.shell.Commands;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.shell.Context;
import com.shell.StandardShellIO;

public class Pwd implements ShellCommand{
    public void execute(List<String> arguments, List<String> options,StandardShellIO shellIO, Context context) 
    {
        shellIO.writeLine(context.getCwd().toString());
    }
    
}
