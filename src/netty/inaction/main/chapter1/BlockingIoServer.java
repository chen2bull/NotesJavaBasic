package netty.inaction.main.chapter1;

import java.io.*;
import java.net.*;

/**
 * 基于阻塞IO的服务器
 * Created by Administrator on 2016/10/2.
 */
public class BlockingIoServer {
    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);              //#1 Bind server to port
        try {
            while (true) {
                final Socket clientSocket = socket.accept();             //#2 Block until new client connection is accepted
                System.out.println("Accepted connection from " +
                        clientSocket);
                new Thread(new Runnable() {                              //#3 Create new thread to handle client connection
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                            while (true) {                                //#4 Read data from client and write it back
                                writer.println(reader.readLine());
                                writer.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }
                    }
                }).start();                                              //#5 Start thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
