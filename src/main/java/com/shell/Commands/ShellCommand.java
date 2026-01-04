package com.shell.Commands;
import java.io.OutputStream;
import java.util.List;
import com.shell.Context;
import com.shell.ShellIO;
import com.shell.StandardShellIO;
@FunctionalInterface
public interface ShellCommand{
    void execute(List<String> arguments, List<String> options, ShellIO shellIO, Context context);
}
