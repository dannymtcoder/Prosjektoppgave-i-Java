package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspReturnStmt extends AspStmt {
    AspExpr body;
    AspReturnStmt(int n) {
        super(n);
    }

    static AspReturnStmt parse(Scanner s){
        Main.log.enterParser("return stmt");

        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
        skip(s, TokenKind.returnToken);
        ars.body = AspExpr.parse(s);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("return stmt");

        return ars;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite("return ");
        body.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = body.eval(curScope);
        trace("return " + v.showInfo());
        throw new RuntimeReturnValue(v);
    }
}
