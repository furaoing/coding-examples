/**
 * Created by rao on 16-10-31.
 */
import ApolloNLP.GetTransitionTable;
import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import java.io.File;
import java.util.Map;

public class TestGetTransitionTable {
    public static void main(String args[]){
        String fileName = "/home/rao/workspace/coding-examples/java/ApolloNLP/data/CoreNatureDictionary.ngram.txt";
        String dictString;
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            dictString = Files.asCharSource(new File(fileName), Charsets.UTF_8).read();
            stopwatch.stop();
            System.out.println(stopwatch);
        } catch (Exception e){
            dictString = "";
            e.printStackTrace();
        }
        Map result = GetTransitionTable.getTransitionTable(dictString);
        String a = "a";
    }
}
