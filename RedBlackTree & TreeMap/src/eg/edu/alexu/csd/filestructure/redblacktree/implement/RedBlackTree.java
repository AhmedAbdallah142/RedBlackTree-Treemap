package eg.edu.alexu.csd.filestructure.redblacktree.implement;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T,V> nill;
	private INode<T, V> root;

	public RedBlackTree() {
		nill = new Node<T, V>();
		nill.setColor(false);
	}
	
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
		/*** insert in suitable place like any BST ***/
		
		//if root is null, easy
		if(root==null) {
			root = new Node<T, V>(key, value, null);
			root.setColor(false);
			root.setLeftChild(nill);
			root.setRightChild(nill);
			return;
		}
		
		INode<T, V> curr = this.root;
		boolean flag = true;
		
		while(flag) {
			if(key.compareTo(curr.getKey()) < 0) { // key < current.key
				if(curr.getLeftChild() == nill) {
					curr.setLeftChild(new Node<T, V>(key, value, curr));
					flag = false;
				}
				curr = curr.getLeftChild();
			}
			else if(key.compareTo(curr.getKey()) > 0) { // key > current.key
				if(curr.getRightChild() == nill) {
					curr.setRightChild(new Node<T, V>(key, value, curr));
					flag = false;
				}
				curr = curr.getRightChild();
			}
			else { //key == current.key
				curr.setValue(value); 
				return;
			}	
		}
		curr.setLeftChild(nill); 
		curr.setRightChild(nill);
		if(curr.getParent().getParent() == null) return;
		
		/*** fix violations ***/
		
		while(curr.getParent().getColor()) {
			if(curr.getParent() == curr.getParent().getParent().getRightChild()) {
				INode<T, V> y = curr.getParent().getParent().getLeftChild();
				if(y.getColor()) {
					curr.getParent().setColor(false);
					y.setColor(false);
					curr.getParent().getParent().setColor(true);
					curr = curr.getParent().getParent();
				} else {
					if(curr == curr.getParent().getLeftChild()) {
						curr = curr.getParent();
						right_rotate(curr);
					}
					curr.getParent().setColor(false);
					curr.getParent().getParent().setColor(true);
					left_rotate(curr.getParent().getParent());
				}
			} else {
				INode<T, V> y = curr.getParent().getParent().getRightChild();
				if(y.getColor()) {
					curr.getParent().setColor(false);
					y.setColor(false);
					curr.getParent().getParent().setColor(true);
					curr = curr.getParent().getParent();
				} else {
					if(curr == curr.getParent().getRightChild()) {
						curr = curr.getParent();
						left_rotate(curr);
					}
					curr.getParent().setColor(false);
					curr.getParent().getParent().setColor(true);
					right_rotate(curr.getParent().getParent());
				}
			}
			if(curr == root) break;
		}
		root.setColor(false);
	}

	@Override
	public boolean delete(T key) {
		return false;
	}
	
	private void right_rotate(INode<T, V> x) {
		INode<T, V> y = x.getLeftChild();
		x.setLeftChild(y.getRightChild());
		if(y.getRightChild() != nill) y.getRightChild().setParent(x);
		y.setParent(x.getParent());
		if(x.getParent() == null) 
			this.root = y;
		else if(x == x.getParent().getRightChild())
			x.getParent().setRightChild(y);
		else
			x.getParent().setLeftChild(y);
		y.setRightChild(x);
		x.setParent(y);
		
	}
	
	private void left_rotate(INode<T, V> x) {
		INode<T, V> y = x.getRightChild();
		x.setRightChild(y.getLeftChild());
		if(y.getLeftChild() != nill) y.getLeftChild().setParent(x);
		y.setParent(x.getParent());
		if(x.getParent() == null) 
			this.root = y;
		else if(x == x.getParent().getLeftChild())
			x.getParent().setLeftChild(y);
		else
			x.getParent().setRightChild(y);
		y.setLeftChild(x);
		x.setParent(y);
	}
	
	public static void main(String[] args) {
		RedBlackTree<Integer, Integer> t = new RedBlackTree<Integer, Integer>();
		t.insert(41, -1);
		t.insert(38, -1);
		t.insert(31, -1);
		t.insert(12, -1);
		t.insert(19, -1);
		t.insert(8, -1);
		t.dfs_left(t.root);
	}
	private void dfs_left(INode<T, V> root) {
		if(root == nill) return;
		System.out.println(root.toString());
		dfs_left(root.getLeftChild());
		dfs_left(root.getRightChild());
	}
	

}
