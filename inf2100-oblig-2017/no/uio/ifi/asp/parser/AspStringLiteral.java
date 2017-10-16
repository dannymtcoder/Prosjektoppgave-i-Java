package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspStringLiteral extends AspAtom {
    String stringLiteral;
    AspStringLiteral(int n) {
        super(n);
    }
    static AspStringLiteral parse(Scanner s){
        Main.log.enterParser("string literal");
        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
        asl.stringLiteral = s.curToken().stringLit;
        skip(s, TokenKind.stringToken);
        Main.log.leaveParser("string literal");
        return asl;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(stringLiteral);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
