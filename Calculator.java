package logic;

import java.util.Stack;

public class Calculator {

    private final boolean p;
    private final boolean q;
    private final boolean r;
    private final String input;
    private final Stack stack;

    public Calculator(boolean p, boolean q, boolean r, String input) {
        this.p = p;
        this.q = q;
        this.r = r;
        this.input = input;
        stack = new Stack();
    }

    public boolean getValue() {
        return calculate();
    }

    private boolean calculate() {
        int index = 0;
        while (index != input.length()) {
            String temp = input.substring(index, index + 1);
            switch (temp) {
                case "p":
                    stack.push(p);
                    break;
                case "q":
                    stack.push(q);
                    break;
                case "r":
                    stack.push(r);
                    break;
                case "⊥":
                    stack.push(false);
                    break;
                default:
                    boolean left = (boolean) stack.pop();
                    boolean right = (boolean) stack.pop();
                    boolean result;
                    switch (temp) {
                        case "&":
                            result = left && right;
                            stack.push(result);
                            break;
                        case "v":
                            result = left || right;
                            stack.push(result);
                            break;
                        case "→":
                            result = !left || right;
                            stack.push(result);
                            break;
                    }
            }
            index++;
        }
        return (boolean) stack.pop();
    }

}
