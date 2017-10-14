package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;


public class AspFloatLiteral extends AspAtom{
    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s){
        Main.log.enterParser("float literal");
        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        skip(s, TokenKind.floatToken);
        Main.log.leaveParser("float literal");

        return afl;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
