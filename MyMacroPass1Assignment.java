import java.io.*;
import java.util.*;

class NodeKPDTAB
{
	String par1, par2;
}

class NodeMNT
{
	String macroName;
	int pp=0, kp=0, p_kpdtab, p_MDT;
}

class MacroDataStructsPass1
{
	ArrayList<NodeKPDTAB> KPDTAB = new ArrayList<NodeKPDTAB>();
	ArrayList<String> MDT = new ArrayList<String>();
	ArrayList<NodeMNT> MNT = new ArrayList<NodeMNT>();
	int k=1;
	
	void processStart(ArrayList<String> Token)
	{
		int i=0;
		ArrayList<String> pntab = new ArrayList<String>();
		NodeMNT tempMNT = new NodeMNT();
		tempMNT.macroName = Token.get(i);
		tempMNT.p_kpdtab = KPDTAB.size();
		tempMNT.p_MDT = MDT.size();
		while(true)
		{
			i++;
			String word = Token.get(i);
			if(word.equalsIgnoreCase(";"))
			{
				i++;
				break;
			}
			if(word.contains("="))
			{
				NodeKPDTAB tempKPDTAB = new NodeKPDTAB();
				tempMNT.kp++;
				if(word.charAt(word.length()-1) == '=')
				{
					String newWord = word.substring(0, word.length()-1);
					tempKPDTAB.par1 = newWord;
					tempKPDTAB.par2 = "null";
				}
				else
				{
					String[] newWord = word.split("=");
					tempKPDTAB.par1 = newWord[0];
					tempKPDTAB.par2 = newWord[1];
				}
				pntab.add(tempKPDTAB.par1);
				KPDTAB.add(tempKPDTAB);
			}
			else
			{
				pntab.add(word);
				tempMNT.pp++;
			}
		}
		int j;
		for(j=i;j<Token.size();j++)
		{
			String line = Integer.toString(k)+". ";
			while(!Token.get(j).equalsIgnoreCase(";"))
			{
				if(Token.get(j).equalsIgnoreCase("mend"))
				{
					line = line + Token.get(j);
					j++;
					break;
				}
				if(Token.get(j).charAt(0) == '&')
				{
					String pnToken = "(P, " + (pntab.indexOf(Token.get(j))+1) + ")";
					line = line + pnToken + " ";
					j++;
				}
				else
				{
					line = line + Token.get(j) + " ";
					j++;
				}
			}
			k++;
			line = line + "\n";
			MDT.add(line);
		}
		if(tempMNT.kp == 0)
			tempMNT.p_kpdtab = -1;
		MNT.add(tempMNT);
	}
	
	void writeInFile() throws IOException
	{
		FileWriter writeMNT = new FileWriter("MNT.txt");
		for(int i=0;i<MNT.size();i++)
		{
			NodeMNT tempMNT = new NodeMNT();
			tempMNT = MNT.get(i);
			String line = Integer.toString(i+1) + ".\t" + tempMNT.macroName + "\t" + Integer.toString(tempMNT.pp) + "\t" + Integer.toString(tempMNT.kp) + "\t" + Integer.toString(tempMNT.p_MDT + 1) + "\t" + Integer.toString(tempMNT.p_kpdtab + 1) + "\n";
			writeMNT.write(line);
		}
		writeMNT.close();
		if(KPDTAB.size()!=0)
		{
			FileWriter writeKPDTAB = new FileWriter("KPDTAB.txt");
			for(int i=0;i<KPDTAB.size();i++)
			{
				NodeKPDTAB tempKPDTAB = new NodeKPDTAB();
				tempKPDTAB = KPDTAB.get(i);
				String line = Integer.toString(i+1) + ".\t" + tempKPDTAB.par1 + "\t" + tempKPDTAB.par2+ "\n";
				writeKPDTAB.write(line);
			}
			writeKPDTAB.close();
		}
		
		FileWriter writeMDT = new FileWriter("MDT.txt");
		for(int i=0;i<MDT.size();i++)
		{
			writeMDT.write(MDT.get(i));
		}
		writeMDT.close();
	}
}

public class Assignment3 
{
	void printArrayList(ArrayList<NodeKPDTAB> arr)
	{
		for(int i=0;i<arr.size();i++)
			System.out.println(arr.get(i).par1 +" "+ arr.get(i).par2);
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException
	{
		File myFile = new File("C:\\Users\\manjo\\eclipse-workspace/Macro.txt");
		Scanner sc = new Scanner(myFile);
		MacroDataStructsPass1 obj = new MacroDataStructsPass1();
		ArrayList<String> question = new ArrayList<String>();
		ArrayList<String> calls = new ArrayList<String>();
		while(sc.hasNext())
			question.add(sc.next());
		int j=1;
		for(int i=0;i<question.size();i++)
		{
			if(question.get(i).equalsIgnoreCase("macro"))
			{
				ArrayList<String> tokens = new ArrayList<String>();
				boolean flag = true;
				while(flag)
				{
					i++;
					tokens.add(question.get(i));
					if(question.get(i).equalsIgnoreCase("mend"))
						flag = false;
				}
				obj.processStart(tokens);
			}
			if(question.get(i).equalsIgnoreCase("call"))
			{
				boolean flag = true;
				String line = Integer.toString(j)+". ";
				j++;
				while(flag)
				{
					i++;
					line = line + question.get(i) + " ";
					if(question.get(i).equalsIgnoreCase(";"))
						flag = false;
				}
				calls.add(line);
			}
		}
		FileWriter writeCalls = new FileWriter("Calls.txt");
		for(int i=0;i<calls.size();i++)
			writeCalls.write(calls.get(i)+"\n");
		writeCalls.close();
		try
		{
			obj.writeInFile();
		}
		catch(IOException e)
		{
			System.out.print("Some error occurred while writing in the file");
			e.printStackTrace();
		}
		sc.close();
	}
}
