package com.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shell.Commands.Echo;
import com.shell.Commands.ShellCommand;

public class Shell {
    String inText = "";
    private Scanner inPipe;
    private static HashMap<String,ShellCommand> commandMapping=new HashMap<>();
    static 
    {
        ShellCommand echo=new Echo();
        commandMapping.put("echo",echo);
    }
    static final Pattern pattern;
    static {
        pattern = Pattern.compile("\"([^\"]*)\"|(-\\S+)|(\\S+)");
    }

    Shell() {
        this.inPipe = new Scanner(System.in);
    }

    class ParsedResults {
        String command="";// using string because commands dont change that often
        List<String> options = new ArrayList<>();
        List<String> arguments = new ArrayList<>();
        void setOption(String option)
        {
            options.add(option);
        }
        void setArgument(String argument)
        {
            arguments.add(argument);
        }
        void setCommand(String command)
        {
            this.command=command;
        }
        String getCommand()
        {
            return this.command;
        }
        List<String> getOption()
        {
            return this.options;
        }
        List<String> getArguments()
        {
            return this.arguments;
        }

    }

    ParsedResults eval() {
        ParsedResults parsedResults=new ParsedResults();
        Matcher matcher = pattern.matcher(inText);
        boolean isFirst = true;
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parsedResults.setArgument(matcher.group(1));
            } else if (matcher.group(2) != null) {
                parsedResults.setOption(matcher.group(2));
            } else if (matcher.group(3) != null) {
                if (isFirst) {
                    parsedResults.setCommand(matcher.group(3));
                    isFirst = false;
                } else {
                    parsedResults.setArgument(matcher.group(1));// for unquoted arguments like file path
                }
            }
        }
        return parsedResults;
    }

    void processCommand(String command, List<String> arguments, List<String> options) {
        commandMapping.get(command).execute(arguments, options);
    }

    boolean isCommandValid(String command) {
        return commandMapping.containsKey(command);
    }

   
    void run() {
        while (true) {
            System.out.print("$ ");
            inText = inPipe.nextLine();
            ParsedResults parseResult=eval();
            if (parseResult.getCommand().equalsIgnoreCase("exit")) {
                break;
            } else if (isCommandValid(parseResult.getCommand())) {
                processCommand(parseResult.getCommand(),parseResult.getArguments(),parseResult.getOption());
            } else {
                System.out.printf("%s: command not found %n", inText);
            }
        }
    }
}
