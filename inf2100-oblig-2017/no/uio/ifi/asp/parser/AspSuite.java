package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    ArrayList<AspStmt> as = new ArrayList<>();
    int checkList1;
    boolean checkDedent;
    boolean checkNewline;
    boolean isTrue;
    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        Main.log.enterParser("suite");
        //Some variables for prettyprint
        AspSuite aSuite = new AspSuite(s.curLineNum());
        aSuite.checkList1 = 0;
        aSuite.checkDedent = false;
        aSuite.checkNewline = false;
        aSuite.isTrue = false;
        while(s.curToken().kind == TokenKind.newLineToken){
            skip(s,TokenKind.newLineToken);
            aSuite.checkList1++;
        }
        while(s.curToken().kind == TokenKind.indentToken){
            skip(s, TokenKind.indentToken);
        }
        while(true){
            //Skip all newlines
            if(s.curToken().kind == TokenKind.newLineToken){
                skip(s, TokenKind.newLineToken);
                if(aSuite.checkNewline){
                    aSuite.isTrue = true;
                }
            }else if(s.curToken().kind != TokenKind.dedentToken &&
                    s.curToken().kind != TokenKind.eofToken){
                aSuite.as.add(AspStmt.parse(s));
                aSuite.checkNewline = true;
            }else{
                break;}
        }
        if(s.curToken().kind == TokenKind.dedentToken){
            skip(s,TokenKind.dedentToken);
            aSuite.checkDedent = true;
        }
        Main.log.leaveParser("suite");

        return aSuite;
    }
    @Override
    void prettyPrint() {

        //Makes newLines
        Main.log.prettyWriteLn();

        Main.log.prettyIndent();
        for(AspStmt ast: as) {
            //Makes newline before stmt
            ast.prettyPrint();
        }
        if(checkDedent)Main.log.prettyDedent();
        if(!checkDedent)Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }


}
