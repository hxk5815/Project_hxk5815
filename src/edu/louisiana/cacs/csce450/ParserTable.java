package edu.louisiana.cacs.csce450;

import java.util.ArrayList;
import java.util.Arrays;

public class ParserTable {

	private String[] Header = {"Step","Stack","Input","action lookup",
					  "action Value","value of LHS","len of RHS",
					  "temp Stack","goto lookup","gotovalue","stack action",
					  "Parse Tree Stack"};
	private ArrayList< String[] > list;
	public ParserTable() {
		list = new ArrayList<String[]>();
	}
	
	public void add(int step,int col,String Value){
		
		if(step > list.size()){
			String[] arr = new String[12];
			Arrays.fill(arr, "");
			list.add(arr);
		}
		
		String[] tmp = list.get(step-1);
		tmp[col] = Value;
	}
	
	
	public void print(){
		for (String string : Header) {
			System.out.printf("%-13s",string);
		}
		System.out.println();
		
		for(String[] arr:list ){
			for (String string : arr) {
				System.out.printf("%-13s",string);
			}
			System.out.println();
		}
	}
	public void print(int l){
		for (String string : Header) {
			System.out.printf("%-13s",string);
		}
		System.out.println();
		
		String[] arr = list.get(l);
			for (String string : arr) {
				System.out.printf("%-13s",string);
			}
			System.out.println();
		
	}
}
