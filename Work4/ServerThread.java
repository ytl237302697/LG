package stage4.HomeWork.Work4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;

    public ServerThread(){
    }

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    @Override
    public void run() {

        //服务器与客户端通信
        //获取客户端提交的信息
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            UserMessage tum = (UserMessage)ois.readObject();
            if ("admin".equals(tum.getUser().getUsername()) && "123456".equals(tum.getUser().getPassword())){
                tum.setType("success");
            }
            else {
                tum.setType("fail");
            }

            //返回客户端信息

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(tum);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null !=oos){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
