package com.shell.Commands;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import com.shell.Shell.Context;
public class Echo implements ShellCommand {
    public void execute(List<String> arguments, List<String> options,OutputStream stream, Context context) {
        PrintStream ps=(PrintStream)stream;
        if (arguments.size()!=0)
        {
        for (int i=0; i<arguments.size()-1;i++)
        {
            ps.print(arguments.get(i)+" ");
        }
        ps.println(arguments.get(arguments.size()-1));
    }
    else{
        ps.println("");
    }
    };
}
