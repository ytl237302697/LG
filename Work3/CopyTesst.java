package stage4.HomeWork.Work3;

import java.io.*;
import java.util.Scanner;

public class CopyTesst implements Runnable{
    /**
     * 将目标目录复制为指定目录(也可以用于复制文件)
     * @param src 源路径
     * @param dest 目标路径
     * @throws IOException
     */
    public static void copy(File src,File dest) throws IOException {
        if (!src.exists()){     // 检查源路径是否存在
            System.out.println("源目录不存在");
        }
        else if (src.isFile()){     // 如果源路径是一个文件
            if (dest.isDirectory()) {
                // 不能将文件复制为一个目录
                System.out.println("目标路径不是一个文件");
            }else{
                // 复制文件
                FileInputStream fis = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(dest);
                byte [] arr = new byte[1024];
                int len;
                while ((len = fis.read(arr)) !=-1){
                    fos.write(arr,0,len);
                }

                fis.close();
                fos.close();
            }
        }else {     // 如果源路径是一个目录
            if (dest.isFile()){
                // 不能将目录复制为一个文件
                System.out.println("目标路径不是一个目录");
            }else {
                // 先检查目标是否存在, 不存在则创建
                if (!dest.exists()){
                    dest.mkdirs();
                }
                // 如果目标路径是一个目录, 递归调用本方法进行复制
                // 获取源目录的子文件/目录
                String []  files = src.list();
                // 遍历源目录进行复制
                for (String file : files){
                    copy(new File(src,file),new File(dest,file));
                }

            }

        }


    }

    @Override
    public void run() {
        try {
            main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public static void main(String[] args) throws IOException {
         Scanner sc = new Scanner(System.in);
         System.out.println("请输入源路径:");
         String src = sc.nextLine();
         System.out.println("请输入目标路径:");
         String dest = sc.nextLine();
         copy(new File(src),new File(dest));
         System.out.println("复制完毕");
      }
}

