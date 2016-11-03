package ApolloNLP;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rao on 16-10-31.
 */

public class GetTransitionTable {
    private static Map<String, Integer> bigramCount = new HashMap<String, Integer>();
    public static Map<String, Float> bigramTransitionTable = new HashMap<String, Float>();

    private static Map<String, Integer> getBigramCount(String bigramDict){
        String[] bigramsDictRecord = bigramDict.split("\n");
        for (String bigramRecord: bigramsDictRecord){
            try {
                String bigram = bigramRecord.split("\t")[0];
                String pString = bigramRecord.split("\t")[1];
                Integer p = Integer.parseInt(pString);
                bigramCount.put(bigram, p);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bigramCount;
    }

    public static Map<String, Float> getTransitionTable(String bigramDict) {
        Map<String, Integer> bigramCount = getBigramCount(bigramDict);
        Map<String, Integer> wordCount = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry: bigramCount.entrySet()) {
            String bigram = entry.getKey();
            String word = bigram.split("@")[0];
            Integer count = entry.getValue();
            if (wordCount.containsKey(word)) {
                wordCount.put(word,
                        wordCount.get(word) + count);
            }
            else {
                wordCount.put(word, count);
            }
        }
        for (Map.Entry<String, Integer> entry: bigramCount.entrySet()) {
            String bigram = entry.getKey();
            String word = bigram.split("@")[0];
            Integer bigramOccurance = entry.getValue();
            Integer wordOccurance = wordCount.get(word);
            Float p = bigramOccurance.floatValue()/wordOccurance.floatValue();
            bigramTransitionTable.put(bigram, p);
        }
    return bigramTransitionTable;
    }
}
