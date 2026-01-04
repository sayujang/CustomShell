package com.shell;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) throws Exception
    {
        Shell sh=new Shell(System.in,System.out,System.err);
        sh.run();
        // System.out.println(System.getProperty("user.dir"));
        // System.out.println(System.getProperty("user.home"));
    }
}
