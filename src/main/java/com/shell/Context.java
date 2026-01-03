package com.shell;
import java.nio.file.Path;

public class Context{
        private boolean isExit=false;
        private Path cwd=Path.of(System.getProperty("user.dir"));
        // static{
        //     cwd=Path.of(System.getProperty("user.dir"));
        // }
        public void setCwd(Path cwd)
        {
            this.cwd=cwd;
        }
        public Path getCwd()
        {
            return cwd;
        }
    }
