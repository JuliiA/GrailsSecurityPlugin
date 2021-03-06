package security

class AuthenticationTagLib {

    static namespace = "security"
    
    def authenticationService

    boolean checkLoggedIn() { 
        return authenticationService.isLoggedIn(request)
	}
	
	boolean checkRole(roles){
		return authenticationService.hasRoles(roles)
	}

	boolean checkPermissions(permissions){
		return authenticationService.hasPermissions(permissions)
	}

	def ifLoggedIn = { attrs, body -> 
		if (checkLoggedIn()) {
			out << body()
		}
	}
	
	def role = { attrs, body ->
		def roles = attrs.remove('roles')
		if (checkRole(roles)) {
			out << body()
		}
	}
	
	def permission = { attrs, body ->
		def has = attrs.remove('has')
		def hasNot = attrs.remove('hasNot')
		if (has != null && !has.isEmpty() && checkPermissions(has)) {
			out << body()
		}
		else if (hasNot != null && !hasNot.isEmpty() && !checkPermissions(hasNot)) {
			out << body()
		}
	} 
	
	def id = { attrs, body ->
		def ids = attrs.remove('id')
		if(authenticationService.hasId(ids)){
			out << body()
		}
	}

	def ifUnconfirmed = { attrs, body -> 
		if (session[AuthenticationService.SESSION_KEY_AUTH_USER]?.result == AuthenticatedUser.AWAITING_CONFIRMATION) {
			out << body()
		}
	}

	def ifNotLoggedIn = { attrs, body -> 
		if (!session[AuthenticationService.SESSION_KEY_AUTH_USER] || !checkLoggedIn()) {
			out << body()
		}
	}
	
	def user = { attrs -> 
		if (checkLoggedIn()) {
		    def u = session[AuthenticationService.SESSION_KEY_AUTH_USER]
		    if (u) {
		        def codec = attrs.codec != null ? (attrs.codec ?: 'HTML') : null
		        def v = u[attrs.property ? attrs.property : 'login'] 
			    out << (codec ? v?."encodeAs$codec"() : v)
		    }
		}
	}

	def userPrincipal = { attrs -> 
		if (checkLoggedIn()) {
	        def u = authenticationService.userPrincipal
		    if (u) {
		        def codec = attrs.codec != null ? (attrs.codec ?: 'HTML') : null
		        def v = u[attrs.property ? attrs.property : 'login'] 
			    out << (codec ? v?."encodeAs$codec"() : v)
		    }
		}
	}
	
    /**
     * Render an authentication form
     * 
     * Attributes:
     * 
     * action - optional : will default to plugin's authentication controller with "authAction" as action
     * method - optional : defaults to POST (via g:form)
     * successUrl - map of controller/action/id params
     * errorUrl - map of controller/action/id params
     * Usage:
     * 
     * <security:form authAction="login" successUrl="[controller:'portal', action:'justLoggedIn']"
     *     errorUrl="[controller:'content', action:'login']">
     *    <!-- input fields here, auto generated if blank body() -->
     * </security:form>
     */
    def form = { attrs, body ->
        def authAction = attrs.remove('authAction')
        def args = [success:attrs.remove('success'), error:attrs.remove('error')]
        
        if (!args.success) {
            args.success = [:]
        }
        
        if (!args.error) {
            args.error = [:]
        }

        args.keySet().each() {
            def theparams = args[it]
            if (!theparams.controller) {
                theparams.controller = controllerName
                 // only autocomplete action if we autocomplete controller
                 if (!theparams.action) theparams.action = actionName
            }
        }
            
        def formAttrs = [:] + attrs
        if (!attrs.url) {
            if (!authAction)
                throwTagError("security:form tag requires 'authAction' parameter to indicate login action")
            formAttrs.url = [controller:'authentication', action:authAction]
        }
            
        out << g.form(formAttrs) {
            args.keySet().each() { kind ->
                def url = args[kind]
                if (url.controller) {
                    out << g.hiddenField(name:"${kind}_controller", value: url.controller)
                }
                if (url.action) {
                    out << g.hiddenField(name:"${kind}_action", value: url.action)
                }
                if (url.id) {
                    out << g.hiddenField(name:"${kind}_id", value: url.id)
                }
            }
            if (body) {
                out << body() // Add to this - if body is null, automatically write a simple user+pass form with Log in button
            }
        }
    }
    
    def logoutLink = { attrs, body ->
        def attribs = [controller:'authentication', action:'logout', params:[:]]
        def success = attrs.remove('success') ?: [:]
        if (!success.controller) {
            success.controller = controllerName
            // only autocomplete action if we autocomplete controller
            if (!success.action) success.action = actionName
        }

        if (success.controller) attribs.params["success_controller"] = success.controller
        if (success.action) attribs.params["success_action"] = success.action
        if (success.id) attribs.params["success_id"] = success.id
        
        attrs.url = attribs
        out << g.link(attrs, body)
    }
}