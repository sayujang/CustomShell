package com.shell.Commands;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import com.shell.Context;
import com.shell.StandardShellIO;

public class Cd implements ShellCommand {

    public void execute(List<String> arguments, List<String> options, StandardShellIO shellIO, Context context) {
        Path potentialCwd = Path.of("");

        if (arguments.size() == 1) {

            if (arguments.get(0).startsWith("~")) {
                potentialCwd = Path.of(System.getProperty("user.home") + arguments.get(0).substring(1)).normalize();
            } else if (Path.of(arguments.get(0)).isAbsolute()) {
                potentialCwd = Path.of(arguments.get(0)).normalize();
            } else {
                Path path = Path.of(arguments.get(0));
                Path absolutePath = context.getCwd().resolve(path);
                potentialCwd = absolutePath.normalize();
            }

        } else if (arguments.size() == 0) {
            potentialCwd = Path.of(System.getProperty("user.home")).normalize();
        } else {
            shellIO.writeError("too many arguments...");
            return;
        }
        //os filesystem level interactions occurs only here
        if (Files.exists(potentialCwd)) {
            if (Files.isDirectory(potentialCwd)) //here if just relative path is provided it resolves with the directory that jvm starts at which we dont want
            {
                context.setCwd(potentialCwd);
            }
            else if (Files.isRegularFile(potentialCwd))
            {
                shellIO.writeError(potentialCwd.getFileName().toString()+": Not a directory");
            }
            
        } else {
            shellIO.writeError("given path doesn't exists");
            return;
        }

    }
}
