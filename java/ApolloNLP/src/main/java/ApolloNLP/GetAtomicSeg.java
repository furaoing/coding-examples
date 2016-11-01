package ApolloNLP;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.bigboo.apollo.adt.tree.generaltree.*;

/**
 * Created by rao on 16-11-1.
 */
public class GetAtomicSeg {
    public static int searchDepth = 4;
    public static Set<String[]> getAtomicSeg(String sen){
        List<Tree<PartitionPair>> trees = new LinkedList<Tree<PartitionPair>>();
        Set<PartitionPair> rootPartitionPairs = getPartitions(sen);
        for (PartitionPair partitionPair: rootPartitionPairs) {
            Node<PartitionPair> rootNode = new Node<PartitionPair>(partitionPair);
            Tree<PartitionPair> tree = new Tree<PartitionPair>(rootNode);
            trees.add(tree);
        }
        for (Tree<PartitionPair> tree: trees) {
            Boolean isComplete = false;
            while(!isComplete){

            }
        }
    }

    static Set<PartitionPair> getPartitions(String sen){
        Set<PartitionPair> partitions = new HashSet<PartitionPair>();
        for(int tokenLen = 1; tokenLen < searchDepth + 1; tokenLen++){
            PartitionPair partition = separate(tokenLen, sen);
            if(partition!=null){
                partitions.add(partition);
            }
        }
        return partitions;
    }

    static boolean inVocabulary(String token){
        return GetVocabulary.vocabulary.contains(token);
    }

    static PartitionPair separate(int tokenLen, String text){
        String token = text.substring(0, tokenLen);
        if(inVocabulary(token)){
            return new PartitionPair(token, text.substring(tokenLen));
        }
        else {
            return null;
        }
    }
}
