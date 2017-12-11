package ru.alttiri;


    /*
    999 2299 0 999
    Сервер прочел половину.

    999 999 1999 999
    сразу же закрыл
    */


import ru.alttiri.io_handlers.InputLineCharsPrinter;
import ru.alttiri.io_handlers.InputLinePrinter;
import ru.alttiri.io_handlers.InputStreamHandlerCreator;
import ru.alttiri.io_handlers.MessageBufferedWriter;
import ru.alttiri.io_handlers.MessageCharsWriter;
import ru.alttiri.io_handlers.MessageOutputStreamHandlerCreator;
import ru.alttiri.runners.printer.CharsPrinter;
import ru.alttiri.runners.printer.CharsWithInfoPrinter;
import ru.alttiri.runners.printer.LinePrinter;
import ru.alttiri.runners.printer.LineWithInfoPrinter;
import ru.alttiri.runners.printer.ProcessInputPrinter;

import java.nio.charset.Charset;


public final class Settings {

    //------------------------------------------------------------------------------------------------------------------
    // Runner
    public static final boolean RUN_CLIENT_IN_NEW_PROCESS = true;

    //------------------------------------------------------------------------------------------------------------------
    // JavaProcessRunner
    public static final ProcessInputPrinter PROCESS_INPUT_PRINTER = new CharsPrinter();
    //// LinePrinter, LineWithInfoPrinter, CharsPrinter, CharsWithInfoPrinter
    // для Chars*Printer нужно сделать синхронизацию _процессов_

    // ProcessInputHandler
    public static final Charset PROCESS_OUTPUT_CHARSET = Charset.forName("CP1251"); // utf-8, CP866, CP1251


    //------------------------------------------------------------------------------------------------------------------
    // ServerStarter
    public static final int SERVER_LIFE_TIME   = 15299; // Time ms
    public static final int SERVER_PORT        = 5050;

    // Server
    public static final int BUFFER_SIZE        = 12;
    public static final int SERVER_PAUSE_AFTER = 399; // Time ms
    public static final boolean SERVER_CLOSES_CLIENT_SOCKETS = true;


    //------------------------------------------------------------------------------------------------------------------
    // IOSocketHandler: WriteReadSocketHandler, ReadSocketHandler
    public static final int CLIENT_PAUSE_BEFORE_ITERATION = 0;    // Time ms
    public static final int CLIENT_PAUSE_MIDDLE           = 350;  // Time ms
    public static final int CLIENT_PAUSE_AFTER_ITERATION  = 999;  // Time ms
    public static final int CLIENT_ITERATION              = 5;    // Time ms

    // WriteReadSocketHandler
    public static final String CLIENT_MESSAGE = "TEST1234567890QWERTY№";

    // ClientStarter: IOSocketHandler
    public static final MessageOutputStreamHandlerCreator MESSAGE_OUTPUT_HANDLER_CREATOR = MessageCharsWriter::new;
    //// MessageBufferedWriter, MessagePrintWriter, MessageCharsWriter
    public static final InputStreamHandlerCreator INPUT_HANDLER_CREATOR = InputLineCharsPrinter::new;
    //// InputLinePrinter, InputLineCharsPrinter


}