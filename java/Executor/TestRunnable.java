import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by OL on 5/9/2016.
 */

public class TestRunnable implements Callable<Integer> {
    int[] myData;
    int threadID;
    int lenMyData;
    TestRunnable(int[] myData, int threadID, int lenMyData){
        this.threadID = threadID;
        this.myData = myData;
        this.lenMyData = lenMyData;
    }

    public Integer call() throws Exception {
        int result = 0;

        for(int i=0; (8*i+threadID)<lenMyData; i++){
            int tmp = 0;
            for (int k = 0; k < 100; k++){
                tmp += k;
            }

            result += myData[8*i+threadID] + tmp;
        }
        return result;
    }
}
