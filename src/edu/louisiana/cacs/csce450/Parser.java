package edu.louisiana.cacs.csce450;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class Parser{
	
	/*
	* YOUR CODE GOES HERE
	* 
	* You must implement two methods
	* 1. parse
	* 2. printParseTree
     
    * Print the intermediate states of the parsing process,
    * including the intermediate states of the parse tree,make
    * as specified in the class handout.
    * If the input is legal according to the grammar,
    * print ACCEPT, else UNGRAMMATICAL.
    * If the parse is successful, print the final parse tree.

	* You can modify the input and output of these function but not the name
	*/

	private Scanner scanner  = null;
	private CSV_Table actionTable = null;
	private CSV_Table goToTable   = null;
	private Grammer grammer = null;
	private ParserTable table = null;

	private Stack<String> LRStack 	= null;
	private Stack<Node> TreeStack = null;
	private Stack<String> tmpStock  = null;
	private Queue<String> input = null;
	
	int step =1;
	private class Node{
		
		public ArrayList<Node> Children = null;
		public Map<Integer,String> mapping = null;
		public String Value="";

		public  Node(String v) {
			Children = new ArrayList<Node>();
			mapping = new HashMap<Integer, String>();
			Value = v;
		}
		
		public void removeChildren(String k){
			Children.remove(k);
			Map<Integer, String> tmp = new HashMap<Integer, String>();
			//System.out.println(mapping.toString());
			for(int i=0; i< mapping.size(); i++){
				if(!mapping.get(i).equals(k)){
					tmp.put(tmp.size(), mapping.get(i));
				}
			}
			mapping=tmp;
			
		}
		

		public String toString(){
			
			String result ="";
		
			
			if(Children.isEmpty()){
				result= Value;
			}else{
				
				
				if(!Value.equalsIgnoreCase("root")){
					result += "["+Value;
					
					for(int i=0; i<Children.size();++i){
						result+=Children.get(i).toString();
					}
					
					result+="]";
				}else{
					
					for(int i=0; i<Children.size();++i){
						result+=Children.get(i).toString();
					}
					


				}
	
			}
			return result;
			
		}
	}
	
	private Node root;
	
	public Parser(String fileName){
		System.out.println("File to parse : "+fileName);
		scanner = new Scanner(fileName);
		LRStack = new Stack<String>();
		TreeStack = new Stack<Node>();
		input = new ArrayDeque<String>();
		tmpStock= new Stack<String>();
		table = new ParserTable();
		root =new Node("root");
	}
	
	
	public void printParseTree(int indent,Node node){
		
		for(int i=0;i<indent*2;++i)
			System.out.print(" ");
		
		System.out.println(node.Value);
		for(Node n: node.Children){
			printParseTree(indent+1,n);
		}
	}

	/*
	* Dummy code
	*/
	public void parse(){
		
		int NextToken = Scanner.UNKNOWN;
		
		while(NextToken != Scanner.EOF){
			LRStack.add(Integer.toString(0));
			do{
				NextToken = scanner.getToken();
				
			String token = null;
			switch(NextToken){
			
			case Scanner.DIGIT:
			{
				StringBuffer sb = new StringBuffer();
				while(NextToken != Scanner.DIGIT){
					sb.append((char)scanner.curr_char);
				}
				token = sb.toString();
				scanner.setBackToken((char) Scanner.curr_char);
			}
				break;
				
			case Scanner.LETTER:
			{
				StringBuffer sb = new StringBuffer();
				while(NextToken == Scanner.DIGIT || NextToken == Scanner.LETTER){
					sb.append((char)scanner.curr_char);
					NextToken = scanner.getToken();
					
				}
				token = sb.toString();
				
				scanner.setBackToken((char) Scanner.curr_char);
				NextToken =Scanner.LETTER;
			}
				break;
				
			case Scanner.NEWLINE:
			case Scanner.IDENT:
				
				break;
				
			case Scanner.ADD_OP:
			case Scanner.MULT_OP:
			case Scanner.RIGHT_PAREN:
			case Scanner.LEFT_PAREN:
			case Scanner.DOLLAR:
				token=((char)scanner.curr_char)+"";
				break;
				
			default:
			
				break;
				
			}

			if(token != null)
				input.add(token);

		
			}while(NextToken != Scanner.DOLLAR && NextToken != Scanner.EOF );

		if(!input.isEmpty()){
		LRParser();
		table.print();
		printParseTree(0,TreeStack.pop());
		}
		System.out.println("---------------------------------");
		LRStack.clear();
		input.clear();
		
		}

      
	}
	
	public boolean LRParser(){

		try{
		while(true){
			table.add(step, 2, UtilToString(input));
			
			String id="";
			try{
			id= input.element();
			}catch(NoSuchElementException e){
				System.out.println("Error herew");
				table.add(step, 4, "error In grammer");
				//System.exit(1);
				break;
			}
			table.add(step, 0, Integer.toString(step));
			
			int a = Integer.parseInt(LRStack.lastElement());
			
			table.add(step, 1, UtilToString(LRStack));
			table.add(step, 3, "["+a+","+id+"]");
			String action_value = actionTable.get(a, id);
			
			if(action_value.trim().isEmpty()){
				table.add(step, 4, "error In grammer");
				break;
			}
			table.add(step, 4, action_value);
			char action = action_value.charAt(0);
			String value  = action_value.substring(1);
			
			if(action=='s'){
				tmpStock.clear();
				tmpStock = (Stack<String>) LRStack.clone();
				Shift(input,step,Integer.parseInt(value+""));
				
			}
			else if(action=='r'){
				int result = Reduce(input,step,Integer.parseInt(value+""));
				if(result == -99){
					table.add(step, 9, "error In grammer");
					break;
				}
				
			}
			else if(action=='s'){
				break;
			}else {
				break;
			}
			//table.print(step-1);
			step++;
		}
		}catch(Exception e){
			e.printStackTrace();
			table.print();
		}
		return true;
		
		
	}

	public void readAction(){
		actionTable = new CSV_Table("data/action.txt",12,6);
		actionTable.read();
		//actionTable.print();
		
		goToTable = new CSV_Table("data/Goto.csv", 12, 3);
		goToTable.read();
		//goToTable.print();
		
		grammer = new Grammer("data/grammer.txt");
		grammer.read();
		//grammer.print();
		
	}
	
	
	
	public String ActionLookup(String value,String id){
		
		int v = Integer.parseInt(value);
		return actionTable.get(v, id);
		
	}
	
	
	public int GotoLookup(String value,String id){
		int v = Integer.parseInt(value);
		String goto_value = goToTable.get(v, id);
		try{
		return Integer.parseInt(goto_value);
		}catch(NumberFormatException e){
		return 0;
		}
	}
	
	public int Shift(Queue<String> input,int step,int value){

		String id = input.poll();

		table.add(step, 10, "Push"+id+value+"");
		
		if(!id.equals("+") && !id.equals("*") && !id.equals("(") && !id.equals(")")){
          
			Node n = new Node(id);
			
			
			TreeStack.push(n);
		}
		
			table.add(step, 11, UtilToString(TreeStack));
		//System.out.println(id);
		LRStack.push(id);
		LRStack.push(value+"");
		//LRParser(input, step);
		return step+1;
	}
	
	public int Reduce(Queue<String> input,int step,int value){
		
		String id = input.element();
		int index = Integer.parseInt(value+"");
		String lhs = grammer.lhs(index);
		table.add(step, 5, lhs);
		List<String> rhs = grammer.rhs(index);
		int rhs_len = rhs.size();
		table.add(step, 6, Integer.toString(rhs_len));
		
		String s="";
		for(int i=0; i<rhs_len*2;i++){
			s+=LRStack.pop();
		}

		table.add(step, 7, UtilToString(LRStack));
		String l = LRStack.lastElement();
		table.add(step, 8, "["+l+","+lhs+"]");
		int gotoValue = GotoLookup(l, lhs);
		if(gotoValue == 0){
			return -99;
		}
		
		table.add(step, 9, gotoValue+"");
		
		
		table.add(step, 10, "Push"+lhs+gotoValue+"");
		
		Node n = new Node(lhs);
		Node[] tmpL = new Node[rhs_len];
		
	    for(int i=0; i<rhs.size();i++){
	    	
	    	if(rhs.get(i).equals("*") | rhs.get(i).equals("+") |rhs.get(i).equals("(")|rhs.get(i).equals(")")){
	    		tmpL[i] = new Node(rhs.get(i));
	    		continue;
	    	}
	    	Node tmp = TreeStack.pop();
	    	tmpL[rhs.indexOf(tmp.Value)] = tmp;
	    }
	    
	    for(int i=0; i<rhs.size();i++){
	    	
	    	n.Children.add(tmpL[i]); 
	    }
	    
	    TreeStack.push(n);
	    
		table.add(step, 11, UtilToString(TreeStack));


		
		
		//table.add(step, 11, UtilToString(TreeStack));
		LRStack.push(lhs);
		LRStack.push(Integer.toString(gotoValue));
		
		
		
		return step+1;
	}
	
	public int GOTO(int a,String id){

		String goto_value = goToTable.get(a, id);
		int result=0;
		try{
		result = Integer.parseInt(goto_value);
		}catch(NumberFormatException e){

		}
		return result;
	}
	
	public String UtilToString(Collection<String> list){
		StringBuilder sb = new StringBuilder();
		
		for (String string : list) {
			sb.append(string);
		}
		return sb.toString();
	}
	
	public String UtilToString(Stack<Node> list){
		StringBuilder sb = new StringBuilder();
		
		for (Node n : list) {
			sb.append(n.toString());
		}
		return sb.toString();
	}

	
}
