package ru.alttiri.socket_hadlers;

import ru.alttiri.Sleeper;
import ru.alttiri.context.ClientContext;
import ru.alttiri.context.GlobalContext;
import ru.alttiri.logger.Logger;

import java.net.Socket;


public class ReadSocketHandler extends IOSocketHandler {

    private Logger logger = Logger.getInstance();
    public ReadSocketHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void handle() {

        GlobalContext context = GlobalContext.getInstance();
        ClientContext client = context.getClientContext();

        //System.out.println(PREFIX + "Соединение установлено");
        logger.log(this, "Соединение установлено");

        for (int i = 0; i < client.iterations(); i++) {

            Sleeper.sleep(client.pauseBeforeIteration());

            //System.out.println(PREFIX + "Чтение: ");
            logger.log(this, "Чтение: ");
            getInputStreamHandler().handle();
            //System.out.println(PREFIX + "Прочитано");
            logger.log(this, "Прочитано");

            Sleeper.sleep(client.pauseAfterIteration());
        }

    }
}