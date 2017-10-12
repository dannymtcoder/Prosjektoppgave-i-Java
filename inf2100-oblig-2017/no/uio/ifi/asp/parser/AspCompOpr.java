package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspCompOpr extends AspSyntax {
    AspCompOpr(int n) {
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        Main.log.enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        Main.log.leaveParser("comp opr");

        return aco;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
