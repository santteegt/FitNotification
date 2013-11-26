package com.fitnotif.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * Representa una excepción que sucedió remotamente.
 *
 * @author FitBank CI
 */
public class RemoteException extends Exception {

    public RemoteException(String message, String stackTraceString) {
        super(getStackTraceMessage(stackTraceString, message));

        if (stackTraceString != null && stackTraceString.indexOf(" ") != -1) {
            setStackTrace(parseStackTrace(stackTraceString));
        }
    }

    private static String getStackTraceMessage(String stackTraceString, String message) {
        if (StringUtils.isBlank(stackTraceString) || stackTraceString.indexOf(
                " ") == -1) {
            return message;
        } else {
            return stackTraceString.substring(0, stackTraceString.indexOf(" "))
                    + " " + message;
        }
    }

    private StackTraceElement[] parseStackTrace(String stackTraceString) {
        if (StringUtils.isBlank(stackTraceString)) {
            return new StackTraceElement[0];
        }

        Collection<StackTraceElement> elements = new LinkedList<StackTraceElement>();

        Matcher matcher = Pattern.compile("at\\s+([\\w\\.]+)\\.([\\w]+)\\((.+):(\\d+)\\)").matcher(stackTraceString);

        while (matcher.find()) {
            int lineNumber = Integer.parseInt(matcher.group(4));
            elements.add(new StackTraceElement(matcher.group(1),
                    matcher.group(2), matcher.group(3), lineNumber));
        }

        return elements.toArray(new StackTraceElement[0]);
    }

}
