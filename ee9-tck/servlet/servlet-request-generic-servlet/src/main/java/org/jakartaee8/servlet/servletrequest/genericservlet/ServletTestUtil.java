package org.jakartaee8.servlet.servletrequest.genericservlet;

import java.io.IOException;
import java.io.PrintWriter;

public class ServletTestUtil {

    public static final String PASSED = "Test PASSED";

    public static final String FAILED = "Test FAILED";

    public static void printResult(PrintWriter writer, boolean passed) throws IOException {
        if (passed) {
            writer.println(PASSED);
        } else {
            writer.println(FAILED);
        }
    }

}
