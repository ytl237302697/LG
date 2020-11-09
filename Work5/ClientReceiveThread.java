package stage4.HomeWork.Work5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientReceiveThread extends Thread {
    private Socket socket;
    private String path;

    public ClientReceiveThread() {
    }

    public ClientReceiveThread(Socket socket, String path) {
        this.socket = socket;
        this.path = path;
    }

    BufferedReader bufferedReader = null;
    ObjectInputStream ois = null;
    Transmission tm = null;

    @Override
    public void run() {

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            while(true) {
                try {
                    // 客户端接收服务端消息
                    tm = (Transmission) ois.readObject();
                    if(tm.itemType == 0){ // 普通聊天内容
                        System.out.println(tm.userName + ": " + tm.content);
                    }else if(tm.itemType == 1){ // 文件内容
                        String fileName = tm.fileName;
                        Util.downloadFile(socket, path + fileName); // 保存到客户端对于目录
                    }
                }catch (SocketException e){
                    System.out.println("服务器断开连接");
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(null != bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


