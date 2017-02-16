import org.danz.model.Account

class BootStrap {

    def init = { servletContext ->

        def johnDoe = new Account( name: "John Doe", email: "jd@gmail.com" ).save(failOnError: true)
        def joeReed = new Account( name: "Joe Reed", email: "jr@yahoo.com" ).save(failOnError: true)
        def jimSmith = new Account( name: "Jim Smith", email: "js@gmail.com" ).save(failOnError: true)

    }
    def destroy = {
        Account.deleteAll()
    }
}
