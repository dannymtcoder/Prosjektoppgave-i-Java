package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspTerm extends AspSyntax {
    ArrayList<AspTermOpr> termopr = new ArrayList<>();
    ArrayList<AspFactor> factor= new ArrayList<>();
    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        Main.log.enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());

        while(true){
            at.factor.add(AspFactor.parse(s));
            if(s.isTermOpr()){
                at.termopr.add(AspTermOpr.parse(s));
            }else{
                break;
            }
        }
        Main.log.leaveParser("term");
        return at;
    }
    @Override
    void prettyPrint() {
        int counter = 0;
        for(AspFactor af : factor){
            af.prettyPrint();
            if(counter<termopr.size()){
                termopr.get(counter++).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }


}
