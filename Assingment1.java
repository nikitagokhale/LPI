import java.util.*;

public class Assignment1
{
	int arrival_time, exe_time, service_time, wait_time, ;
	
	void FCFS()
	{
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
