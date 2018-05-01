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
        TokenDictionary.put("BluePrint", "Class");
        TokenDictionary.put("InNMN", "Inheritance");
        TokenDictionary.put("ERo", "Condition");
        TokenDictionary.put("Plow", "Condition");
        TokenDictionary.put("IPOL", "Integer");
        TokenDictionary.put("SiPOsL", "SInteger");
        TokenDictionary.put("Grp", "Character");
        TokenDictionary.put("Folp", "String");
        TokenDictionary.put("Shrk", "Float");
        TokenDictionary.put("Derp", "SFloat");
        TokenDictionary.put("EQl", "Void");
        TokenDictionary.put("Bplo", "Boolean");
        TokenDictionary.put("Spt", "stop");
        TokenDictionary.put("DpiWhen", "Loop");
        TokenDictionary.put("LPwhen", "Loop");
        TokenDictionary.put("Retu", "Return");
        TokenDictionary.put("ZQe", "Switch");
        TokenDictionary.put("KLO", "Switch");
        TokenDictionary.put("Pero", "Stat Statement");
        TokenDictionary.put("Fine", "End Statement");
        TokenDictionary.put("+", "Arithmetic Operation");
        TokenDictionary.put("-", "Arithmetic Operation");
        TokenDictionary.put("*", "Arithmetic Operation");
        TokenDictionary.put("/", "Arithmetic Operation");
        TokenDictionary.put("&&", "Logic operators ");
        TokenDictionary.put("||", "Logic operators ");
        TokenDictionary.put("~", "Logic operators ");
        TokenDictionary.put("==", "relational operators");
        TokenDictionary.put("<=", "relational operators");
        TokenDictionary.put(">=", "relational operators");
        TokenDictionary.put("!=", "relational operators");
        TokenDictionary.put(">", "relational operators");
        TokenDictionary.put("<", "relational operators");
        TokenDictionary.put("=", "Assignment operator");
        TokenDictionary.put(".", "Access Operator");
        TokenDictionary.put("}", "Braces");
        TokenDictionary.put("{", "Braces");
        TokenDictionary.put("]", "Braces");
        TokenDictionary.put("[", "Braces");
        TokenDictionary.put("(", "Braces");
        TokenDictionary.put(")", "Braces");
        TokenDictionary.put("\"", "Quotation Mark");
        TokenDictionary.put("\'", "Quotation Mark");
        TokenDictionary.put(",", "comma");
        TokenDictionary.put(";", "semicolon");
        TokenDictionary.put("Having", "Inclusion");
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
         String Token = TokenDictionary.get(lexeme);
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
         String Token = TokenDictionary.get(lexeme);
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
