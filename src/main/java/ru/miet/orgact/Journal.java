package ru.miet.orgact;

public class Journal {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String name;
    private String issn;
    private boolean russian;
    private boolean vak;
    private boolean recenz;
    private boolean isi;
    private boolean scopus;
    private boolean rinc;
    private double impact_factor;
    private boolean impact_factor_JSR;
    private String link;


    public Journal() {

    }


    public String toString() {
        String result = name;
        return result;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public boolean isRussian() {
        return russian;
    }

    public void setRussian(boolean russian) {
        this.russian = russian;
    }

    public boolean isVak() {
        return vak;
    }

    public void setVak(boolean vak) {
        this.vak = vak;
    }

    public boolean isRecenz() {
        return recenz;
    }

    public void setRecenz(boolean recenz) {
        this.recenz = recenz;
    }

    public boolean isIsi() {
        return isi;
    }

    public void setIsi(boolean isi) {
        this.isi = isi;
    }

    public boolean isScopus() {
        return scopus;
    }

    public void setScopus(boolean scopus) {
        this.scopus = scopus;
    }

    public boolean isRinc() {
        return rinc;
    }

    public void setRinc(boolean rinc) {
        this.rinc = rinc;
    }

    public double getImpact_factor() {
        return impact_factor;
    }

    public void setImpact_factor(double impact_factor) {
        this.impact_factor = impact_factor;
    }

    public boolean isImpact_factor_JSR() {
        return impact_factor_JSR;
    }

    public void setImpact_factor_JSR(boolean impact_factor_JSR) {
        this.impact_factor_JSR = impact_factor_JSR;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}