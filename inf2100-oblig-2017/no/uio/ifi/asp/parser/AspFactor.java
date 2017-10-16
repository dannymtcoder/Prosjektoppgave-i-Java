package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspFactor extends AspSyntax {
    AspFactorPrefix factorPrefix;
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOpr = new ArrayList<>();
    AspFactor(int n) {
        super(n);
    }

    public static AspFactor parse(Scanner s){
        Main.log.enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());
        if(s.isFactorPrefix()){
            af.factorPrefix = AspFactorPrefix.parse(s);
        }
        while(true){
            af.primary.add(AspPrimary.parse(s));
            if(s.isFactorOpr()){
                af.factorOpr.add(AspFactorOpr.parse(s));
            }else{
                break;
            }
        }
        Main.log.leaveParser("factor");

        return af;
    }
    @Override
    void prettyPrint() {
        if(factorPrefix != null){
            factorPrefix.prettyPrint();
        }
        int counter = 0;
        for(AspPrimary ap: primary){
            ap.prettyPrint();
            if(counter<primary.size()-1){
                factorOpr.get(counter++).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
