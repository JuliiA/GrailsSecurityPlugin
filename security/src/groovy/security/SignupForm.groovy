package security
import grails.validation.Validateable


@Validateable
class SignupForm implements Serializable {
	String login
	String email
	String password
	String passwordConfirm
	
	boolean rememberMe
	
	static constraints = {
		login(size:5..40, blank:false, nullable: false)
		email(email:true, size:6..40, blank:false, nullable: false)
		password(size:5..40, password:true, blank:false, nullable: false)
		passwordConfirm(password:true, validator: { val, obj -> obj.password == val })
	}
}