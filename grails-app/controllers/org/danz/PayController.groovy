package org.danz

import grails.validation.ValidationException
import org.danz.model.Account
import org.danz.model.Transaction

class PayController {

    def mailService

    def index() {

        return [ errors: [], accounts: getAllAccounts()]
    }

    def doPayment() {
        def errors = []
        def nameFrom = request.getParameter("accountFrom")
        def nameTo = request.getParameter("accountTo")
        def amount = request.getParameter("amount")
        def accountFrom = Account.findByName(nameFrom)
        def accountTo = Account.findByName(nameTo)
        def amountDouble, success

        amountDouble = validateAmount(amount, errors)

        validateAccounts(accountFrom, errors, amountDouble, accountTo)

        if (errors.isEmpty()) {
            success = doTransaction(accountTo, accountFrom, errors, amountDouble)
            sendEmails(accountFrom, amountDouble, accountTo)
        }

        render(view:'/pay/index',
                model: [errors: errors,
                        success: success,
                        accounts: getAllAccounts(),
                        accountFrom: accountFrom,
                        accountTo:accountTo])
    }

    private List<Account> getAllAccounts() {
        Account.list([sort: "name", order: "asc"])
    }

    private void sendEmails(accountFrom, amountDouble, accountTo) {

        mailService.sendMail {
            from "admin@seCodincGhallenge.com"
            to "\"" + accountFrom.email + "\""
            subject "New Transaction"
            body "A new payment has been made to you from " + accountFrom.name + ": £" + amountDouble
        }
        mailService.sendMail {
            from "admin@seCodincGhallenge.com"
            to "\"" + accountTo.email + "\""
            subject "New Transaction"
            text "A new payment has been made from you to " + accountTo.name + ": £" + amountDouble
        }
    }

    private synchronized String doTransaction(Account accountTo, Account accountFrom, ArrayList errors, double amountDouble) {
        def success
        accountFrom.balance -= amountDouble;
        accountTo.balance += amountDouble;
        accountFrom.save(flush: true)
        accountTo.save(flush: true)
        try {
            new Transaction(toAccount: accountTo.name, fromAccount: accountFrom.name, amount: amountDouble, date: new Date())
                    .save(failOnError: true)
            success = "£" + amountDouble + " was transferred successfully!"
        } catch (ValidationException e) {
            errors.add("The value must be greater than 0")
        }
        success
    }

    private void validateAccounts(Account accountFrom, ArrayList errors, double amountDouble, Account accountTo) {
        if (accountFrom == null) {
            errors.add("Please select a name from the list for 'From account' ")
        } else if (amountDouble && accountFrom.balance < amountDouble) {
            errors.add("The value exceeds the account balance of " + accountFrom.name)
        }
        if (accountTo == null) {
            errors.add("Please select a name from the list for 'To account' ")
        } else if (accountTo.equals(accountFrom)) {
            errors.add("One cannot transfer into one's own account ")
        }
    }

    private double validateAmount(String amount, ArrayList errors) {
        def amountDouble = 0d
        try {
            amountDouble = amount.toDouble();
        } catch (Exception e) {
            errors.add("The amount is not numeric! ")
        }
        amountDouble
    }
}
