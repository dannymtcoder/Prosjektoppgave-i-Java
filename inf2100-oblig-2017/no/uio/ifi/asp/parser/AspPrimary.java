package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.leftParToken;

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
        RuntimeValue v = body.eval(curScope);
        RuntimeValue func = null;
      for(int j = 0; j<aps.size();j++){
            RuntimeValue tmp = aps.get(j).eval(curScope);
            if(aps.get(j).token == "["){ ;
                v = v.evalSubscription(tmp,this);
            }else if(aps.get(j) instanceof AspArguments){

                RuntimeListValue list = (RuntimeListValue) tmp;
                func = curScope.find(v.getStringValue("primary", this),this);
                String output = "Call function " + v.getStringValue("primary", this) + " with params [";
                for(int i = 0; i<list.getList().size(); i++){
                    if(i != list.getList().size()-1) {
                        output += list.getList().get(0) + " ,";
                    }
                    output += list.getList().get(0);
                }
                output += "]";
                Main.log.traceEval(output, this);
                v = func.evalFuncCall(list.getList(), curScope, this);
            }
      }
      return v;
    }


}
