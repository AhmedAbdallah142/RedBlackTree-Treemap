package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> root;

	@Override
	public INode<T, V> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root == null;
	}

	@Override
	public void clear() {
		this.root = null;
	}

	@Override
	public V search(T key) {
		return null;
	}

	@Override
	public boolean contains(T key) {
		return false;
	}

	@Override
	public void insert(T key, V value) {

	}

	@Override
	public boolean delete(T key) {
		return false;
	}

}
