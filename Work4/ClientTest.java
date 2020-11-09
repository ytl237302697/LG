package stage4.HomeWork.Work4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {

     public static void main(String[] args) {

         User user = null;
         UserMessage tum = null;
         ObjectOutputStream oos = null;
         ObjectInputStream ois = null;
         Socket socket = null;
         try {
             System.out.println("连接服务器");
             socket = new Socket("127.0.0.1",9999);
             Scanner sc =new Scanner(System.in);
             System.out.println("请输入账号:");
             String username = sc.next();
             System.out.println("请输入密码:");
             String password = sc.next();

             //创建用户对象
             user = new User(username,password);
             //创建用户信息对象
             tum = new UserMessage(user);

             //实现客户端与服务器的通信
             oos = new ObjectOutputStream(socket.getOutputStream());
             oos.writeObject(tum);

             //获取放服务器返回的数据
             ois = new ObjectInputStream(socket.getInputStream());
             UserMessage tumResult = (UserMessage)ois.readObject();

             //判断是否登录成功
             String result = tumResult.getType();
             if ("success".equals(result)){
                 System.out.println("登录成功");
             }
             else {
                 System.out.println("登录失败");
             }
         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         } finally {
             if (null != ois) {
                 try {
                     ois.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             if (null != oos){
                 try {
                     oos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             if (null != socket){
                 try {
                     socket.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }

     }
}
