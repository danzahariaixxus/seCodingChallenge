package org.danz.model

class Transaction {

    String fromAccount;
    String toAccount;
    double amount;
    Date date;

    static constraints = {
        toAccount(blank: false)
        fromAccount(blank: false)
        amount(min:0.01d, scale: 2)
    }
}
