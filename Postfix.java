package logic;

import java.util.Arrays;
import java.util.Stack;

public class Postfix {

    String input;
    private tempClass[] operator;
    private final Stack stack;

    public Postfix(String input) {
        this.input = input;
        stack = new Stack();
        doPostfix(input);
    }

    public String getPostfix() {
        String postfix = "";
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            String temp = (String) stack.pop();
            postfix = postfix + temp;
        }
        return postfix;
    }

    private void doPostfix(String string) {
        int index = findLastOperator(string);
        String element, left, right;
        if (index != -1) {
            element = operator[index].getValue();
            stack.push(element);
            left = string.substring(0, operator[index].getIndex());
            right = string.substring(operator[index].getIndex() + 1, string.length());
            doPostfix(left);
            doPostfix(right);
        } else {
            boolean match = string.matches("p|q|r|⊥");
            if (match) {
                stack.push(string);
            } else if (string.charAt(0) == '(' && string.charAt(string.length() - 1) == ')') {
                while (index == -1) {
                    string = string.substring(1, string.length() - 1);
                    index = findLastOperator(string);
                }
                element = operator[index].getValue();
                stack.push(element);
                left = string.substring(0, operator[index].getIndex());
                right = string.substring(operator[index].getIndex() + 1, string.length());
                doPostfix(left);
                doPostfix(right);
            }
        }
    }

    private int findLastOperator(String string) {
        int index = 0;
        int bracketCounter = 0;
        int counter = 0;
        operator = new tempClass[0];

        while (index != string.length()) {
            String temp = string.substring(index, index + 1);
            if (bracketCounter == 0) {
                boolean match = temp.matches("&|v|→");
                if (match) {
                    operator = Arrays.copyOf(operator, counter + 1);
                    tempClass op = new tempClass(index, temp);
                    operator[counter] = op;
                    counter++;
                }
            }
            if (temp.equals("(")) {
                bracketCounter++;
            }
            if (temp.equals(")")) {
                bracketCounter--;
            }
            index++;
        }
        counter--;

        return counter;
    }
}
