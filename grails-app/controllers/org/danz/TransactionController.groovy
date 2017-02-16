package org.danz

import org.danz.model.Account
import org.danz.model.Transaction

class TransactionController {

    def index() {

        return [ errors: [], accounts: getAccounts()]
    }

    def viewTransactions() {

        def accounts = getAccounts()
        def errors = []
        def transactions = []
        def name = request.getParameter("accountName")
        def account = Account.findByName(name)

        transactions = getTransactions(account, errors, transactions)

        render(view:'/transaction/index',
                model: [transactions: transactions,
                        errors: errors,
                        account: account,
                        accounts:accounts])
    }

    private List<Account> getAccounts() {
        Account.list([sort: "name", order: "asc"])
    }

    private List<Transaction> getTransactions(Account account, ArrayList errors, List<Transaction> transactions) {
        if (account == null) {
            errors.add("Please select a name from the list")
        } else {
            transactions = Transaction.findAllByFromAccountOrToAccount(account.name, account.name, [sort: "date"]);
        }
        transactions
    }
}

