package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;


public class AspAssignment extends AspStmt{
    AspName name;
    ArrayList<AspSubscription> as = new ArrayList<>();
    AspExpr body;
    AspAssignment(int n) {
        super(n);
    }
    public static AspAssignment parse(Scanner s) {
        Main.log.enterParser("Assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.name = AspName.parse(s);
        while(true) {
            if (s.curToken().kind == TokenKind.leftBracketToken) {
                aa.as.add(AspSubscription.parse(s));
            }else{
                break;
            }
        }
        skip(s,TokenKind.equalToken);
        aa.body = AspExpr.parse(s);

        skip(s, TokenKind.newLineToken);


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
