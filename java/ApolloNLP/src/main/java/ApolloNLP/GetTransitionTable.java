package ApolloNLP;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rao on 16-10-31.
 */

public class GetTransitionTable {
    public static Map<String, Float> transitionTable;

    public static Map<String, Float> getTransitionTable(String bigramDict){
        String[] bigramsDictRecord = bigramDict.split("\n");
        Map<String, Float> bigramTransitionTable = new HashMap<String, Float>();

        for (String bigramRecord: bigramsDictRecord){
            String bigram = bigramRecord.split(" ")[0];
            String pString = bigramRecord.split(" ")[1];
            Float p = Float.parseFloat(pString);
            bigramTransitionTable.put(bigram, p);
        }
        return bigramTransitionTable;
    }
}
