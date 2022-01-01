package main;

public class Tree {
    private short legend;
    private short hero1;
    private short hero2;
    private short epic1;
    private short epic2;
    private short epic3;
    private short epic4;
    private short unique1;
    private short unique2;
    private short unique3;
    private short unique4;
    private short unique5;
    private short unique6;
    private short unique7;
    private short unique8;
    private short value;
    private byte vh1;
    private byte vh2;
    private byte ve1;
    private byte ve2;
    private byte ve3;
    private byte ve4;
    private byte vu1;
    private byte vu2;
    private byte vu3;
    private byte vu4;
    private byte vu5;
    private byte vu6;
    private byte vu7;
    private byte vu8;

    public Tree() {
    }

    public Tree(short legend, short hero1, short hero2, short epic1, short epic2, short epic3, short epic4, short value, byte vh1, byte vh2, byte ve1, byte ve2, byte ve3, byte ve4) {
        this.legend = legend;
        this.hero1 = hero1;
        this.hero2 = hero2;
        this.epic1 = epic1;
        this.epic2 = epic2;
        this.epic3 = epic3;
        this.epic4 = epic4;
        this.value = value;
        this.vh1 = vh1;
        this.vh2 = vh2;
        this.ve1 = ve1;
        this.ve2 = ve2;
        this.ve3 = ve3;
        this.ve4 = ve4;
    }

    public byte getVu1() {
        return vu1;
    }

    public void setVu1(byte vu1) {
        this.vu1 = vu1;
    }

    public byte getVu2() {
        return vu2;
    }

    public void setVu2(byte vu2) {
        this.vu2 = vu2;
    }

    public byte getVu3() {
        return vu3;
    }

    public void setVu3(byte vu3) {
        this.vu3 = vu3;
    }

    public byte getVu4() {
        return vu4;
    }

    public void setVu4(byte vu4) {
        this.vu4 = vu4;
    }

    public byte getVu5() {
        return vu5;
    }

    public void setVu5(byte vu5) {
        this.vu5 = vu5;
    }

    public byte getVu6() {
        return vu6;
    }

    public void setVu6(byte vu6) {
        this.vu6 = vu6;
    }

    public byte getVu7() {
        return vu7;
    }

    public void setVu7(byte vu7) {
        this.vu7 = vu7;
    }

    public byte getVu8() {
        return vu8;
    }

    public void setVu8(byte vu8) {
        this.vu8 = vu8;
    }

    public byte getVh1() {
        return vh1;
    }

    public void setVh1(byte vh1) {
        this.vh1 = vh1;
    }

    public byte getVh2() {
        return vh2;
    }

    public void setVh2(byte vh2) {
        this.vh2 = vh2;
    }

    public byte getVe1() {
        return ve1;
    }

    public void setVe1(byte ve1) {
        this.ve1 = ve1;
    }

    public byte getVe2() {
        return ve2;
    }

    public void setVe2(byte ve2) {
        this.ve2 = ve2;
    }

    public byte getVe3() {
        return ve3;
    }

    public void setVe3(byte ve3) {
        this.ve3 = ve3;
    }

    public byte getVe4() {
        return ve4;
    }

    public void setVe4(byte ve4) {
        this.ve4 = ve4;
    }

    public short getLegend() {
        return legend;
    }

    public void setLegend(short legend) {
        this.legend = legend;
    }

    public short getHero1() {
        return hero1;
    }

    public void setHero1(short hero1) {
        this.hero1 = hero1;
    }

    public short getHero2() {
        return hero2;
    }

    public void setHero2(short hero2) {
        this.hero2 = hero2;
    }

    public short getEpic1() {
        return epic1;
    }

    public void setEpic1(short epic1) {
        this.epic1 = epic1;
    }

    public short getEpic2() {
        return epic2;
    }

    public void setEpic2(short epic2) {
        this.epic2 = epic2;
    }

    public short getEpic3() {
        return epic3;
    }

    public void setEpic3(short epic3) {
        this.epic3 = epic3;
    }

    public short getEpic4() {
        return epic4;
    }

    public void setEpic4(short epic4) {
        this.epic4 = epic4;
    }

    public short getUnique1() {
        return unique1;
    }

    public void setUnique1(short unique1) {
        this.unique1 = unique1;
    }

    public short getUnique2() {
        return unique2;
    }

    public void setUnique2(short unique2) {
        this.unique2 = unique2;
    }

    public short getUnique3() {
        return unique3;
    }

    public void setUnique3(short unique3) {
        this.unique3 = unique3;
    }

    public short getUnique4() {
        return unique4;
    }

    public void setUnique4(short unique4) {
        this.unique4 = unique4;
    }

    public short getUnique5() {
        return unique5;
    }

    public void setUnique5(short unique5) {
        this.unique5 = unique5;
    }

    public short getUnique6() {
        return unique6;
    }

    public void setUnique6(short unique6) {
        this.unique6 = unique6;
    }

    public short getUnique7() {
        return unique7;
    }

    public void setUnique7(short unique7) {
        this.unique7 = unique7;
    }

    public short getUnique8() {
        return unique8;
    }

    public void setUnique8(short unique8) {
        this.unique8 = unique8;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "legend=" + legend +
                ", hero1=" + hero1 +
                ", hero2=" + hero2 +
                ", epic1=" + epic1 +
                ", epic2=" + epic2 +
                ", epic3=" + epic3 +
                ", epic4=" + epic4 +
                ", unique1=" + unique1 +
                ", unique2=" + unique2 +
                ", unique3=" + unique3 +
                ", unique4=" + unique4 +
                ", unique5=" + unique5 +
                ", unique6=" + unique6 +
                ", unique7=" + unique7 +
                ", unique8=" + unique8 +
                ", value=" + value +
                ", vh1=" + vh1 +
                ", vh2=" + vh2 +
                ", ve1=" + ve1 +
                ", ve2=" + ve2 +
                ", ve3=" + ve3 +
                ", ve4=" + ve4 +
                ", vu1=" + vu1 +
                ", vu2=" + vu2 +
                ", vu3=" + vu3 +
                ", vu4=" + vu4 +
                ", vu5=" + vu5 +
                ", vu6=" + vu6 +
                ", vu7=" + vu7 +
                ", vu8=" + vu8 +
                '}';
    }
}
