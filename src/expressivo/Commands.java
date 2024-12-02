package expressivo;

import java.util.Map;

public class Commands {
    
    public static String differentiate(String expression, String variable) {
        assert expression != null && expression != "";
        assert variable != null && variable != "";
        
        Expression expr = Expression.parse(expression);
        Expression deriv = expr.differentiate(variable);
        
        return deriv.toString();
    }
    
    
    public static String simplify(String expression, Map<String,Double> environment) {
        assert expression != null && expression != "";
        assert environment != null;
        
        Expression expr = Expression.parse(expression);
        Expression simpExpr = expr.substitute(environment);
        
        return simpExpr.toString();
    }
    
}
