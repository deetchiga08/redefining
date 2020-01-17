
package com.briller.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class Mail {

    public Mail() {
    }

    public Mail(String address, String subject, String text) {
        this.address = address;
        this.subject = subject;
        this.text = text;
    }

    private String address;

    private String subject;

    private String text;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}

