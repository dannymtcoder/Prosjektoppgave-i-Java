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
        Main.log.enterParser("assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.name = AspName.parse(s);
        while(true) {
            //Checks if there is leftbracket if not break the loop
            if (s.curToken().kind == TokenKind.leftBracketToken) {
                aa.as.add(AspSubscription.parse(s));
            }else{
                break;
            }
        }
        skip(s,TokenKind.equalToken);
        aa.body = AspExpr.parse(s);

        skip(s, TokenKind.newLineToken);


        Main.log.leaveParser("assignment");
       return aa;
    }

    @Override
    void prettyPrint() {
        name.prettyPrint();
        for(AspSubscription tmp: as){
            tmp.prettyPrint();
        }
        Main.log.prettyWrite(" = ");
        body.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
