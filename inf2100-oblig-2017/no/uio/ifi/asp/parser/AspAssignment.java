package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;


public class AspAssignment extends AspStmt{
    AspName name;
    ArrayList<AspSubscription> as = new ArrayList<>();
    AspExpr body;
    AspAssignment(int n) {
        super(n);
    }
    public static AspAssignment parse(Scanner s) {
        Main.log.enterParser("assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.name = AspName.parse(s);
        while(true) {
            //Checks if there is leftbracket if not break the loop
            if (s.curToken().kind == TokenKind.leftBracketToken) {
                aa.as.add(AspSubscription.parse(s));
            }else{
                break;
            }
        }
        skip(s,TokenKind.equalToken);
        aa.body = AspExpr.parse(s);
        skip(s, TokenKind.newLineToken);

        Main.log.leaveParser("assignment");
       return aa;
    }

    @Override
    void prettyPrint() {
        name.prettyPrint();
        for(AspSubscription tmp: as){
            tmp.prettyPrint();
        }
        Main.log.prettyWrite(" = ");
        body.prettyPrint();
        Main.log.prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue var = body.eval(curScope);

        if(as.size() == 0){
            curScope.assign(name.name, var);
            Main.log.traceEval(name.name+ " = "+ var.showInfo(),this);
        }else if(as.size() > 0){
            RuntimeValue v = curScope.find(name.name, this);
            String output = name.name;
            boolean forloop = false;
            for(int i = 0; i<as.size()-1;i++){
                v = v.evalSubscription(as.get(i).eval(curScope),this);
                output +=  "["+as.get(i).eval(curScope).toString() + "]";
                forloop = true;
            }
            if(!forloop){
                output +=  "["+as.get(0).eval(curScope).toString() + "]";
            }
            Main.log.traceEval(output + " = " + var.toString(),this);
            v.evalAssignElem(var, as.get(as.size()-1).eval(curScope), this);
        }

        return var;
    }
}
