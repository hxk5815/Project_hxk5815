package edu.louisiana.cacs.csce450;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
public class Scanner {
	
//Character classes
	static final int LETTER = 0;
	static final int DIGIT 	 = 1;
	static final int UNKNOWN = 99;
	static final int  EOF 	 = -1;
	static final int ERROR   = -2;

//Token codes
	static final int NEWLINE 	 = 10;
	static final int IDENT		 = 11;
	static final int ASSIGN_OP	 = 20;
	static final int ADD_OP 	 = 21;
	static final int SUB_OP 	 = 22;
	static final int MULT_OP 	 = 23;
	static final int DIV_OP 	 = 24;
	static final int LEFT_PAREN  = 25;
	static final int RIGHT_PAREN = 26;
	static final int DOLLAR = 36;
		
	public static int curr_type;
	public static int curr_char;
	public PushbackReader pbIn;
	public Scanner(String fileName){
		try {
			pbIn = new PushbackReader(new FileReader(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  int getToken() {
		char c=(char) EOF;
		try {
			c = (char)pbIn.read();

			//while((c = (char)pbIn.read())==' ');

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(c){
		case '*': Scanner.curr_char=c; return MULT_OP;
		case '+': Scanner.curr_char=c; return ADD_OP;
		case '(': Scanner.curr_char=c; return LEFT_PAREN;
		case ')': Scanner.curr_char=c; return RIGHT_PAREN;
		case '$': Scanner.curr_char=c; return DOLLAR;
		case (char)32:
		case (char)8:
		case (char)9: Scanner.curr_char=c;return IDENT;
		case '\n': Scanner.curr_char=c; return NEWLINE;
		case (char) EOF : Scanner.curr_char=c;return EOF;
		
		default: if(Character.isLetter(c)){
			Scanner.curr_char=c;
			return LETTER;
		}else if(Character.isDigit(c)){
			Scanner.curr_char=c; return DIGIT;
		}else{
			return UNKNOWN;
		}
	}
		
  }
	
  public void setBackToken(char ch){
		try {
			pbIn.unread(ch);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}