package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;
import java.util.EnumSet;

import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> term = new ArrayList<>();
    ArrayList<AspCompOpr> compopr = new ArrayList<>();

    AspComparison(int n) {
        super(n);

    }

    static AspComparison parse(Scanner s){
        Main.log.enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
            while(true){
                ac.term.add(AspTerm.parse(s));
                TokenKind tmp = s.curToken().kind;
                if(tmp == lessToken ||
                        tmp == greaterToken||
                        tmp == doubleEqualToken ||
                        tmp == lessEqualToken||
                        tmp == greaterEqualToken||
                        tmp == notEqualToken){
                    ac.compopr.add(AspCompOpr.parse(s));
                    skip(s, tmp);
                }else{
                    break;
                }
            }
        Main.log.leaveParser("comparison");
        return ac;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
