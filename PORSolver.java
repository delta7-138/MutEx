import java.util.*; 

public class PORSolver extends Solver
{
    PORSolver(GlobalState init)
    {
        super(init); 
    }

    public Boolean calcDependency(ASTNode instr , List<ASTNode>persList)
    {
        if(persList.size() == 0)
            return true; 
            
        for(ASTNode i : persList)
        {
            if(i.left.val.name.equals(instr.left.val.name))
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

            if(calcDependency(cloneState.pList.get(i).getCurrInstr() , persInstr))
            {
                //add only if dependent
                persInstr.add(cloneState.pList.get(i).getCurrInstr());
                cloneState.incrEval(i);
                persList.add(cloneState); 
            }
        }
        return persList; 
    }
}

