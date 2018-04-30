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
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LexicalAnalysis test =new LexicalAnalysis();
        test.scanProject("test.txt");
        TokenHolder temp;
        while((temp=test.nextToken())!=null)
        {
            System.out.println(temp.token);
        }
    }
    
}
