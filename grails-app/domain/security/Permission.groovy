package security

class Permission {
	static final char SEPARATOR = ':'
	static final String ALL="all"
	static belongsTo = Role
	ControllerConfiguration controller
	Method method
	
    static constraints = {
		controller(nullable: false)
		method(nullable: false)
    }

	String toString() {
		return controller.toString() + SEPARATOR + method.toString()
	}
	
	boolean validatePermission(String permission) {
		def tokens = permission.tokenize(SEPARATOR)
		return validateController(tokens.first()) && validateMethod(tokens.last())
	}
	
	boolean validateController(String controller){
		return ALL.equals(this.controller.name) ? true : this.controller.name.equals(controller)
	}
	
	boolean validateMethod(String method){
		return ALL.equals(this.method.name) ? true : this.method.name.equals(method)
	}
}
