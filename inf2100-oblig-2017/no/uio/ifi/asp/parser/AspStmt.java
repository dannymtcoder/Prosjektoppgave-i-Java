package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspStmt extends AspSyntax {
    AspStmt(int n) {
        super(n);
    }
    static AspStmt parse(Scanner s){
        Main.log.enterParser("stmt");
        AspStmt a = null;
        switch (s.curToken().kind) {
            case leftParToken:
            case leftBraceToken:
            case leftBracketToken:
            case trueToken:
            case falseToken:
            case notToken:
            case floatToken:
            case minusToken:
            case integerToken:
            case stringToken:
            case nameToken:
                if(s.anyEqualToken()){
                    a = AspAssignment.parse(s);
                    break;
                }else{
                    a = AspExprStmt.parse(s);
                    break;
                }
            case ifToken:
                a = AspIfStmt.parse(s);

                break;
            case whileToken:
                a = AspWhileStmt.parse(s);
                break;
            case returnToken:
                a = AspReturnStmt.parse(s);
                break;
            case passToken:
                a = AspPassStmt.parse(s);
                break;
            case defToken:
                a = AspFuncDef.parse(s);
                break;
            default:
                parserError("Expected a stmt but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        Main.log.leaveParser("stmt");
        return a;
    }
}
