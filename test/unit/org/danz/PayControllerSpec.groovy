package org.danz

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.danz.model.Account
import org.danz.model.Transaction
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PayController)
@Mock([Account, Transaction])
class PayControllerSpec extends Specification {

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

    void "index sorts accounts by name"() {
        given:

        when:
        def response = controller.index()

        then:
        response == [errors: [], accounts: sortedAccounts]

    }

    void "get accounts"() {
        given:

        when:
        def response = controller.getAllAccounts()

        then:
        response == sortedAccounts

    }

}
