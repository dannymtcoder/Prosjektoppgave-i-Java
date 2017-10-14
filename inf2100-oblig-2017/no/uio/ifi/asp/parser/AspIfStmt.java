package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspIfStmt extends AspStmt {
    ArrayList<AspExpr> ae =new ArrayList<>();
    ArrayList<AspSuite> as = new ArrayList<>();

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s){
        Main.log.enterParser("if stmt");

        AspIfStmt ais = new AspIfStmt(s.curLineNum());
        skip(s, TokenKind.ifToken);
        while(true){
           ais.ae.add(AspExpr.parse(s));
           skip(s,TokenKind.colonToken);
           ais.as.add(AspSuite.parse(s));
           if(s.curToken().kind != TokenKind.elifToken){
               if(s.curToken().kind != TokenKind.elseToken)break;
               skip(s, TokenKind.elseToken);
               skip(s, TokenKind.colonToken);
               ais.as.add(AspSuite.parse(s));
               break;
           }else{
               skip(s, TokenKind.elifToken);
           }
        }

        Main.log.enterParser("if stmt");
        return null;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
