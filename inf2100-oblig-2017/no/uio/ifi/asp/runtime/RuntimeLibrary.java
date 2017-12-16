package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
	//len

        assign("len", new RuntimeFunc("len"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
                                             RuntimeScope scope,AspSyntax where){
                checkNumParams(actualParams,1,"len", where);
                return actualParams.get(0).evalLen(where);
            }
        });
        ;
        assign("input", new RuntimeFunc("input"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
                                             RuntimeScope scope, AspSyntax where){
                Main.log.traceEval("input call", where);
                String output = actualParams.get(0).toString();
                output = output.substring(1, output.length()-1);
                for(int i = 1; i<actualParams.size();i++){
                    output += actualParams.get(i);
                }
                System.out.print(output + ": ");

                return new RuntimeStringValue(keyboard.nextLine() + "\n");
            }
        });
        assign("int", new RuntimeFunc("int")
        {

            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, RuntimeScope scope,
                                             AspSyntax where) {
                checkNumParams(actualParams, 1, "int", where);
                Main.log.traceEval("Call function int with params [" + actualParams.get(0).toString() + "]",where);
                try{
                    if(actualParams.get(0) instanceof RuntimeStringValue)
                        return new RuntimeIntValue(Integer.parseInt(actualParams.get(0).toString() +"int()"));
                }
                catch(java.lang.NumberFormatException e){
                    runtimeError("can not convert string: " + actualParams.get(0).toString() + " to int", where);
                }
                return new RuntimeIntValue(actualParams.get(0).getIntValue("int func", where));
            }
        });
        assign("str", new RuntimeFunc("str")
        {

            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, RuntimeScope scope,
                                             AspSyntax where)
            {
                checkNumParams(actualParams, 1, "str", where);
                Main.log.traceEval("Call function str with params[" + actualParams.get(0).toString() +"]", where);
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });
            assign("print", new RuntimeFunc("print")
            {

                @Override
                public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, RuntimeScope scope,
                                                 AspSyntax where)
                {
                    Main.log.traceEval("Call function print", where);
                    String output = "";
                    for(RuntimeValue runtimeValue : actualParams) {
                        output += runtimeValue.toString().substring(1,runtimeValue.toString().length()-1) + " ";
                    }
                    System.out.println(output);
                    return null;
                }
            });
    }
    private void checkNumParams(ArrayList<RuntimeValue> actArgs, 
				int nCorrect, String id, AspSyntax where) {
	if (actArgs.size() != nCorrect)
	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
