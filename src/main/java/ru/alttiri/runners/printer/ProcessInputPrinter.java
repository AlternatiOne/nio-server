package ru.alttiri.runners.printer;

import java.io.BufferedReader;
import java.io.IOException;

public interface ProcessInputPrinter {
    void print(BufferedReader br, String type) throws IOException;
}
