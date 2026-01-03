package com.shell.Commands;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import com.shell.Context;
import com.shell.StandardShellIO;
public class Echo implements ShellCommand {
    public void execute(List<String> arguments, List<String> options,StandardShellIO shellIO, Context context) {
        if (arguments.size()!=0)
        {
        for (int i=0; i<arguments.size()-1;i++)
        {
            shellIO.writeLine(arguments.get(i)+" ");
        }
        shellIO.writeLine(arguments.get(arguments.size()-1));
    }
    else{
        shellIO.writeLine("");
    }
    };
}
