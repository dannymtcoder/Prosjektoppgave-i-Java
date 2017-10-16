package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;


public class AspBooleanLiteral extends AspAtom {
    String bool;
    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s){
        Main.log.enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        abl.bool = s.curToken().kind.toString();
        skip(s, s.curToken().kind);
        Main.log.leaveParser("boolean literal");

        return abl;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(bool + "");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
