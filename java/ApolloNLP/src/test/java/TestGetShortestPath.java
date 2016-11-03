import ApolloNLP.GetAtomicSeg;
import ApolloNLP.GetTransitionTable;
import ApolloNLP.GetVocabulary;
import ApolloNLP.GetShortestPath;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by rao on 16-11-3.
 */

public class TestGetShortestPath {
    public static void main(String args[]) {
        String fileName = "/home/rao/workspace/coding-examples/java/ApolloNLP/data/CoreNatureDictionary.ngram.mini.txt";
        String dictString;
        try {
            dictString = Files.asCharSource(new File(fileName), Charsets.UTF_8).read();
        } catch (Exception e) {
            dictString = "";
            e.printStackTrace();
        }
        Map result = GetTransitionTable.getTransitionTable(dictString);
        Set<String> vocabulary = GetVocabulary.getVocabulary(result);
        String s = "我买了一件衣服";
        Set<ArrayList<String>> atomicSegs = GetAtomicSeg.getAtomicSeg(s);
        GetShortestPath.getShortestPath(atomicSegs, result);
    }
}
