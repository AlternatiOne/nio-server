package ru.alttiri.socket_hadlers;

import ru.alttiri.Sleeper;
import ru.alttiri.context.ClientContext;
import ru.alttiri.context.GlobalContext;
import ru.alttiri.logger.Logger;

import java.net.Socket;


//todo убрать наследованик и сделать композицию, IOSocketHandler можно заменить на класс Контекста
public class WriteReadSocketHandler extends IOSocketHandler {

    private Logger logger = Logger.getInstance();
    public WriteReadSocketHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void handle() {

        GlobalContext context = GlobalContext.getInstance();
        ClientContext client = context.getClientContext();

        //System.out.println(PREFIX + "Соединение установлено");
        logger.log(this, "Соединение установлено");

        for (int i = 0; i < client.iterations(); i++) {

            //System.out.println(PREFIX + "Подготовка к отправке... " + i);
            logger.log(this, "Подготовка к отправке... " + i);
            Sleeper.sleep(client.pauseBeforeIteration());

            getMessageOutputStreamHandler().setMessage(client.message() + i);
            getMessageOutputStreamHandler().handle();

            //System.out.println(PREFIX + "Сообщение отправлено... " + i);
            logger.log(this, "Сообщение отправлено... " + i);


            Sleeper.sleep(client.pauseInMiddleOfIteration());


            //System.out.println(PREFIX + "Чтение: ");
            logger.log(this, "Чтение: ");
            getInputStreamHandler().handle();
            //System.out.println(PREFIX + "Прочитано");
            logger.log(this, "Прочитано");

            Sleeper.sleep(client.pauseAfterIteration());
        }

    }
}