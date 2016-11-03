package ApolloNLP;

import java.util.*;

import net.bigboo.apollo.adt.tree.generaltree.*;

/**
 * Created by rao on 16-11-1.
 */

public class GetAtomicSeg {
    private static int searchDepth = 4;
    public static Set<ArrayList<String>> getAtomicSeg(String sen) {
        Set<ArrayList<String>> atomicSegs = new HashSet<ArrayList<String>>();
        List<Tree<PartitionPair>> trees = new LinkedList<Tree<PartitionPair>>();
        Set<PartitionPair> rootPartitionPairs = getPartitions(sen);
        for (PartitionPair partitionPair: rootPartitionPairs) {
            Node<PartitionPair> rootNode = new Node<PartitionPair>(partitionPair);
            Tree<PartitionPair> tree = new Tree<PartitionPair>(rootNode);
            trees.add(tree);
        }
        for (Tree<PartitionPair> tree: trees) {
            while(!isComplete(tree)) {
                List<Node<PartitionPair>> leaves = tree.getLeaves();
                for (Node<PartitionPair> leaf: leaves) {
                    String res = leaf.getData().restText;
                    Set<PartitionPair> newPairs = getPartitions(res);
                    for (PartitionPair newPair: newPairs) {
                        leaf.addChild(new Node<PartitionPair>(newPair));
                    }
                }
            }
            ArrayList<ArrayList<Node<PartitionPair>>> nodePaths = tree.getPathsFromRootToAnyLeaf();
            for (ArrayList<Node<PartitionPair>> nodePath: nodePaths) {
                ArrayList<String> segs = new ArrayList<String>();
                for (Node<PartitionPair> node: nodePath) {
                    segs.add(node.getData().token);
                }
                atomicSegs.add(segs);
            }
        }
        return atomicSegs;
    }

    private static boolean isComplete(Tree<PartitionPair> tree) {
        List<Node<PartitionPair>> leaves = tree.getLeaves();
        Boolean isComplete = true;
        for (Node<PartitionPair> leaf: leaves) {
            isComplete = isComplete && (leaf.getData().restText.equals(""));
        }
        return isComplete;
    }

    private static Set<PartitionPair> getPartitions(String sen){
        Set<PartitionPair> partitions = new HashSet<PartitionPair>();
        for(int tokenLen = 1; tokenLen < searchDepth + 1; tokenLen++){
            if (sen.length() >= tokenLen) {
                PartitionPair partition = separate(tokenLen, sen);
                if(partition!=null){
                    partitions.add(partition);
                }
            }
        }
        return partitions;
    }

    private static boolean inVocabulary(String token){
        return GetVocabulary.vocabulary.contains(token);
    }

    private static PartitionPair separate(int tokenLen, String text){
        String token = text.substring(0, tokenLen);
        if(inVocabulary(token)){
            return new PartitionPair(token, text.substring(tokenLen));
        }
        else {
            return null;
        }
    }
}
