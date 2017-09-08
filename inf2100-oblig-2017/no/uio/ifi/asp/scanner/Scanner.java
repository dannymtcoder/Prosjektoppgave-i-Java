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
		boolean none = false;
		if(line != null) {
			char[] ch = line.toCharArray();
			for (int i = 0; i<ch.length;) {
				if (ch[i] == '#') {
					none = true;
					break;
				}
				//Fix
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
					for(TokenKind k: TokenKind.values()){
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
					boolean fl = false;
					tmp += ch[i++];
					while(ch.length > i){
						if(isDigit(ch[i])){
							tmp += ch[i++];
						}else if(ch[i] == '.'){
							tmp += ch[i++];
							fl = true;
						}else{
							//TODO WRITE ERROR MESSAGE
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
				else if(ch[i] == '\"'){
					tmp += ch[i++];

					while(ch.length > i){
						if(ch[i] == '\"'){
							tmp += ch[i];
							break;
						}
						tmp += ch[i++];
					}
					// TODO Make an error when a second \" is not found
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
									&& ch[i] != '\"' && ch[i] != ':'){
								tmp += ch[i++];
							}else{
								break;
							}
						}

						for(TokenKind k: TokenKind.values()){
							if(k.toString().equals(tmp)){
								curLineTokens.add(new Token(k,curLineNum()));
							}
						}
						// TODO Make an error when we have not found any special letters token
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
		}
		//-- Must be changed in part 1:

		// Terminate line:
		if(!none) {
			curLineTokens.add(new Token(newLineToken, curLineNum()));
		}else{
			//TODO Fix when there is a comment
			curLineTokens.add(new Token(newLineToken, curLineNum()));
		}
		for (Token t: curLineTokens)
			Main.log.noteToken(t);

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
