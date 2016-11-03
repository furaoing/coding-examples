package ApolloNLP;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by rao on 16-11-3.
 */

public class GetShortestPath {
    private static Float minTransitionProb = 0.001f;
    public static ArrayList<String> getShortestPath(Set<ArrayList<String>> atomicSegs,
                                                    Map<String, Float> transitionTable) {
        Float maxP = 0f;
        ArrayList<String> bestSeg = null;
        for (ArrayList<String> seg: atomicSegs) {
            Float tmpP = 1f;
            for ( int i = 0; i < seg.size() - 1; i++) {
                String bigram = seg.get(i) + "@" + seg.get(i+1);
                if (transitionTable.get(bigram) != null) {
                    tmpP = tmpP * transitionTable.get(bigram);
                }
                else {
                    tmpP = tmpP * minTransitionProb;
                }
            }
            if (maxP < tmpP) {
                maxP = tmpP;
                bestSeg = seg;
            }
        }
        System.out.println(bestSeg);
        return bestSeg;
    }
}
