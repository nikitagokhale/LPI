import java.util.*;

class Table_Node
{
	int service_time, wait_time;
}

public class Assignment1
{
	public static void main(String [] args)
	{
		int sum = 0;
		Table_Node temp = new Table_Node();
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
			temp.service_time = sc.nextInt();
			table[i] = temp;
		}
		for(int i=0;i<no_processes;i++)
		{
			table
			sum = table[i].service_time
		}
	}
}
