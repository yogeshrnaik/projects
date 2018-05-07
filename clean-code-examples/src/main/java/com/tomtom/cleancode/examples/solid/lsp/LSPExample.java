package com.tomtom.cleancode.examples.solid.lsp;

public class LSPExample {

    public void testCalculateArea(Rectangle r) {
        r.setBreadth(2);
        r.setLength(3);
        assert r.getArea() == 6 : printError("area", r);
        assert r.getBreadth() == 2 : printError("breadth", r);
        assert r.getLength() == 3 : printError("length", r);
    }

    private String printError(String errorIdentifer, Rectangle r) {
        return "Unexpected value of " + errorIdentifer + "  for instance of " + r.getClass().getName();
    }

    public static void main(String[] args) {
        LSPExample lsp = new LSPExample();
        lsp.testCalculateArea(new Rectangle());
        lsp.testCalculateArea(new Square());
    }
}

class Rectangle {

    private int length;
    private int breadth;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getArea() {
        return this.length * this.breadth;
    }
}

class Square extends Rectangle {

    @Override
    public void setBreadth(int breadth) {
        super.setBreadth(breadth);
        super.setLength(breadth);
    }

    @Override
    public void setLength(int length) {
        super.setLength(length);
        super.setBreadth(length);
    }
}