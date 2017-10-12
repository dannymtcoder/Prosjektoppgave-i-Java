package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspStmt {
    AspExpr test;
    AspSuite body;
    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s){
        Main.log.enterParser("while stmt");

        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken); aws.test = AspExpr.parse(s);
        //skip(s, colonToken); aws.body = AspSuite.parse(s);

        Main.log.leaveParser("while stmt");
        return aws;
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
