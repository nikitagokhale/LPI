import java.util.*;
import java.io.*;

class NodeMNT
{
	String macroName;
	int pp=0, kp=0, p_kpdtab, p_MDT;
}

class NodeKPDTAB
{
	String par1, par2;
}

class MacroDataStructsPass2
{
	ArrayList<String> MDT = new ArrayList<String>();
	ArrayList<NodeMNT> MNT = new ArrayList<NodeMNT>();
	ArrayList<NodeKPDTAB> KPDTAB = new ArrayList<NodeKPDTAB>();
	ArrayList<String> Pass2OutputTokens = new ArrayList<String>();
	
	void processStart(ArrayList<String> callArgs, String macroName) throws IOException
	{
		NodeMNT tempMNT = macroIndex(macroName);
		String[] aptab = new String[tempMNT.kp+tempMNT.pp];
		for(int i=0;i<tempMNT.pp;i++)
		{
			aptab[i] = callArgs.get(0);
			callArgs.remove(0);
		}
		
		int p_kpdtab = tempMNT.p_kpdtab;
		int j = tempMNT.pp;
		for(int i=0;i<tempMNT.kp;i++)
		{
			aptab[j] = KPDTAB.get(p_kpdtab).par2;
			j++;
			p_kpdtab++;
		}
		
		for(int i=0;i<callArgs.size();i++)
		{
			String[] args = callArgs.get(i).split("=");
			int index = tempMNT.pp + searchKPDTAB(args[0]) - tempMNT.p_kpdtab + 1;
			aptab[index-1] = args[1];
		}
		
		Pass2OutputTokens.add(macroName);
		Pass2OutputTokens.add(";");
		for(int i=tempMNT.p_MDT;i<MDT.size();i++)
		{
			String[] words = MDT.get(i).split(" ");
			if(words[words.length-1].equalsIgnoreCase("mend"))
				break;
			for(j=1;j<words.length;j++)
			{
				if(words[j].contains("(P,"))
				{
					int indexAPTAB = Integer.parseInt(String.valueOf(words[j].charAt(3)));
					Pass2OutputTokens.add(aptab[indexAPTAB-1]);
				}
				else
					Pass2OutputTokens.add(words[j]);
			}
			Pass2OutputTokens.add(";");
		}
	}
	
	void readPass1Output() throws FileNotFoundException
	{
		File readMNT = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroPass1/MNT.txt");
		Scanner scMNT = new Scanner(readMNT);
		while(scMNT.hasNextLine())
		{
			NodeMNT tempMNT = new NodeMNT();
			String[] line = scMNT.nextLine().split("\t");
			tempMNT.macroName = line[1];
			tempMNT.pp = Integer.parseInt(line[2]);
			tempMNT.kp = Integer.parseInt(line[3]);
			tempMNT.p_MDT = Integer.parseInt(line[4])-1;
			tempMNT.p_kpdtab = Integer.parseInt(line[5])-1;
			MNT.add(tempMNT);
		}
		scMNT.close();
		
		if(KPDTABFile())
		{
			File readKPDTAB = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroPass1/KPDTAB.txt");
			Scanner scKPDTAB = new Scanner(readKPDTAB);
			while(scKPDTAB.hasNextLine())
			{
				NodeKPDTAB tempKPDTAB = new NodeKPDTAB();
				String[] line = scKPDTAB.nextLine().split("\t");
				tempKPDTAB.par1 = line[1];
				tempKPDTAB.par2 = line[2];
				KPDTAB.add(tempKPDTAB);
			}
			scKPDTAB.close();
		}
		
		File readMDT = new File("C:\\Users\\manjo\\eclipse-workspace\\MacroPass1/MDT.txt");
		Scanner scMDT = new Scanner(readMDT);
		while(scMDT.hasNextLine())
		{
			MDT.add(scMDT.nextLine());
		}
		scMDT.close();
	}

	void printMDTPass2() throws IOException
	{
		for(int k=0;k<Pass2OutputTokens.size();k++)
		{
			System.out.println(Pass2OutputTokens.get(k));
		}
		FileWriter writeMDTPass2 = new FileWriter("Pass2Output.txt");
		for(int k=0;k<Pass2OutputTokens.size();k++)
		{
			writeMDTPass2.write("+ ");
			while(!Pass2OutputTokens.get(k).equalsIgnoreCase(";"))
			{
				String line1 = Pass2OutputTokens.get(k) + " ";
				writeMDTPass2.write(line1);
				k++;
			}
			writeMDTPass2.write("\n");
		}
		writeMDTPass2.close();
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
	
	int searchKPDTAB(String par1)
	{
		for(int i=0;i<KPDTAB.size();i++)
		{
			if(KPDTAB.get(i).par1.equalsIgnoreCase(par1))
				return i;
		}
		return -1;
	}
	
	NodeMNT macroIndex(String token)
	{
		for(int i=0;i<MNT.size();i++)
		{
			if(MNT.get(i).macroName.equalsIgnoreCase(token))
				return MNT.get(i);
		}
		return null;
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
}

public class Assignment2Pass2
{
	public static void main(String[] args) throws IOException
	{
		MacroDataStructsPass2 obj = new MacroDataStructsPass2();
		obj.readPass1Output();
		File readCalls = new File("C:\\\\Users\\\\manjo\\\\eclipse-workspace\\\\MacroPass1/Calls.txt");
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
		scCalls.close();
		obj.printMDTPass2();
	}
}
