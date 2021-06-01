package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;
@SuppressWarnings("rawtypes")
public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	RedBlackTree<T, V> tree;
	int treeSize;

	public TreeMap() {
		tree = new RedBlackTree<>();
		treeSize = 0;
	}
	ArrayList<Node> treeNodes = new ArrayList<Node>();
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
		if (tree.getRoot() == null)
            return null;
 
 
        Stack<Node> s = new Stack<Node>();
        INode curr =  tree.getRoot();
 
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

	@SuppressWarnings("unchecked")
	@Override
	public T ceilingKey(Comparable key) {
	    Node curr = (Node) ceilingEntry(key);
	    if (curr == null) { 
	    	return null;
	    }else {
	    	//Comparable k = curr.getKey();
	    	return (T) curr.getKey();
	    }
	}

	@Override
	public void clear() {
		tree = new RedBlackTree<T, V>();
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
		if (tree.getRoot() == null)
            return false;
 
        Stack<Node> s = new Stack<Node>();
        INode curr =  tree.getRoot();
 
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
	public T firstKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		Node<T, V> temp = (Node<T, V>) tree.getRoot();
		Node<T, V> Parent = null;
		while (temp != null) {
			if (temp.getKey().compareTo(key) > 0) {
				if ((temp.getLeftChild() == null) && (Parent != null)) {
					return (Entry<T, V>) Parent;
				}
				temp = (Node<T, V>) temp.getLeftChild();
			} else if (temp.getKey().compareTo(key) == 0) {
				return (Entry<T, V>) temp;
			} else {
				if (temp.getRightChild() == null) {
					return (Entry<T, V>) temp;
				}
				Parent = temp;
				temp = (Node<T, V>) temp.getRightChild();
			}
		}
		return null;
	}

	@Override
	public T floorKey(T key) {
		Node<T, V> temp = (Node<T, V>) floorEntry(key);
		if (temp == null)
			return null;
		return (T) temp.getKey();
	}

	@Override
	public V get(T key) {
		return tree.search((T) key);
	}


	@Override
	public ArrayList headMap(T toKey) {
		ArrayList<Node<T,V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, false, (Node<T, V>) tree.getRoot(), Nodes);
		return Nodes;
	}

	@Override
	public ArrayList headMap(T toKey, boolean inclusive) {
		ArrayList<Node<T,V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, inclusive, (Node<T, V>) tree.getRoot(), Nodes);
		return Nodes;
	}

	private void headMapHelper(T toKey, boolean inclusive, Node<T, V> temp, ArrayList<Node<T, V>> nodes) {
		if (temp == null)
			return;
		headMapHelper(toKey, inclusive, (Node<T, V>) temp.getLeftChild(), nodes);
		if (temp.getKey().compareTo(toKey) < 0) {
			nodes.add(temp);
			headMapHelper(toKey, inclusive, (Node<T, V>) temp.getRightChild(), nodes);
		} else if (temp.getKey().compareTo(toKey) == 0) {
			if (inclusive)
				nodes.add(temp);
		}
	}

	@Override
	public Set<T> keySet() {
		Set<T> set = new HashSet<T>();
		keySetHelper((Node<T, V>) tree.getRoot(), set);
		return set;
	}

	private void keySetHelper(Node<T, V> root, Set<T> set) {
		if (root == null)
			return;
		set.add((T) root.getKey());
		keySetHelper((Node<T, V>) root.getLeftChild(),set);
		keySetHelper((Node<T, V>) root.getRightChild(),set);
	}

	@Override
	public Entry<T, V> lastEntry() {
		Node<T, V> temp = (Node<T, V>) tree.getRoot();
		while (temp!=null) {
			if (temp.getRightChild()==null)
				return (Entry<T, V>) temp;
		}
		return null;
	}

	@Override
	public T lastKey() {
		Node<T, V> temp = (Node<T, V>) lastEntry();
		if (temp == null)
			return null;
		return (T) temp.getKey();
	}

	@Override
	public Entry pollFirstEntry() {
		return null ;
	}

	@Override
	public Entry pollLastEntry() {
		if (size() == 0){
			return null;
		}
		Node<T , V> temp = (Node<T, V>) lastEntry();
		tree.delete(temp.getKey());
		return (Entry) temp.getValue();
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
	public boolean remove(T key) {
		return tree.delete(key);
	}

	@Override
	public int size() {
		return treeSize;
	}

	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}

}