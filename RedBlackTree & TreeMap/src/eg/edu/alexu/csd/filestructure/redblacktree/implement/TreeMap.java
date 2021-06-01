package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.*;
import java.util.Map.Entry;

import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	RedBlackTree<T, V> tree;
	int treeSize;

	public TreeMap() {
		tree = new RedBlackTree<>();
		treeSize = 0;
	}

	ArrayList<Node<T, V>> treeNodes = new ArrayList<Node<T, V>>();

	int lower_bound(T key) {
		int mid;
		int low = 0;
		int high = treeNodes.size();
		while (low < high) {
			mid = low + (high - low) / 2;

			if (key.compareTo(treeNodes.get(mid).getKey()) <= 0) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		if (low < treeNodes.size() && (treeNodes.get(low).getKey().compareTo(key) < 0)) {
			low++;
		}

		return low;
	}

	@Override
	public Entry<T, V> ceilingEntry(T key) {
		if (tree.isEmpty())
			return null;
		Stack<Node<T, V>> s = new Stack<Node<T, V>>();
		Node<T, V> curr = (Node<T, V>) tree.getRoot();

		// traverse the tree
		while (!curr.isNull() || s.size() > 0) {
			while (!curr.isNull()) {
				s.push((Node<T, V>) curr);
				curr = (Node<T, V>) curr.getLeftChild();
			}

			curr = s.pop();

			if (curr.getKey().compareTo(key) >= 0) {
				return (Entry<T, V>) curr;
			}
			curr = (Node<T, V>) curr.getRightChild();
		}
		return null;

		/*
		 * if (key.compareTo(treeNodes.get(treeSize-1).getKey()) > 0) { return null;
		 * }else { int temp = lower_bound(key); if
		 * (key.compareTo(treeNodes.get(temp).getKey()) == 0) { return (Entry)
		 * treeNodes.get(temp); }else if (temp == treeSize) { return null; }else {
		 * return (Entry) treeNodes.get(temp+1); } }
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public T ceilingKey(T key) {
		Node<T, V> curr = (Node<T, V>) ceilingEntry(key);
		if (curr.isNull()) {
			return null;
		} else {
			// Comparable k = curr.getKey();
			return (T) curr.getKey();
		}
	}

	@Override
	public void clear() {
		treeSize = 0;
		tree = new RedBlackTree<T, V>();
	}

	@Override
	public boolean containsKey(T key) {
		/*
		 * Node<T,V> curr = (Node<T,V>) ceilingEntry(key); if (curr == null) { return
		 * false; }else if (key.compareTo(curr.getKey()) == 0) { return true; }else {
		 * return false; }
		 */
		return tree.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if (tree.getRoot().isNull())
			return false;

		Stack<Node<T, V>> s = new Stack<Node<T, V>>();
		Node<T, V> curr = (Node<T, V>) tree.getRoot();

		// traverse the tree
		while (!curr.isNull() || s.size() > 0) {
			while (!curr.isNull()) {
				s.push((Node<T, V>) curr);
				curr = (Node<T, V>) curr.getLeftChild();
			}

			curr = s.pop();

			if (curr.getValue().equals(value)) {
				return true;
			}

			curr = (Node<T, V>) curr.getRightChild();
		}
		return false;
	}

	@Override
	public Set entrySet() {
		Set<Node<T, V>> set = new HashSet<Node<T, V>>();
		if (tree.isEmpty())
			return null;
		Stack<Node<T, V>> s = new Stack<Node<T, V>>();
		Node<T, V> curr = (Node<T, V>) tree.getRoot();

		// traverse the tree
		while (!curr.isNull() || s.size() > 0) {
			while (!curr.isNull()) {
				s.push((Node<T, V>) curr);
				curr = (Node<T, V>) curr.getLeftChild();
			}

			curr = s.pop();
			if (!curr.isNull()) {
				set.add(curr);
			}

			curr = (Node<T, V>) curr.getRightChild();
		}

		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry firstEntry() {
		if (tree.isEmpty()) {
			return null;
		} else {
			Node<T, V> root = (Node<T, V>) tree.getRoot();
			while (!root.isNull()) {
				root = (Node<T, V>) root.getLeftChild();
			}
			return (Entry) root;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T firstKey() {
		if (tree.isEmpty()) {
			return null;
		} else {
			Node<T, V> last = (Node<T, V>) firstEntry();
			return (T) last.getKey();
		}
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		Node<T, V> temp = (Node<T, V>) tree.getRoot();
		Node<T, V> Parent = null;
		while (!temp.isNull()) {
			if (temp.getKey().compareTo(key) > 0) {
				if (!Parent.isNull()) {
					return (Entry<T, V>) Parent;
				}
				temp = (Node<T, V>) temp.getLeftChild();
			} else if (temp.getKey().compareTo(key) == 0) {
				return (Entry<T, V>) temp;
			} else {
				if (temp.getRightChild().isNull()) {
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
		if (temp.isNull())
			return null;
		return (T) temp.getKey();
	}

	@Override
	public V get(T key) {
		return tree.search((T) key);
	}

	@Override
	public ArrayList headMap(T toKey) {
		ArrayList<Node<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, false, (Node<T, V>) tree.getRoot(), Nodes);
		return Nodes;
	}

	@Override
	public ArrayList headMap(T toKey, boolean inclusive) {
		ArrayList<Node<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, inclusive, (Node<T, V>) tree.getRoot(), Nodes);
		return Nodes;
	}

	private void headMapHelper(T toKey, boolean inclusive, Node<T, V> temp, ArrayList<Node<T, V>> nodes) {
		if (temp.isNull())
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
		if (root.isNull())
			return;
		set.add((T) root.getKey());
		keySetHelper((Node<T, V>) root.getLeftChild(), set);
		keySetHelper((Node<T, V>) root.getRightChild(), set);
	}

	@Override
	public Entry<T, V> lastEntry() {
		Node<T, V> temp = (Node<T, V>) tree.getRoot();
		while (!temp.isNull()) {
			if (temp.getRightChild().isNull())
				return (Entry<T, V>) temp;
			temp = (Node<T, V>) temp.getRightChild();
		}
		return null;
	}

	@Override
	public T lastKey() {
		Node<T, V> temp = (Node<T, V>) lastEntry();
		if (temp.isNull())
			return null;
		return (T) temp.getKey();
	}

	@Override
	public Entry pollFirstEntry() {
		if (size() == 0) {
			return null;
		}
		Node<T, V> temp = (Node<T, V>) firstEntry();
		tree.delete(temp.getKey());
		return (Entry) temp.getValue();
	}

	@Override
	public Entry pollLastEntry() {
		if (size() == 0) {
			return null;
		}
		Node<T, V> temp = (Node<T, V>) lastEntry();
		tree.delete(temp.getKey());
		return (Entry) temp.getValue();
	}

	@Override
	public void put(T key, V value) {
		tree.insert(key, value);
		treeSize++;

	}

	@Override
	public void putAll(Map<T,V> map) {
		inOrder((Node<T, V>) tree.getRoot(), map);
	}

	public void inOrder(Node<T, V> node, Map<T,V> map) {
		if (node.isNull()) {
			return;
		}
		inOrder((Node<T, V>) node.getLeftChild(), map);
		map.put(node.getKey(), node.getValue());
		inOrder((Node<T, V>) node.getRightChild(), map);
	}

	@Override
	public boolean remove(T key) {
		if (treeSize != 0) {
			treeSize--;
		}
		return tree.delete(key);
	}

	@Override
	public int size() {
		return treeSize;
	}

	@Override
	public Collection values() {
		ArrayList<V> arrayList = new ArrayList<>();
		return coll((Node<T,V>) tree.getRoot(), arrayList);
	}

	public ArrayList<V> coll(Node<T,V> node, ArrayList<V> arrayList) {
		if (node.isNull()) {
			return arrayList;
		}
		coll((Node<T,V>) node.getLeftChild(), arrayList);
		arrayList.add((V) node.getValue());
		coll((Node<T,V>) node.getRightChild(), arrayList);
		return arrayList;
	}

	@Override
	public String toString() {
		return "TreeMap [tree=" + tree + ", treeSize=" + treeSize + ", treeNodes=" + treeNodes + "]";
	}

	public static void main(String[] args) {
		TreeMap<Integer, String> tree = new TreeMap<Integer, String>();
		tree.put(1, "Mark1");
		tree.put(2, "Faxawy2");
		tree.put(3, "Mark3");
		tree.put(4, "Faxawy4");
		tree.put(5, "Mark5");
		tree.put(6, "Faxawy6");
		tree.put(7, "Mark7");
		tree.put(8, "Faxawy8");
		// tree.ceilingEntry(7);

		java.util.TreeMap<Integer, String> tree1 = new java.util.TreeMap<Integer, String>();
		/*
		 * tree1.put(1,"Mark"); tree1.put(2, "Faxawy"); tree1.put(3,"Mark");
		 * tree1.put(4, "Faxawy"); tree1.put(5,"Mark"); tree1.put(6, "Faxawy");
		 * tree1.put(7,"Mark"); tree1.put(8, "Faxawy"); //System.out.println(tree1);
		 */
		tree.putAll(tree1);
		System.out.println(tree.ceilingEntry(6));
		System.out.println(tree1.lastEntry());
		// System.out.println(tree.pollFirstEntry());
	}

}