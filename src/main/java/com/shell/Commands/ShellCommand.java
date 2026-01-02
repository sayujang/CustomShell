package com.shell.Commands;
import java.util.List;
@FunctionalInterface
public interface ShellCommand{
    void execute(List<String> arguments, List<String> options);
}