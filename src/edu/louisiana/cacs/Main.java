package edu.louisiana.cacs;

import edu.louisiana.cacs.csce450.Parser;

public class Main{
    public static void main(String[] args){
       
	System.out.println("Hello World from Main");
        Parser myParser = new Parser(args[0]);
        myParser.readAction();
        myParser.parse();
        
    }
}
