package ru.miet.orgact;

//статический класс для базовых проверок значений полей
public class Checker {

    public static boolean checkNumber(String field) {
        try {
            Integer.parseInt(field);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean checkStringWithoutNumber(String str) {
        String pattern = "[a-zа-я]*";
        return checkPattern(str.toLowerCase(), pattern);
    }

    public static boolean checkFIO(String fio) {
        String pattern = "[a-zа-я]*\\s+[a-zа-я]{1}.[a-zа-я]{1}.";
        //String pattern = "[a-zа-я]*\\s+[a-zа-я]+.";
        return checkPattern(fio.toLowerCase(), pattern);
    }

    public static boolean checkISBN(String isbn) {
        String pattern = "\\d{3}-\\d{1}-\\d{5}-\\d{3}-\\d{1}";
        return checkPattern(isbn, pattern);
    }

    public static boolean checkPattern(String str, String pattern) {
        if (str.matches(pattern)) {
            System.out.println("Yes");
            return false;
        } else {
            System.out.println("No");
            return true;
        }
    }


    public static void main(String[] args) {
        Checker.checkISBN("978-1-33333-333-1");
        Checker.checkISBN("978-1-33333-333-1");
        Checker.checkISBN("978-1-33333-333-1");
    }

}
