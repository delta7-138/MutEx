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

        proc.preProcess();
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

        /**
         * Test 3
         * */ 
        // String [] varNameList = {"x" , "y" , "z" , "w"}; 
        // String [] processNameList = {"P1" , "P2" , "P3" , "P4" , "P5"}; 
        // String [] processList = {"x 1\ny 3\nz 2\ny 9\nx 23\nz 9" , "w 34\ny 1\nz 2\nx 4\ny 12" , "x 8\nz 9\nw 12" , "y 6\nx 19\nw 34\nz 18\nx 11\ny 91\nx 18" , "w 7\nz 65\nx 23\ny 56"};
        // 125.25399060017779 % of sel search time

        /**
         * Test 1
         */
        // String [] varNameList = {"x" , "y"}; 
        // String [] processNameList = {"P1" , "P2"}; 
        // String [] processList = {"x 1\ny 3" , "y 2\nx 4"}; //3.47 % of sel search time

        /**
         * Test 2
         */
        // String [] varNameList = {"x" , "y" , "z"}; 
        // String [] processNameList = {"P1" , "P2" , "P3" , "P4"}; 
        // String [] processList = {"x 1\ny 3\nz 2\ny 9\nx 23\nz 9" , "y 1\nz 2\nx 4\ny 12" , "y 8\nz 9\nx 12" , "x 6\nz 19\ny 34"}; // 42.72701629461129 % of sel search time

        /**
         * Trivial Test 1
         */
        // String [] varNameList = {"x" , "y" , "z" , "w"}; 
        // String [] processNameList = {"P1" , "P2" , "P3" , "P4"}; 
        // String [] processList = {"x 1\nx 3\nx 2\nx 9" , "y 1\ny 2\ny 4\ny 12" , "z 8\nz 9\nz 12" , "w 6\nw 19\nw 34\nw 18"}; // 0.7377401191563621 % of sel search time

        /**
         * Trivial Test 1 - mutation
         *  */ 
        String [] varNameList = {"x" , "y" , "z" , "w"}; 
        String [] processNameList = {"P1" , "P2" , "P3" , "P4"}; 
        String [] processList = {"x 1\nz 3\ny 2\nx 9" , "y 1\ny 2\ny 4\ny 12" , "x 8\nz 9\ny 12" , "w 6\nw 19\nw 34\nw 18"}; //7.250304641819727 % of sel search time


        for(String varn: varNameList)
        {
            Main.globalVariableMap.put(varn , new Variable(varn)); 
        }
         
        for(int i = 0; i<processList.length; i++)
        {
            Main.globalProcessList.add(parse(processList[i] , processNameList[i])); 
        }

        GlobalState init = new GlobalState(Main.globalProcessList , Main.globalVariableMap); 
        SelSearch saul1 = new SelSearch(init); 
        PORSolver saul2 = new PORSolver(init); 
        long selStart = System.nanoTime(); 
        saul1.solve();
        long selEnd = System.nanoTime();

        long porStart = System.nanoTime(); 
        saul2.solve(); 
        long porEnd = System.nanoTime(); 

        System.out.println("Selective Search Time taken : " + (selEnd - selStart)); 
        System.out.println("Search with P.O.R. Time taken : " + (porEnd - porStart)); 
        saul1.printFinalStates(); 
        saul2.printFinalStates();
        //saul.printStateGraph();
        saul1.printNumStates();
        saul2.printNumStates();
    }
}