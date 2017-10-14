package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspPrimarySuffix extends AspSyntax {
    AspPrimarySuffix(int n) {
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s) {
        //Remember to skip
        Main.log.enterParser("primary suffix");
        AspPrimarySuffix aps = null;
        if(s.curToken().kind == TokenKind.leftParToken){
            aps = AspArguments.parse(s);
        }else{
            aps = AspSubscription.parse(s);
        }
        Main.log.leaveParser("primary suffix");
        return aps;
    }
}
