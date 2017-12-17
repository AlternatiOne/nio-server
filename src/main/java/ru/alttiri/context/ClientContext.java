package ru.alttiri.context;

import ru.alttiri.io_handlers.InputStreamHandlerCreator;
import ru.alttiri.io_handlers.MessageOutputStreamHandlerCreator;
import ru.alttiri.socket_hadlers.IOSocketHandlerCreator;


public interface ClientContext {

    int pauseBeforeIteration();
    int pauseInMiddleOfIteration();
    int pauseAfterIteration();
    int iterations();
    IOSocketHandlerCreator socketHandlerCreator();

    String message();

    MessageOutputStreamHandlerCreator messageWriterCreator();
    InputStreamHandlerCreator inputStreamCreator();

}
