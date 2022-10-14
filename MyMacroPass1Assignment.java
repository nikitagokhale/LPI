import java.io.*;
import java.util.*;

class NodeKPDTAB
{
	
}

class MacroDataStructs
{
	LinkedList<NodeKPDTAB> kpdtab = new LinkedList<NodeKPDTAB>();
	
}

public class Pass1 {

	public static void main(String[] args) throws FileNotFoundException
	{
		File myFile = new File("/home/pict/Documents/31130_Nikita/MyMacroInput.txt");
		Scanner sc = new Scanner(myFile);
		ArrayList<String> tokens = new ArrayList<String>();
		MacroDataStructs obj;
		while(sc.hasNext())
		{
			tokens.add(sc.next());
		}
		if(tokens.get(0).toLowerCase() == "macro")
			obj = new MacroDataStructs();
	}

}
