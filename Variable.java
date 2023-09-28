import java.util.*; 

public class Variable 
{
    public String name; 
    public int val;
    public Boolean isConstant = false; 
    
    Variable(String name)
    {
        this.name = name; 
        this.val = 0;
    }

    public void setConst()
    {
        this.isConstant = true; 
    }

    Variable(String name, int val)
    {
        this.name = name; 
        this.val = val; 
    }
    Variable()
    {}

    public Variable clone()
    {
        Variable v = new Variable();
        v.name = this.name; 
        v.val = this.val;
        return v;  
    }
    public void print()
    {
        System.out.println(this.name + " = " + this.val); 
    }

    public String getName()
    {
        return this.name; 
    }

    public int get()
    {
        return this.val; 
    }

    public void set(int x)
    {
        this.val = x; 
    }
}
