import java.util.*; 
import java.io.*; 

public class Main
{
    public static List<Process>globalProcessList; 
    public static HashMap<String , Variable>globalVariableMap; 

    public static Process parse(String prog , String processName)
    {
        Process proc = new Process(processName); 
        //File file = new File(new File("").getAbsolutePath() + "/" +  processName + ".txt"); 
        Scanner scn = new Scanner(prog); 

        while(scn.hasNextLine())
        {
            proc.assign(scn.nextLine() , Main.globalVariableMap);
        }

        scn.close();

        return proc; 
    }


    public static void main(String [] args)
    {
        //String [] processNameList = {"P1" , "P2"};
        Main.globalProcessList = new ArrayList<Process>(); 
        Main.globalVariableMap = new HashMap<String , Variable>(); 

        //File file = new File("var.txt");
        // Scanner scn = new Scanner("rrrr"); 
        // String varString = scn.nextLine(); 
        // scn.close(); 

        String [] varNameList = {"x" , "y"}; 
        String [] processNameList = {"P1" , "P2"}; 
        String [] processList = {"x 1\nx 3" , "y 1\ny 2"}; 
        for(String varn: varNameList)
        {
            Main.globalVariableMap.put(varn , new Variable(varn)); 
        }
         
        for(int i = 0; i<processList.length; i++)
        {
            Main.globalProcessList.add(parse(processList[i] , processNameList[i])); 
        }

        GlobalState init = new GlobalState(Main.globalProcessList , Main.globalVariableMap); 
        SelSearch saul = new SelSearch(init); 
        //PORSolver saul = new PORSolver(init); 
        saul.solve(); 
        //saul.printFinalStates();
        saul.printStateGraph();
    }
}