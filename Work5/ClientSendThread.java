package stage4.HomeWork.Work5;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSendThread extends Thread{

    private Socket socket;
    private String name;

    public ClientSendThread() {
    }

    public ClientSendThread(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {

        ObjectOutputStream oos = null;
        try {
            // 客户端发送消息给服务端
            oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            Transmission tm = new Transmission();
            while (true) {
                // 实现通信 输入bye退出客户端
                System.out.println("请输入聊天内容 || 上传文件 || 下载文件:");
                String message = sc.next();
                tm.userName = name;
                // 传输文件
                if("上传文件".equalsIgnoreCase(message)){
                    System.out.println("请输入文件全路径：");
                    String path = sc.next();
                    File file = new File(path);
                    if(!file.exists()){ // 判断文件是否存在
                        System.out.println("文件路径不存在");
                        continue;
                    }else {
                        tm.fileName = file.getName();  // 文件名
                        tm.itemType = 1;  // 发送类型 0 普通内容， 1文件  2下载文件
                        Util.sendMessage(oos, tm);  // 先发送文件名
                        Util.sendFile(socket, path);  // 再发送文件内容
                    }
                    // 下载文件
                }else if("下载文件".equalsIgnoreCase(message)){
                    System.out.println("文件列表有：");
                    File file = new File("./聊天室/服务器/");
                    File [] files =file.listFiles();
                    for (File file1 :files){
                        System.out.println(file1.getName());
                    }
                    System.out.println("请输入要下载的文件");
                    String fileName = sc.next();

                    tm.content = fileName;
                    tm.itemType = 2;
                    Util.sendMessage(oos, tm);

                    // 文字内容
                }else {
                    tm.content = message;
                    tm.itemType = 0;
                    Util.sendMessage(oos, tm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}

