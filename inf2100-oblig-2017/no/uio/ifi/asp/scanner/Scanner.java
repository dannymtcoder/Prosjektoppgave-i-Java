package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private final int tabDist = 4;
    private int tabLength = 0;
    private boolean findTabLength = true;


    public Scanner(String fileName) {
		curFileName = fileName;
		indents[0] = 0;  numIndents = 1;

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
					new FileInputStream(fileName),
					"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
		//Read the whole file
		readNextLine();
		while(curLineTokens.get(0).kind != eofToken)readNextLine();
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }


    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
    }


    public void readNextToken() {
		if (! curLineTokens.isEmpty())
			curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
		}
		return false;
    }


    private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();

			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");

		}
		//Break up the line and create tokens
		String tmp = "";
		boolean skip = false;
		if(line != null) {
			char[] ch = line.toCharArray();
			for (int i = 0; i<ch.length;) {
				if (ch[i] == '#') {
					break;
				}
				//Checks if it is a keyword or put it as a nameToken
				if (isLetterAZ(ch[i])) {
					boolean isnameToken = true;
					tmp += ch[i++];
					while(ch.length > i){
						if(isLetterAZ(ch[i]) || isDigit(ch[i])){
							tmp += ch[i++];
						}else{
							break;
						}
					}
					//Checks keywords
					for(TokenKind k : EnumSet.range(andToken, yieldToken)){
						if(k.toString().equals(tmp)){
							curLineTokens.add(new Token(k,curLineNum()));
							isnameToken = false;
						}
					}
					if(isnameToken){
						curLineTokens.add(new Token(nameToken,curLineNum()));
						curLineTokens.get(curLineTokens.size()-1).name = tmp;
					}

					tmp = "";
				}else if(isDigit(ch[i])){
					//Checks if it is an integer og a float
					boolean fl = false;
					tmp += ch[i++];
					while(ch.length > i) {
						if (isDigit(ch[i])) {
							tmp += ch[i++];
						} else if (ch[i] == '.') {
							tmp += ch[i++];
							try{
								if(!isDigit(ch[i])){
									scannerError("Not a valid float");
								}
							}catch(Exception e){
								scannerError("Not a valid float");
							}

							fl = true;
						}else{
							break;
						}
					}
					if(fl){
						Token tmpFloat = new Token(floatToken, curLineNum());
						tmpFloat.floatLit = Float.parseFloat(tmp);
						curLineTokens.add(tmpFloat);

					}else{
						Token tmpInteger = new Token(integerToken, curLineNum());
						tmpInteger.integerLit = Integer.parseInt(tmp);
						curLineTokens.add(tmpInteger);
					}
					tmp = "";
				}
				else if(ch[i] == '\"') {
					//Find the whole string
					tmp += ch[i++];
					boolean found = false;
					while (ch.length > i) {
						if (ch[i] == '\"') {
							tmp += ch[i++];
							found = true;
							break;
						}
						tmp += ch[i++];
					}
					if (!found) {
						scannerError("String not closed, missing a second \" ");
					}
					Token tmpString = new Token(stringToken, curLineNum());
					tmpString.stringLit = tmp;
					curLineTokens.add(tmpString);
					tmp = "";
				}else if(ch[i] == '\''){
					//Find the whole string
					tmp += ch[i++];
					boolean found = false;
					while (ch.length > i) {
						if (ch[i] == '\'') {
							tmp += ch[i++];
							found = true;
							break;
						}
						tmp += ch[i++];
					}
					if (!found) {
						scannerError("String not closed, missing a second \' ");
					}
					Token tmpString = new Token(stringToken, curLineNum());
					tmpString.stringLit = tmp;
					curLineTokens.add(tmpString);
					tmp = "";

				}else{
					//If the character is special letters
					if(ch[i] != ' '){
						tmp += ch[i++];
						while(ch.length > i) {
							if (ch[i] != ' ' && !isDigit(ch[i]) && !isLetterAZ(ch[i])
									&& ch[i] != '\"'){
								tmp += ch[i++];
							}else{
								break;
							}
						}
						boolean found = false;
						for(TokenKind k : EnumSet.range(ampToken, slashEqualToken)){
							if(k.toString().equals(tmp)){
								found = true;
								curLineTokens.add(new Token(k,curLineNum()));
							}
						}
						String eMessage = "\"" +  tmp + "\"";
						while(found == false){
							try{
								tmp = tmp.substring(0, tmp.length() - 1);
								i--;
							}catch(Exception e){
								found = true;
								scannerError("Not a delimters or operator \t\t" + eMessage);
								//ERROR
							}
							for(TokenKind k : EnumSet.range(ampToken, slashEqualToken)){
								if(k.toString().equals(tmp)){
									found = true;
									curLineTokens.add(new Token(k,curLineNum()));
								}
							}
						}
					}else{
						//If the ch[i] is a whitespace
						i++;
					}
					tmp = "";
				}

			}
		}else{
			//When the file has no more lines, make a eoftoken to stop the process
			curLineTokens.add(new Token(eofToken, curLineNum()));
			skip = true;
		}
		//Create indent or dedent
		if(!skip && curLineTokens.size() != 0){
			line = expandLeadingTabs(line);
			int indent = findIndent(line);

			if(indents[numIndents-1] < indent){
				int count = indents[numIndents-1];
				indents[numIndents++] = indent;
				curLineTokens.add(0, new Token(indentToken,curLineNum()));
				if(findTabLength){
					tabLength = indent;
					findTabLength = false;
				}
				while(count<indent){
					curLineTokens.add(0, new Token(indentToken,curLineNum()));
					count += tabLength;
				}
			}else if(indents[numIndents-1] > indent){
				int count = indents[numIndents-1];
				indents[numIndents++] = indent;

				curLineTokens.add(0, new Token(dedentToken, curLineNum()));
				count -= tabLength;

				//Creates more than one dedent if the previous line was idented twice
				while(count>indent) {
					curLineTokens.add(0, new Token(dedentToken, curLineNum()));
					count -= tabLength;
				}
				//Reset tabLength
				if(indent == 0){
					findTabLength = true;
				}
			}else{
				System.out.println("HEI");
				indents[numIndents++] = indent;

			}
		}
		if(!skip){
			curLineTokens.add(new Token(newLineToken, curLineNum()));
		}
		if(skip || curLineTokens.size() != 1) {
			for (Token t: curLineTokens)
				Main.log.noteToken(t);

		}
	}

    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
		String newS = "";
		for (int i = 0;  i < s.length();  i++) {
			char c = s.charAt(i);
			if (c == '\t') {
			do {
				newS += " ";
			} while (newS.length()%tabDist != 0);
			} else if (c == ' ') {
			newS += " ";
			} else {
			newS += s.substring(i);
			break;
			}
		}
		return newS;
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }
}
