package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.*;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;
import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
	class entry implements Entry<T, V> {
		T key;
		V value;

		entry(INode<T,V> node) {
			this.key = node.getKey();
			this.value = node.getValue();
		}
		entry(T key,V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public T getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}

		@Override
		public String toString() {
			return key+"="+value;
		}

	};
	RedBlackTree<T, V> tree;
	public TreeMap() {
		tree = new RedBlackTree<>();
	}

	/*ArrayList<INode<T, V>> treeNodes = new ArrayList<INode<T, V>>();

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
	}*/
	Comparator<Entry<T, V>> keyComparator = new Comparator<Entry<T, V>>() {
		@Override
		public int compare(Entry<T, V> o1, Entry<T, V> o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
	};
	Set<Entry<T, V>> set = new TreeSet<Entry<T, V>>(keyComparator);
	@Override
	public Entry<T, V> ceilingEntry(T key) {
		if (key==null)
			throw new RuntimeErrorException(null);
		if (!tree.isEmpty()) {
			INode<T, V> temp = tree.getRoot();
			INode<T, V> Parent = null;
			while (!temp.isNull()) {
				if (temp.getKey().compareTo(key) < 0) {
					if ((Parent!=null)&&(temp.getRightChild().isNull())) {
						return new entry(Parent);
					}
					temp = temp.getRightChild();
					} else if (temp.getKey().compareTo(key) == 0) {
						return new entry(temp);
					} else {
						if (temp.getLeftChild().isNull()) {
							return new entry(temp);
						}
						Parent = temp;
						temp = temp.getLeftChild();
						}
					}
			}
		return null;
	}

	@Override
	public T ceilingKey(T key) {
		Entry<T, V> curr = ceilingEntry(key);
		if (curr == null) {
			return null;
		} else {
			return curr.getKey();
		}
	}

	@Override
	public void clear() {
		tree = new RedBlackTree<T, V>();
	}

	@Override
	public boolean containsKey(T key) {
		Entry<T,V> curr = ceilingEntry(key);
		if (curr == null) { return false; }
		else if (key.compareTo(curr.getKey()) == 0) { return true; }
		else { return false; }
	}

	@Override
	public boolean containsValue(Object value) {
		if (value==null)
			throw new RuntimeErrorException(null);
		if (tree.getRoot()==null || tree.getRoot().isNull())
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


	@Override
	public Set<Entry<T, V>> entrySet() {
		return set;
	}

	@Override
	public Entry<T, V> firstEntry() {
		if (tree.isEmpty()) {
			return null;
		} else {
			INode<T, V> root = tree.getRoot();
			INode<T, V> child = root.getLeftChild();
			while (!child.isNull()) {
				root = root.getLeftChild();
				child = child.getLeftChild();
			}
			return new entry(root);
		}
	}

	@Override
	public T firstKey() {
		if (tree.isEmpty()) {
			return null;
		} else {
			Entry<T, V> last = firstEntry();
			return last.getKey();
		}
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		if (key==null)
			throw new RuntimeErrorException(null);
		if (!tree.isEmpty()) {
			INode<T, V> temp = tree.getRoot();
			INode<T, V> Parent = null;
			while (!temp.isNull()) {
				if (temp.getKey().compareTo(key) > 0) {
					if ((Parent!=null)&&(temp.getLeftChild().isNull())) {
						return new entry(Parent);
					}
					temp = temp.getLeftChild();
				} else if (temp.getKey().compareTo(key) == 0) {
					return new entry(temp);
				} else {
					if (temp.getRightChild().isNull()) {
						return new entry(temp);
					}
					Parent = temp;
					temp = temp.getRightChild();
				}
			}
		}
		return null;
	}

	@Override
	public T floorKey(T key) {
		Entry<T, V> temp = floorEntry(key);
		if (temp == null)
			return null;
		return (T) temp.getKey();
	}

	@Override
	public V get(T key) {
		return tree.search(key);
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		if (toKey==null)
			throw new RuntimeErrorException(null);
		ArrayList<Entry<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, false, tree.getRoot(), Nodes);
		return Nodes;
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		ArrayList<Entry<T, V>> Nodes = new ArrayList<>();
		headMapHelper(toKey, inclusive, tree.getRoot(), Nodes);
		return Nodes;
	}

	private void headMapHelper(T toKey, boolean inclusive, INode<T, V> temp, ArrayList<Entry<T, V>> nodes) {
		if (temp.isNull())
			return;
		headMapHelper(toKey, inclusive, temp.getLeftChild(), nodes);
		if (temp.getKey().compareTo(toKey) < 0) {
			nodes.add((Map.Entry<T, V>)new entry(temp));
			headMapHelper(toKey, inclusive, temp.getRightChild(), nodes);
		} else if (temp.getKey().compareTo(toKey) == 0) {
			if (inclusive)
				nodes.add((Map.Entry<T, V>)new entry(temp));
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
		if (tree.isEmpty())
			return null;
		INode<T, V> temp = tree.getRoot();
		while (!temp.isNull()) {
			if (temp.getRightChild().isNull())
				return new entry(temp);
			temp = temp.getRightChild();
		}
		return null;
	}

	@Override
	public T lastKey() {
		Entry<T, V> temp = lastEntry();
		if (temp == null)
			return null;
		return (T) temp.getKey();
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		if (size() == 0) {
			return null;
		}
		Entry<T, V> temp = firstEntry();
		if(temp != null) {
			set.remove(temp); }
		tree.delete(temp.getKey());
		return temp;
	}

	@Override
	public Entry<T, V> pollLastEntry()  {
		if (size() == 0) {
			return null;
		}
		Entry<T, V> temp = lastEntry();
		if(temp != null) {
			set.remove(temp); }
		tree.delete(temp.getKey());
		return temp;
	}

	@Override
	public void put(T key, V value) {
		if(containsKey(key)) {
			V val = tree.search(key);
			set.remove(new entry(key, val));
			set.add(new entry(key, value));
		}else {
			set.add(new entry(key, value));
		}
		tree.insert(key, value);
	}

	@Override
	public void putAll(Map<T, V> map) {
		if (map==null)
			throw new RuntimeErrorException(null);
		tree = new RedBlackTree<T, V>();
		/*T [] key = (T[]) map.keySet().toArray();
		V [] val = (V[]) map.values().toArray();
		for (int i=0 ; i< map.size() ; i++){
			tree.insert(key[i] , val[i]);
		}*/
		for ( Map.Entry<T, V> entry : map.entrySet()) {
			T key = entry.getKey();
			V val = entry.getValue();
			if(containsKey(key)) {
				V value = tree.search(key);
				set.remove(new entry(key, value));
				set.add(new entry(key, val));
			}else {
				set.add(new entry(key, val));
			}
			tree.insert(key,val);
		}
	}

	/*public void inOrder(INode<T, V> node, Map<T, V> map) {
		if (node.isNull()) {
			return;
		}
		inOrder(node.getLeftChild(), map);
		map.put(node.getKey(), node.getValue());
		inOrder(node.getRightChild(), map);
	}
	}*/

	@Override
	public boolean remove(T key) {
		if (key==null)
			throw new RuntimeErrorException(null);
		if(containsKey(key)) {
			V val = tree.search(key);
			set.remove(new entry(key, val));
		}
		return tree.delete(key);
	}

	@Override
	public int size() {
		return entrySet().size();
	}

	@Override
	public Collection<V> values() {
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
		return "TreeMap [tree=" + tree +  "]";
	}

	public static void main(String[] args) {
		TreeMap<Integer, String> tree = new TreeMap<Integer, String>();
		tree.lastEntry();
		tree.put(1, "Mark1");
		tree.put(2, "Faxawy2");
		tree.put(3, "Mark3");
		tree.put(5, "Mark5");
		tree.put(6, "Faxawy6");
		tree.put(7, "Mark");
		tree.put(8, "Faxawy8");
		tree.put(8, "Fax");
		tree.remove(5);
		tree.put(4, "Faxawy4");
		//tree.put(10, "fax");


		java.util.TreeMap<Integer, String> tree1 = new java.util.TreeMap<Integer, String>();

		tree1.put(1, "Mark");
		tree1.put(2, "Faxawy");
		tree1.put(3, "Mark");
		tree1.put(5, "Mark");
		tree1.put(6, "Faxawy");
		tree1.put(7, "Mark");
		tree1.put(8, "Faxawy");
		tree1.put(8, "Fax");
		tree1.remove(5);
		tree1.put(4, "Faxawy");
		//tree1.put(10, "fax");
		
		// Ahmed Ashraf tests
		/*System.out.println(tree.ceilingEntry(3));
		System.out.println(tree1.ceilingEntry(3));
		System.out.println(tree.ceilingEntry(4));
		System.out.println(tree1.ceilingEntry(4));
		System.out.println(tree.ceilingKey(3));
		System.out.println(tree1.ceilingKey(3));
		System.out.println(tree.ceilingKey(0));
		System.out.println(tree1.ceilingKey(0));
		System.out.println(tree.containsKey(3));
		System.out.println(tree1.containsKey(3));
		System.out.println(tree.containsKey(13));
		System.out.println(tree1.containsKey(13));
		/*System.out.println(tree.containsValue("Mark"));
		System.out.println(tree1.containsValue("Mark"));
		System.out.println(tree.containsValue("zoo"));
		System.out.println(tree1.containsValue("zoo"));
		System.out.println(tree.entrySet());
		System.out.println(tree1.entrySet());
		/*System.out.println(tree.firstEntry());
		System.out.println(tree1.firstEntry());
		System.out.println(tree.firstKey());
		System.out.println(tree1.firstKey());
		//tree.clear();
		//tree1.clear();
		System.out.println(tree.size());
		System.out.println(tree1.size());
		
		
		// Ahmed Abdallah tests
		System.out.println(tree.floorEntry(9));
		System.out.println(tree1.floorEntry(9));
		System.out.println(tree.floorEntry(2));
		System.out.println(tree1.floorEntry(2));
		System.out.println(tree.floorKey(0));
		System.out.println(tree1.floorKey(0));
		System.out.println(tree.floorKey(12));
		System.out.println(tree1.floorKey(12));
		System.out.println(tree.get(0));
		System.out.println(tree1.get(0));
		System.out.println(tree.get(8));
		System.out.println(tree1.get(8));
		System.out.println(tree.headMap(0));
		System.out.println(tree1.headMap(0));
		System.out.println(tree.headMap(8));
		System.out.println(tree1.headMap(8));
		System.out.println(tree.headMap(0,true));
		System.out.println(tree1.headMap(0,true));
		System.out.println(tree.headMap(8,true));
		System.out.println(tree1.headMap(8,true));
		System.out.println(tree.keySet());
		System.out.println(tree1.keySet());
		System.out.println(tree.lastEntry());
		System.out.println(tree1.lastEntry());
		System.out.println(tree.lastKey());
		System.out.println(tree1.lastKey());*/
		//Mark Test
		/*//17
		System.out.println(tree1.pollFirstEntry());
		System.out.println(tree.pollFirstEntry());
		//18
		System.out.println(tree1.pollLastEntry());
		System.out.println(tree.pollLastEntry());
		//19
		in main
		//20
		/*Map<Integer, String> map = new HashMap<>();
		map.put(1,"mark100");
		map.put(5,"mark500");
		map.put(2,"mark200");
		map.put(6,"mark600");
		tree.putAll(map);
		System.out.println(tree.entrySet());
		//21
		System.out.println(tree.remove(1));
		System.out.println(tree1.remove(1));
		System.out.println(tree.entrySet());
		System.out.println(tree1.entrySet());
		//22
		System.out.println(tree.size());
		System.out.println(tree1.size());
		//23
		System.out.println(tree.values());
		System.out.println(tree1.values());*/
		
		
		
	}

}