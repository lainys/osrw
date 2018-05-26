package ru.miet.orgact;

public class Book {
    private String name;
    private String ISBN;
    private String publishingHouse;
    private String link;
    private String bookTom;

    public Book() {
        name = "";
        ISBN = "";
        publishingHouse = "";
        link = "";
        bookTom = "";

    }

    public String getBookTom() {
        return bookTom;
    }

    public void setBookTom(String bookTom) {
        this.bookTom = bookTom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
