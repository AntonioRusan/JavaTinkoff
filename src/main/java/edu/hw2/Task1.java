package edu.hw2;

public class Task1 {
    public sealed interface Expr {
        double evaluate();

        public record Constant(double number) implements Expr {
            @Override
            public double evaluate() {
                return number;
            }
        }

        public record Negate(Expr number) implements Expr {
            @Override
            public double evaluate() {
                return -number.evaluate();
            }
        }

        public record Exponent(Expr base, double power) implements Expr {
            @Override
            public double evaluate() {
                return Math.pow(base.evaluate(), power);
            }
        }

        public record Addition(Expr first, Expr second) implements Expr {
            @Override
            public double evaluate() {
                return first.evaluate() + second.evaluate();
            }
        }

        public record Multiplication(Expr first, Expr second) implements Expr {
            @Override
            public double evaluate() {
                return first.evaluate() * second.evaluate();
            }
        }
    }
}
