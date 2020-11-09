package stage4.HomeWork.Work5;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient2 {

    public static void main(String[] args){

        Socket socket = null;

        try {
            // 创建客户端套接字
            socket = new Socket("127.0.0.2", 8888);
            String name = "用户2";
            String path = "./聊天室/" + name + "/";
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            // 发送信息线程
            ClientSendThread cst = new ClientSendThread(socket, name);
            // 接收信息线程
            ClientReceiveThread crt = new ClientReceiveThread(socket, path);
            // 线程启动
            cst.start();
            crt.start();
            // 主进程阻塞等待线程执行完毕
            cst.join();
            crt.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(null != socket) {
                // 关闭套接字
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
