import java.util.*; 
import java.io.*; 

public class Test
{
    public int varNum; 
    public int procNum; 
    public List<String>testStringList; 
    private List<String>varNameList; 
    private List<String>procNameList; 
    public int LOC; 

    Test(int v , int p)
    {
        this.varNum = v; 
        this.procNum = p; 
        this.testStringList = new ArrayList<String>(); 
        this.varNameList = new ArrayList<String>(); 
        this.procNameList = new ArrayList<String>(); 
    }

    Test(List<String>vList , List<String>pList , int LOC)
    {
        this.varNameList = vList; 
        this.procNameList = pList; 
        this.varNum = vList.size(); 
        this.procNum = pList.size(); 
        this.LOC = LOC; 
    }


    public void testGen()
    {
        
    }
}