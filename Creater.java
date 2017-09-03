package logic;

import java.util.Arrays;

public class Creater {

    private String premise;
    private String conclusion;
    private final Tree tree;
    private Formula[] preArray;
    private Formula[] conArray;
    private tempClass[] operator;
    private Node node;

    public Creater(String premise, String conclusion) {
        this.premise = premise.replaceAll(" ", "");
        this.conclusion = conclusion.replaceAll(" ", "");
        tree = new Tree();
    }

    public Tree getTree() {
        return tree;
    }

    public void print() {
        tree.printLevelOrder(tree.getRoot());
    }

    public void createTree() {
        Queue queue = new Queue();
        node = new Node(premise, conclusion);
        tree.insert(null, node);
        queue.enqueue(node);
        while (!queue.isEmpty()) {
            node = queue.dequeue();
            premise = node.getPremise();
            conclusion = node.getConclusion();
            arrays();
            if (!checkAxiomAndBut()) {
                Node[] children = createChildren();
                for (Node child : children) {
                    if (child != null) {
                        tree.insert(node, child);
                        queue.enqueue(child);
                    }
                }
            }
        }
    }

    private void arrays() {
        String[] pre = premise.split(",");
        String[] con = conclusion.split(",");
        preArray = new Formula[pre.length];
        conArray = new Formula[con.length];

        for (int i = 0; i < pre.length; i++) {
            Formula f = new Formula(pre[i], true);
            preArray[i] = f;
        }

        for (int i = 0; i < con.length; i++) {
            Formula f = new Formula(con[i], true);
            conArray[i] = f;
        }
    }

    private boolean checkAxiomAndBut() {
        boolean pInPermise = false;
        boolean qInPermise = false;
        boolean rInPermise = false;
        boolean pInConclusion = false;
        boolean qInConclusion = false;
        boolean rInConclusion = false;
        boolean leftBut = false;

        for (Formula formula : preArray) {
            switch (formula.getForm()) {
                case "p":
                    pInPermise = true;
                    break;
                case "q":
                    qInPermise = true;
                    break;
                case "r":
                    rInPermise = true;
                    break;
                case "⊥":
                    leftBut = true;
                    break;
            }
        }

        for (Formula formula : conArray) {
            switch (formula.getForm()) {
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
        } else if (leftBut) {
            node.setLeftFunction(true);
            node.setFunction("⊥");
            return true;
        }

        return false;
    }

    private Node[] createChildren() {
        for (int i = 0; i < preArray.length; i++) {
            if (!preArray[i].getForm().equals("p") && !preArray[i].getForm().equals("q")
                    && !preArray[i].getForm().equals("r") && !preArray[i].getForm().equals("")) {
                Node[] child = divide(preArray[i].getForm(), "premise", i);
                return child;
            }
        }
        for (int i = 0; i < conArray.length; i++) {
            if (!conArray[i].getForm().equals("p") && !conArray[i].getForm().equals("q")
                    && !conArray[i].getForm().equals("r") && !conArray[i].getForm().equals("⊥")) {
                Node[] child = divide(conArray[i].getForm(), "conclusion", i);
                return child;
            }
        }
        return null;
    }

    private Node[] divide(String string, String preOrCon, int indexOfString) {
        Node[] child = null;
        if (preOrCon.equals("premise")) {
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
                    node.setFunction("&");
                    node.setLeftFunction(true);
                    child = leftAnd(left, right, indexOfString);
                    break;
                case "v":
                    node.setFunction("v");
                    node.setLeftFunction(true);
                    child = leftOr(left, right, indexOfString);
                    break;
                case "→":
                    node.setFunction("→");
                    node.setLeftFunction(true);
                    child = leftImply(left, right, indexOfString);
                    break;
            }
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
                    node.setFunction("&");
                    node.setRightFuncion(true);
                    child = rightAnd(left, right, indexOfString);
                    break;
                case "v":
                    node.setFunction("v");
                    node.setRightFuncion(true);
                    child = rightOr(left, right, indexOfString);
                    break;
                case "→":
                    node.setFunction("→");
                    node.setRightFuncion(true);
                    child = rightImply(left, right, indexOfString);
                    break;
            }
        }
        return child;
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

    private Node[] leftImply(String left, String right, int index) {
        premise = "";
        for (int i = 0; i < preArray.length; i++) {
            if (!"".equals(premise)) {
                premise = premise + ",";
            }
            if (index != i) {
                premise = premise + preArray[i].getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        conclusion = left;
        for (Formula conArray1 : conArray) {
            conclusion = conclusion + "," + conArray1.getForm();
        }

        Node leftNode = new Node(premise, conclusion);

        premise = right;
        for (int i = 0; i < preArray.length; i++) {
            if (i != index) {
                premise = premise + "," + preArray[i].getForm();
            }
        }
        conclusion = conArray[0].getForm();
        for (int i = 1; i < conArray.length; i++) {
            conclusion = conclusion + "," + conArray[i].getForm();
        }
        Node rightNode = new Node(premise, conclusion);

        Node[] child = {leftNode, rightNode};
        return child;
    }

    private Node[] rightImply(String left, String right, int index) {
        premise = left;
        conclusion = right;
        for (Formula preArray1 : preArray) {
            if (!preArray1.getForm().equals("")) {
                premise = premise + "," + preArray1.getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        for (int i = 0; i < conArray.length; i++) {
            if (i != index) {
                conclusion = conclusion + "," + conArray[i].getForm();
            }
        }
        Node mainNode = new Node(premise, conclusion);
        Node[] child = {mainNode};
        return child;
    }

    private Node[] leftAnd(String left, String right, int index) {
        premise = left + "," + right;
        conclusion = conArray[0].getForm();
        for (int i = 0; i < preArray.length; i++) {
            if (i != index) {
                premise = premise + "," + preArray[i].getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        for (int i = 1; i < conArray.length; i++) {
            conclusion = conclusion + "," + conArray[i].getForm();
        }
        Node mainNode = new Node(premise, conclusion);
        Node[] child = {mainNode};
        return child;
    }

    private Node[] rightAnd(String left, String right, int index) {
        premise = "";
        for (int i = 0; i < preArray.length; i++) {
            if (!"".equals(premise)) {
                premise = premise + ",";
            }
            if (index != i) {
                premise = premise + preArray[i].getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        conclusion = left;
        for (int i = 0; i < conArray.length; i++) {
            if (i != index) {
                conclusion = conclusion + "," + conArray[i].getForm();
            }
        }
        Node leftNode = new Node(premise, conclusion);
        conclusion = right;
        for (int i = 0; i < conArray.length; i++) {
            if (i != index) {
                conclusion = conclusion + "," + conArray[i].getForm();
            }
        }
        Node rightNode = new Node(premise, conclusion);

        Node[] child = {leftNode, rightNode};
        return child;
    }

    private Node[] leftOr(String left, String right, int index) {
        premise = left;
        for (int i = 0; i < preArray.length; i++) {
            if (i != index) {
                premise = premise + "," + preArray[i].getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        conclusion = conArray[0].getForm();
        for (int i = 1; i < conArray.length; i++) {
            conclusion = conclusion + "," + conArray[i].getForm();
        }
        Node leftNode = new Node(premise, conclusion);

        premise = right;
        for (int i = 0; i < preArray.length; i++) {
            if (i != index) {
                premise = premise + "," + preArray[i].getForm();
            }
        }
        Node rightNode = new Node(premise, conclusion);

        Node[] child = {leftNode, rightNode};
        return child;
    }

    private Node[] rightOr(String left, String right, int index) {
        premise = "";
        for (int i = 0; i < preArray.length; i++) {
            if (!"".equals(premise)) {
                premise = premise + ",";
            }
            if (index != i) {
                premise = premise + preArray[i].getForm();
            }
        }
        if (!"".equals(premise) && premise.charAt(premise.length() - 1) == ',') {
            premise = premise.substring(0, premise.length() - 1);
        }
        conclusion = left + "," + right;
        for (int i = 0; i < conArray.length; i++) {
            if (i != index) {
                conclusion = conclusion + "," + conArray[i].getForm();
            }
        }
        Node mainNode = new Node(premise, conclusion);
        Node[] child = {mainNode};
        return child;
    }
}
