package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> asl = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();
    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        Main.log.enterParser("dict display");
        AspDictDisplay adDisplay = new AspDictDisplay(s.curLineNum());
        skip(s, TokenKind.leftBraceToken);
        while(true){
            if(s.curToken().kind == TokenKind.stringToken){
                adDisplay.asl.add(AspStringLiteral.parse(s));
                skip(s, TokenKind.semicolonToken);
                adDisplay.expr.add(AspExpr.parse(s));
                if(s.curToken().kind == TokenKind.commaToken){
                    skip(s, s.curToken().kind);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        skip(s, TokenKind.rightBraceToken);
        Main.log.leaveParser("dict display");
        return adDisplay;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

}
