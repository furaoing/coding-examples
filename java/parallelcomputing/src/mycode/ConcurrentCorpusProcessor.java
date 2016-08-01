package mycode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.berkeley.nlp.PCFGLA.ArrayParser;
import edu.berkeley.nlp.PCFGLA.Grammar;
import edu.berkeley.nlp.PCFGLA.Lexicon;
import edu.berkeley.nlp.PCFGLA.StateSetTreeList;
import edu.berkeley.nlp.syntax.StateSet;
import edu.berkeley.nlp.syntax.Tree;

public abstract class ConcurrentCorpusProcessor<T> implements Callable<T> {
	public static ExecutorService es;
	public static int nThread;

	protected Future<T>[] allFutures;

	/**
	 * Run all the processors and return the aggregated results. Ideally this
	 * method should be static, but to use generics we have to make it unstatic.
	 * 
	 * @param processors
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T runAllThreads(ConcurrentCorpusProcessor<T>[] processors) {
		int nThread = processors.length;
		allFutures = new Future[nThread];
		if (es == null)
			es = Executors.newFixedThreadPool(nThread);
		for (int i = 0; i < nThread; i++) {
			processors[i].allFutures = allFutures;
			allFutures[i] = es.submit(processors[i]);
		}
		T ret;
		try {
			ret = allFutures[0].get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	protected int id;
	protected int start, end;
	protected ArrayParser parser;
	protected StateSetTreeList trainStateSetTrees;
	protected T ret;

	public ConcurrentCorpusProcessor(int id, Grammar g, Lexicon l,
			StateSetTreeList trainStateSetTrees) {
		this.id = id;
		int nTrees = trainStateSetTrees.size();
		start = (int) Math.ceil((id * nTrees) / nThread);
		end = (int) Math.ceil(((id + 1) * nTrees) / nThread);
		parser = new ArrayParser(g, l);
		this.trainStateSetTrees = trainStateSetTrees;
	}

	public T call() throws Exception {
		// process assigned trees
		for (int i = start; i < end; i++) {
			Tree<StateSet> stateSetTree = trainStateSetTrees.get(i);
			processTree(stateSetTree, i);
		}

		// aggregate counts from sibling threads
		for (int i = 1; id % Math.pow(2, i) == 0; i++) {
			int toMergeId = id + (int) Math.pow(2, i - 1);
			if (toMergeId >= allFutures.length)
				break;
			T toMerge = allFutures[toMergeId].get();
			mergeResult(toMerge);
		}

		return ret;
	}

	protected abstract void processTree(Tree<StateSet> stateSetTree, int treeId);

	protected abstract void mergeResult(T toMerge);
}
