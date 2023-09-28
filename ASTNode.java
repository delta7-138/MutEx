import java.util.*; 

public class ASTNode
{
    public char op; 
    public ASTNode left;
    public ASTNode right; 
    public int level; 
    public  Variable val; 
    public Boolean isOperator; 

    ASTNode(char op)
    {
        this.op = op; 
        this.val = new Variable("" , 0);  
        this.isOperator = true; 
        this.level = 0; 
    }

    ASTNode(Variable val)
    {
        this.val = val; 
        this.op = 'n'; 
        this.isOperator = false; 
        this.level = 0; 
    }

    public void setLR(ASTNode l , ASTNode r)
    {
        this.left = l; 
        this.right = r; 
    }

    public void print()
    {
        for(int i = 0; i<level; i++)
        {
            System.out.print(" "); 
        }

        if(this.isOperator)
        {
            System.out.println(this.op); 
        }
        else
        {
            if(this.val.isConstant)
            {
                System.out.println(this.val.get()); 
            }
            else
            {
                System.out.println(this.val.getName());
            }
            return ;  
        }
        this.left.setLevel(level + 1);
        this.right.setLevel(level + 1);
        this.left.print(); 
        this.right.print(); 
    }

    public void setLevel(int l)
    {
        this.level = l; 
    }

    public int oprEval()
    {
        switch(this.op)
        {
            case '+': 
            return this.left.oprEval() + this.right.oprEval(); 

            case '-': 
            return this.left.oprEval() - this.right.oprEval(); 

            case '*': 
            return this.left.oprEval() * this.right.oprEval(); 

            case '/': 
            return this.left.oprEval() / this.right.oprEval(); 


            default: 
            return this.val.get();  
        }   
    }

    public Variable assignEval()
    {
        this.left.val.set(this.right.val.get()); 
        return this.left.val;
    }

}