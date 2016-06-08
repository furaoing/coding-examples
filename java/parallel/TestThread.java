package parallel;

/**
 * Created by OL on 5/9/2016.
 */
public class TestThread {
    public static void main(String args[]) {
        int[] a = {1, 2, 3, 4, 5};
        // int[] b = {1, 2, 3, 4, 6};
        RunnableADemo t1 = new RunnableADemo("Thread 1", 0);
        RunnableADemo t2 = new RunnableADemo("Thread 2", 1);


        t1.start(a);
        t2.start(a);


        t1.join();
        t2.join();

        for(int x: a){
            System.out.println(x);

        }
    }
}
