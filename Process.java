import java.util.*; 
import java.io.*; 

public class Process
{
    public String name; 
    public List<ASTNode>instrList; 
    public int PC; //index in instrlist; 

    Process(String name)
    {
        this.name = name; 
        this.PC = 0; 
        this.instrList = new ArrayList<ASTNode>(); 
    }

    Process(Process b)
    {
        this.PC = b.PC;
        this.name = b.name; 
        this.instrList = new ArrayList<ASTNode>(b.instrList); 
    }
    Process(){
        
    }

    public Process clone()
    {
        Process p = new Process(); 
        p.name = this.name; 
        p.PC = this.PC; 
        p.instrList = this.instrList; 
        return p; 
    }

    public Variable eval()
    {
        ASTNode currInstr = this.instrList.get(this.PC); 
        this.PC++; 
        return currInstr.assignEval(); 
    }

    public ASTNode getCurrInstr()
    {
        return this.instrList.get(this.PC); 
    }

    public Boolean EOP()
    {
        return this.PC >= this.instrList.size(); 
    }

    public void printAST()
    {
        int ctr = 0; 
        for(ASTNode node : this.instrList)
        {
            System.out.println(ctr); 
            node.print();
            System.out.println(); 
            ctr++; 
        }
        System.out.println("-----------------------"); 
    }

    public void print()
    {
        System.out.println("Process <" + this.name + ">; PC = " + this.PC); 
    }

    public int precendence(char op)
    {
        switch(op)
        {
            case '+': 
            case '-': 
            return 0; 

            case '*': 
            case '/': 
            return 1;  
        }
        return -1; 
    }

    public void createAstNodeOp(String line , HashMap<String , Variable>varmap)
    {
        String [] lexlist = line.split(" ");
        Stack<Character>opStack = new Stack<Character>(); 
        List<ASTNode>outList = new ArrayList<ASTNode>(); 
        Stack<ASTNode>outStack = new Stack<ASTNode>(); 
        //output queue
        //operator stack
        //Shunting Yard

        for(String lex: lexlist)
        {
            if(lex.equals("+") || lex.equals("-" ) || lex.equals("*") || lex.equals("/"))
            {
                while(!opStack.isEmpty() && precendence(opStack.peek()) >= precendence(lex.charAt(0))){
                    char ch = opStack.pop(); 
                    ASTNode opNode = new ASTNode(ch); 
                    outList.add(opNode); 
                }
                opStack.push(lex.charAt(0)); 
            }
            else
            {
                ASTNode varNode = new ASTNode(varmap.get(lex)); 
                outList.add(varNode); 
            }
        }
        for(int i = 0; i<outList.size(); i++)
        {
            if(!outList.get(i).isOperator)
            {
                outStack.push(outList.get(i)); 
            }
            else
            {
                ASTNode left = outStack.pop(); 
                ASTNode right = outStack.pop(); 
                outList.get(i).setLR(left, right);
                outStack.push(outList.get(i)); 
            }
        }

        this.instrList.add(outStack.peek()); 
    }

    public void assign(String line , HashMap<String , Variable>varmap)
    {
        String [] lexlist = line.split(" ");
        //System.out.println(line);
        ASTNode asNode = new ASTNode('=');
        ASTNode left = new ASTNode(varmap.get(lexlist[0])); 
        Variable constVar = new Variable("" , Integer.parseInt(lexlist[1])); 
        constVar.setConst();
        ASTNode right = new ASTNode(constVar);  
        asNode.setLR(left, right); 
        this.instrList.add(asNode); 
    }
}