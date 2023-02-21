package fr.univlyon1.m2tiw.tiw1.annotations;

public enum COMPONENT_TYPE {
    CONTROLLER("controller", 1),
    BUSINESS("business", 2),
    SERVICE("service", 3),
    PERSISTENCE("persistence", 4);

    private int value = -1;
    private String name;

    COMPONENT_TYPE(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "COMPONENT_TYPE{" +
                "name='" + name + '\'' +
                '}';
    }
}
