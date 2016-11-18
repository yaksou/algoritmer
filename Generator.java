
import java.util.*;
public class Generator
{	
	public static int [] generateArray( int size, int min,	int max) 
	{
		int[] returnArray = new int[size];
		
		if(size > max-min+1)
			return null;
		
		for(int i = 0; i < size; i++)
		{
			int temp = (int)(Math.random()*(max- min +1) + min);
			
			while(find(returnArray,temp))
				temp = (int)(Math.random()*(max- min +1) + min);
		
			returnArray[i]=temp;
		}
		
		
		return returnArray;
	}
	
	public static boolean find(int[] arr, int x)
	{
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i] == x) return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		int[] a = generateArray(7,6,8);
		System.out.println(Arrays.toString(a));
	}
}
