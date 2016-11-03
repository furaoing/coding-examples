import ApolloNLP.GetTransitionTable;
import ApolloNLP.GetVocabulary;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import ApolloNLP.GetAtomicSeg;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Created by rao on 16-11-3.
 */
public class TestAtomicSeg {
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
        String s = "庆祝中国成立五十周年";
        GetAtomicSeg.getAtomicSeg(s);
    }
}
