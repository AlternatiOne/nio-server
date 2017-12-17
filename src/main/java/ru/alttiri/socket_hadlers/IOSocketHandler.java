package ru.alttiri.socket_hadlers;


import ru.alttiri.io_handlers.InputStreamHandler;
import ru.alttiri.io_handlers.InputStreamHandlerCreator;
import ru.alttiri.io_handlers.MessageOutputStreamHandler;
import ru.alttiri.io_handlers.MessageOutputStreamHandlerCreator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public abstract class IOSocketHandler implements SocketHandler {

    protected Socket socket;

    public IOSocketHandler(Socket socket) {
        this.socket = socket;
    }

    //protected static final String PREFIX = "IOSocketHandler: ";

    private InputStreamHandler inputStreamHandler;
    private InputStreamHandlerCreator inputStreamHandlerCreator;

    private MessageOutputStreamHandler messageOutputStreamHandler;
    private MessageOutputStreamHandlerCreator messageOutputStreamHandlerCreator;


    public void init() throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        createInputStreamHandler(inputStream);
        createMessageOutputStreamHandler(outputStream);
    }

    public void setMessageOutputStreamHandlerCreator(MessageOutputStreamHandlerCreator messageOutputStreamHandlerCreator) {
        this.messageOutputStreamHandlerCreator = messageOutputStreamHandlerCreator;
    }

    private void createMessageOutputStreamHandler(OutputStream outputStream) {
        this.messageOutputStreamHandler = messageOutputStreamHandlerCreator.create(outputStream);
    }


    public void setInputStreamHandlerCreator(InputStreamHandlerCreator inputStreamHandlerCreator) {
        this.inputStreamHandlerCreator = inputStreamHandlerCreator;
    }

    private void createInputStreamHandler(InputStream inputStream) {
        this.inputStreamHandler = inputStreamHandlerCreator.create(inputStream);
    }

    protected MessageOutputStreamHandler getMessageOutputStreamHandler() {
        return messageOutputStreamHandler;
    }

    protected InputStreamHandler getInputStreamHandler() {
        return inputStreamHandler;
    }
}
