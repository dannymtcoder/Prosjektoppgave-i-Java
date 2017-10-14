package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspSubscription extends AspPrimarySuffix{
    AspExpr body;
    AspSubscription(int n) {
        super(n);
    }
    static AspSubscription parse(Scanner s){
        Main.log.enterParser("subscription");

        AspSubscription as = new AspSubscription(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);
        as.body = AspExpr.parse(s);
        skip(s, TokenKind.rightBracketToken);

        Main.log.leaveParser("subscription");
        return as;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
