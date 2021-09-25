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
			 * If num is lesser than the value of the given node, 
			 * see if you can find rank for num in
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
		 * We find the kth smallest element using the rank of each node
		 * 
		 * The kth smallest element is the element whose rank is k-1
		 * 
		 * Or the k smallest element is the element whose count+1 = k
		 * 
		 * @param x
		 * @param k
		 * @return
		 */
		private int kSmall(Node x, int k) {
			
			// since we need the smallest node 
			// we keep track of the number of elements in the left sub tree
			
			// for example if the left subtree has 5 elements, 
			// the 1st, 2nd, 3rd, 4th and the 5th smallest elements would
			// be present in that sub tree

			// if the tree is empty - return null
			if(x == null) return -1;
			
			int leftCount = size(x.left);
			
			// if the value of k = count of elements in the left sub tree + 1
			// this means that there are k-1 elements in the left sub tree
			// and the current node is the kth smallest node
			// this is because in a bst, the nodes to the left of any node
			// are smaller than the current node
			if ( k == leftCount + 1) return x.key;
			
			// if we notice that there are lesser elements in the left sub tree
			// than the value of k, then there is no use of spending time
			// trying to find that element in the left sub tree
			// 
			// For example, if we need to find the 7th smallest element in the trr
			// and if the root node is the 4th smallest element, the 7th smallest element
			// would be in the right sub tree
			// 
			// Also when we move to the right sub tree, 
			// rather than finding the 7th smallest element in the whole tree
			// we can find the 3rd largest element in the right sub tree under 
			// the root node
			
			// this value of 3 comes from 
			// k(7) - (leftSubTreeCount(3) + 1(rootNode)) = 3
			
			// so we reset the value of k before proceeding to search for that
			// element in the left sub tree
			else if (k > leftCount +1) {
				
				k = k - (size(x.left) + 1);
				
				return kSmall(x.right, k);
			}
			
			// if none of the above conditions hold true
			// the number of elements in the left subtree is greater than the 
			// value of k
			// Example: we need the 3rd smallest element, and there are 
			// 5 elements in the left subtree
			// so we just jump into the left sub tree and proceed
			else return kSmall(x.left, k);
			
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

		public int kthSmallest(int k) {
			
			return kSmall(root, k);
			
		}


			
		/**
		 * End of BST class --------------- x ----------------------- x -----------------
		 */
}

public class binarySearchTree{
	
	public static void main(String[] args) {
		
		// create a BST object
		BST bst = new BST();
		
		int keys[] = { 20, 8, 22, 4, 12, 10, 14 };
		
		for (int x : keys)	bst.insert(x);
		
		System.out.printf("The maximum element in the tree is %s \n", bst.findMax());
		
		System.out.printf("The minimum element in the tree is %s \n", bst.findMin());
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter a number to search the floor of : ");
		
		int num = sc.nextInt();
		
		System.out.printf("The element lesser than or equal to %s is %s \n", num , bst.findFloor(num));

		
		System.out.print("Enter a number to search the ceiling of : ");
		
		num = sc.nextInt();
		
		System.out.printf("The element greater than or equal to %s is %s \n", num , bst.findCeiling(num));
		
		System.out.println("Let's look at the rank of the elments in the tree ");
		
		for (int x : keys) {
			System.out.printf("The rank of %s in the tree is %s \n", x , bst.findRank(x));
		}
		
		System.out.print("Enter the value of k: ");
		int k = sc.nextInt();
		
		System.out.printf("Enter the value of %s smallest element is : %s",k, bst.kthSmallest(k));;

		
		sc.close();

	}
}


