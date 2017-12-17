package ru.alttiri.runners;

import ru.alttiri.context.ClientContext;
import ru.alttiri.context.GlobalContext;
import ru.alttiri.context.SettingsClientContext;
import ru.alttiri.logger.Logger;
import ru.alttiri.socket_hadlers.IOSocketHandler;
import ru.alttiri.socket_hadlers.WriteReadSocketHandler;

import java.io.IOException;
import java.net.Socket;


public class ClientStarter {

    public static void main(String... args) {
        try (Socket socket = new Socket("localhost", 5050)) {

            final GlobalContext globalContext = GlobalContext.getInstance();
            globalContext.setClientContext(SettingsClientContext.getInstance());
            ClientContext context = globalContext.getClientContext();

            // какой обработчик сокета
            //IOSocketHandler socketHandler = new WriteReadSocketHandler(socket);
            IOSocketHandler socketHandler = context.socketHandlerCreator().create(socket);

            // как клиент пишет
            socketHandler.setMessageOutputStreamHandlerCreator(context.messageWriterCreator()); // MessageCharsWriter::new
            // как клиент читает
            socketHandler.setInputStreamHandlerCreator(context.inputStreamCreator()); // InputLineCharsPrinter::new

            socketHandler.init();
            socketHandler.handle();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Logger.getInstance().log(ClientStarter.class,"Работа с сокетом завершена");
        }
    }
}


