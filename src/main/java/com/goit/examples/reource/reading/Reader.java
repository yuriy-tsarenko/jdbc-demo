package com.goit.examples.reource.reading;

import com.goit.examples.reource.exception.ResourceReadException;

import java.io.InputStream;
import java.util.Scanner;

public class Reader {

    private final String resourceName;

    public Reader(String resourceName) {
        this.resourceName = resourceName;
    }

    public String read() {
        try (InputStream stream = Reader.class.getClassLoader().getResourceAsStream(resourceName)) {
            Scanner scanner = new Scanner(stream);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            return builder.toString();
        } catch (Exception e) {
            throw new ResourceReadException("Resource: " + resourceName + " reading failed", e);
        }
    }
}
