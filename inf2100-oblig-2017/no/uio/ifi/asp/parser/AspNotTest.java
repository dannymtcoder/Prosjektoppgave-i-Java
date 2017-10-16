package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;


public class AspNotTest extends AspSyntax {
    AspComparison body;
    boolean not = false;
    AspNotTest(int n) {
        super(n);
    }
    static AspNotTest parse(Scanner s){
        Main.log.enterParser("not test");

        AspNotTest ant = new AspNotTest(s.curLineNum());
        //If curToken is not a notToken, jump over it
        if(s.curToken().kind == TokenKind.notToken) {
            skip(s, TokenKind.notToken);
            ant.not = true;
        }
        ant.body = AspComparison.parse(s);
        Main.log.leaveParser("not test");

        return ant;
    }
    @Override
    void prettyPrint() {
        if(not) Main.log.prettyWrite(" not ");
        body.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
