package com.shell.Commands;
import java.io.OutputStream;
import java.util.List;
import com.shell.Shell.Context;
@FunctionalInterface
public interface ShellCommand{
    void execute(List<String> arguments, List<String> options, OutputStream stream, Context context);
}
