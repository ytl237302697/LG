package stage4.HomeWork.Work4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) {
        ServerSocket serverSocket =null;
        Socket socket =null;
        try {
             serverSocket = new ServerSocket(9999);
            while (true){
                System.out.println("等待客户端连接...");
                socket = serverSocket.accept();
                System.out.println("客户端"+socket.getInetAddress()+"连接");
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=socket){
                System.out.println("客户端"+socket.getInetAddress()+"断开连接");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != serverSocket){
                try {
                    System.out.println("服务器关闭");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
