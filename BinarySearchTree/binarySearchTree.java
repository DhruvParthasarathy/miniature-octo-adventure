import java.util.Scanner;

class BST {
		
	
	/**
	 * PRIVATE METHODS =====================================
	 *
	 */
		
		private class Node{
			
			private int key; // holds the value of that node
			private Node left, right; // hold the reference to the left and right sub trees under this node
			private int count; // holds the value of number of nodes in the sub tree originating from this node
			
			/**
			 * Constructor of a Node 
			 * Every Node of the Binary search tree has a value, a left node and a right node attached to it
			 * 
			 */
			public Node(int num) {
				
				this.key = num;
				this.count = 1;
			}
		}
		
		private Node root; 
		
		/**
		 * Returns the size of a given node
		 * 
		 * If node is null, returns 0
		 * 
		 * @param x
		 * @return
		 */
		private int size(Node x) {
			
			if(x == null) return 0;
			return x.count;
		}
		
		/**
		 * This private method has access to the root node 
		 * and the new value which is given
		 * this method traverses through the tree and inserts 
		 * the node in the appropriate position
		 * @param x
		 * @param value
		 * @return
		 */
		private Node put(Node x, int value) {
			
			if(x == null) return new Node(value);  // When we encounter an empty binary tree
			
			if(value < x.key) {
				
				x.left = put(x.left, value); // we do a recursive call here
			}
			
			else if ( value > x.key ) {
				x.right = put(x.right, value); // we do a recursive call here
			}
			
			else x.key = value;
			
			/**
			 * Count value of a node = 
			 * size of left sub tree + 1 ( for this node ) + size of the right sub tree
			 */
			x.count = size(x.left) + 1 + size(x.right) ; 
			
			return x;
		}
		
		private Node floor(Node x, int val) {
			
			/**
			 * If the tree is empty or if we can't find any node lesser than the given node, 
			 * we return null - assume a totally left leaning bst.
			 */
			if(x == null) return null; 
			
			/**
			 *  if the val is == the key of the node, return the node - we have found the floor
			 */
			if(val == x.key) return x;
			
			/**
			 *  if the val is less than the key of the node, check in the left sub tree
			 *  if there is a key lesser than the given val
			 *  this is a recursive call
			 */
			if(val < x.key) return floor(x.left, val);
			
			/**
			 *  if value is greater than the key of the node, 
			 *  check if there is any element less than the given 
			 *  element in the right subtree
			 */
			Node temp = floor(x.right, val);
			
			/**
			 * After checking if we found any element lesser than the key in the 
			 * right sub tree we return that
			 * 
			 * else the current element x is the floor of the given value
			 */
			if(temp != null) return temp;
			
			
			else return x;
			
			
		}
		
		/**
		 * Here we need to find the first node larger or equal to the given val
		 * @param x
		 * @param val
		 * @return
		 */
		private Node ceiling(Node x, int val) {
			
			/**
			 * If the tree is empty or we reach a null node at a leaf we return null
			 */
			if(x == null) return null;
			
			/**
			 * If the given val belongs to the current node, 
			 * we return the key of the current node
			 */
			if( x.key == val) return x;
			
			/**
			 * If the val is greater than the given node's key, proceed checking 
			 * in the right sub tree if we have a value greater than the given val
			 */
			if(val > x.key) return ceiling(x.right, val);
			
			/**
			 * If val is lesser than the key of the current node, 
			 * check if there is any value greater than this val in the 
			 * left sub tree of the given node
			 */
			Node temp = ceiling(x.left, val);
			
			/**
			 * If we found a node greater than the val in the left sub tree
			 */
			if(temp != null) return temp;
			
			/**
			 * If we were unsuccessful in the above step, it means that the current node 
			 * is the ceiling
			 */
			else return x;
			
		}
		
		/**
		 * This method helps the client get the rank of a given node in the subtree
		 * @param x
		 * @param num
		 * @return
		 */
		private int rank(Node x, int num) {
			
			/**
			 * If there are no elements in the tree, return 0
			 */
			if( x == null) return 0;
			
			/**
			 * If num is lesser than the value of the given node, see if you can find rank for num in
			 * the left sub tree of this node
			 */
			if(num < x.key) return rank(x.left, num);
			
			/**
			 * if num is greater than the given node,
			 * add the size of the left sub tree, 1 for this node and proceed to check 
			 * for the rank of num in the right sub tree
			 */
			else if(num > x.key) return size(x.left) + 1 + rank(x.right,num);
			
			/**
			 * If the number is == the value of the node, then the rank is the size of the left sub tree
			 */
			else return size(x.left);
		}
		
		/**
		 * PUBLIC METHODS ============================================
		 */

		
		/**
		 * Constructor for this BST
		 */
		public BST() {}
		
		/**
		 * This is a public method which we expose to clients  
		 * that have access to any instance of the BST class
		 * 
		 * The clients can just give the number which they want to 
		 * insert and it will be inserted in the BST
		 *  
		 * @param num
		 */
		public void insert(int num) {
			
			root = put(root, num);
		}

		/**
		 * Given a binary search tree, we know that 
		 * the maximum element would be the right most node
		 * @return
		 */
		public int findMax() {
			
			Node x = root;
			
			while(x.right != null) {
				x = x.right;
			}
			
			return x.key ;
		}

		/**
		 * Given a binary search tree, we know that the minimum element 
		 * would be the left most node
		 * @return
		 */
		public int findMin() {
			
			Node x = root;
			
			while(x.left!=null) {
				x = x.left;
			}
			return x.key;
		}


		/**
		 * Floor refers to the first node less than or equal to the given num.
		 * 
		 * Return -1 if there is no floor value
		 * @param num
		 * @return
		 */
		public int findFloor(int num) {
			
			Node x = floor(root, num);
			
			if(x == null)  return -1;
			
			return x.key;
		}

		
		/**
		 * Ceiling refers to the immediate number greater than or equal to the given number
		 * 
		 * Return -1 if there is no such value in the tree
		 * 
		 * @param num
		 * @return
		 */
		public int findCeiling(int num) {
			
			Node x = ceiling(root, num);
			
			if(x == null) return -1;
			
			return x.key;
		}

		
		
		/**
		 * Rank of a node is the number of elements lesser than that given node in the tree
		 * @param num
		 * @return
		 */
		public int findRank(int num) {
			
			return rank(root, num);
			
		}

			
		/**
		 * End of BST class --------------- x ----------------------- x -----------------
		 */
}

public class binarySearchTree{
	
	public static void main(String[] args) {
		
		// create a BST object
		BST bst = new BST();
		
		bst.insert(8); 
		bst.insert(6); 
        bst.insert(4); 
        bst.insert(2); 
        bst.insert(1); 
        bst.insert(3); 
        bst.insert(5);
        bst.insert(7);
		
		System.out.printf("The maximum element in the tree is %s \n", bst.findMax());
		
		System.out.printf("The minimum element in the tree is %s \n", bst.findMin());
		
		Scanner sc = new Scanner(System.in);
		
//		System.out.print("Enter a number to search the floor of : ");
//		
//		int num = sc.nextInt();
//		
//		System.out.printf("The element lesser than or equal to %s is %s \n", num , bst.findFloor(num));
//
//		
//		System.out.print("Enter a number to search the ceiling of : ");
//		
//		num = sc.nextInt();
//		
//		System.out.printf("The element greater than or equal to %s is %s \n", num , bst.findCeiling(num));
//		
		System.out.println("Let's look at the rank of the elments in the tree ");
		
		for(int i = 1; i <= 8 ; i++) {
			System.out.printf("The rank of %s in the tree is %s \n", i , bst.findRank(i));
		}
		
		
		
		sc.close();

	}
}

