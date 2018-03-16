package com.ms.algorithm;

public class TestRBSTree {

	public static void main(String[] args) {
		
		Integer[] nums = {8, 6, 10, 5, 16, 13, 3, 7, 9};
		RBSTree<Integer> tree = new RBSTree<Integer>();
		for (int i = 0; i < nums.length; i++) {
			tree.insert(nums[i]);
		}
		tree.print();
		
		System.out.println("==================================");
		tree.remove(16);
		tree.print();
	}
}
