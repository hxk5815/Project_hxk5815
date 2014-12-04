package edu.louisiana.cacs.csce450;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Grammer {
	
	private Map<Integer,String > lhs = null;
	private Map<Integer,List<String> > rhs = null;
	private BufferedReader br = null;
	public Grammer(String FileName){
		
		try {
			br=new BufferedReader(new FileReader(new File(FileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lhs = new TreeMap<Integer, String>();
		rhs = new TreeMap<Integer, List<String> >();
	}
	
	public void read(){
		String line;
		try {
			int cnt =1;
			while( (line = br.readLine()) != null){
				StringBuilder sb = new StringBuilder();
				ArrayList<String> t= new ArrayList<String>();
				
				for(int i=0; i<line.length(); ++i){
					char ch = line.charAt(i);
					switch(ch){
						
					case '+':
					case '*':
					case '(':
					case ')':
						if(sb.length() > 0){
							t.add(sb.toString());
							sb.delete(0, sb.length());
						}
						t.add(ch+"");
						break;
						
					case '-':
						lhs.put(cnt, sb.toString());
						sb.delete(0, sb.length());
						i++;
						break;
					default:
						if(Character.isLetter(ch)){
							sb.append(ch);
						}
						if(i == line.length()-1){
							t.add(sb.toString());
							sb.delete(0, sb.length());
						}
					}
					
					
				}
				rhs.put(cnt, t);
				cnt++;
				//System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String lhs(int line){
		
		return lhs.get(line);
	}
	
	public List<String> rhs(int line){
		return rhs.get(line);
	}
	
	public void print(){
		
		int size = lhs.size();
		for(int i=1; i <=size; i++){
			System.out.println(lhs.get(i)+"->"+rhs.get(i).toString());
		}
	}
}
