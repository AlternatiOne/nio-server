package ru.alttiri.socket_hadlers;

import java.net.Socket;

public interface IOSocketHandlerCreator {
    IOSocketHandler create(Socket socket);
}
