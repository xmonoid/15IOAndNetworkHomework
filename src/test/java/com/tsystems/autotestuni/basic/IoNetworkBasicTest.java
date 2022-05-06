package com.tsystems.autotestuni.basic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tsystems.autotestuni.basic.IoNetworkBasic.createFile;
import static com.tsystems.autotestuni.basic.IoNetworkBasic.readFile;
import static org.junit.jupiter.api.Assertions.*;

class IoNetworkBasicTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errorStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    void createFileTest() throws Exception {
        String uuid = UUID.randomUUID().toString();
        final var filename = uuid + ".txt";
        final var filepath = System.getProperty("user.home")
                + File.separator
                + "Desktop"
                + File.separator
                + filename;
        final var file = new File(filepath);
        assertFalse(file.exists());

        File createdFile = createFile(filename);

        assertEquals(file, createdFile);
        assertTrue(file.exists());

        try (var in = new FileReader(file);
             var reader = new BufferedReader(in)) {
            long count = reader.lines().count();
            assertEquals(50, count, "File should contain 50 lines");
        }

        assertTrue(file.delete());
    }

    @Test
    void readFileTest() throws Exception {
        String uuid = UUID.randomUUID().toString();
        final var filename = uuid + ".txt";
        final var filepath = System.getProperty("user.home")
                + File.separator
                + "Desktop"
                + File.separator
                + filename;

        final var file = createFile(filename);

        readFile(filepath);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            List<String> lines = reader.lines().collect(Collectors.toList());

            List<String> evenLines = new ArrayList<>( lines.size() / 2 + 1 );
            List<String> oddLines = new ArrayList<>( lines.size() / 2 + 1 );

            for (int i = 0; i < lines.size(); i++) {
                if (i % 2 == 0) {
                    evenLines.add(lines.get(i));
                } else {
                    oddLines.add(lines.get(i));
                }
            }

            String evens = evenLines.stream().collect(Collectors.joining(System.lineSeparator()))
                    + System.lineSeparator();
            String odds = oddLines.stream().collect(Collectors.joining(System.lineSeparator()))
                    + System.lineSeparator();

            String out = outputStreamCaptor.toString();
            String err = errorStreamCaptor.toString();

            assertEquals(evens, out);
            assertEquals(odds, err);

        } finally {
            assertTrue(file.delete());
        }
    }
}