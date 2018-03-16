package com.ms.algorithm;

public class TestBSTree {

	public static void main(String[] args) {
		
		//testAdd();
		
		Integer[] nums = {14, 7, 1, 12, 10, 8, 11, 9, 18, 16, 15, 24, 30, 26, 31, 29};
		BSTree<Integer> btree = new BSTree<Integer>();
		for (Integer each : nums) {
			btree.add(each);
		}
		btree.printBTree();
		System.out.println("删除1之后....");
		btree.delete(1);
		btree.printBTree();
		System.out.println("删除12之后....");
		btree.delete(12);
		btree.printBTree();
		System.out.println("删除8之后....");
		btree.delete(8);
		btree.printBTree();
		System.out.println("删除24之后....");
		btree.delete(24);
		btree.printBTree();
		System.out.println("删除18之后....");
		btree.delete(18);
		btree.printBTree();
	}
	

	private static void testAdd() {
		BSTree<String> btree = new BSTree<String>();
		btree.add("b");
		btree.add("a");
		btree.add("c");
		btree.add("aa");
		btree.add("cd");
	}
}
