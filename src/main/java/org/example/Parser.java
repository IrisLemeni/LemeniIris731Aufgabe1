package org.example;

import java.io.IOException;
import java.util.List;

public interface Parser {
    List<Ereignis> parseLogs(String path) throws IOException;
}
