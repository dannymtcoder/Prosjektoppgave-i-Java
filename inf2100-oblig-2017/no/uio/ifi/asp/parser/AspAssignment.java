package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;


public class AspAssignment extends AspStmt{
    AspName name;
    AspExpr body;
    AspAssignment(int n) {
        super(n);
    }
    public static AspAssignment parse(Scanner s) {
        Main.log.enterParser("Assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        skip(s, TokenKind.nameToken); aa.name = AspName.parse(s);
        //Må sjekke subscription
        skip(s, TokenKind.equalToken);

        aa.body = AspExpr.parse(s);



        Main.log.leaveParser("Assignment");
       return aa;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
