import java.util.*;

class Table_Node
{
	int arrival_time, exe_time, service_time, wait_time;
}

public class Assignment1
{
	void FCFS()
	{
		Table_Node obj_FCFS = new Table_Node();
		
	}
	
	public static void main(String [] args)
	{
		Assignment1 obj_main = new Assignment1();
		Scanner sc = new Scanner(System.in);
		int choice=0;
		while(choice!=5)
		{
			System.out.print("MAIN MENU\n1. FCFS.\n2. SJF (Preemptive).\n3. Priority (Non-Preemptive).\n4. Round Robin (Preemptive).\n5.EXIT.\nEnter your choice:- ");
			choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				obj_main.FCFS();
				break;
				
			case 5:
				System.out.println("Thank you!");
				break;
				
			default:
				System.out.println("Please enter a valid choice!");
				break;
			}
		}
	}
}
