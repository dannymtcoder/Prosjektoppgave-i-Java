package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactorPrefix extends AspSyntax {
    String factorPrefix;
    AspFactorPrefix(int n) {
        super(n);
    }

    static AspFactorPrefix parse(Scanner s) {
        Main.log.enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        afp.factorPrefix = s.curToken().kind.toString();
        skip(s, s.curToken().kind);
        Main.log.leaveParser("factor prefix");

        return afp;
    }

    @Override
    void prettyPrint() {
        Main.log.prettyWrite(" " + factorPrefix + " ");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }


}
