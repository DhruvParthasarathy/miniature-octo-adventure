package algos;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

	public static void main(String[] args) {
		
		Random rand = new Random();

		int[] nums = new int[10];
		
		for(int i = 0; i < nums.length ; i++) {
			nums[i] = rand.nextInt(100);
		}
		
		System.out.printf("Input array: %s \n",Arrays.toString(nums));
		
		mergeSort(nums);
		
		System.out.printf("Sorted array: %s \n",Arrays.toString(nums));
		
		System.gc();
	}

	/**
	 * This algorithm uses a divide and conquer strategy, where we keep dividing the array in a recursive manner
	 * till we reach individual elements.
	 * 
	 * Post that we start combining the individual elements with the help of ausiliary arrays
	 */
	static void mergeSort(int[] nums) {
		
		int start = 0;
		int end = nums.length -1;
		
		
		divideAndConquer(nums, start, end);
			
	}

	/**
	 * This is the core logic of the merge sort algorithm
	 * We start by recursively splitting the array into a left sub half and a right sub half 
	 * And before we return, we use the sorting order to merge the left sub half and the right sub half into the main array
	 * 
	 * @param nums
	 * @param start
	 * @param end
	 */
	static void divideAndConquer(int[] nums, int start, int end) {
		
		int mid = start + (end - start)/2;
		
		if(start < mid) {
			divideAndConquer(nums, start, mid);
		}
		
		if(mid+1 < end) {
			divideAndConquer(nums, mid+1, end);
		}
		
		
		conquer(nums, start, mid, end);
		
		
	}

	static void conquer(int[] nums, int start, int mid, int end) {
		
		int leftSize = mid - start + 1;
		int rightSize = end - mid;
		
		/*Creating temp arrays*/
		int[] leftArr = new int[leftSize];
		int[] rightArr = new int[rightSize];
		
		/*Populating temp arrays*/
		for(int i = 0 ; i < leftSize; i++) {
			leftArr[i] = nums[start + i];
		}
		
		for(int j = 0; j < rightSize; j++ ) {
			rightArr[j] = nums[mid + 1 + j];
		}
		
		
		/*Initializing variables
		 * k is the index for the main array
		 * i is the index for the left sub array
		 * j is the index for the right sub array
		 */
		int k = start, i = 0, j = 0;
		
		/* This loop will run till either the left part or the right part reaches its end
		 * This contains the sort order logic
		 */
		while(i < leftSize && j < rightSize) {
			if(leftArr[i] < rightArr[j]) {
				nums[k] = leftArr[i];
				i++;
			}
			else {
				nums[k] = rightArr[j];
				j++;
			}
			
			k++;
		}
		
		/* When the above while loop ends,
		 * if either the left half and he right half are of unequal lengths, 
		 * then there would be some elements which we have not compared
		 * We just extract the remaining portion of the arrays and place them as they are */ 
		
		while(i < leftSize) {
			nums[k] = leftArr[i];
			i++;
			k++;
		}
		
		while(j < rightSize ) {
			nums[k] = rightArr[j];
			j++; 
			k++;
		}
		
		
		
	}

}
