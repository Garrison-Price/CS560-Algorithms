import java.util.Arrays;
import java.util.BitSet;


public class HuffmanTree<E> {
	private Node<E>[] queue;		//Queue used for building tree using greedy method
	private int wordsInDictionary;	//Number of unique data "words" in the data sequence
	private int nodesInQueue;
	private E[] dataSequence;		//Sequence of data to build huffman tree for
	
	public HuffmanTree(E[] dataSequence) {
		this.dataSequence = dataSequence;
		wordsInDictionary = 0;
		BuildTree();
	}
	
	private void BuildTree() {
		FillInitialQueue();
		SortQueue();
		while(nodesInQueue > 1) {
			Node<E> newRootNode = new Node<E>(queue[0].count+queue[1].count,null);
			newRootNode.left = queue[0];
			newRootNode.right = queue[1];
			
			//Shift Queue
			for(int i = 2; i < nodesInQueue; i++) {
				queue[i-2] = queue[i];
			}
			
			//Only decrement once since we are removing two nodes but adding in one new one.
			nodesInQueue--;
			
			//Slap the new root in at the end of the tree
			queue[nodesInQueue-1] = newRootNode;
			SortQueue();
		}
	}
	
	private void FillInitialQueue() {
		queue = new Node[dataSequence.length];
		for(int dataSequenceIndex = 0; dataSequenceIndex < dataSequence.length;dataSequenceIndex++) {
			int matchingDataIndex = CheckUniqueInQueue(dataSequence[dataSequenceIndex]);
			if(matchingDataIndex == -1)
				queue[wordsInDictionary++] = new Node<E>(1,dataSequence[dataSequenceIndex]);
			else
				queue[matchingDataIndex].count++;
		}
		nodesInQueue = wordsInDictionary;
		queue = Arrays.copyOf(queue, nodesInQueue);
	}
	
	//Sort the queue using insertion sort because most of the time the queue is nearly sorted and we need stability.
	private void SortQueue() {
		for(int i = 0; i < nodesInQueue; i++)
			for (int k = i; k > 0 && queue[k].count < queue[k-1].count; k--) {
				Node<E> temp = queue[k];	//Swap
				queue[k] = queue[k-1];
				queue[k-1] = temp;
			}
	}
	
	//Returns the index of the found non unique item in the queue or -1 if it is unique.
	private int CheckUniqueInQueue(E data) {
		for(int queueIndex = 0; queueIndex < wordsInDictionary; queueIndex++) {
			if(((Comparable<E>)queue[queueIndex].leafValue).compareTo(data) == 0)
				return queueIndex;
		}
		return -1;
	}
	
	
	//NOT YET IMPLEMENTED
	public BitSet encode(E[] dataSequence) {
		BitSet encodedData = new BitSet();
		for(int dataSequenceIndex = 0; dataSequenceIndex < dataSequence.length;dataSequenceIndex++) {
			Node<E> currentNode = queue[0]; //Root of tree
			//Depth first search
		}
		return null;
	}
	
	public E[] decode(BitSet dataBits) {
		E[] dataSequence = (E[]) new Object[dataBits.length()];
		int sequenceLength = 0;
		for(int bitIndex = 0; bitIndex < dataBits.length(); bitIndex++) {
			Node<E> currentNode = queue[0]; //Root of tree
			while(currentNode != null && currentNode.leafValue == null) {
				if(dataBits.get(bitIndex))
					currentNode = currentNode.right;
				else
					currentNode = currentNode.left;
				bitIndex++;
			}
			bitIndex--;
			if(currentNode == null)
				throw new RuntimeException("Invalid Bit Sequence.");
			dataSequence[sequenceLength++] = currentNode.leafValue;
		}
		
		return Arrays.copyOf(dataSequence, sequenceLength);
	}
	
	protected class Node<T> {
		public int count;
		public T leafValue;			//If this is null, this is not a leaf node
		public Node<T> left,right;
		
		public Node(int count, T leafValue) {
			this.count = count;
			this.leafValue = leafValue;
			left = right = null;
		}
		
		public String toString() {
			return "{"+count+","+leafValue+","+left+","+right+"}";
		}
	}
	
	public static void main(String[] args) {
		String[] fullString = {"O","O","O","O","O","O","O","O","O","O","N","N","N","N","T","T","T","P","S"};
		HuffmanTree<String> ht = new HuffmanTree<String>(fullString);
		BitSet bs = new BitSet(16);
		bs.set(1);
		bs.set(3);
		bs.set(5);
		bs.set(8);
		bs.set(9);
		bs.set(13);
		bs.set(15);
		
		System.out.println(bs.toString()+" = "+Arrays.toString(ht.decode(bs)));
	}
}
