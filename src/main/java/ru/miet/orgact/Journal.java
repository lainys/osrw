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
    private String russian;
    private String vak;
    private String recenz;
    private String isi;
    private String scopus;
    private String rinc;
    private String impact_factor;
    private String impact_factor_JSR;
    private String link;


    public Journal() {

        code = -1;
        issn = "";
        russian = "";
        vak = "";
        recenz = "";
        isi = "";
        scopus = "";
        rinc = "";
        impact_factor = "";
        impact_factor_JSR = "";
        link = "";


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
        return Boolean.parseBoolean(russian);
    }

    public void setRussian(boolean russian) {
        this.russian = Boolean.toString(russian);
    }

    public boolean isVak() {
        return Boolean.parseBoolean(vak);
    }

    public void setVak(boolean vak) {
        this.vak = Boolean.toString(vak);
    }

    public boolean isRecenz() {
        return Boolean.parseBoolean(recenz);
    }

    public void setRecenz(boolean recenz) {
        this.recenz = Boolean.toString(recenz);
    }

    public boolean isIsi() {
        return Boolean.parseBoolean(isi);
    }

    public void setIsi(boolean isi) {
        this.isi = Boolean.toString(isi);
    }

    public boolean isScopus() {
        return Boolean.parseBoolean(scopus);
    }

    public void setScopus(boolean scopus) {
        this.scopus = Boolean.toString(scopus);
    }

    public boolean isRinc() {
        return Boolean.parseBoolean(rinc);
    }

    public void setRinc(boolean rinc) {
        this.rinc = Boolean.toString(rinc);
    }

    public double getImpact_factor() {
        return Double.parseDouble(impact_factor);
    }

    public void setImpact_factor(double impact_factor) {
        this.impact_factor = Double.toString(impact_factor);
    }

    public boolean isImpact_factor_JSR() {
        return Boolean.parseBoolean(impact_factor_JSR);
    }

    public void setImpact_factor_JSR(boolean impact_factor_JSR) {
        this.impact_factor_JSR = Boolean.toString(impact_factor_JSR);
    }


    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public String getVak() {
        return vak;
    }

    public void setVak(String vak) {
        this.vak = vak;
    }

    public String getRecenz() {
        return recenz;
    }

    public void setRecenz(String recenz) {
        this.recenz = recenz;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getScopus() {
        return scopus;
    }

    public void setScopus(String scopus) {
        this.scopus = scopus;
    }

    public String getRinc() {
        return rinc;
    }

    public void setRinc(String rinc) {
        this.rinc = rinc;
    }

    public void setImpact_factor(String impact_factor) {
        this.impact_factor = impact_factor;
    }

    public String getImpact_factor_JSR() {
        return impact_factor_JSR;
    }

    public void setImpact_factor_JSR(String impact_factor_JSR) {
        this.impact_factor_JSR = impact_factor_JSR;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}