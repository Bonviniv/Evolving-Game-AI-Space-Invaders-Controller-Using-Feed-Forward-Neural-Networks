package utilis;
import java.util.List;

import nn.NeuralNetwork;
import space.Board;

public class QuickSort
{
    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    static int partition(double arr[], int low, int high, NeuralNetwork[] nn) {
        double pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;
 
                // swap arr[i] and arr[j]
                double temp = arr[i];
                NeuralNetwork tempnn=nn[i];
                
                arr[i] = arr[j];     
                nn[i]= nn[j];
                
                arr[j] = temp;
                nn[j]= tempnn;
            }
        }
 
        // swap arr[i+1] and arr[high] (or pivot)
        double temp = arr[i+1];
        NeuralNetwork tempnn=nn[i+1];
        
        arr[i+1] = arr[high];
        nn[i+1]=nn[high];
        
        arr[high] = temp;
        nn[high]= tempnn;
        return i+1;
    }
 
 
    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    public static void sort(NeuralNetwork[] nn, int low, int high){
    	
    	double[]arr=new double[nn.length];
    	
    	for (int i = 0; i < nn.length; i++) {
			arr[i]=nn[i].getFitness();
			
		}
		
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high,nn);
 
            // Recursively sort elements before
            // partition and after partition
            sort(nn, low, pi-1);
            sort(nn, pi+1, high);
        }
    }
 
    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
 
//    // Driver program
//    public static void main(String args[])
//    {
//        int arr[] = {10, 7, 8, 9, 1, 5};
//        int n = arr.length;
// 
//        QuickSort ob = new QuickSort();
//        ob.sort(arr, 0, n-1);
// 
//        System.out.println("sorted array");
//        printArray(arr);
//    }
}
