package edu.louisiana.cacs.csce450;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSV_Table {

	private String filename = "";
	private String[][] table = null;
	private int numCols;
	private int numRows;
	private Map<String,Integer> attribute;
	
	public CSV_Table(String filename,int numRows,int numCols){
		this.filename = filename;
		table = new String[numRows][numCols];
		attribute = new HashMap<String,Integer>();
		this.numRows = numRows;
		this.numCols = numCols;
	}
	
	public void read(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String Header = br.readLine();
			
			String atts[] = Header.split(",");
			for(int i=1; i<atts.length; ++i ){
				attribute.put(atts[i], i-1);
			}
			
			String Line = null;
			while( (Line=br.readLine())!=null){
				String[] values = Line.split(",");
				for (int i = 1; i < values.length; i++) {
					set(Integer.parseInt(values[0]),i-1,values[i]);
					//System.out.print(values[i]+"\t|");
				}
				
				//System.out.println("");
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String get(int x,int y){
		
		String result = "";
		if(x >= 0 && x <numRows && y >=0 && y < numCols){
			result = table[x][y];
		}
		return result;
	}
	
	public String get(int x,String attr){
		
		int y = attribute.get(attr);

		/*String result = "";
		if(x >= 0 && x <numRows && y >=0 && y < numCols){
			result = table[x][y];
		}*/
		return get(x,y);
	} 
	
	public void set(int x,int y,String Value){

		if(x >= 0 && x <numRows && y >=0 && y < numCols){
			table[x][y]=Value;
		}
		
	}
	
	public void print(){
		System.out.println("=========================================================================");
		System.out.println(filename);
		System.out.println("=========================================================================");
		for(int i=0; i <numRows; ++i){
			for(int j=0; j< numCols; ++j){
				System.out.print(table[i][j]+"\t|");
			}
			System.out.println();
		}
	}
	
}
