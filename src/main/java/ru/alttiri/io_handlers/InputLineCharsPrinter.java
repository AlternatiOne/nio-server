package ru.alttiri.io_handlers;

import ru.alttiri.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputLineCharsPrinter implements InputStreamHandler {

    private BufferedReader reader;
    private Logger logger = Logger.getInstance();


    public InputLineCharsPrinter(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }
    public InputLineCharsPrinter(Reader reader) {
        this.reader = new BufferedReader(reader);
    }
    public InputLineCharsPrinter(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void handle() {
        try {
            int ch = reader.read();
            while (ch != -1) {
                //System.out.print((char)ch);
                logger.log((char)ch);

//                if (ch == '\r'){
//                    System.err.println("\\r");
//                }

                if (ch == '\n') {
                    break;
                }
                ch = reader.read();
            }

            if (ch == -1) {
                logger.log(this, "End of the stream has been reached");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
