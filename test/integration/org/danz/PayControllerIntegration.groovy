package org.danz

import grails.test.mixin.TestFor
import grails.test.spock.IntegrationSpec
import org.danz.model.Account

@TestFor(PayController)
class PayControllerIntegration extends IntegrationSpec {


    def jimSmith = new Account( name: "Jim Smith", email: "js@gmail.com" )
    def johnDoe = new Account( name: "John Doe", email: "jd@gmail.com" )
    def joeReed = new Account( name: "Joe Reed", email: "jr@yahoo.com" )
    def sortedAccounts = [jimSmith, joeReed, johnDoe]
    def mailService
    def greenMail

    void "do payment for valid inputs"() {
        given:
        request.method = 'POST'
        request.setParameter("accountFrom", "Jim Smith")
        request.setParameter("accountTo", "John Doe")
        request.setParameter("amount", "5")
        request.makeAjaxRequest()

        when:
        controller.doPayment()

        then:
        view =='/pay/index'
        model == [errors: [],
                success: "£5 was transferred successfully!",
                accounts: sortedAccounts,
                accountFrom: jimSmith,
                accountTo:johnDoe]

    }
}