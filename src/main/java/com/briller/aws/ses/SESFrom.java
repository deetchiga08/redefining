package com.briller.aws.ses;

public enum SESFrom {

    NO_REPLY("notification@brillersys.com", "FTDC Questionnaire App");

    private final String email;
    private final String name;

    private SESFrom(String email, String name) {
        this.email =email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
