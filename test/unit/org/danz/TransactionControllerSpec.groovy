package org.danz

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.danz.model.Account
import org.danz.model.Transaction
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TransactionController)
@Mock([Transaction, Account])
class TransactionControllerSpec extends Specification {

    def jimSmith = new Account( name: "Jim Smith", email: "js@gmail.com" )
    def johnDoe = new Account( name: "John Doe", email: "jd@gmail.com" )
    def joeReed = new Account( name: "Joe Reed", email: "jr@yahoo.com" )

    def sortedAccounts = [jimSmith, joeReed, johnDoe]

    def setup() {
        jimSmith.save(failOnError: true)
        johnDoe.save(failOnError: true)
        joeReed.save(failOnError: true)
    }

    def cleanup() {
        Account.deleteAll(sortedAccounts)
    }

    void "viewTransactions for empty account name"() {
        given:
        request.method = 'POST'
        request.setParameter("accountName", "")
        request.makeAjaxRequest()

        when:
        controller.viewTransactions()

        then:
        view =='/transaction/index'
        model == [transactions: [], errors: ["Please select a name from the list"], account: null, accounts:sortedAccounts]
    }

    void "viewTransactions for valid account name"() {
        given:
        request.method = 'POST'
        request.setParameter("accountName", "John Doe")
        request.makeAjaxRequest()
        def foundAccount = johnDoe

        when:
        controller.viewTransactions()

        then:
        view =='/transaction/index'
        model == [transactions: [], errors: [], account: foundAccount, accounts:sortedAccounts]
    }

    void "index sorts accounts by name"() {
        given:

        when:
        def response = controller.index()

        then:
        response == [errors: [], accounts: sortedAccounts]

    }
}
