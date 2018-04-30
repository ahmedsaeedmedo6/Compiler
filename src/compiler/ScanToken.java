/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;
/**
 *
 * @author lenovo G50-80
 */
public class ScanToken {
    private final int[][] Transitions;
    private final char[]  Inputs;
    private final boolean[] Acceptances;
    private final boolean IsComplementary;
    private boolean FoundError;
    private boolean Acceptance;
    private boolean AcceptAny;
    private boolean fine;
    private String  lexeme;
    private int     CurrentState;
    public  int Line=0;
    private TokenDictionary Dictionary=new TokenDictionary();
    
    
    
    public ScanToken(int[][] Transitions , char[]  Inputs , boolean IsComplementary  , boolean[] Acceptances)
    {
        this.Transitions=Transitions;
        this.Inputs=Inputs;
        this.IsComplementary=IsComplementary;
        this.Acceptances=Acceptances;
        this.FoundError=false;
        this.Acceptance=false;
        this.AcceptAny=false;
        this.fine=false;
        this.lexeme="";
        this.CurrentState=1;
        Dictionary.put("BluePrint", "Class");
        Dictionary.put("InNMN", "Inheritance");
        Dictionary.put("ERo", "Condition");
        Dictionary.put("Plow", "Condition");
        Dictionary.put("IPOL", "Integer");
        Dictionary.put("SiPOsL", "SInteger");
        Dictionary.put("Grp", "Character");
        Dictionary.put("Folp", "String");
        Dictionary.put("Shrk", "Float");
        Dictionary.put("Derp", "SFloat");
        Dictionary.put("EQl", "Void");
        Dictionary.put("Bplo", "Boolean");
        Dictionary.put("Spt", "stop");
        Dictionary.put("DpiWhen", "Loop");
        Dictionary.put("LPwhen", "Loop");
        Dictionary.put("Retu", "Return");
        Dictionary.put("ZQe", "Switch");
        Dictionary.put("KLO", "Switch");
        Dictionary.put("Pero", "Stat Statement");
        Dictionary.put("Fine", "End Statement");
        Dictionary.put("+", "Arithmetic Operation");
        Dictionary.put("-", "Arithmetic Operation");
        Dictionary.put("*", "Arithmetic Operation");
        Dictionary.put("/", "Arithmetic Operation");
        Dictionary.put("&&", "Logic operators ");
        Dictionary.put("||", "Logic operators ");
        Dictionary.put("~", "Logic operators ");
        Dictionary.put("==", "relational operators");
        Dictionary.put("<=", "relational operators");
        Dictionary.put(">=", "relational operators");
        Dictionary.put("!=", "relational operators");
        Dictionary.put(">", "relational operators");
        Dictionary.put("<", "relational operators");
        Dictionary.put("=", "Assignment operator");
        Dictionary.put(".", "Access Operator");
        Dictionary.put("}", "Braces");
        Dictionary.put("{", "Braces");
        Dictionary.put("]", "Braces");
        Dictionary.put("[", "Braces");
        Dictionary.put("(", "Braces");
        Dictionary.put(")", "Braces");
        Dictionary.put("\"", "Quotation Mark");
        Dictionary.put("\'", "Quotation Mark");
        Dictionary.put(",", "comma");
        Dictionary.put(";", "semicolon");
        Dictionary.put("Having", "Inclusion");
    }
    
    
    private int getIndexChar(char ch)
    {
        for(int i=0;i<Inputs.length;i++)
        {
            if(ch == Inputs[i])
            {
                return i;
            }
        }
        return -1;
    }
    
    
    public String getToken()
    {
         String Token = Dictionary.get(lexeme);
         if(Token == null)
         {
             char ch=lexeme.charAt(0);
             if(ch=='^')
                return "Single line Comment";
             if(ch=='<')
                return "multiple line Comment";
             if(Character.isDigit(ch))
                return "Digit";
             else
                return "Identifier";
         }
         return Token;
    }
    
    public String getReturnToken()
    {
         String Token = Dictionary.get(lexeme);
         if(Token == null)
         {
             char ch=lexeme.charAt(0);
             if(ch=='^')
                return null;
             if(ch=='<')
                return null;
             if(Character.isDigit(ch))
                return "Number";
             else
                return "ID";
         }
         return lexeme;
    }
    
    public void clear()
    {
        this.FoundError=false;
        this.lexeme="";
        this.CurrentState=1;
        this.Acceptance=false;
        this.AcceptAny=false;
        this.fine=false;
    }
    
    
    public boolean canContinue()
    {
        return !this.FoundError;
    }
    
    
    public boolean isFine()
    {
        return this.fine;
    }
    
    
    public String getLexeme()
    {
        return this.lexeme;
    }
    
   
    
    
    public boolean match(char ch)
    {
        
        if(lexeme.length()==0 &&(ch==' ' || ch=='\t' ||ch=='\n') )
            return true;
        boolean match=true;
        if(this.AcceptAny && ch != '\n' && ch !='^' && ch !='>')
        {
            this.lexeme+=ch;
        }
        else
        {
            
            int index = this.getIndexChar(ch);
            int State;
            
            if(index != -1)
            {
                State=this.Transitions[this.CurrentState-1][index]; 
            }
            else
            {
                State=0;
            }
            this.AcceptAny=false;
            if(this.CurrentState==30 && ch !='^' && ch != '>' && ch != '\n' )
            {
                this.AcceptAny=true; 
                this.CurrentState=29;
                this.lexeme+=ch;
                return true;
            }
            if(State != 0)
            {
                if(State < 0)
                {
                   this.AcceptAny=true; 
                   State*=-1;
                }
                
               
                
                boolean Accept=this.Acceptances[State-1];
                this.CurrentState=State;
                this.Acceptance=Accept;
                if(ch!=' ' && ch !='\t' && ch!= '\n')
                    this.lexeme+=ch;
            }
            else if(this.Acceptance)
            {
                if(ch != ' ' && ch != '\t' && !this.IsComplementary)
                {
                    this.FoundError=true;
                    if(ch!=' ' && ch !='\t' && ch!= '\n')
                        this.lexeme+=ch;
                }
                else if(ch == ' ' || ch == '\t')
                {
                    this.fine=true;
                }
                else 
                {
                    this.fine=true;
                    match=false;
                }
            }
            else if(ch != '\n')
            {
                this.FoundError=true;
                this.lexeme+=ch;
            }
        }
        return match;
        
    }
     
    
}
