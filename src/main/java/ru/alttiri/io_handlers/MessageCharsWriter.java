package ru.alttiri.io_handlers;

import ru.alttiri.Sleeper;
import ru.alttiri.logger.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.util.Objects.isNull;

public class MessageCharsWriter extends MessageOutputStreamHandler {

    private Writer writer;
    private Logger logger = Logger.getInstance();

    public MessageCharsWriter(OutputStream output) {
        this.writer = new OutputStreamWriter(output);
    }
    public MessageCharsWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void handle() {
        if (!isNull(getMessage())) {
            try {
                char[] chars = getMessage().toCharArray(); // getBytes() -- кодировку потеряет
                for (char ch : chars) {
                    writer.write(ch);
                    logger.log(ch+"");
                    //Sleeper.sleep(200);
                    //writer.flush();
                }
                writer.write(System.getProperty("line.separator")); // reader.readLine() ожидает \n
                writer.flush();
            } catch (IOException e) {
                logger.log(e);
//                e.printStackTrace();
            }
        }

    }
}
