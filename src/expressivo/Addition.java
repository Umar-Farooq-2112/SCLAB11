package expressivo;

import java.util.Map;
 
public class Addition implements Expression {
    private final Expression left;
    private final Expression right;


    private void checkRep() {
        assert left != null;
        assert right != null;
    }
    public Addition(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }
    @Override public Expression addExpr(Expression e) {
        if (e.equals(new Value(0))) {
            return this;
        }
        Value two = new Value(2);
        if (this.equals(e)) {
            Expression newLeft = this.left.multiplyExpr(two);
            Expression newRight = this.right.multiplyExpr(two);

            checkRep();
            return new Addition(newLeft, newRight);
        }
        if (this.left.equals(e)) {
            Expression newLeft = this.left.multiplyExpr(two);

            checkRep();
            return new Addition(newLeft, this.right);
        }
        if (this.right.equals(e)) {
            Expression newRight = this.right.multiplyExpr(two);

            checkRep();
            return new Addition(this.left, newRight);
        }
        return new Addition(this, e);
    }

    @Override public Expression multiplyExpr(Expression e) {
        Value zero = new Value(0);
        if (e.equals(zero)) {
            return zero;
        }
        if (e.equals(new Value(1))) {
            return this;
        }
        return new Multiplication(this, e);
    }
    
    @Override public Expression addVariable(String variable) {
        assert variable != null && variable != "";
        
        return new Addition(new Variable(variable), this);
    }
    @Override public Expression multiplyVariable(String variable) {
        assert variable != null && variable != "";
        
        return new Multiplication(new Variable(variable), this);
    }
    @Override public Expression addConstant(double num) {
        assert num >= 0 && Double.isFinite(num);
        
        Value valNum = new Value(num);
        if (valNum.equals(new Value(0))) {
            return this;
        }
        if (this.left.equals(valNum)) {
            Expression newLeft = this.left.addConstant(num);
            checkRep();
            return new Addition(newLeft, this.right);
        }
        if (this.right.equals(valNum)) {
            Expression newRight = this.right.addConstant(num);
            checkRep();
            return new Addition(this.left, newRight);
        }
        checkRep();
        return new Addition(valNum, this);
    }
    @Override public Expression appendCoefficient(double num) {
        assert num >= 0 && Double.isFinite(num);
        
        Value valNum = new Value(num);
        Value zero = new Value(0);
        if (valNum.equals(zero)) {
            return zero;
        }
        if (valNum.equals(new Value(1))) {
            return this;
        }
        checkRep();
        return this.left.appendCoefficient(num)
                .addExpr(this.right.appendCoefficient(num));
    }
    @Override public Expression differentiate(String variable) {
        assert variable != null && variable != "";
        // d(u + v)/dx = du/dx + dv/dx
        // where u + v = this, u = left, v = right
        Expression diffLeft = left.differentiate(variable);
        Expression diffRight = right.differentiate(variable);

        checkRep();
        return diffLeft.addExpr(diffRight);     
    }
    @Override public Expression substitute(Map<String, Double> environment) {
        assert environment != null;
        
        return left.substitute(environment)
                .addExpr(right.substitute(environment));
    }
    @Override public String toString() {
        return left.toString()
               + " + "
               + right.toString();
    }
    
    @Override public boolean equals(Object thatObject) {
        if (thatObject == this) {
            return true;
        }
        if (!(thatObject instanceof Addition)) {
            return false;
        }
        Addition thatAdd = (Addition) thatObject;

        checkRep();
        return this.toString().equals(thatAdd.toString());
    }
    @Override public int hashCode() {
        final int prime = 37;
        int result = 1;
        
        result = prime*result + left.hashCode();
        result = prime*result + right.hashCode();

        checkRep();
        return result;
    }
}
