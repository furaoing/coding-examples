package ApolloNLP;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rao on 16-10-31.
 */
public class GetVocabulary {
    public static Set<String> vocabulary;

    public static Set<String> getVocabulary(Map<String, Float> transitionTable){
        Set<String> transitionEntries = transitionTable.keySet();
        Set<String> vocabularies = new HashSet<String>();
        for (String entry: transitionEntries){
            String lexicon =  entry.split("@")[0];
            vocabularies.add(lexicon);
        }
        return vocabularies;
    }
}
