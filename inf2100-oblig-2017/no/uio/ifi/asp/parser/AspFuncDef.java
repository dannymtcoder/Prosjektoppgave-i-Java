package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeFunc;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

public class AspFuncDef extends AspStmt{
    AspName body;
    AspSuite test;
    ArrayList<AspName> an = new ArrayList<>();
    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s){
        Main.log.enterParser("func def");

        AspFuncDef afd = new AspFuncDef(s.curLineNum());
        skip(s, TokenKind.defToken);
        afd.body = AspName.parse(s);
        skip(s,TokenKind.leftParToken);
        if(s.curToken().kind != TokenKind.rightParToken){
            while(true){
                afd.an.add(AspName.parse(s));
                if(s.curToken().kind == TokenKind.commaToken){
                    skip(s, TokenKind.commaToken);
                }else{
                    break;
                }
            }
        }
        skip(s,TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);
        afd.test = AspSuite.parse(s);

        Main.log.leaveParser("func def");

        return afd;
    }
    @Override
    void prettyPrint() {
        Main.log.prettyWrite("def ");
        body.prettyPrint();
        Main.log.prettyWrite("(");
        int counter = 0;
        for(AspName ant: an){
            ant.prettyPrint();
            if(counter<an.size()-1){
                Main.log.prettyWrite(", ");
            }
        }
        Main.log.prettyWrite("):");
        test.prettyPrint();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        RuntimeValue v = new RuntimeFunc(this,curScope, body.name);
        Main.log.traceEval("def " + body.name, this);
        curScope.assign(body.name, v);

        return v;
    }
    public ArrayList<AspName> getList(){
        return an;
    }

    public AspSuite getTest() {
        return test;
    }
}
