package ru.alttiri.context;

import ru.alttiri.runners.printer.LinePrinter;
import ru.alttiri.runners.printer.ProcessInputPrinter;

import java.nio.charset.Charset;

public class DefaultProcessContext implements ProcessContext {

    private static DefaultProcessContext instance = new DefaultProcessContext();
    private DefaultProcessContext() {}

    public static ProcessContext getInstance() {
        return DefaultProcessContext.instance;
    }

    @Override
    public ProcessInputPrinter printer() {
        return new LinePrinter();
    }

    @Override
    public Charset charset() {
        return Charset.forName("CP1251"); // utf-8, CP866, CP1251
    }
}
