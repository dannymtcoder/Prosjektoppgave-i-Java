package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix {
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s){
        Main.log.enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());
        skip(s,leftParToken);
        while(true){
            //Checks if there is expr before rightpar if not break the loop
            if(s.curToken().kind != rightParToken){
                aa.expr.add(AspExpr.parse(s));
                //If there is a comma, loop it again
                if(s.curToken().kind == commaToken){
                    skip(s, s.curToken().kind);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        skip(s, rightParToken);

        Main.log.leaveParser("arguments");

        return aa;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite("(");
        int counter = 0;
        for(AspExpr ae: expr){
            ae.prettyPrint();
            if(counter<expr.size()-1){
                Main.log.prettyWrite(", ");
                counter++;
            }
        }
        Main.log.prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
