package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.Ereignis;
import org.example.Parser;

public class XMLLogParser implements Parser {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "\\p{javaSpaceChar}*<Datum>(\\d{4}-\\d{2}-\\d{2})</Datum>");

    public static final Pattern EREIGNIS_PATTERN = Pattern.compile(
            "\\p{javaSpaceChar}*<Ereignis>(.*)</Ereignis>");

    private static final Pattern HAUS_PATTERN = Pattern.compile(
            "\\p{javaSpaceChar}*<Haus>(" + Ereignis.Haus.Lannister + "|" + Ereignis.Haus.Stark + "|" + Ereignis.Haus.Baratheon + Ereignis.Haus.Martell + "|" + Ereignis.Haus.Greyjoy + "|" + Ereignis.Haus.Targaryen + "|" + Ereignis.Haus.Tyrell + ")</Haus>");

    private static final Pattern ID_PATTERN = Pattern.compile("\\p{javaSpaceChar}*<Id>(.*)</Id>");

    private static final Pattern MITGLIED_PATTERN = Pattern.compile("\\p{javaSpaceChar}*<Mitgliedsname>(.*)</Mitgliedsname>");

    private static XMLLogParser instance;

    private XMLLogParser() {
    }

    public static XMLLogParser getInstance() {
        if (instance == null) {
            instance = new XMLLogParser();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    public List<Ereignis> parseLogs(String path) throws IOException {
        Path filePath = Path.of(path);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            if (!reader.readLine().equals("<logs>")) {
                throw new IOException("Invalid log file");
            }

            List<Ereignis> logs = new ArrayList<>();
            String nextLine = reader.readLine();

            while (!nextLine.equals("</logs>")) {
                logs.add(parseLog(reader, nextLine));
                nextLine = reader.readLine();
            }
            return logs;
        }
    }

    private Ereignis parseLog(BufferedReader reader, String firstLine) throws IOException {
        Ereignis ereignis = new Ereignis();

        if (!firstLine.contains("<log>")) {
            throw new IOException("Invalid log file");
        }

        String nextLine = reader.readLine();
        while (true) {
            parseField(reader, nextLine, ereignis);
            nextLine = reader.readLine();
            if (nextLine.contains("</log>")) {
                return ereignis;
            }
        }
    }

    private void parseField(BufferedReader reader, String firstLine, Ereignis ereignis) throws IOException {
        Matcher dateMatcher = DATE_PATTERN.matcher(firstLine);
        if (dateMatcher.matches()) {
            ereignis.setDate(dateMatcher.group(1));
        }

        Matcher ereignisMatcher = EREIGNIS_PATTERN.matcher(firstLine);
        if (ereignisMatcher.matches()) {
            ereignis.setDescription(ereignisMatcher.group(1));
        }
        if (firstLine.contains("<Haus>")) {
            ereignis.(parseHause(reader));
        }
        Matcher idMatcher = ID_PATTERN.matcher(firstLine);
        if (idMatcher.matches()) {
            ereignis.setId(Integer.valueOf(idMatcher.group(1)));
        }
        Matcher mitgliedMatcher = MITGLIED_PATTERN.matcher(firstLine);
        if (mitgliedMatcher.matches()) {
            ereignis.setName(mitgliedMatcher.group(1));
        }

    }


    private static ArrayList<Ereignis.Haus> parseHause(BufferedReader reader) throws IOException {
        ArrayList<Ereignis.Haus> visibilities = new ArrayList<>();

        String nextLine = reader.readLine();
        while (true) {
            Matcher matcher = HAUS_PATTERN.matcher(nextLine);
            if (matcher.matches()) {
                visibilities.add(Ereignis.Haus.valueOf(matcher.group(1)));
            }
            nextLine = reader.readLine();
            if (nextLine.contains("</Haus>")) {
                return visibilities;
            }
        }
    }
}
