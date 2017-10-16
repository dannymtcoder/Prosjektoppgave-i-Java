package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax {
    AspAtom body;
    ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }
    static AspPrimary parse(Scanner s) {
        Main.log.enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());
        ap.body = AspAtom.parse(s);
        while(true){
            if(s.curToken().kind == TokenKind.leftParToken ||
                    s.curToken().kind == TokenKind.leftBracketToken){
                ap.aps.add(AspPrimarySuffix.parse(s));
            }else{
                break;
            }
        }


        Main.log.leaveParser("primary");
        return ap;
    }


    @Override
    void prettyPrint() {
        body.prettyPrint();
        for (AspPrimarySuffix aps: aps) {
            aps.prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }


}
