package org.jakartaee8.servlet.servletrequest.genericfilter;


import java.io.IOException;
import java.io.PrintWriter;



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

}
