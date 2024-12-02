/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionMainVisitor;
import expressivo.parser.ExpressionParser;

import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;


public interface Expression { 
    
    public static Expression parse(String input) {
        assert input != null && input != "";
        try {
            CharStream inputStream = CharStreams.fromString(input);
            ExpressionLexer lexer = new ExpressionLexer(inputStream);
            lexer.reportErrorsAsExceptions();
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(tokens);
            parser.reportErrorsAsExceptions();
            
            parser.setBuildParseTree(true);
            ParseTree parseTree = parser.root();
            
            ExpressionMainVisitor exprVisitor = new ExpressionMainVisitor();
            Expression expr = exprVisitor.visit(parseTree);
            
            return expr;
        } catch (ParseCancellationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Expression emptyExpression() {
        return new Value(0.0);
    }
    
    public Expression addExpr(Expression e);
    
    public Expression multiplyExpr(Expression e);
    
    public Expression addVariable(String variable);
    
    public Expression multiplyVariable(String variable);
    
    public Expression addConstant(double num);
    
    public Expression appendCoefficient(double num);
    
    public Expression substitute(Map<String,Double> environment);
    
    public Expression differentiate(String variable);
    
    @Override public String toString();
    
    @Override
    public boolean equals(Object thatObject);  
    
    @Override
    public int hashCode();    
}
