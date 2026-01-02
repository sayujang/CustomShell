package com.shell.Commands;

import java.util.List;

public class Echo implements ShellCommand {
    public void execute(List<String> arguments, List<String> options) {
        if (arguments.size()==1)
        {
            System.out.println(arguments.get(0));
        }
        else
        {
            System.out.println("Echo accepts only one argument");
        }
    };
}
