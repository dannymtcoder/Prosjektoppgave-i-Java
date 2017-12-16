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
        RuntimeValue v = primary.get(0).eval(curScope);
        if(factorPrefix != null){
            if(factorPrefix.factorPrefix == "-") {
                v.evalNegate(factorPrefix);
            }else if(factorPrefix.factorPrefix == "+"){
                v.evalPositive(factorPrefix);
            }
        }
        for(int i = 1; i<primary.size();i++){

            if(i<primary.size()){
                TokenKind kind = factorOpr.get(i-1).factorOpr;
                switch(kind){
                    case astToken:
                       v = v.evalMultiply(primary.get(i).eval(curScope),this);
                       break;
                    case slashToken:
                        v = v.evalDivide(primary.get(i).eval(curScope),this);
                        break;
                    case percentToken:
                        v = v.evalModulo(primary.get(i).eval(curScope), this);
                         break;
                    case doubleSlashToken:
                        v = v.evalIntDivide(primary.get(i).eval(curScope), this);
                        break;
                    default:
                        Main.panic("Illegal factor operator: " + kind + "!");
                }
            }
        }
        return v;
    }
}
