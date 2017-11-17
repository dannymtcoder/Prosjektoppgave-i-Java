package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeDictValue;
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
                skip(s, TokenKind.colonToken);
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
        Main.log.prettyWrite("{");
        for(int i = 0;i<asl.size();i++){
            asl.get(i).prettyPrint();
            Main.log.prettyWrite(": ");
            expr.get(i).prettyPrint();
            if(i<asl.size()-1){
                Main.log.prettyWrite(", ");
            }
        }
        Main.log.prettyWrite("}");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeDictValue v = new RuntimeDictValue();
        for(int i = 0; i<asl.size();i++){
            v.add(asl.get(i).eval(curScope).getStringValue("dict", this), expr.get(i).eval(curScope));
        }
        return v;
    }

}
