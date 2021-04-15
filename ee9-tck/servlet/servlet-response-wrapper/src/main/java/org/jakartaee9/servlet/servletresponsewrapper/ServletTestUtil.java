package org.jakartaee9.servlet.servletresponsewrapper;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletOutputStream;

public class ServletTestUtil {

    public static final String PASSED = "Test PASSED";
    public static final String FAILED = "Test FAILED";

    public static void printResult(PrintWriter pw, boolean passed) throws IOException {
        if (passed) {
            pw.println(PASSED);
        } else {
            pw.println(FAILED);
        }
    }

    public static void printResult(PrintWriter pw, String s) {

        // if string is null or empty, then it passed
        if (s == null || s.equals("")) {
            pw.println(PASSED);
        } else {
            pw.println(FAILED + ": " + s);
        }
    }

    public static void printResult(ServletOutputStream servletOutputStream, boolean b) throws IOException {
        if (b) {
            servletOutputStream.println(PASSED);
        } else {
            servletOutputStream.println(FAILED);
        }
    }

}
