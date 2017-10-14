package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSuite extends AspSyntax {
    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        Main.log.enterParser("suite");

        AspSuite as = new AspSuite(s.curLineNum());
        skip(s, s.curToken().kind);
        Main.log.leaveParser("suite");

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
