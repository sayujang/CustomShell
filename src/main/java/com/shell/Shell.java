package com.shell;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shell.Commands.Cd;
import com.shell.Commands.Echo;
import com.shell.Commands.Pwd;
import com.shell.Commands.ShellCommand;
import com.shell.Shell.ParsedResults;
import com.shell.StandardShellIO;

public class Shell {
    String inText = "";
    private Scanner inPipe;
    private static HashMap<String, ShellCommand> commandMapping = new HashMap<>();
    static final Pattern pattern;
    Context context = new Context();
    StandardShellIO shellIO = new StandardShellIO();
    // initialize commandmapping
    static {
        ShellCommand echo = new Echo();
        commandMapping.put("echo", echo);
        ShellCommand pwd = new Pwd();
        commandMapping.put("pwd", pwd);
        ShellCommand cd = new Cd();
        commandMapping.put("cd", cd);
    }
    // initialize pattern compilation
    static {
        pattern = Pattern.compile("(\"[^\"]*\"|[^\\s\"]+)+");
    }

    Shell() {
        this.inPipe = new Scanner(System.in);
    }

    class ParsedResults {
        String command = "";// using string because commands dont change that often
        List<String> options = new ArrayList<>();
        List<String> arguments = new ArrayList<>();
        List<String> rawList=new ArrayList<>();
        void setOption(String option) {
            options.add(option);
        }

        void setArgument(String argument) {
            arguments.add(argument);
        }

        void setCommand(String command) {
            this.command = command;
        }

        String getCommand() {
            return this.command;
        }

        List<String> getOption() {
            return this.options;
        }

        List<String> getArguments() {
            return this.arguments;
        }
        void addToRawList(String token)
        {
            rawList.add(token);
        }
        List<String> getRawStringList()
        {
            return rawList;
        }
    }

    ParsedResults eval() {
        ParsedResults parsedResults = new ParsedResults();
        Matcher matcher = pattern.matcher(inText);

        boolean isFirst = true;
        while (matcher.find()) {
            String rawToken = matcher.group(0);
            String token = rawToken.replace("\"", "");

            if (isFirst) {
                parsedResults.setCommand(token);
                isFirst = false;
            } else if (token.startsWith("-")) {
                parsedResults.setOption(token);
            } else {
                parsedResults.setArgument(token);
            }
            parsedResults.addToRawList(token);
        }
        return parsedResults;
    }

    void processCommand(String command, List<String> arguments, List<String> options) {
        commandMapping.get(command).execute(arguments, options, shellIO, context);
    }

    boolean isBuiltIn(String command) {
        return commandMapping.containsKey(command);
    }

    void run() {

        while (true) {
            System.out.printf("%s $ ", context.getCwd().toString());
            // if inpipe has no input break the loop
            if (inPipe.hasNextLine()) {
                inText = inPipe.nextLine();
            } else {
                break;
            }
            ParsedResults parseResult = eval();
            if (parseResult.getCommand().equalsIgnoreCase("exit")) {
                break;
            } else if (isBuiltIn(parseResult.getCommand())) {

                processCommand(parseResult.getCommand(), parseResult.getArguments(), parseResult.getOption());
            } 
            else {
                try{
                    ProcessBuilder pb = new ProcessBuilder(parseResult.getRawStringList());
                    pb.directory(new File(context.getCwd().toString()));
                    pb.inheritIO();
                    Process process=pb.start();//child process throws exception
                    int exitcode=process.waitFor();
                    System.out.printf("process exited with exitcode: %d %n",exitcode);
                }
                catch(Exception e)
                {
                    System.out.printf("%s: command not found %n", parseResult.getCommand());
                }
                
            }
        }
    }
}
