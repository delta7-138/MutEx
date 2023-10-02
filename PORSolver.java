import java.util.*;

public class PORSolver extends Solver
{ 
    PORSolver(GlobalState init)
    {
        super(init);  
    }

    public Boolean calcDependency(GlobalState currState , int tinx , int tdashinx)
    {
        Process tProcess = currState.pList.get(tinx); 
        Process tdashProcess = currState.pList.get(tdashinx); 

        ASTNode tProcessInstr = tProcess.getCurrInstr(); 
        ASTNode tdashProcessInstr = tdashProcess.getCurrInstr(); 

        //conflicting transitions
        if(tProcessInstr.left.val.name.equals(tdashProcessInstr.left.val.name))
        {
            return true;
        }

        //parallel and independent
        if(!tProcess.lastUseMap.containsKey(tdashProcessInstr.left.val.name) || !tdashProcess.lastUseMap.containsKey(tProcessInstr.left.val.name))
        {
            return false; 
        }

        //parallel and can-be-dependent 
        if(tProcess.lastUseMap.get(tdashProcessInstr.left.val.name) > tProcess.PC || tdashProcess.lastUseMap.get(tProcessInstr.left.val.name) > tdashProcess.PC)
        {
            return true; 
        }

        //parallel and independent
        return false; 
    }

    @Override
    public List<GlobalState> calcTranSet(GlobalState currState)
    {
        List<Integer>persIndexList = new ArrayList<Integer>(); 
        List<GlobalState> stateSet = new ArrayList<GlobalState>(); 
        Set<Integer>controlSet = new HashSet<Integer>();

        for(int i = 0; i<currState.pList.size(); i++)
        {
            if(!currState.pList.get(i).EOP())
            {
                persIndexList.add(i); 
                controlSet.add(i); 
                break;
            }
        }

        if(persIndexList.size() == 0)
            return stateSet; 

        for(int i = 0; i<persIndexList.size(); i++)
        {
            int persInx = persIndexList.get(i); 
            Process controlProc = currState.pList.get(persInx); 
            for(int j = 0; j<currState.pList.size(); j++)
            {
                Process compProc = currState.pList.get(j); 
                if(persInx == j || controlSet.contains(j) || compProc.EOP() || controlProc.EOP())
                {
                    continue;
                }

                if(calcDependency(currState, persInx, j))
                {
                    persIndexList.add(j); 
                    controlSet.add(j); 
                }
            }
        }

        for(int i : persIndexList)
        {
            GlobalState cloneState = new GlobalState(currState); 
            cloneState.incrEval(i);
            stateSet.add(cloneState); 
        }
        return stateSet; 
    }
}

