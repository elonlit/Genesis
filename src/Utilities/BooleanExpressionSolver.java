package Utilities;

import Commands.Variable;
import UI.UIManager;

public class BooleanExpressionSolver extends ExpressionSolver
{
    private boolean tempValueHolder;
    public long LB = Long.parseLong("5529856576");//decimal for surrogate aleph
    public long UB = Long.parseLong("5529856597");//decimal for surrogate tau

    public BooleanExpressionSolver(String str, int lineNumber)
    {
        super(str, lineNumber);
    }

    @Override
    public Object getResult()
    {
        nextChar();
        return parseExpression_Boolean();
    }

    // The Grammar:
    // Expression = term || expression `&&` term || expression `||` term
    // Term = factor || term `==` factor || term `<` factor ....
    // Factor = (` expression `)` || number || string || boolean || globalVariables || true\false

    private boolean parseExpression_Boolean()
    {
        boolean x = parseTerm_Boolean();
        while(true)
        {
            if(dealWithDoubleChar('|', '|')) // Or
            {
                boolean a = parseTerm_Boolean(); // If the first side is true he will not check the second and it will trow an error because not all the string was considered
                x = x || a;
            }
            else if(dealWithDoubleChar('&', '&')) // And
            {
                boolean a = parseTerm_Boolean(); // If the first side is false he will not check the second and it will trow an error because not all the string was considered
                x = x && a;
            }
            else return x;
        }
    }

    private boolean parseTerm_Boolean()
    {
        if(parseBooleanFactor()) return tempValueHolder; // Check for boolean globalVariables, word and parentheses
        if(parseStringComparison()) return tempValueHolder; // Check for string == string or !=

        double x = parseExpression_Numbers();
        if(dealWithDoubleChar('=', '=')) return x == parseExpression_Numbers(); // Equal
        else if(dealWithDoubleChar('!', '=')) return x != parseExpression_Numbers(); // Not-Equal
        else if(dealWithDoubleChar('<', '=')) return x <= parseExpression_Numbers(); // Smaller or equal
        else if(dealWithDoubleChar('>', '=')) return x >= parseExpression_Numbers(); // Bigger or equal
        else if(dealWithChar('<')) return x < parseExpression_Numbers(); // Smaller
        else if(dealWithChar('>')) return x > parseExpression_Numbers(); // Bigger
        else UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤄𐤔𐤅𐤅𐤀𐤄 𐤋𐤀 𐤇𐤅𐤒𐤉𐤕" + (char) currentChar + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
        return false;
    }

    private boolean parseBooleanFactor() // Return true if parsing succeed - the parsing itself is stored in the tempValueHolder
    {
        int startPos = this.currentPosition;
        if(dealWithChar('(')) // Parentheses
        {
            tempValueHolder = parseExpression_Boolean();
            dealWithChar(')');
        }
        else if((currentChar >= LB && currentChar <= UB)) // Words and Variables. Note: decimal values represent letters.
        {
            while ((currentChar >= LB && currentChar <= UB) || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String word;
            if(str.contains("𐤀𐤌𐤕") || str.contains("𐤔𐤒𐤓")){
                word = str.substring(startPos, this.currentPosition+5);
            } else{
                word = str.substring(startPos, this.currentPosition+8);
                for(int i=0; i<8; i++){ //originally 8
                    nextChar();
                }
            }

            for(String varName: Variable.getVariablesNames()) // Variables
            {
                if(word.equals(varName))
                {
                    if(Variable.getAVariableValue(word) instanceof Double || Variable.getAVariableValue(word) instanceof String)
                    {
                        currentPosition = startPos;
                        currentChar = str.charAt(currentPosition);
                        return false;
                    }
                    else if(Variable.getAVariableValue(word) instanceof Object[]) // Array variable
                    {
                        Object[] theArray = (Object[]) Variable.getAVariableValue(word);
                        if(dealWithChar('[')) // Parentheses
                        {
                            int index = (int) parseExpression_Numbers();
                            if(index > theArray.length - 1 || index < 0) UIManager.consoleInstance.printErrorMessage("𐤌𐤏𐤓𐤊 𐤇𐤓𐤂 𐤌𐤄𐤕𐤇𐤅𐤌" + " - 𐤁𐤔𐤅𐤓𐤄 " + lineNumber, lineNumber);
                            dealWithChar(']');
                            if(theArray[index] instanceof Boolean)
                            {
                                tempValueHolder = (boolean) theArray[index];
                                return true;
                            }
                            else
                            {
                                currentPosition = startPos;
                                currentChar = str.charAt(currentPosition);
                                return false;
                            }
                        }
                    }

                    tempValueHolder = (boolean) Variable.getAVariableValue(word);
                    return true;
                }
            }

            // Words
            if (word.equals("𐤀𐤌𐤕")) tempValueHolder = true;
            else if (word.equals("𐤔𐤒𐤓")) tempValueHolder = false;
            else
            {
                currentPosition = startPos;
                currentChar = str.charAt(currentPosition);
                return false;
            }
        }
        else
        {
            currentPosition = startPos;
            currentChar = str.charAt(currentPosition);
            return false;
        }

        return true;
    }

    private boolean parseStringComparison() // Return true if parsing succeed - the parsing itself is stored in the tempValueHolder
    {
        int startPos = this.currentPosition;
        boolean comparisonType; // false == , true =!

        String a = getAStringForComparison(this.currentPosition);
        if(!tempValueHolder) return false;

        if(dealWithDoubleChar('=', '=')) comparisonType = false;
        else if(dealWithDoubleChar('!', '=')) comparisonType = true;
        else
        {
            currentPosition = startPos;
            currentChar = str.charAt(currentPosition);
            return false;
        }

        String b = getAStringForComparison(this.currentPosition);
        if(!tempValueHolder) return false;

        if(!comparisonType) tempValueHolder = a.equals(b);
        else tempValueHolder = !a.equals(b);
        return true;
    }

    private String getAStringForComparison(int startPos) // tempValueHolder store here succeed or failure
    {
        if(dealWithChar('"')) // Quote
        {
            while (currentChar != '"') nextChar();
            tempValueHolder = true;
            nextChar();
            return str.substring(startPos + 1, this.currentPosition - 1);
        }
        else if((currentChar >= LB && currentChar <= UB)) // Variables
        {
            while ((currentChar >= LB && currentChar <= UB) || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String word = str.substring(startPos, this.currentPosition);

            for(String varName: Variable.getVariablesNames())
            {
                if(word.equals(varName))
                {
                    if(Variable.getAVariableValue(word) instanceof Double || Variable.getAVariableValue(word) instanceof Boolean || Variable.getAVariableValue(word) instanceof Object[])
                    {
                        currentPosition = startPos;
                        currentChar = str.charAt(currentPosition);
                        tempValueHolder = false;
                        return "";
                    }

                    tempValueHolder = true;
                    return (String) Variable.getAVariableValue(word);
                }
            }
        }

        return "";
    }
}
