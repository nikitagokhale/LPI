import java.util.*;
import java.io.*;

class NodeMNTPass2
{
	String macroName;
	int pp=0, kp=0, p_kpdtab, p_MDT;
}

class NodeKPDTABPass2
{
	String par1, par2;
}

class MacroDataStructsPass2
{
	ArrayList<String> MDT = new ArrayList<String>();
	ArrayList<NodeMNTPass2> MNT = new ArrayList<NodeMNTPass2>();
	ArrayList<NodeKPDTABPass2> KPDTAB = new ArrayList<NodeKPDTABPass2>();
	ArrayList<String> MDTPass2 = new ArrayList<String>();
	
	void processStart(ArrayList<String> callArgs, String macroName) throws IOException
	{
		NodeMNTPass2 tempMNT = macroIndex(macroName);
		String[] aptab = new String[tempMNT.kp+tempMNT.pp];
		for(int i=0;i<tempMNT.pp;i++)
		{
			aptab[i] = callArgs.get(0);
			callArgs.remove(0);
		}	
		int p_kpdtab = tempMNT.p_kpdtab - 1;
		int p_MDT = tempMNT.p_MDT - 1;
		int j = tempMNT.pp;
		for(int i=0;i<tempMNT.kp;i++)
		{
			aptab[j] = KPDTAB.get(p_kpdtab).par2;
			j++;
			p_kpdtab++;
		}
		p_kpdtab = tempMNT.p_kpdtab - 1;
		for(int i=0;i<callArgs.size();i++)
		{
			String[] args = callArgs.get(i).split("=");
			int index = tempMNT.pp + searchKPDTAB(args[0]) - p_kpdtab + 1;
			aptab[index-1] = args[1];
		}
		MDTPass2.add(macroName);
		MDTPass2.add(";");
		for(int i=p_MDT;i<MDT.size();i++)
		{
			String[] words = MDT.get(i).split(" ");
			if(words[words.length-1].equalsIgnoreCase("mend"))
				break;
			for(int k=1;k<words.length;k++)
			{
				if(words[k].contains("(P,"))
				{
					int indexAPTAB = Integer.parseInt(String.valueOf(words[k].charAt(3)));
					MDTPass2.add(aptab[indexAPTAB-1]);
				}
				else

					MDTPass2.add(words[k]);
			}
			MDTPass2.add(";");
		}
	}
	
	int searchKPDTAB(String par1)
	{
		for(int i=0;i<KPDTAB.size();i++)
		{
			if(KPDTAB.get(i).par1.equalsIgnoreCase(par1))
				return i;
		}
		return -1;
	}
	
	NodeMNTPass2 macroIndex(String token)
	{
		for(int i=0;i<MNT.size();i++)
		{
			if(MNT.get(i).macroName.equalsIgnoreCase(token))
				return MNT.get(i);
		}
		return null;
	}
	
	boolean containsMacro(String token)
	{
		for(int i=0;i<MNT.size();i++)
		{
			if(MNT.get(i).macroName.equalsIgnoreCase(token))
				return true;
		}
		return false;
	}
	
	boolean KPDTABFile()
	{
		for(int i=0;i<MNT.size();i++)
		{
			if(MNT.get(i).kp != 0)
				return true;
		}
		return false;
	}
	
	void readPass1Output() throws FileNotFoundException
	{
		File readMNT = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroAssignment/MNT.txt");
		Scanner scMNT = new Scanner(readMNT);
		while(scMNT.hasNextLine())
		{
			NodeMNTPass2 tempMNT = new NodeMNTPass2();
			String[] line = scMNT.nextLine().split("\t");
			tempMNT.macroName = line[1];
			tempMNT.pp = Integer.parseInt(line[2]);
			tempMNT.kp = Integer.parseInt(line[3]);
			tempMNT.p_MDT = Integer.parseInt(line[4]);
			tempMNT.p_kpdtab = Integer.parseInt(line[5]);
			MNT.add(tempMNT);
		}
		scMNT.close();
		
		if(KPDTABFile())
		{
			File readKPDTAB = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroAssignment/KPDTAB.txt");
			Scanner scKPDTAB = new Scanner(readKPDTAB);
			while(scKPDTAB.hasNextLine())
			{
				NodeKPDTABPass2 tempKPDTAB = new NodeKPDTABPass2();
				String[] line = scKPDTAB.nextLine().split("\t");
				tempKPDTAB.par1 = line[1];
				tempKPDTAB.par2 = line[2];
				KPDTAB.add(tempKPDTAB);
			}
			scKPDTAB.close();
		}
		
		File readMDT = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroAssignment/MDT.txt");
		Scanner scMDT = new Scanner(readMDT);
		while(scMDT.hasNextLine())
		{
			MDT.add(scMDT.nextLine());
		}
		scMDT.close();
	}

	void printMDTPass2() throws IOException
	{
		for(int k=0;k<MDTPass2.size();k++)
		{
			System.out.println(MDTPass2.get(k));
		}
		FileWriter writeMDTPass2 = new FileWriter("Pass2Output.txt");
		for(int k=0;k<MDTPass2.size();k++)
		{
			writeMDTPass2.write("+ ");
			while(!MDTPass2.get(k).equalsIgnoreCase(";"))
			{
				String line1 = MDTPass2.get(k) + " ";
				writeMDTPass2.write(line1);
				k++;
			}
			writeMDTPass2.write("\n");
		}
		writeMDTPass2.close();
	}
}

public class Assignment4
{
	public static void main(String[] args) throws IOException
	{
		MacroDataStructsPass2 obj = new MacroDataStructsPass2();
		obj.readPass1Output();
		File readCalls = new File("C:\\\\Users\\\\manjo\\\\eclipse-workspace\\\\MacroAssignment/Calls.txt");
		Scanner scCalls = new Scanner(readCalls);
		while(scCalls.hasNextLine())
		{
			String[] line = scCalls.nextLine().split(" ");
			if(obj.containsMacro(line[1]))
			{
				ArrayList<String> call = new ArrayList<String>();
				for(int i=2;i<line.length-1;i++)
					call.add(line[i]);
				obj.processStart(call, line[1]);
			}
			else
				System.out.println("Macro " + line[1] + "not found!");
		}
		obj.printMDTPass2();
		scCalls.close();
	}
}
