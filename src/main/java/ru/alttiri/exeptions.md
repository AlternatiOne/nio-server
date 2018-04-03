
________________________________________________________________________________________________________________________

- `channel.accept()`
- `channel.read(buffer)`
- `channel.write(buffer)`
Генерируют `java.nio.channels.ClosedByInterruptException`, если тред был заинтерапчет _перед_ их вызывом
даже несмотря на то, что каналы в неблокирующем режиме. (Видимо потому, что по-умолчанию они в блокирующем режиме.)

Например, если интератп произошел перед `channel.accept()`:
```
    java.nio.channels.ClosedByInterruptException
        at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:202)
        at sun.nio.ch.ServerSocketChannelImpl.accept(ServerSocketChannelImpl.java:257)
        at nio_server.Server.onAccept(Server.java:171)
        at nio_server.Server.run(Server.java:134)
        at java.lang.Thread.run(Thread.java:748)
```
________________________________________________________________________________________________________________________


Если сервер завершил работу, оставив сокеты открытыми (не вызвав метод `close(Selector selector)`), 
эти сокеты останутся открытыми, пока не завершится процесс, в котором был запущен сервер.
В таком случае клиент "зависнет" в ожидании данных, если он читает из сокета. Если же он пишет в сокет, 
то зависнет, после того, как запишет в сокет определенное количество данных. 
Например, при вызове `Writer.write(new char[65536*10+1])` программа (клиент) зависнет, ожидая, 
пока с другой стороны данные начнут читать.

________________________________________________________________________________________________________________________

### Про закрытие ресурсов

ServerSocketChannel и Selector закрываются благодоря блоку try-w-r.
ServerSocket внутри ServerSocketChannel закрывается вместе с ним.

Открытые в методе `onAccept()` соединения SocketChannel (а точнее Socket внутри) закрываются в методе `onRead()`,
когда `channel.read(buffer)` возвращает `-1`, т.е. когда клиент завершил соединение, закрыв сокет методом `close()`.

Если клиент _разорвал_ соединение, а сервер следом попытается прочитать/записать данные,
-> `IOException: Удаленный хост принудительно разорвал существующее подключение`
и в catch блоке канал закрывается.

Если вызван `interrupt()` на потоке, в котором работает сервер, сервер сразу же прекращает работу, но соединения,
которые обычно закрываются в методе `onRead()`, тоже будут закрыты. У клиента в этом случает будет SocketException.

`interrupt()`, в частности, прерывает блокирующий метод `selector.select()`

________________________________________________________________________________________________________________________

 Удивительно, но если клиент подсоединился к серверу, далее сервер закрыл соединение, то лишь только 
 после второго эффективного* `flush()` будет `SocketException: Software caused connection abort: socket write error`
 данные зафлашенные первым вызовом уйдут в никуда.
 * нужны каке-либо данные для `flush()` - хоть 1 символ
 Даже операция чтения перед записью не помогает, хоть она сразу определяет `End of the stream`.
  
 
 `new SettingsArray(999, 999, 1111, 0, 999);`
 Сервер закрыл соединение, и только после этого клиент отправил в сокет данные 
  (`WriteReadSocketHandler`) сообщение было в никуда отправлено, а последующее чтение и запись будут с исключениями.  

1. Клиент подсоединился к серверу
2. Соединение было закрыто сервером (пауза)
3. Клиент якобы "передал" данные серверу - "в холостую". (без исключения)
4. Чтение или запись -- исключения.
    - (сокет закрыт сервером (методом `close(selector)`))
        - `SocketException: Software caused connection abort: recv failed`,
        - `SocketException: Software caused connection abort: socket write error`.
        
Если же клиент сначала прочел, когда сокет закрыт (`ReadWriteSocketHandler`), - сразу увидет `End of the stream`, 
а последующие записи, кроме первой, будут с исключениями.

1. Клиент подсоединился к серверу
2. Соединение было закрыто сервером
3. Клиент прочел - `null` (последующие тоже) (т.е. увидел `end of the stream`)
4. Все опытки записи -- исключение.
    - `SocketException: Software caused connection abort: socket write error`.



`new SettingsArray(2299, 999, 0, 0, 999);`
1. Клиент подсоединился к серверу
2. Клиент успешно передал данные серверу
3. Сервер закрыл соединение.
4.  - последующие чтение будет возвращать `null` (или -1)
    - первая запись (`flush`) пройдет в холостую, следующие за ней - с исключением
    - `SocketException: Software caused connection abort: socket write error`


___________________

Исключения вылетают из:
 - `OutputStreamWriter.flush()`
 - `BufferedReader.readLine()`
 - в случае использовая `PrintWriter` исключений при записе не будет, там 
 лишь флаг устанавливается (проверять наличие методом `writer.checkError()`)
 
 __________________
 
 
 Метод read() возвращает `null` (а не без генерирует исключение), когда увидит "end of the stream".
 Т.е. когда... 
    нет данных на запись у сервера и сокет сервером был закрыт (???)
    а если были данные?
        
 
  
 
________________________________________________________________________________________________________________________
 
- если сокет не был закрыт сервером (но процесс, в котором был запущен сервер, завершен)
  - (read):  SocketException: Connection reset
  - (write): SocketException: Connection reset by peer: socket write error
- если сокет был закрыт сервером (методом `close(selector)`)*
  - (read):  SocketException: Software caused connection abort: recv failed
  - (write): SocketException: Software caused connection abort: socket write error.
  
  * Исключения будут при описанных выше ситуациях.

________________________________________________________________________________________________________________________
 
```
 java.net.SocketException: Software caused connection abort: socket write error
 	at java.net.SocketOutputStream.socketWrite0(Native Method)
 	at java.net.SocketOutputStream.socketWrite(SocketOutputStream.java:111)
 	at java.net.SocketOutputStream.write(SocketOutputStream.java:155)
 	at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
 	at sun.nio.cs.StreamEncoder.implFlushBuffer(StreamEncoder.java:291)
 	at sun.nio.cs.StreamEncoder.implFlush(StreamEncoder.java:295)
 	at sun.nio.cs.StreamEncoder.flush(StreamEncoder.java:141)
 	at java.io.OutputStreamWriter.flush(OutputStreamWriter.java:229)
 	at nio_server.runners.ClientStarter.withWriter(Client.java:53)
 	at nio_server.runners.ClientStarter.main(Client.java:28)
 	at nio_server.Server.lambda$main$0(Server.java:53)
 	at java.lang.Thread.run(Thread.java:748)
 	
 java.net.SocketException: Software caused connection abort: recv failed
 	at java.net.SocketInputStream.socketRead0(Native Method)
 	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
 	at java.net.SocketInputStream.read(SocketInputStream.java:171)
 	at java.net.SocketInputStream.read(SocketInputStream.java:141)
 	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
 	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
 	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
 	at java.io.InputStreamReader.read(InputStreamReader.java:184)
 	at java.io.BufferedReader.fill(BufferedReader.java:161)
 	at java.io.BufferedReader.readLine(BufferedReader.java:324)
 	at java.io.BufferedReader.readLine(BufferedReader.java:389)
 	at nio_server.runners.ClientStarter.withWriter(Client.java:61)
 	at nio_server.runners.ClientStarter.main(Client.java:28)
 	at nio_server.Server.lambda$main$0(Server.java:53)
 	at java.lang.Thread.run(Thread.java:748)
```

И еще, при  `channel.close();`  происходит удаление регистрации у селектора и
`SelectionKey` становится невалидным (`isValid()`), и если на нем вызвать, например, метод `interestOps()`,
то -> `CancelledKeyException`. "Завершенный" `SelectionKey` удаляется при вызове `selector.select()`.

```
Exception in thread "Thread-1" java.nio.channels.CancelledKeyException
    at sun.nio.ch.SelectionKeyImpl.ensureValid(SelectionKeyImpl.java:73)
    at sun.nio.ch.SelectionKeyImpl.interestOps(SelectionKeyImpl.java:77)
    at nio_server.Server.lambda$close$1(Server.java:281)
    at java.lang.Iterable.forEach(Iterable.java:75)
    at java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1080)
    at nio_server.Server.close(Server.java:278)
    at nio_server.Server.run(Server.java:161)
    at java.lang.Thread.run(Thread.java:748)
```

________________________________________________________________________________________________________________________
                            



                                     
