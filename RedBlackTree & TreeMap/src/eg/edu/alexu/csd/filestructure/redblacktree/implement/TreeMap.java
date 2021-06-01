package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

@SuppressWarnings("rawtypes")
public class TreeMap<T extends Comparable<T>, V> implements ITreeMap{
	RedBlackTree< T, V> redBlackTree = new RedBlackTree<T, V>();
	ArrayList<Node> tree = new ArrayList<Node>();
	int upper_bound(int arr[], int N, int X)
	{
	    int mid;
	    int low = 0;
	    int high = N;
	    while (low < high) {
	        mid = low + (high - low) / 2;
	 
	        if (X >= arr[mid]) {
	        	low = mid;
	        }else {
	        	 high = mid;
	        }
	    }
	   
	    // if X is greater than arr[n-1]
	    if(low < N && arr[low] <= X) {
	       low++;
	    }
	       
	    // Return the lower_bound index
	    return low;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Entry ceilingEntry(Comparable key) {
		if (redBlackTree.getRoot() == null)
            return null;
 
 
        Stack<Node> s = new Stack<Node>();
        INode curr =  redBlackTree.getRoot();
 
        // traverse the tree
        while (curr != null || s.size() > 0)
        {
            while (curr !=  null)
            {
                s.push((Node) curr);
                curr =  curr.getLeftChild();
            }
 
            curr = s.pop();
 
            if (key.compareTo(curr.getKey()) >= 0) {
            	return (Entry) curr;
            }

            curr = curr.getRightChild();
        }
		return null;
	}

	@Override
	public Comparable ceilingKey(Comparable key) {
	    Node curr = (Node) ceilingEntry(key);
	    if (curr == null) { 
	    	return null;
	    }else {
	    	return curr.getKey();
	    }
	}

	@Override
	public void clear() {
		redBlackTree = new RedBlackTree<T, V>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsKey(Comparable key) {
		Node curr = (Node) ceilingEntry(key);
	    if (curr == null) { 
	    	return false;
	    }else if (key.compareTo(curr.getKey()) == 0) {
	    	return true;
	    }else {
	    	return false;
		}
	}

	@Override
	public boolean containsValue(Object value) {
		if (redBlackTree.getRoot() == null)
            return false;
 
        Stack<Node> s = new Stack<Node>();
        INode curr =  redBlackTree.getRoot();
 
        // traverse the tree
        while (curr != null || s.size() > 0)
        {
            while (curr !=  null)
            {
                s.push((Node) curr);
                curr =  curr.getLeftChild();
            }
 
            curr = s.pop();
 
            if (curr.getValue().equals(value)) {
            	return true;
            }

            curr = curr.getRightChild();
        }
		return false;
	}

	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry firstEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable firstKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry floorEntry(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable floorKey(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList headMap(Comparable toKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList headMap(Comparable toKey, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry lastEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable lastKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry pollFirstEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry pollLastEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Comparable key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAll(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(Comparable key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}

}
