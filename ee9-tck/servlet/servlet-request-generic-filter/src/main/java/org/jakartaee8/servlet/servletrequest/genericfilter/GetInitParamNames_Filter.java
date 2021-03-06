/*
*
* The Apache Software License, Version 1.1
*
* Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
* Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
* reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* 1. Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in
*    the documentation and/or other materials provided with the
*    distribution.
*
* 3. The end-user documentation included with the redistribution, if
*    any, must include the following acknowlegement:
*       "This product includes software developed by the
*        Apache Software Foundation (http://www.apache.org/)."
*    Alternately, this acknowlegement may appear in the software itself,
*    if and wherever such third-party acknowlegements normally appear.
*
* 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
*    Foundation" must not be used to endorse or promote products derived
*    from this software without prior written permission. For written
*    permission, please contact apache@apache.org.
*
* 5. Products derived from this software may not be called "Apache"
*    nor may "Apache" appear in their names without prior written
*    permission of the Apache Group.
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
* ====================================================================
*
* This software consists of voluntary contributions made by many
* individuals on behalf of the Apache Software Foundation.  For more
* information on the Apache Software Foundation, please see
* <http://www.apache.org/>.
*
* [Additional notices, if required by prior licensing conditions]
*
*/

package org.jakartaee8.servlet.servletrequest.genericfilter;

import static org.jakartaee8.servlet.servletrequest.genericfilter.ServletTestUtil.printResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public final class GetInitParamNames_Filter extends GenericFilter {

    private static final long serialVersionUID = -1984661778456278294L;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean passed = false;
        PrintWriter printWriter = response.getWriter();
        printWriter.println("doFilter was successfully called in GetInitParamNames_Filter");

        if (getFilterConfig() == null) {
            passed = false;
            printWriter.println("doFilter of GetInitParamNames_Filter was called but this filter instance is not currently configured ");
        } else {

            String expectedResult1 = "GetInitParamNames_Filter_attribute1";
            String expectedResult2 = "GetInitParamNames_Filter_attribute2";

            boolean expectedResult1Found = false;
            boolean expectedResult2Found = false;
            Enumeration<String> initParameterNames = getInitParameterNames();
            int expectedCount = 2;
            int count = 0;

            if (initParameterNames.hasMoreElements()) {
                List<String> parameterNames = new ArrayList<>();

                while (initParameterNames.hasMoreElements()) {
                    String result = initParameterNames.nextElement();

                    if (result.equals(expectedResult1)) {
                        if (!expectedResult1Found) {
                            count++;
                            expectedResult1Found = true;
                        } else {
                            passed = false;
                            printWriter.println("GenericFilter.getInitParameterNames() method return the same parameter name twice  ");
                            printWriter.println("The parameter name already received was " + expectedResult1);
                        }
                    } else if (result.equals(expectedResult2)) {
                        if (!expectedResult2Found) {
                            count++;
                            expectedResult2Found = true;
                        } else {
                            passed = false;
                            printWriter.println("GenericFilter.getInitParameterNames() method return the same parameter name twice ");
                            printWriter.println("The parameter name already received was " + expectedResult2);
                        }
                    } else {
                        parameterNames.add(result);
                    }
                }

                if (count != expectedCount) {
                    passed = false;
                    printWriter.println("GenericFilter.getInitParameterNames() method FAILED  ");
                    printWriter.println("Expected count = " + expectedCount);
                    printWriter.println("Actual count = " + count);
                    printWriter.println("The expected parameter names received were :");

                    if (expectedResult1Found) {
                        printWriter.println(expectedResult1);
                    }

                    if (expectedResult2Found) {
                        printWriter.println(expectedResult2);
                    }

                    printWriter.println("    Other parameter names received were :");

                    for (String parameterName : parameterNames) {
                        printWriter.println("     " + parameterName);
                    }
                } else {
                    passed = true;
                }
            } else {
                passed = false;
                printWriter.println("GenericFilter.getInitParameterNames() returned an empty enumeration");
            }
        }

        printResult(printWriter, passed);

    }

    // remove the filter configuration object for this filter.
    @Override
    public void destroy() {
    }

}
