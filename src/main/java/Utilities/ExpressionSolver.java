package Utilities;

import Commands.Variable;
import UI.UIManager;

public abstract class ExpressionSolver
{
    protected int currentPosition = -1, lineNumber;
    protected long currentChar;
    protected String str;
    protected boolean errorFlag = false;
    public final long LB = Long.parseLong("5529856576");//decimal for aleph
    public final long UB = Long.parseLong("5529856597");//decimal for tau

    protected ExpressionSolver(String str, int lineNumber)
    {
        this.lineNumber = lineNumber;
        this.str = str;
    }

    public abstract Object getResult();

    protected void nextChar()
    {
        if(++currentPosition < str.length())
        {
            if((int)str.charAt(currentPosition) >= 55298 && (int)str.charAt(currentPosition+1) >= 55298){
                currentChar = Long.parseLong((int)str.charAt(currentPosition) + "" + (int)str.charAt(currentPosition+1));
            }
            else if((int)str.charAt(currentPosition) >= 55298) //check if char is valid surrogate code point
            {
                ;
            }
            else
            {
                currentChar = str.charAt(currentPosition);
            }
        }
        else
        {
            currentChar = -1;
        }
    }

    protected boolean dealWithChar(int charToEat)
    {
        if(currentChar == charToEat)
        {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean dealWithDoubleChar(int charToEat1, int charToEat2)
    {
        if(this.currentChar == charToEat1)
        {
            if(this.str.charAt(this.currentPosition + 1) == charToEat2)
            {
                nextChar();
                nextChar();
                return true;
            }
        }
        return false;
    }

    // The Grammar:
    // Expression = term || expression `+` term || expression `-` term
    // Term = factor || term `*` factor || term `/` factor
    // Factor = `+` factor || `-` factor || `(` expression `)` || number || functionName factor || factor `^` factor

    protected double parseExpression_Numbers()
    {
        double x = parseTerm_Numbers();
        while(true)
        {
            if(dealWithChar('+')) x += parseTerm_Numbers(); // Addition
            else if(dealWithChar('-')) x -= parseTerm_Numbers(); // Subtraction
            else return x;
        }
    }

    private double parseTerm_Numbers()
    {
        double x = parseFactor_Numbers();
        while(true)
        {
            if(dealWithChar('*')) x *= parseFactor_Numbers(); // Multiplication
            else if(dealWithChar('/')) x /= parseFactor_Numbers(); // Division
            else return x;
        }
    }

    private double parseFactor_Numbers()
    {
        if(dealWithChar('+')) return parseFactor_Numbers(); // Unary plus
        if(dealWithChar('-')) return -parseFactor_Numbers(); // Unary minus

        double x = 0;
        int startPos = this.currentPosition;
        if(dealWithChar('(')) // Parentheses
        {
            x = parseExpression_Numbers();
            dealWithChar(')');
        }
        else if((currentChar >= '0' && currentChar <= '9') || currentChar == '.') // Numbers
        {
            while ((currentChar >= '0' && currentChar <= '9') || currentChar == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.currentPosition));
        }
        else if((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= 'ا' && currentChar <= 'ي') || (currentChar >= LB && currentChar <= UB)) // Functions and Variables
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= 'ا' && currentChar <= 'ي') || (currentChar >= LB && currentChar <= UB) || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String func = str.substring(startPos, this.currentPosition);
            if(str.contains("\uD802\uDD14\uD802\uDD05\uD802\uDD13\uD802\uDD14")){ //sqrt 𐤔𐤅𐤓𐤔
                func = str.substring(startPos, this.currentPosition+7);
                for(int i=0; i<7; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD0E\uD802\uDD09\uD802\uDD0D")){ //sin 𐤎𐤉𐤍
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD12\uD802\uDD05\uD802\uDD0E")){ //cos 𐤒𐤅𐤎
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD08\uD802\uDD0D")){ //tan 𐤈𐤍
                func = str.substring(startPos, this.currentPosition+3);
                for(int i=0; i<3; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD0B\uD802\uDD03")){ //toDegrees 𐤋𐤃
                func = str.substring(startPos, this.currentPosition+3);
                for(int i=0; i<3; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD0B\uD802\uDD13")){ //toRadians 𐤋𐤓
                func = str.substring(startPos, this.currentPosition+3);
                for(int i=0; i<3; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD0F\uD802\uDD0C\uD802\uDD07")){ //abs 𐤏𐤌𐤇
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD0B\uD802\uDD05\uD802\uDD02")){ //log 𐤋𐤅𐤂
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD00\uD802\uDD12\uD802\uDD0E\uD802\uDD10")){ //exp 𐤀𐤒𐤎𐤐
                func = str.substring(startPos, this.currentPosition+7);
                for(int i=0; i<7; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD00\uD802\uDD05\uD802\uDD0B\uD802\uDD10")){ //ulp 𐤀𐤅𐤋𐤐
                func = str.substring(startPos, this.currentPosition+7);
                for(int i=0; i<7; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD13\uD802\uDD0D\uD802\uDD03")){ //random 𐤓𐤍𐤃
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            } else if(str.contains("\uD802\uDD10\uD802\uDD09\uD802\uDD09")){ //𐤐𐤉𐤉 pi
                func = str.substring(startPos, this.currentPosition+5);
                for(int i=0; i<5; i++){
                    nextChar();
                }
            }

            for(String varName: Variable.getVariablesNames()) // Variables
            {
                if(func.equals(varName))
                {
                    if(Variable.getAVariableValue(func) instanceof Boolean || Variable.getAVariableValue(func) instanceof String)
                    {
                        errorFlag = true;
                        return 0;
                    }

                    if(Variable.getAVariableValue(func) instanceof Object[]) // Array variable
                    {
                        Object[] theArray = (Object[]) Variable.getAVariableValue(func);
                        if(dealWithChar('[')) // Parentheses
                        {
                            System.out.println("1");
                            int index = (int) parseExpression_Numbers();
                            if(index > theArray.length - 1 || index < 0) UIManager.consoleInstance.printErrorMessage("𐤌𐤏𐤓𐤊 𐤇𐤓𐤂 𐤌𐤄𐤕𐤇𐤅𐤌" + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
                            if(!dealWithChar(']')) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤌𐤏𐤓𐤊" + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
                            if(theArray[index] instanceof Double)
                            {
                                System.out.println("2");
                                x = (double) theArray[index];
                                return x;
                            }
                            else
                            {
                                errorFlag = true;
                                return 0;
                            }
                        }
                    }
                    x = (double) Variable.getAVariableValue(func);
                    return x;
                }
            }
            // Functions
            switch (func) {
                case "\uD802\uDD14\uD802\uDD05\uD802\uDD13\uD802\uDD14":
                    x = Math.sqrt(getFunctionInput()); //𐤔𐤅𐤓𐤔
                    break;
                case "\uD802\uDD0E\uD802\uDD09\uD802\uDD0D":
                    x = Math.sin(Math.toRadians(getFunctionInput())); //𐤎𐤉𐤍
                    break;
                case "\uD802\uDD12\uD802\uDD05\uD802\uDD0E":
                    x = Math.cos(Math.toRadians(getFunctionInput())); //𐤒𐤅𐤎
                    break;
                case "\uD802\uDD08\uD802\uDD0D":
                    x = Math.tan(Math.toRadians(getFunctionInput())); //𐤈𐤍
                    break;
                case "\uD802\uDD0B\uD802\uDD03":
                    x = Math.toDegrees(getFunctionInput()); //𐤋𐤃
                    break;
                case "\uD802\uDD0B\uD802\uDD13":
                    x = Math.toRadians(getFunctionInput()); //𐤋𐤓
                    break;
                case "\uD802\uDD0F\uD802\uDD0C\uD802\uDD07":
                    x = Math.abs(getFunctionInput()); //𐤏𐤌𐤇
                    break;
                case "\uD802\uDD0B\uD802\uDD05\uD802\uDD02":
                    x = Math.log(getFunctionInput()); //𐤋𐤅𐤂
                    break;
                case "\uD802\uDD00\uD802\uDD12\uD802\uDD0E\uD802\uDD10":
                    x = Math.exp(Math.toRadians(getFunctionInput())); //𐤀𐤒𐤎𐤐
                    break;
                case "\uD802\uDD00\uD802\uDD05\uD802\uDD0B\uD802\uDD10":
                    x = Math.ulp(Math.toRadians(getFunctionInput())); //𐤀𐤅𐤋𐤐
                    break;
                case "\uD802\uDD13\uD802\uDD0D\uD802\uDD03":
                    x = Math.random(); //𐤓𐤍𐤃
                    break;
                case "\uD802\uDD10\uD802\uDD09\uD802\uDD09":
                    x = Math.PI; //𐤐𐤉𐤉
                default:
                    errorFlag = true;
                    break;
            }
        }
        else
        {
            errorFlag = true;
            return 0;
        }

        if (dealWithChar('^')){
            x = java.lang.Math.pow(x, parseFactor_Numbers()); // Exponentiation
        }
        if(dealWithChar('|')){
            x = java.lang.Math.round(x);
        }

        return x;
    }

    private double getFunctionInput()
    {
        if(dealWithChar('(') || dealWithChar(')'))
        {
            double x = parseExpression_Numbers();
            if(!dealWithChar(')')) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤄𐤌𐤕𐤌𐤈𐤉𐤕" + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
            return x;
        }
        else
        {
            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤄𐤌𐤕𐤌𐤈𐤉𐤕" + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
            return 0;
        }
    }
}