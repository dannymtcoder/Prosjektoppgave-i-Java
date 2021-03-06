package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
     ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	super(n);
    }


    public static AspProgram parse(Scanner s) {
	Main.log.enterParser("program");

	AspProgram ap = new AspProgram(s.curLineNum());
	while (s.curToken().kind != eofToken) {

        if(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
        }else if(s.curToken().kind != eofToken) {
            ap.stmts.add(AspStmt.parse(s));
        }

	}

	Main.log.leaveParser("program");
	return ap;
    }


    @Override
    public void prettyPrint() {
        for(AspStmt tmp: stmts){
            tmp.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 4:

        for(int i = 0;i<stmts.size();i++){
            stmts.get(i).eval(curScope);
        }
	return null;
    }
}
