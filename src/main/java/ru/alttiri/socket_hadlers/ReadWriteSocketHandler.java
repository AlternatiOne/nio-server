package ru.alttiri.socket_hadlers;

import ru.alttiri.Sleeper;
import ru.alttiri.context.ClientContext;
import ru.alttiri.context.GlobalContext;
import ru.alttiri.logger.Logger;

import java.net.Socket;

public class ReadWriteSocketHandler extends IOSocketHandler {

    private Logger logger = Logger.getInstance();
    public ReadWriteSocketHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void handle() {

        GlobalContext context = GlobalContext.getInstance();
        ClientContext client = context.getClientContext();

        logger.log(this, "Соединение установлено");

        for (int i = 0; i < client.iterations(); i++) {

            Sleeper.sleep(client.pauseBeforeIteration());

            logger.log(this, "Чтение: ");
            getInputStreamHandler().handle();
            logger.log(this, "Прочитано");

            Sleeper.sleep(client.pauseInMiddleOfIteration());

            logger.log(this, "Подготовка к отправке... " + i);
            getMessageOutputStreamHandler().setMessage(client.message() + i);
            getMessageOutputStreamHandler().handle();
            logger.log(this, "Сообщение отправлено... " + i);


            Sleeper.sleep(client.pauseAfterIteration());
        }

    }
}