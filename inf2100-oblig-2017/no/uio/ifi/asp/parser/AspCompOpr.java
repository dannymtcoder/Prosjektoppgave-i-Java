package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspCompOpr extends AspSyntax {
    String comp;
    TokenKind t;
    AspCompOpr(int n) {
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        Main.log.enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        aco.comp = s.curToken().kind.toString();
        aco.t = s.curToken().kind;
        skip(s, s.curToken().kind);
        Main.log.leaveParser("comp opr");

        return aco;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(" " + comp + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
