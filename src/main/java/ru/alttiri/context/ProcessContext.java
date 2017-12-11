package ru.alttiri.context;

import ru.alttiri.runners.printer.ProcessInputPrinter;

import java.nio.charset.Charset;

public interface ProcessContext {
    ProcessInputPrinter printer();
    Charset charset();
}
