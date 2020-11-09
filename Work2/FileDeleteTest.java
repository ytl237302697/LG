package stage4.HomeWork.Work2;
import java.io.File;
import java.util.Scanner;


public class FileDeleteTest {

     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         System.out.println("请输入要删除文件夹的路径");
         String path = sc.next();
         File file = new File(path);
         delete(file);
      }
      public static void delete (File file){
         if (file.isFile()){
             file.delete();
             System.out.println("删除了"+file.getName());
         }
         else {
             File [] files = file.listFiles();
             for (File fes : files){
                 delete(fes);
             }
             file.delete();
             System.out.println("删除了"+file.getName());
         }
      }
}