package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspInnerExpr extends AspAtom {
    AspExpr body;

    AspInnerExpr(int n) {
        super(n);
    }
    static AspInnerExpr parse(Scanner s){
        Main.log.enterParser("inner expr");

        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
        skip(s, TokenKind.leftParToken);
        aie.body = AspExpr.parse(s);
        skip(s, TokenKind.rightParToken);

        Main.log.leaveParser("inner expr");

        return aie;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
