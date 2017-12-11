package ru.alttiri.context;

import static ru.alttiri.Settings.BUFFER_SIZE;
import static ru.alttiri.Settings.SERVER_CLOSES_CLIENT_SOCKETS;
import static ru.alttiri.Settings.SERVER_LIFE_TIME;
import static ru.alttiri.Settings.SERVER_PAUSE_AFTER;
import static ru.alttiri.Settings.SERVER_PORT;

public class SettingsServerContext implements ServerContext {

    private static SettingsServerContext instance = new SettingsServerContext();
    private SettingsServerContext() {}
    public static SettingsServerContext getInstance() {
        return SettingsServerContext.instance;
    }

    @Override
    public int port() {
        return SERVER_PORT;
    }

    @Override
    public int bufferSize() {
        return BUFFER_SIZE;
    }

    @Override
    public int pauseAfterIteration() {
        return SERVER_PAUSE_AFTER;
    }

    @Override
    public int lifeTime() {
        return SERVER_LIFE_TIME;
    }

    @Override
    public boolean closeClientsSockets() {
        return SERVER_CLOSES_CLIENT_SOCKETS;
    }
}
