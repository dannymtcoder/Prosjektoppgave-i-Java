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
        //Usikker om dette er riktig
        while(true){
            if(s.curToken().kind == andToken ||
                    s.curToken().kind == orToken){
                aa.expr.add(AspExpr.parse(s));
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

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
