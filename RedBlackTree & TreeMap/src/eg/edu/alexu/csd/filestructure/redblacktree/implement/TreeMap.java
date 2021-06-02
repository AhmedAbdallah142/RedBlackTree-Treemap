package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.*;
import java.util.Map.Entry;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	RedBlackTree<T, V> tree;
	int treeSize;

	public TreeMap() {
		tree = new RedBlackTree<>();
		treeSize = 0;
	}

	ArrayList<INode<T, V>> treeNodes = new ArrayList<INode<T, V>>();

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
		Stack<INode<T, V>> s = new Stack<INode<T, V>>();
		INode<T, V> curr = tree.getRoot();

		// traverse the tree
		while (!curr.isNull() || s.size() > 0) {
			while (!curr.isNull()) {
				s.push(curr);
				curr = curr.getLeftChild();
			}

			curr = s.pop();

			if (curr.getKey().compareTo(key) >= 0) {
				return (Entry<T, V>) curr;
			}
			curr = curr.getRightChild();
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

	@Override
	public T ceilingKey(T key) {
		INode<T, V> curr = (INode<T, V>) ceilingEntry(key);
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

		Stack<INode<T, V>> s = new Stack<INode<T, V>>();
		INode<T, V> curr = tree.getRoot();

		// traverse the tree
		while (!curr.isNull() || s.size() > 0) {
			while (!curr.isNull()) {
				s.push(curr);
				curr = curr.getLeftChild();
			}

			curr = s.pop();

			if (curr.getValue().equals(value)) {
				return true;
			}

			curr = curr.getRightChild();
		}
		return false;
	}

	void addSetInOrder(INode<T, V> node, ArrayList<INode<T, V>> list) {
		if (node.isNull())
			return;
		/* first recur on left child */
		addSetInOrder(node.getLeftChild(), list);
		if (node.getKey() != null) {
			list.add(node);
		}
		addSetInOrder(node.getRightChild(), list);
	}

	@Override
	public Set entrySet() {

		Comparator<INode<T, V>> key = new Comparator<INode<T, V>>() {

			@Override
			public int compare(INode<T, V> o1, INode<T, V> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		};
		Set<INode<T, V>> set = new TreeSet<INode<T, V>>(key);
		if (tree.isEmpty())
			return null;
		// Stack<Node<T,V>> s = new Stack<Node<T,V>>();
		INode<T, V> curr = tree.getRoot();
		/*
		 * // traverse the tree while (curr != null || s.size() > 0) { while (curr !=
		 * null) { s.push((Node<T,V>) curr); curr = (Node<T,V>) curr.getLeftChild(); }
		 * curr = s.pop(); //if(curr.getKey() != null) { set.add(curr);//} curr =
		 * (Node<T,V>)curr.getRightChild(); }
		 */
		ArrayList<INode<T, V>> list = new ArrayList<INode<T, V>>();
		addSetInOrder(curr, list);
		/*
		 * Comparator<INode<T, V>> NodeComparator = new Comparator<INode<T, V>>() {
		 * 
		 * public int compare(INode<T, V> s1, INode<T, V> s2) { return
		 * s1.getKey().compareTo(s2.getKey()); } }; Collections.sort(list,
		 * NodeComparator);
		 */
		System.out.println(list);
		set.addAll(list);
		return set;
	}

	@Override
	public Entry firstEntry() {
		if (tree.isEmpty()) {
			return null;
		} else {
			INode<T, V> root = tree.getRoot();
			while (!root.isNull()) {
				root = root.getLeftChild();
			}
			return (Entry) root;
		}
	}

	@Override
	public T firstKey() {
		if (tree.isEmpty()) {
			return null;
		} else {
			INode<T, V> last = (INode<T, V>) firstEntry();
			return (T) last.getKey();
		}
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		INode<T, V> temp = tree.getRoot();
		INode<T, V> Parent = null;
		while (!temp.isNull()) {
			if (temp.getKey().compareTo(key) > 0) {
				if (!Parent.isNull()) {
					return (Entry<T, V>) Parent;
				}
				temp = temp.getLeftChild();
			} else if (temp.getKey().compareTo(key) == 0) {
				return (Entry<T, V>) temp;
			} else {
				if (temp.getRightChild().isNull()) {
					return (Entry<T, V>) temp;
				}
				Parent = temp;
				temp = temp.getRightChild();
			}
		}
		return null;
	}

	@Override
	public T floorKey(T key) {
		INode<T, V> temp = (INode<T, V>) floorEntry(key);
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
		ArrayList<INode<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, false, tree.getRoot(), Nodes);
		return Nodes;
	}

	@Override
	public ArrayList headMap(T toKey, boolean inclusive) {
		ArrayList<INode<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, inclusive, tree.getRoot(), Nodes);
		return Nodes;
	}

	private void headMapHelper(T toKey, boolean inclusive, INode<T, V> temp, ArrayList<INode<T, V>> nodes) {
		if (temp.isNull())
			return;
		headMapHelper(toKey, inclusive, temp.getLeftChild(), nodes);
		if (temp.getKey().compareTo(toKey) < 0) {
			nodes.add(temp);
			headMapHelper(toKey, inclusive, temp.getRightChild(), nodes);
		} else if (temp.getKey().compareTo(toKey) == 0) {
			if (inclusive)
				nodes.add(temp);
		}
	}

	@Override
	public Set<T> keySet() {
		Set<T> set = new TreeSet<T>();
		keySetHelper(tree.getRoot(), set);
		return set;
	}

	private void keySetHelper(INode<T, V> root, Set<T> set) {
		if (root.isNull())
			return;
		set.add((T) root.getKey());
		keySetHelper(root.getLeftChild(), set);
		keySetHelper(root.getRightChild(), set);
	}

	@Override
	public Entry<T, V> lastEntry() {
		INode<T, V> temp = tree.getRoot();
		while (!temp.isNull()) {
			if (temp.getRightChild().isNull())
				return (Entry<T, V>) temp;
			temp = temp.getRightChild();
		}
		return null;
	}

	@Override
	public T lastKey() {
		INode<T, V> temp = (INode<T, V>) lastEntry();
		if (temp.isNull())
			return null;
		return (T) temp.getKey();
	}

	@Override
	public Entry pollFirstEntry() {
		if (size() == 0) {
			return null;
		}
		INode<T, V> temp = (INode<T, V>) firstEntry();
		tree.delete(temp.getKey());
		return (Entry) temp.getValue();
	}

	@Override
	public Entry pollLastEntry() {
		if (size() == 0) {
			return null;
		}
		INode<T, V> temp = (INode<T, V>) lastEntry();
		tree.delete(temp.getKey());
		return (Entry) temp.getValue();
	}

	@Override
	public void put(T key, V value) {
		tree.insert(key, value);
		treeSize++;

	}

	@Override
	public void putAll(Map<T, V> map) {
		inOrder(tree.getRoot(), map);
	}

	public void inOrder(INode<T, V> node, Map<T, V> map) {
		if (node.isNull()) {
			return;
		}
		inOrder(node.getLeftChild(), map);
		map.put(node.getKey(), node.getValue());
		inOrder(node.getRightChild(), map);
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
		return coll(tree.getRoot(), arrayList);
	}

	public ArrayList<V> coll(INode<T, V> node, ArrayList<V> arrayList) {
		if (node.isNull()) {
			return arrayList;
		}
		coll(node.getLeftChild(), arrayList);
		arrayList.add((V) node.getValue());
		coll(node.getRightChild(), arrayList);
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

		tree.put(5, "Mark5");
		tree.put(6, "Faxawy6");
		tree.put(7, "Mark7");
		tree.put(8, "Faxawy8");
		tree.put(4, "Faxawy4");
		// tree.ceilingEntry(7);

		java.util.TreeMap<Integer, String> tree1 = new java.util.TreeMap<Integer, String>();
		/*
		 * tree1.put(1,"Mark"); tree1.put(2, "Faxawy"); tree1.put(3,"Mark");
		 * tree1.put(4, "Faxawy"); tree1.put(5,"Mark"); tree1.put(6, "Faxawy");
		 * tree1.put(7,"Mark"); tree1.put(8, "Faxawy"); //System.out.println(tree1);
		 */
		tree.putAll(tree1);
		// System.out.println(tree.keySet());
		System.out.println(tree.entrySet());
		// System.out.println(tree1.lastEntry());
		// System.out.println(tree.pollFirstEntry());
	}

}