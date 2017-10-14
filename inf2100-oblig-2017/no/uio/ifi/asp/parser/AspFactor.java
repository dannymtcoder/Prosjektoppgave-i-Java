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
        TokenKind tmp = s.curToken().kind;
        if(tmp == TokenKind.plusToken ||tmp == TokenKind.minusToken){
            af.factorPrefix = AspFactorPrefix.parse(s);
            skip(s,tmp);
        }
        while(true){
            af.primary.add(AspPrimary.parse(s));
            tmp = s.curToken().kind;
            if(tmp == TokenKind.astToken ||
                    tmp == TokenKind.slashToken ||
                    tmp == TokenKind.doubleSlashToken||
                    tmp == TokenKind.percentToken){
                af.factorOpr.add(AspFactorOpr.parse(s));
                skip(s,tmp);
            }else{
                break;
            }
        }
        Main.log.leaveParser("factor");

        return af;
    }
    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
