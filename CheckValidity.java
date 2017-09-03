package logic;

public class CheckValidity {

    boolean satisfy;
    private final Formula[] preArray;
    private final Formula[] conArray;
    private boolean p;
    private boolean q;
    private boolean r;

    private int counter;
    private boolean saveP;
    private boolean saveQ;
    private boolean saveR;
    private String[] formul;
    private boolean[] value;

    public CheckValidity(String premise, String conclusion) {
        this.satisfy = true;
        counter = 0;
        premise = premise.replaceAll(" ", "");
        conclusion = conclusion.replaceAll(" ", "");

        String[] pre = premise.split(",");
        String[] con = conclusion.split(",");
        preArray = new Formula[pre.length];
        conArray = new Formula[con.length];

        for (int i = 0; i < pre.length; i++) {
            if (!pre[i].equals("")) {
                Formula f = new Formula(pre[i], true);
                Postfix post = new Postfix(pre[i]);
                f.setPostfixForm(post.getPostfix());
                preArray[i] = f;
            }
        }

        for (int i = 0; i < con.length; i++) {
            Formula f = new Formula(con[i], true);
            Postfix post = new Postfix(con[i]);
            f.setPostfixForm(post.getPostfix());
            conArray[i] = f;
        }
    }

    public boolean isValid() {
        setValue();
        if (!satisfy) {
            setArrays();
        }
        return satisfy;
    }

    private void setValue() {
        boolean allPremiseTrue = true;

        for (int i = 0; i < 2 && satisfy; i++) {
            p = !p;
            for (int j = 0; j < 2 && satisfy; j++) {
                q = !q;
                for (int k = 0; k < 2 && satisfy; k++) {
                    r = !r;
                    for (Formula formula : preArray) {
                        if (formula != null) {
                            Calculator c = new Calculator(p, q, r, formula.getPostFixForm());
                            if (!c.getValue()) {
                                allPremiseTrue = false;
                                break;
                            }
                        }
                    }
                    if (allPremiseTrue) {
                        for (Formula formula : conArray) {
                            Calculator c = new Calculator(p, q, r, formula.getPostFixForm());
                            if (!c.getValue()) {
                                saveP = p;
                                saveQ = q;
                                saveR = r;
                                satisfy = false;
                                break;
                            }
                        }
                    }
                    allPremiseTrue = true;
                }
            }
        }
    }

    private void setArrays() {
        for (Formula formula : preArray) {
            if (formula != null) {
                counter++;
            }
        }
        int length = 3 + counter + conArray.length;
        formul = new String[length];
        value = new boolean[length];
        formul[0] = "p";
        value[0] = saveP;
        formul[1] = "q";
        value[1] = saveQ;
        formul[2] = "r";
        value[2] = saveR;
        int temp = 3;
        for (Formula formula : preArray) {
            if (formula != null) {
                Calculator c = new Calculator(p, q, r, formula.getPostFixForm());
                formul[temp] = formula.getForm();
                value[temp] = c.getValue();
                temp++;
            }
        }
        for (Formula formula : conArray) {
            Calculator c = new Calculator(p, q, r, formula.getPostFixForm());
            formul[temp] = formula.getForm();
            value[temp] = c.getValue();
            temp++;
        }
    }

    public String[] getFormul() {
        return formul;
    }

    public boolean[] getValue() {
        return value;
    }
}
