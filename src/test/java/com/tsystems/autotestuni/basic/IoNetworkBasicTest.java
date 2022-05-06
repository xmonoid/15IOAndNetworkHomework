package com.tsystems.autotestuni.basic;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

import static com.tsystems.autotestuni.basic.IoNetworkBasic.createFile;
import static com.tsystems.autotestuni.basic.IoNetworkBasic.readFile;
import static org.junit.jupiter.api.Assertions.*;

class IoNetworkBasicTest {

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

        assertTrue(file.delete());
    }
}