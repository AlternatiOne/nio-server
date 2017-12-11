package ru.alttiri.context;

public interface ServerContext {
    int port();
    int bufferSize();
    int pauseAfterIteration();
    int lifeTime();
    boolean closeClientsSockets();
}