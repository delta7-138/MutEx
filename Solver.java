import java.util.*;


public abstract class Solver
{
    protected GlobalState initState;
    protected HashMap<String, GlobalState>StateMap; 
    protected HashMap<String , Set<String>>StateGraph; 
    protected int numStates; 

    Solver(GlobalState gbstate)
    {   
        this.initState = gbstate; 
        this.StateMap = new HashMap<String , GlobalState>(); 
        this.StateGraph = new HashMap<String , Set<String>>(); 
    }

    public void printFinalStates()
    {
        int ctr = 0; 
        for(Map.Entry<String , GlobalState> set: this.StateMap.entrySet())
        {
            Boolean finalState = true;
            for(Process p: set.getValue().pList)
            {
                if(!p.EOP())
                {
                    finalState = false; 
                }
            }
            
            if(finalState)
            {
                System.out.println(set.getValue().serialize());
                ctr++; 
            }

        }

        System.out.println(ctr); 
    }

    public void printAllStates()
    {
        int ctr = 0; 
        for(Map.Entry<String , GlobalState> set: this.StateMap.entrySet())
        {
            System.out.println(set.getValue().serialize()); 
            ctr++;
        }
        this.numStates = ctr; 
    }

    public void printNumStates()
    {
        this.numStates = this.StateMap.size(); 
        System.out.println(this.numStates); 
    }


    public void printStateGraph()
    {
        for(Map.Entry<String , Set<String>> set: this.StateGraph.entrySet())
        {
            System.out.print(set.getKey() + " ---> "); 
            for(String ch : set.getValue())
            {
                System.out.print(ch + "|"); 
            }
            System.out.println("END"); 
        }
    }

    public List<GlobalState> calcTranSet(GlobalState currState)
    {
        List<GlobalState>transList = new ArrayList<GlobalState>(); 
        /*
         * Create new global state with current state value
         * eval if not EOP
         * increment PC
         * add the new state to stack
         * do this for every process
        */

        for(int i = 0; i<currState.pList.size(); i++){
            GlobalState clone = new GlobalState(currState);  
            if(clone.pList.get(i).EOP())
            {
                continue; 
            }
            clone.incrEval(i);
            transList.add(clone);
        }
        return transList; 
    } 

    public void solve()
    {
        Stack<GlobalState>stateSearchStack = new Stack<GlobalState>(); 
        stateSearchStack.push(initState); 

        while(!stateSearchStack.isEmpty())
        {
            GlobalState topNode = stateSearchStack.pop(); 
            if(!StateMap.containsKey(topNode.serialize()))
            {
                StateMap.put(topNode.serialize() , topNode);

                Set<String>childNodes = new HashSet<String>(); 
                List<GlobalState>tList = calcTranSet(topNode); 

                for(GlobalState s : tList)
                {
                    childNodes.add(s.serialize()); 
                    stateSearchStack.push(s); 
                }

                StateGraph.put(topNode.serialize() , childNodes); 
            }
        }
    }
}