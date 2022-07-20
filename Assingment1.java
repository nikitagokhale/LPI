import java.util.*;


public class Assignment1
{
	public class Table_Node
	{
		int service_time, wait_time;
	}

	public static void main(String [] args)
	{
		Table_Node[] table;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter no of processes:- ");
		int no_processes = sc.nextInt();
		System.out.println();
		table = new Table_Node[no_processes];
		System.out.println("Enter the service times:-");
		for(int i=0;i<no_processes;i++)
		{
			System.out.print(">");
			table[i+1].service_time = sc.nextInt();
			System.out.println();			
		}
	}
}
