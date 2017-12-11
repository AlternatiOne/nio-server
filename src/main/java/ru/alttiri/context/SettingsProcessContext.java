package ru.alttiri.context;

import ru.alttiri.runners.printer.ProcessInputPrinter;

import java.nio.charset.Charset;

import static ru.alttiri.Settings.PROCESS_INPUT_PRINTER;
import static ru.alttiri.Settings.PROCESS_OUTPUT_CHARSET;

public class SettingsProcessContext implements ProcessContext {

    private static SettingsProcessContext instance = new SettingsProcessContext();
    private SettingsProcessContext() {}
    public static ProcessContext getInstance() {
        return SettingsProcessContext.instance;
    }

    @Override
    public ProcessInputPrinter printer() {
        return PROCESS_INPUT_PRINTER;
    }

    @Override
    public Charset charset() {
        return PROCESS_OUTPUT_CHARSET;
    }
}
