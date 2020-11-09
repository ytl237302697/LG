package stage4.HomeWork.Work5;

import java.io.*;
import java.net.Socket;
// 工具类
public class Util {
    // 发送文件
    public static void sendFile(Socket socket, String path){

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            // 1.从文件一读取内容
            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(socket.getOutputStream());
            // 缓冲区字节数组1024
            byte[] arr = new byte[1024];
            int res = 0;
            while ((res = bis.read(arr)) != -1) { // 循环每次读取1024个字节，当为-1时结束循环
                bos.write(arr, 0, res);
                bos.flush();
            }
            System.out.println("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 发送消息
    public static void sendMessage(ObjectOutputStream oos, Transmission tm) {

        try {

            oos.writeObject(tm);  // 传输
            oos.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 下载文件
    public static void downloadFile(Socket socket, String path){

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;


        try {
            // 1.从文件一读取内容
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(path));

            // 缓冲区字节数组1024
            byte[] arr = new byte[1024];
            int res = 0;
            while ((res = bis.read(arr)) > 0) { // 循环每次读取1024个字节
                bos.write(arr, 0, res);
                bos.flush();
                if(res < 1024){ //当res不大于1024时结束循环
                    break;
                }
            }
            System.out.println("文件下载成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

