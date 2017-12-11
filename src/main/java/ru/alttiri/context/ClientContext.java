package ru.alttiri.context;

import ru.alttiri.io_handlers.InputStreamHandlerCreator;
import ru.alttiri.io_handlers.MessageOutputStreamHandlerCreator;

public interface ClientContext {

    int pauseBeforeIteration();
    int pauseInMiddleOfIteration();
    int pauseAfterIteration();
    int iterations();

    String message();

    MessageOutputStreamHandlerCreator messageWriterCreator();
    InputStreamHandlerCreator inputStreamCreator();

}
