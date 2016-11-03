package ApolloNLP;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rao on 16-10-31.
 */
public class GetVocabulary {
    public static Set<String> vocabulary = new HashSet<String>();

    public static Set<String> getVocabulary(Map<String, Float> transitionTable){
        Set<String> transitionEntries = transitionTable.keySet();
        for (String entry: transitionEntries){
            String lexicon =  entry.split("@")[0];
            vocabulary.add(lexicon);
        }
        return vocabulary;
    }
}
