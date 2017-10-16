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
    int counterElif = 0;
    boolean checkElse = false;
    boolean checkElif = false;

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
           //If elseToken break, if elseiftoken loop again
           if(s.curToken().kind == TokenKind.elseToken){
               ais.checkElse = true;
               skip(s, TokenKind.elseToken);
               skip(s, TokenKind.colonToken);
               ais.as.add(AspSuite.parse(s));
               break;
           }else if(s.curToken().kind == TokenKind.elifToken){
               skip(s, TokenKind.elifToken);
               ais.checkElif = true;
               ais.counterElif++;
           }else{
               break;
           }
        }

        Main.log.enterParser("if stmt");
        return ais;
    }
    @Override
    void prettyPrint() {
        int i = 0;
        Main.log.prettyWrite("if ");
        while(i<ae.size()){
            ae.get(i).prettyPrint();
            Main.log.prettyWrite(":");
            as.get(i).prettyPrint();
            //Checks if the it exist a elseToken and it is the last
            if(checkElse && ae.size()-1==i){
                Main.log.prettyWrite("else:");
                as.get(i+1).prettyPrint();
                checkElif = false;
            }
            //Check if there is a elifToken
            if(checkElif && ae.size()>i){
                Main.log.prettyWrite("else if");
            }
            i++;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
