/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;

/**
 *
 * @author lenovo G50-80
 */
public class TokenDictionary  {
    private ArrayList<Token> Dictionary=new ArrayList<>();
    
    
    private boolean equal(String str1,String str2)
    {
        if(str1.length()!=str2.length())
            return false;
        else
        {
            for(int i=0;i<str1.length();i++)
            {
                if(str1.charAt(i)!=str2.charAt(i))
                {
                    return false;
                }
            }
        }
        return true;
        
    }
    
    public void put(String key , String value)
    {
        boolean found=false;
        for(int i=0;i<Dictionary.size();i++)
        {
            if(this.equal(key, Dictionary.get(i).key))
            {
                found=true;
            }
        }
        if(!found)
        {
            Token temp=new Token();
            temp.key=key;
            temp.value=value;
            Dictionary.add(temp);
        }
    }
    
    
    public String get(String key)
    {
        for(int i=0;i<Dictionary.size();i++)
        {
            if(this.equal(key, Dictionary.get(i).key))
            {
                return Dictionary.get(i).value;
            }
        }
        return null;
    }
            
            
            
    private class Token
    {
        public String key;
        public String value;
    }
}
