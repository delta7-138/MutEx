import java.util.*;

public class PORSolver extends Solver
{
    //private HashMap<String , HashMap<String , Integer>>lastUseMapinState; 
    PORSolver(GlobalState init)
    {
        super(init); 
        //lastUseMapinState = new HashMap<String , HashMap<String , Integer>>(); 
    }

    public Boolean calcDependency(Process currProcess , ASTNode instr , List<ASTNode>persList , List<GlobalState> perState)
    {
        if(persList.size() == 0)
            return true; 
            
        for(int i = 0; i<persList.size(); i++)
        {
            //conflicting
            ASTNode compInstr = persList.get(i); 
            GlobalState compState = perState.get(i); 
    
            if(compInstr.left.val.name.equals(instr.left.val.name))
            {
                return true; 
            }
            
            Process processSel = compState.getProcess(); 
            if(currProcess.lastUseMap.get(instr.left.val.name) > processSel.PC || currProcess.PC < processSel.lastUseMap.get(instr.left.val.name))
            {
                return true; 
            }
        }

        return false; 
    }

    @Override
    public List<GlobalState> calcTranSet(GlobalState currState)
    {

        List<GlobalState>persList = new ArrayList<GlobalState>(); 
        List<ASTNode>persInstr = new ArrayList<ASTNode>(); 

        for(int i = 0; i<currState.pList.size(); i++)
        {
            GlobalState cloneState = new GlobalState(currState); 
            if(cloneState.pList.get(i).EOP())
            {
                continue; 
            }

            if(calcDependency(cloneState.pList.get(i) , cloneState.pList.get(i).getCurrInstr() , persInstr , persList))
            {
                //add only if can be dependent or conflicting
                cloneState.ProcessIndex = i; 
                persInstr.add(cloneState.pList.get(i).getCurrInstr());
                persList.add(cloneState); 
            }
        }

        for(GlobalState state : persList)
        {
            state.incrEval(state.ProcessIndex);
        }
        return persList; 
    }
}

