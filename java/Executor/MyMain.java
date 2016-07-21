import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by roy on 2016/5/5.
 */

public class MyMain {
    public static void main(String args[]) {
        int len = 100000000;
        int[] myData = new int[len];
        for(int i=0; i<len; i++){
            myData[i] = 2;
        }
        long a = System.currentTimeMillis();

        int result = 0;


        /*
        for (int j = 0; j < len; j++) {
            int tmp = 0;
            for (int i = 0; i < 100; i++){
                tmp += i;
            }
            result += myData[j] + tmp;
        }
        */


        int numThread = 8;
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(8);

        LinkedList<Future<Integer>> futureList = new LinkedList();
        System.out.println("Starting");
        long c = 0;
        long d = 0;
        for(int i=0; i<numThread; i++){
            //System.out.println("Start a new thread");
            futureList.add(cachedThreadPool.submit(new TestRunnable(myData, i, myData.length)));
        }
        for(Future<Integer> future: futureList){
            try{
                result += future.get();
                //System.out.println(result);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        System.out.println("Ending");
        System.out.println("Result: "+result);
        long b = System.currentTimeMillis();
        System.out.println("Time used: " + (b-a));

        System.out.println("One thread Cost: " + d/1000);
    }
}
