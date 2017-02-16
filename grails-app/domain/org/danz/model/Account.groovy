package org.danz.model

class Account {

    String name;
    String email;
    double balance = 200d;

    static constraints = {
        name(blank: false)
        email(blank: false)
    }
}
