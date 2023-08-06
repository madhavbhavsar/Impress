package com.mad.impress.model;

import java.util.List;

public class Customer {

    String id = "";
    String bookName;
    String customerId;
    String customerName;
    String mobile1;
    String mobile2;
    List<String> mobiles;
    String note;

    public Customer() {
    }

    public Customer(String id, String bookName, String customerId, String customerName, List<String> mobiles, String note) {
        this.id = id;
        this.bookName = bookName;
        this.customerId = customerId;
        this.customerName = customerName;

        this.mobiles = mobiles;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
