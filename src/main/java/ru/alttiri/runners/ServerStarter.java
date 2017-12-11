package ru.alttiri.runners;

import ru.alttiri.context.SettingsServerContext;
import ru.alttiri.logger.Logger;
import ru.alttiri.server.Server;
import ru.alttiri.Sleeper;
import ru.alttiri.context.GlobalContext;


public class ServerStarter {

    private static Logger logger = Logger.getInstance();
    private static final String PREFIX = "ServerStarter: ";

    public static void main(String... args) {

        final GlobalContext context = GlobalContext.getInstance();
        context.setServerContext(SettingsServerContext.getInstance());

        Thread server = new Thread(new Server(context.getServerContext().port()));
        server.start();
        System.out.println("Server started");

        Sleeper.sleep(context.getServerContext().lifeTime());

        //System.out.println(PREFIX + "Прерывание работы сервера...");
        logger.log(ServerStarter.class, "Прерывание работы сервера...");
        server.interrupt();
        //System.out.println(PREFIX + "Запрос на прерывание работы сервера отправлен.");
        logger.log(ServerStarter.class, "Запрос на прерывание работы сервера отправлен.");
    }
}
