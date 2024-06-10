package model;

public class MedPat {

    private int ref;
    private int cin;

    public MedPat(int ref, int cin) {
        this.ref = ref;
        this.cin = cin;
    }

    public int getRefmed() {
        return ref;
    }

    public void setRefmed(int ref) {
        this.ref = ref;
    }

    public int getCinpat() {
        return cin;
    }

    public void setCinpat(int cin) {
        this.cin = cin;
    }
}
