package stage4.HomeWork.Work5;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

public class ChatServerThread extends Thread {

    private Map<Socket, ObjectOutputStream> sockets;
    private Socket socket;

    public ChatServerThread(){

    }

    public ChatServerThread(Map<Socket, ObjectOutputStream> sockets, Socket socket) {
        this.sockets = sockets;
        this.socket = socket;
    }
    // 给所有客户端发送消息
    private static void sendMessage(Map<Socket, ObjectOutputStream> sockets, Socket socket, Transmission tm){

        for (Socket socket1 : sockets.keySet()) {
            // 判断如果是本身，就不返回信息
            if(socket == socket1){
                continue;
            }

            ObjectOutputStream oos = sockets.get(socket1);
            boolean flag = false;
            try {
                // 判断客户端是否还在线，false表示在线，异常表示不在线
                flag = socket1.getKeepAlive();
            }catch(Exception e){
                continue;
            }
            if (!flag) {// 在线则发送消息
                Util.sendMessage(oos, tm);
            }
        }
    }

    PrintStream ps = null;
    ObjectInputStream ois = null;

    @Override
    public void run() {
        // 消息包装类对象
        Transmission tm = new Transmission();

        try {
            // 获取客户端传输内容
            ois = new ObjectInputStream(socket.getInputStream());
            int socketName = sockets.size();
            tm.itemType = 0;
            tm.userName = "服务器";
            tm.content = socketName +  "号进入群聊！";
            sendMessage(sockets, socket, tm);
            while (true) {
                try {
                    tm = (Transmission) ois.readObject();  // 不做异常处理，会出现Connection reset异常
                }catch (SocketException e){
                    System.out.println(socket.getInetAddress() + "，断开连接");
                    tm.itemType = 0;
                    tm.content = socketName + "号退出群聊！";
                    tm.userName = "服务器";
                    sendMessage(sockets, socket, tm);
                    // 客户端下线，则移出集合
                    sockets.remove(socket);
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(tm.itemType == 0){ // 判断消息类型，普通聊天内容
                    System.out.println(tm.content);
                    tm.itemType = 0;
                    sendMessage(sockets, socket, tm);

                }else if(tm.itemType == 1){ // 客户端上传文件内容
                    String fileName = tm.fileName;
                    String path = "./聊天室/服务器/" + fileName;
                    Util.downloadFile(socket, path); // 保存到服务器
                    tm.itemType = 0;
                    tm.content = tm.userName + "分享文件： " + fileName;
                    tm.userName = "服务器";
                    sendMessage(sockets, socket, tm); // 将某客户端上传某文件提示给所有客户端
                }else if(tm.itemType == 2) { // 客户端下载文件
                    String fileName = tm.content;
                    String path = "./聊天室/服务器/" + fileName;
                    File file = new File(path);
                    // 判断要下载的文件是否存在
                    if (file.exists() && file.isFile()) {
                        tm.fileName = fileName;
                        tm.itemType = 1;
                        Util.sendMessage(sockets.get(socket), tm); // 先发送文件名称
                        Util.sendFile(socket, path); // 在发送文件内容
                    }else{
                        tm.itemType = 0;
                        tm.content = "文件不存在";
                        tm.userName = "服务器";
                        Util.sendMessage(sockets.get(socket), tm);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != socket){
                try {
                    sockets.remove(socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != ps){
                ps.close();
            }
            if(null != ois){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


