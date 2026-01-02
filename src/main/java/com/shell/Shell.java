package com.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shell {
    String inText ="";
    List<String> options = new ArrayList<>();
    List<String> arguments = new ArrayList<>();
    String command = ""; // using string because commands dont change that often

    String input() {
        System.out.print("$ ");
        Scanner inPipe = new Scanner(System.in);
        inText = inPipe.nextLine();
        return inText;
    }

    void eval() {

        Pattern pattern = Pattern.compile("\"([^\"]*)\"|(-\\S+)|(\\S+)");
        Matcher matcher = pattern.matcher(inText);
        boolean isFirst = true;
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                arguments.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                options.add(matcher.group(2));
            } else if (matcher.group(3) != null) {
                if (isFirst) {
                    command = matcher.group(3);
                    isFirst = false;
                } else {
                    arguments.add(matcher.group(3));// for unquoted arguments like file path
                }
            }
        }
    }

    boolean processCommand() {
        boolean success = false;
        if (!command.equalsIgnoreCase("exit")) {
            success = true;
        }
        return success;
    }

    void run() {
        while (true) {
            input();
            eval();
            if (!processCommand()) {
                break;
            } else {
                System.out.printf("%s: command not found %n", inText);
            }
        }
    }
}
