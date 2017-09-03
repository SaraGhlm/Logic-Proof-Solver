package logic;

import java.util.Arrays;

public class Evaluate {

    private String premise;
    private String conclusion;
    private String[] premiseArray;
    private String[] conclusionArray;
    private tempClass[] operator;

    public Evaluate(String premise, String conclusion) {
        this.premise = premise;
        this.conclusion = conclusion;
    }

    private void separate() {
        premise = premise.replaceAll(" ", "");
        conclusion = conclusion.replaceAll(" ", "");

        premiseArray = premise.split(",");
        conclusionArray = conclusion.split(",");
    }

    private void eval() {
        while (!checkAxiom()) {
            for (String premiseArray1 : premiseArray) {
            }
        }
    }

    private boolean checkAxiom() {
        boolean pInPermise = false;
        boolean qInPermise = false;
        boolean rInPermise = false;
        boolean pInConclusion = false;
        boolean qInConclusion = false;
        boolean rInConclusion = false;

        for (String string : premiseArray) {
            switch (string) {
                case "p":
                    pInPermise = true;
                    break;
                case "q":
                    qInPermise = true;
                    break;
                case "r":
                    rInPermise = true;
                    break;
            }
        }

        for (String string : conclusionArray) {
            switch (string) {
                case "p":
                    pInConclusion = true;
                    break;
                case "q":
                    qInConclusion = true;
                    break;
                case "r":
                    rInConclusion = true;
                    break;
            }
        }
        if (pInPermise && pInConclusion) {
            return true;
        } else if (qInPermise && qInConclusion) {
            return true;
        } else if (rInPermise && rInConclusion) {
            return true;
        }

        return false;
    }

    /*
     If arrayID is true, then it is premise array.
     If arrayID is false, then it is conclusion array.
     */
    private boolean divide(String string, boolean arrayID) {
        if (arrayID) {
            //String string = premiseArray[indx];
            if (string.equals("p") || string.equals("q") || string.equals("r")) {
                return false;
            } else {
                int index = findOperator(string);
                String element, left, right;
                if (index != -1) {
                    element = operator[index].getValue();
                    left = string.substring(0, operator[index].getIndex());
                    right = string.substring(operator[index].getIndex() + 1, string.length());
                } else {
                    while (index == -1) {
                        string = string.substring(1, string.length() - 1);
                        index = findOperator(string);
                    }
                    element = operator[index].getValue();
                    left = string.substring(0, operator[index].getIndex());
                    right = string.substring(operator[index].getIndex() + 1, string.length());
                }
                switch (element) {
                    case "&":
                        leftAnd(left, right);
                        break;
                    case "v":
                        leftOr(left, right);
                        break;
                    case "→":
                        leftImply(left, right);
                        break;
                }
                return true;
            }
        } else {
            //String string = conclusionArray[indx];
            if (string.equals("p") || string.equals("q") || string.equals("r")) {
                return false;
            } else {
                int index = findOperator(string);
                return true;
            }
        }
    }

    /*
     Returns an integer.
     If it equals to -1, then in divide(int indx, boolean arrayID) should remove 
     brackets and call the function again.
     */
    private int findOperator(String input) {
        int index = 0;
        int bracketCounter = 0;
        int counter = 0;
        operator = new tempClass[0];

        while (index != input.length()) {
            String temp = input.substring(index, index + 1);
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

    private void leftImply(String left, String right) {

    }

    private void rightImply(String left, String right) {

    }

    private void leftAnd(String left, String right) {

    }

    private void rightAnd(String left, String right) {

    }

    private void leftOr(String left, String right) {

    }

    private void rightOr(String left, String right) {

    }

}
