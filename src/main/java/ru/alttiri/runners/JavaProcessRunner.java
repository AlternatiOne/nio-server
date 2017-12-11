package ru.alttiri.runners;

import ru.alttiri.Sleeper;
import ru.alttiri.context.GlobalContext;
import ru.alttiri.runners.printer.CharsPrinter;
import ru.alttiri.runners.printer.ProcessInputHandler;
import ru.alttiri.runners.printer.ProcessInputPrinter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

/**
 * https://javatalks.ru/topics/10697
 */

public class JavaProcessRunner {

    public static String getProcessCommand(String classPath, String clazz) {
        return "java -classpath " + classPath + " " + clazz;
    }

    private Process process;
    private String command;

    public void waitFor() throws InterruptedException {
        process.waitFor();
    }
    public void waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        process.waitFor(timeout, unit);
    }

    public JavaProcessRunner(String command) {
        this.command = command;
    }

    public void runProcess() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        this.process = runtime.exec(command); // throws IOException

        ProcessInputHandler errorGobbler = new ProcessInputHandler(this.process.getErrorStream(), "ERROR");
        ProcessInputHandler outputGobbler = new ProcessInputHandler(this.process.getInputStream(), "OUTPUT");

        // какой стиль печать данных, полученных из процесса
        //ProcessInputPrinter printer = new CharsPrinter();
        ProcessInputPrinter printer = GlobalContext.getInstance().getProcessContext().printer();
        errorGobbler.setPrinter(printer);
        outputGobbler.setPrinter(printer);

        errorGobbler.start();
        outputGobbler.start();
    }

    public void destroy() {
        if (!isNull(this.process)) {
            this.process.destroy();
        }
    }
}


