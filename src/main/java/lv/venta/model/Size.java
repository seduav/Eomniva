package lv.venta.model;

public enum Size {
    X(1), S(2), M(3), L(4), XL(5), notKnown(0);

    private final int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}