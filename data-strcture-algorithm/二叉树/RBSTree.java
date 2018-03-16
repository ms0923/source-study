package com.ms.algorithm;

/**
 * 红黑树
 * @author ASUS
 *
 */
public class RBSTree<T extends Comparable<T>> {

	private	Node root = null; 
	
	public RBSTree() {
		// TODO Auto-generated constructor stub
	}
	
	public void insert(T value) {
		if (value == null) {
			throw new RuntimeException("value is not be null");
		}
		if (root == null) {
			root = new Node(null, null, null, value);
			root.setColor(Node.BLACK);
		} else {
			Node n = root;
			Node p = null;
			while (n != null) {
				p = n;
				int res = value.compareTo(n.value);
				if (res > 0) {
					n = n.right;
				} else if (res < 0) {
					n = n.left;
				} else {
					return;
				}
			}
			Node newNode = new Node(p, null, null, value);
			int res = value.compareTo(p.value);
			if (res > 0) {
				p.right = newNode;
			} else if (res < 0) {
				p.left = newNode;
			}
			if (p.color == Node.RED) {
				fixupAfterAdd(newNode);
			}
		}
	}
	
	public void remove(T value) {
		Node remove = searchNode(value);
		if (remove == null) {
			return;
		}
		if (remove == root) {
			root = null;
			return;
		}
		remove(remove);
	}
	
	private void remove(Node n) {
		Node correct, parent;
		//左右子树都不为空
		if (leftOf(n) != null && rightOf(n) != null) {
			Node successor = successor(n);
			correct = rightOf(successor);
			parent = parentOf(successor);
			n.value = successor.value;
			if (successor == leftOf(parentOf(successor))) {
				parentOf(successor).left = rightOf(successor);
			} else {
				parentOf(successor).right = rightOf(successor);
			}
			if (rightOf(successor) != null) {
				rightOf(successor).parent = parentOf(successor);
			}
			successor.parent = null;
			successor.left = null;
			successor.right = null;
			if (colorOf(successor) == Node.BLACK) {
				fixupAfterRemove(correct, parent);
			}
			return;
		} 
		parent = n.parent;
		if (leftOf(n) != null) {
			correct = leftOf(n);
		} else {
			correct = rightOf(n);
		}
		if (n == leftOf(parentOf(n))) {
			parentOf(n).left = correct;
		} else {
			parentOf(n).right = correct;
		}
		if (correct != null) {
			correct.parent = parentOf(n);
		}
		n.parent = null;
		n.left = null;
		n.right = null;
		if (colorOf(n) == Node.BLACK) 
			fixupAfterRemove(correct, parent);
		
	}

	private void fixupAfterRemove(Node correct, Node parent) {
		while (correct != root && colorOf(correct) == Node.BLACK) {
			if (correct == leftOf(parent)) {
				Node sib = rightOf(parent);
				//兄弟节点为红色
				if (colorOf(sib) == Node.RED) {
					parent.setColor(Node.RED);
					sib.setColor(Node.BLACK);
					rotateLeft(parent);
					sib = rightOf(parent);
				} 
				//兄弟节点为黑色
				//兄弟节点的子节点都为黑色
				if (colorOf(leftOf(sib)) == Node.BLACK && colorOf(rightOf(sib)) == Node.BLACK) {
					sib.setColor(Node.RED);
					correct = parent;
					parent = parentOf(correct);
				} else {
					if (colorOf(rightOf(sib)) == Node.BLACK) {	//左子节点为红色，右子节点为黑色
						sib.setColor(Node.RED);
						leftOf(sib).setColor(Node.BLACK);
						rotateRight(sib);
						sib = rightOf(sib);
					}
					//右子节点为红色，左边节点为任意颜色
					sib.setColor(colorOf(parent));
					parent.setColor(Node.BLACK);
					rightOf(sib).setColor(Node.BLACK);
					rotateLeft(parent);
					correct = root;
				}
			} else {
				Node sib = leftOf(parent);
				//兄弟节点为红色
				if (colorOf(sib) == Node.RED) {
					parent.setColor(Node.RED);
					sib.setColor(Node.BLACK);
					rotateRight(parent);
					sib = leftOf(parent);
				} 
				//兄弟节点为黑色
				//兄弟节点的子节点都为黑色
				if (colorOf(leftOf(sib)) == Node.BLACK && colorOf(rightOf(sib)) == Node.BLACK) {
					sib.setColor(Node.BLACK);
					correct = parent;
					parent = parentOf(correct);
				} else {
					if (colorOf(leftOf(sib)) == Node.BLACK) {	//右子节点为红色，左子节点为黑色
						sib.setColor(Node.RED);
						rightOf(sib).setColor(Node.BLACK);
						rotateLeft(sib);
						sib = leftOf(sib);
					}
					//左子节点为红色，右子节点为任意颜色
					sib.setColor(colorOf(parent));
					parent.setColor(Node.BLACK);
					leftOf(sib).setColor(Node.BLACK);
					rotateRight(parent);
					correct = root;
				}
				
			}
		}
		correct.setColor(Node.BLACK);
	}

	private Node successor(Node n) {
		Node replace = n.right;
		while (replace != null && replace.left != null)
			replace = replace.left;
		return replace;
	}

	private Node searchNode(T value) {
		Node n = root;
		while (n != null) {
			int res = value.compareTo(n.value);
			if (res > 0) {
				n = n.right;
			} else if (res < 0) {
				n = n.left;
			} else {
				return n;
			}
		}
		return null;
	}

	private void fixupAfterAdd(Node newNode) {
		Node n = newNode;
		while (n != null && n != root && n.parent.color == Node.RED) {
			if (parentOf(n) == leftOf(parentOf(parentOf(n)))) {
				boolean color = colorOf(rightOf(parentOf(parentOf(n))));
				if (color == Node.RED) {
					//将父节点叔叔节点涂黑，祖父节点涂红
					parentOf(n).setColor(Node.BLACK);
					rightOf(parentOf(parentOf(n))).setColor(Node.BLACK);
					parentOf(parentOf(n)).setColor(Node.RED);
					n = parentOf(parentOf(n));
				} else {
					//叔叔节点为黑色且为左子节点
					if (n == parentOf(n).right) {
						n = parentOf(n);
						rotateLeft(n);
					} else {	//右子节点
						parentOf(n).setColor(Node.BLACK);
						parentOf(parentOf(n)).setColor(Node.RED);
						rotateRight(parentOf(parentOf(n)));
					}
				}
			} else {
				boolean color = colorOf(leftOf(parentOf(parentOf(n))));
				//叔叔节点为红色
				if (color == Node.RED) {
					//将父节点叔叔节点涂黑，祖父节点涂红
					parentOf(n).setColor(Node.BLACK);
					leftOf(parentOf(parentOf(n))).setColor(Node.BLACK);
					parentOf(parentOf(n)).setColor(Node.RED);
					n = parentOf(parentOf(n));
				} else {
					//叔叔节点为黑色且为左子节点
					if (n == parentOf(n).left) {
						n = parentOf(n);
						rotateRight(n);
					} else {	//右子节点
						parentOf(n).setColor(Node.BLACK);
						parentOf(parentOf(n)).setColor(Node.RED);
						rotateLeft(parentOf(parentOf(n)));
					}
				}
			}
		}
		root.setColor(Node.BLACK);
	}

	/**
	 * 左旋
	 * 
	 * @param n
	 */
	private void rotateLeft(Node n) {
		Node right = n.right;
		Node rightleft = n.right.left;
		right.left = n;
		right.parent = parentOf(n);
		if (parentOf(n) != null) {
			if (parentOf(n).left == n) {
				parentOf(n).left = right;
			} else {
				parentOf(n).right = right;
			}
		}
		
		n.right = rightleft;
		n.parent = right;
		if (rightleft != null) {
			rightleft.parent = n;
		}
	}
	
	/**
	 * 右旋
	 * @param n
	 */
	private void rotateRight(Node n) {
		Node left = n.left;
		Node leftright = n.left.right;
		
		left.right = n;
		left.parent = parentOf(n);
		if (parentOf(n) != null) {
			if (n == parentOf(n).left) {
				parentOf(n).left = left;
			} else {
				parentOf(n).right = left;
			}
		}
		
		n.left = leftright;
		n.parent = left;
		if (leftright != null) {
			leftright.parent = n;
		}
		
	}
	
	private boolean colorOf(Node n) {
		return n == null ? Node.BLACK : n.color;
	}
	
	private Node parentOf(Node n) {
		return n.parent;
	}
	
	private Node leftOf(Node n) {
		return n.left;
	}
	
	private Node rightOf(Node n) {
		return n.right;
	}
	
	public void print() {
		if (root != null) {
			print0(root);
		}
	}
	
	private void print0(Node n) {
		if (n.left != null) {
			print0(n.left);
		}
		System.out.println("[value:" + n.value + "  color:" + (n.color ? "black" : "red") + "]");
		if (n.right != null) {
			print0(n.right);
		}
	}
	
	
	class Node {
		private boolean color = RED;
		private Node parent;
		private Node left;
		private Node right;
		private T value;
		
		public Node(Node parent, Node left, Node right, T value) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.value = value;
		}

		public void setColor(boolean color) {
			this.color = color;
		}

		private static final boolean RED = false;
		private static final boolean BLACK = true;
	
	}
}
