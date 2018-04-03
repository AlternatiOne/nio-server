package ru.alttiri;



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
import ru.alttiri.socket_hadlers.IOSocketHandler;
import ru.alttiri.socket_hadlers.IOSocketHandlerCreator;
import ru.alttiri.socket_hadlers.ReadSocketHandler;
import ru.alttiri.socket_hadlers.ReadWriteSocketHandler;
import ru.alttiri.socket_hadlers.WriteReadSocketHandler;
import ru.alttiri.socket_hadlers.WriteSocketHandler;

import java.nio.charset.Charset;


public final class Settings {

    static class SettingsArray {

        public SettingsArray(int SERVER_LIFE_TIME, int SERVER_PAUSE_AFTER, int CLIENT_PAUSE_BEFORE_ITERATION, int CLIENT_PAUSE_MIDDLE, int CLIENT_PAUSE_AFTER_ITERATION) {
            this.SERVER_LIFE_TIME = SERVER_LIFE_TIME;
            this.SERVER_PAUSE_AFTER = SERVER_PAUSE_AFTER;
            this.CLIENT_PAUSE_BEFORE_ITERATION = CLIENT_PAUSE_BEFORE_ITERATION;
            this.CLIENT_PAUSE_MIDDLE = CLIENT_PAUSE_MIDDLE;
            this.CLIENT_PAUSE_AFTER_ITERATION = CLIENT_PAUSE_AFTER_ITERATION;
        }

        final int SERVER_LIFE_TIME;
        final int SERVER_PAUSE_AFTER;

        final int CLIENT_PAUSE_BEFORE_ITERATION;
        final int CLIENT_PAUSE_MIDDLE;
        final int CLIENT_PAUSE_AFTER_ITERATION;
    }

    private static SettingsArray array =
            //new SettingsArray(2299, 999, 0, 0, 999);    // Клиент отправил данные, сервер прочел часть.
            //new SettingsArray(999, 999, 1111, 0, 999); // Клиент не успел отправить, а сервер уже закрыл сокет
            //new SettingsArray(999, 0, 1111, 0, 999);   // Клиент не успел отправить, а сервер уже закрыл сокет
            new SettingsArray(15299, 399, 0, 350, 999); // было до создания этого SettingsArray



    //------------------------------------------------------------------------------------------------------------------
    // Runner
    public static final boolean RUN_IN_NEW_PROCESS = true;

    //------------------------------------------------------------------------------------------------------------------
    // JavaProcessRunner
    public static final ProcessInputPrinter PROCESS_INPUT_PRINTER = new LinePrinter();
    //// LinePrinter, LineWithInfoPrinter, CharsPrinter, CharsWithInfoPrinter
    // для Chars*Printer нужно сделать синхронизацию _процессов_, иначе будет небольная каша в выводе

    // ProcessInputHandler
    public static final Charset PROCESS_OUTPUT_CHARSET = Charset.forName("CP1251"); // utf-8, CP866, CP1251


    //------------------------------------------------------------------------------------------------------------------
    // ServerStarter
    public static final int SERVER_LIFE_TIME   = array.SERVER_LIFE_TIME; //15299; // Time ms
    public static final int SERVER_PORT        = 5050;

    // Server
    public static final int BUFFER_SIZE        = 12;
    public static final int SERVER_PAUSE_AFTER = array.SERVER_PAUSE_AFTER; //399; // Time ms
    public static final boolean SERVER_CLOSES_CLIENT_SOCKETS = true;


    //------------------------------------------------------------------------------------------------------------------

    public static final IOSocketHandlerCreator IO_SOCKET_HANDLER_CREATOR = WriteReadSocketHandler::new;
    //// WriteReadSocketHandler, ReadSocketHandler, WriteSocketHandler, ReadWriteSocketHandler

    // IOSocketHandler: WriteReadSocketHandler, ReadSocketHandler, WriteSocketHandler, ReadWriteSocketHandler
    public static final int CLIENT_PAUSE_BEFORE_ITERATION = array.CLIENT_PAUSE_BEFORE_ITERATION;//0;    // Time ms
    public static final int CLIENT_PAUSE_MIDDLE           = array.CLIENT_PAUSE_MIDDLE;          //350;  // Time ms
    public static final int CLIENT_PAUSE_AFTER_ITERATION  = array.CLIENT_PAUSE_AFTER_ITERATION; //999;  // Time ms
    public static final int CLIENT_ITERATION              = 5;    // Time ms

    // WriteReadSocketHandler
    public static final String CLIENT_MESSAGE = "TEST1234567890QWERTY№";

    // ClientStarter: IOSocketHandler
    public static final MessageOutputStreamHandlerCreator MESSAGE_OUTPUT_HANDLER_CREATOR = MessageCharsWriter::new;
    //// MessageBufferedWriter, MessagePrintWriter, MessageCharsWriter
    public static final InputStreamHandlerCreator INPUT_HANDLER_CREATOR = InputLineCharsPrinter::new;
    //// InputLinePrinter, InputLineCharsPrinter


}