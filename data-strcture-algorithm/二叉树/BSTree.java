package com.ms.algorithm;

/**
 * 二叉搜索树
 * @author ASUS
 *
 */
public class BSTree<T extends Comparable<T>> {

	//根节点
	private Node root = null;
	
	public BSTree() {
		// TODO Auto-generated constructor stubs
	}
	
	public void add(T value) {
		if (root == null) {
			root = new Node(null, null, null, value);
			return;
		} 
		root.addNode(value);
		
	}
	
	public void delete(T value) {
		if (root == null) {
			return;
		}
		root.delete(value);
		
	}

	public void printBTree() {
		root.ldr();
	}
	class Node {
		private T value;
		private Node parent;
		private Node left;
		private Node right;
		
		public Node(Node parent, Node left, Node right, T value) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.value = value;
		}
		
		private void addNode(T value) {
			Node curNode = root;
			Node pNode = null;
			//找到要插入的节点的父节点
			while (curNode != null) {
				pNode = curNode;
				int result = value.compareTo(curNode.value);
				if (result > 0) {
					curNode = curNode.right;
				} else if (result < 0) {
					curNode = curNode.left;
				} else {
					return;
				}
			}
			Node newNode = new Node(pNode, null, null, value);
			if (newNode.value.compareTo(pNode.value) > 0) {
				pNode.right = newNode;
			} else {
				pNode.left = newNode;
			}
		}
		
		private void delete(T value) {
			//找到要删除的节点
			Node delNode = root;
			while (root != null) {
				int result = value.compareTo(delNode.value);
				if (result > 0) {
					delNode = delNode.right;
				} else if (result < 0) {
					delNode = delNode.left;
				} else {
					break;
				}
			}
			//没有找到节点
			if (delNode == null) {
				throw new RuntimeException("没有找到要删除的节点.");
			}
			if (delNode == root) {
				root = null;
				return;
			}
			//删除节点没有子节点
			if (delNode.left == null && delNode.right == null) {
				deleteNoChildOfNode(delNode);
				return;
			}
			//删除节点有两个子节点
			if (delNode.left != null && delNode.right != null) {
				deleteTwoChildOfNode(delNode);
				return;
			} else {
				//删除节点只有一个子节点
				deleteOneChildOfNode(delNode);
			}
			
		}
		
		/**
		 * 删除有一个子节点的节点
		 * @param delNode
		 */
		private void deleteOneChildOfNode(Node delNode) {
			if (delNode == parentOf(delNode).left) {
				if (delNode.left != null) {
					parentOf(delNode).left = delNode.left;
					delNode.left.parent = parentOf(delNode);
				} else {
					parentOf(delNode).left = delNode.right;
					delNode.right.parent = parentOf(delNode);
				}
			} else {
				if (delNode.left != null) {
					parentOf(delNode).right = delNode.left;
					delNode.left.parent = parentOf(delNode);
				} else {
					parentOf(delNode).right = delNode.right;
					delNode.right.parent = parentOf(delNode);
				}
			}

		}

		/**
		 * 删除有两个子节点的节点
		 * @param delNode
		 */
		private void deleteTwoChildOfNode(Node delNode) {
			//找到中序遍历的后继节点
			Node afterNode = delNode.right;
			while (afterNode.left != null) {
				afterNode = afterNode.left;
			}
			if (afterNode != delNode.right) {
				//处理后继节点父节点与后继节点子节点的关系
				parentOf(afterNode).left = afterNode.right;
				if (afterNode.right != null) {
					afterNode.right.parent = parentOf(afterNode);
				}
				//处理后继节点与删除节点父节点的关系
				if (delNode == parentOf(delNode).left)
					parentOf(delNode).left = afterNode;
				else 
					parentOf(delNode).right = afterNode;
				//处理后继节点与删除节点之间的关系
				afterNode.left = delNode.left;
				afterNode.right = delNode.right;
				afterNode.parent = parentOf(delNode);
			} else {
				afterNode.left = delNode.left;
				afterNode.parent = parentOf(delNode);
				if (delNode == parentOf(delNode).left) {
					parentOf(delNode).left = afterNode;
				} else {
					parentOf(delNode).right = afterNode;
				}
			}
		}
		
		/**
		 * 删除没有子节点的节点
		 * @param delNode
		 */
		private void deleteNoChildOfNode(Node delNode) {
			if (delNode == parentOf(delNode).left) {
				parentOf(delNode).left = null;
			} else {
				parentOf(delNode).right = null;
			}
		}
		
		private Node parentOf(Node o) {
			return o == null ? null : o.parent;
		}
		
		public void ldr() {
			ldr0(this);
		}
		
		public void ldr0(Node node) {
			//先遍历左子树
			if (node.left != null) {
				ldr0(node.left);
			}
			System.out.println(node.value);
			//在遍历右子树
			if (node.right != null) {
				ldr0(node.right);
			}
			
		}
	}
}
