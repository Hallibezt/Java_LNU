package BlackJack.controller;

public enum Input {p(112),h(104),s(115),q(113);
    private final int value;

    Input(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
