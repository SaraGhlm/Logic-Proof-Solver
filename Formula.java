package logic;

public class Formula {

    private final String form;
    private String postfixForm;
    private boolean value;

    public Formula() {
        form = null;
        value = true;
        postfixForm = null;
    }

    public Formula(String form, boolean value) {
        this.form = form;
        this.value = value;
    }

    public String getForm() {
        return form;
    }

    public String getPostFixForm() {
        return postfixForm;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setPostfixForm(String postfixForm) {
        this.postfixForm = postfixForm;
    }
}
