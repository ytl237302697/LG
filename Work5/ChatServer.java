package stage4.HomeWork.Work5;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    public static void main(String[] args){

        Map<Socket, ObjectOutputStream> map = new HashMap<>();
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            // 1.创建服务器套接字
            serverSocket = new ServerSocket(8888);
            File file = new File("./聊天室/服务器");
            if(!file.exists()){
                file.mkdirs();
            }
            // 死循环等待客户端接入，表示服务器永不断开
            ObjectOutputStream oos = null;
            while(true){
                System.out.println("等待客户端连接-----");
                socket = serverSocket.accept();  // 等待客户端接入
                oos = new ObjectOutputStream(socket.getOutputStream());
                map.put(socket, oos);  // 添加到客户端套接字数组中
                System.out.println(socket.getInetAddress() + "已上线");
                // 接入一个客户端则创建一个线程进行服务器和客户端间的同学
                ChatServerThread cst = new ChatServerThread(map, socket);
                cst.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

