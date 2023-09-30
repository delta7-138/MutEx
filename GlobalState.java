import java.util.*; 

public class GlobalState
{
    public List<Process>pList; 
    public HashMap<String , Variable>vMap; 
    public int ProcessIndex = 0; 

    GlobalState(List<Process>pList , HashMap<String , Variable>vMap)
    {
        this.pList = new ArrayList<Process>(); 
        for(Process p: pList)
        {
            this.pList.add(p.clone());  
        } 
        this.vMap = new HashMap<String , Variable>(); 
        vMap.forEach((key , value) -> this.vMap.put(key , value.clone()));
    }

    GlobalState(GlobalState b)
    {
        this.pList = new ArrayList<Process>(); 
        for(Process p: b.pList)
        {
            this.pList.add(p.clone());  
        } 
        this.vMap = new HashMap<String , Variable>(); 
        b.vMap.forEach((key , value) -> this.vMap.put(key , value.clone()));
    }

    GlobalState()
    {

    }

    public Process getProcess()
    {
        return this.pList.get(this.ProcessIndex); 
    }
    
    public String serialize()
    {
        String res = ""; 
        for(Process p : this.pList)
        {
            res = res + p.name + " PC : " + p.PC + "; "; 
        }

        for(Map.Entry<String , Variable> set: this.vMap.entrySet())
        {
            res = res + set.getKey() + " = " + set.getValue().val + "; "; 
        }
        return res; 
    }

    public void print()
    {
        for(Process p : this.pList)
        {
            p.print(); 
        }
        
        this.vMap.forEach((key , value) -> value.print());
        System.out.println("------------------"); 
    }

    public void incrEval(int inx)
    {
        Process p = this.pList.get(inx); 
        if(p.EOP())
        {
            return; 
        }
        Variable res = p.eval(); 
        this.vMap.put(res.getName() , res.clone()); 
    }
}
