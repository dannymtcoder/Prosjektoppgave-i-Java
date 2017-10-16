package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<AspNotTest>();
    AspAndTest(int n){

        super(n);
    }

    static AspAndTest parse(Scanner s){
        Main.log.enterParser("and test");

        AspAndTest aat = new AspAndTest(s.curLineNum());
        while(true){
            aat.notTests.add(AspNotTest.parse(s));
            //If currenttoken is not an andToken break the loop
            if(s.curToken().kind != andToken)break;
            skip(s, andToken);
        }
        Main.log.leaveParser("and test");
        return aat;
    }

    @Override
    void prettyPrint() {
        int counter = 0;
        for(AspNotTest ant: notTests){
            ant.prettyPrint();
            if(counter<notTests.size()-1){
                Main.log.prettyWrite(" and ");
            }
            counter++;
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
