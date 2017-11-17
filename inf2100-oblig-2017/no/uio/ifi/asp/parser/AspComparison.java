package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

import java.util.ArrayList;
import java.util.EnumSet;

import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> term = new ArrayList<>();
    ArrayList<AspCompOpr> compopr = new ArrayList<>();

    AspComparison(int n) {
        super(n);

    }

    static AspComparison parse(Scanner s){
        Main.log.enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
            while(true){
                ac.term.add(AspTerm.parse(s));
                if(s.isCompOpr()){
                    ac.compopr.add(AspCompOpr.parse(s));
                }else{
                    break;
                }
            }
        Main.log.leaveParser("comparison");
        return ac;
    }
    @Override
    void prettyPrint() {
        int counter = 0;
        for(AspTerm ant: term){
            ant.prettyPrint();
            if(counter<compopr.size())compopr.get(counter++).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //Maa fikses,
        RuntimeValue v = null, result, tmp = null, tmpBool = null;
        boolean lagt = true;
        for(int i = 0;i<term.size();i++){
            tmp = term.get(i).eval(curScope);
            if(term.size()-1>i){
                //FIKS
                TokenKind t = compopr.get(i).t;
                switch(t){
                    case lessToken:
                        tmpBool = tmp.evalLess(term.get(i+1).eval(curScope),this);
                        break;
                    case lessEqualToken:
                        tmpBool = tmp.evalLessEqual(term.get(i+1).eval(curScope),this);
                        break;
                    case greaterToken:
                        tmpBool = tmp.evalGreater(term.get(i+1).eval(curScope),this);
                        break;
                    case greaterEqualToken:
                        tmpBool = tmp.evalGreaterEqual(term.get(i+1).eval(curScope),this);
                        break;
                    case doubleEqualToken:
                        tmpBool = tmp.evalEqual(term.get(i+1).eval(curScope),this);
                        break;
                    case notEqualToken:
                        tmpBool = tmp.evalNotEqual(term.get(i+1).eval(curScope),this);
                        break;
                }
                if(i == 0||!tmpBool.getBoolValue("comparison", this) ){
                    v = tmpBool;
                }
            }
            if(term.size() == 1){
                v = term.get(i).eval(curScope);
            }
        }
        return v;
    }
}
