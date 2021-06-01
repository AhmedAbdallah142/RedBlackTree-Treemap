package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap{
	
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
		Node Parent =  null;
		while (temp != null) {
			if (temp.getKey().compareTo(key)>0) {
				if ((temp.getLeftChild()==null)&&(Parent!=null)) {
					return (Entry) Parent;
				}
				temp = (Node) temp.getLeftChild();
			}
			else if (temp.getKey().compareTo(key) == 0) {
				return (Entry) temp;
			}
			else {
				if (temp.getRightChild()==null) {
					return (Entry) temp;
				}	
				Parent = temp;
				temp = (Node) temp.getRightChild();	
			}
		}
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
