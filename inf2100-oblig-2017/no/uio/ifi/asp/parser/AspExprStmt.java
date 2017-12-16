package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspExprStmt extends AspStmt {
    AspExpr body;
    AspExprStmt(int n) {
        super(n);
    }

    static AspExprStmt parse(Scanner s){
        Main.log.enterParser("expr stmt");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.body = AspExpr.parse(s);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("expr stmt");

        return aes;
    }
    @Override
    void prettyPrint() {
        body.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return body.eval(curScope);
    }
}
