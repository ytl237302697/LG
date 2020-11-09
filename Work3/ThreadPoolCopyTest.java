package stage4.HomeWork.Work3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolCopyTest {

     public static void main(String[] args) {

         //创建一个线程池
         ExecutorService executorService = Executors.newFixedThreadPool(3);

         //向线程池中布置任务
         executorService.submit(new CopyTesst());

         //关闭线程池
         executorService.shutdown();
      }
}
