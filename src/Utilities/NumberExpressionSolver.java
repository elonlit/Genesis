package Utilities;

public class NumberExpressionSolver extends ExpressionSolver
{
    public NumberExpressionSolver(String str, int lineNumber)
    {
        super(str, lineNumber);
    }

    @Override
    public Object getResult()
    {
        nextChar();
        double x = parseExpression_Numbers();
        if(currentPosition < str.length()) return new BooleanExpressionSolver(str, lineNumber).getResult(); // If it is not number check for boolean
        if(errorFlag) return new BooleanExpressionSolver(str, lineNumber).getResult(); // If it is not number check for boolean
        return x;
    }
}
