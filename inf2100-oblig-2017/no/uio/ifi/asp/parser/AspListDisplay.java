package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        Main.log.enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);
        while(true){
            if(s.curToken().kind != TokenKind.rightBracketToken){
                ald.expr.add(AspExpr.parse(s));
                if(s.curToken().kind == TokenKind.commaToken){
                    skip(s, TokenKind.commaToken);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        skip(s, TokenKind.rightBracketToken);
        Main.log.leaveParser("list display");
        return ald;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite("[");
        int counter = 0;
        for(AspExpr ae: expr){
            ae.prettyPrint();
            if(counter<expr.size()-1){
                Main.log.prettyWrite(", ");
                counter++;
            }
        }
        Main.log.prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
