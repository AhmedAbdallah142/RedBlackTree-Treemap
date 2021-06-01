package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	RedBlackTree<T, V> tree;
	int treeSize;

	public TreeMap() {
		tree = new RedBlackTree<>();
		treeSize = 0;
	}

	@Override
	public Entry ceilingEntry(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable ceilingKey(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Comparable key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Entry floorEntry(Comparable key) {
		Node temp = (Node) tree.getRoot();
		Node Parent = null;
		while (temp != null) {
			if (temp.getKey().compareTo(key) > 0) {
				if ((temp.getLeftChild() == null) && (Parent != null)) {
					return (Entry) Parent;
				}
				temp = (Node) temp.getLeftChild();
			} else if (temp.getKey().compareTo(key) == 0) {
				return (Entry) temp;
			} else {
				if (temp.getRightChild() == null) {
					return (Entry) temp;
				}
				Parent = temp;
				temp = (Node) temp.getRightChild();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable floorKey(Comparable key) {
		Node temp = (Node) floorEntry(key);
		if (temp == null)
			return null;
		return temp.getKey();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object get(Comparable key) {
		return tree.search((T) key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList headMap(Comparable toKey) {
		ArrayList<Node> Nodes = new ArrayList<>();
		headMapHelper(toKey, false, (Node) tree.getRoot(), Nodes);
		return Nodes;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList headMap(Comparable toKey, boolean inclusive) {
		ArrayList<Node> Nodes = new ArrayList<>();
		headMapHelper(toKey, inclusive, (Node) tree.getRoot(), Nodes);
		return Nodes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void headMapHelper(Comparable toKey, boolean inclusive, Node temp, ArrayList<Node> Nodes) {
		if (temp == null)
			return;
		headMapHelper(toKey, inclusive, (Node) temp.getLeftChild(), Nodes);
		if (temp.getKey().compareTo(toKey) < 0) {
			Nodes.add(temp);
			headMapHelper(toKey, inclusive, (Node) temp.getRightChild(), Nodes);
		} else if (temp.getKey().compareTo(toKey) == 0) {
			if (inclusive)
				Nodes.add(temp);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set keySet() {
		Set<T> set = new HashSet<T>();
		keySetHelper((Node) tree.getRoot(), set);
		return set;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void keySetHelper(Node root, Set<T> set) {
		if (root == null)
			return;
		set.add((T) root.getKey());
		keySetHelper((Node) root.getLeftChild(),set);
		keySetHelper((Node) root.getRightChild(),set);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Entry lastEntry() {
		Node temp = (Node) tree.getRoot();
		while (temp!=null) {
			if (temp.getRightChild()==null)
				return (Entry) temp;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable lastKey() {
		Node temp = (Node) lastEntry();
		if (temp == null)
			return null;
		return temp.getKey();
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
