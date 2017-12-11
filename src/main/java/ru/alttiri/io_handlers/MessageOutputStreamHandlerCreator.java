package ru.alttiri.io_handlers;

import java.io.OutputStream;

@FunctionalInterface
public interface MessageOutputStreamHandlerCreator {
    MessageOutputStreamHandler create(OutputStream outputStream);
}
