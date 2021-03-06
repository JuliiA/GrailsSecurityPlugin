<%@ page import="security.AuthenticationUser" %>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'login', 'error')} ">
	<label for="login">
		<g:message code="authenticationUser.login.label" default="Login" />
	</label>
	<g:textField name="login" maxlength="64" value="${authenticationUserInstance?.login}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="authenticationUser.password.label" default="Password" />
	</label>
	<g:field type="password" name="password" maxlength="64" value="${authenticationUserInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="authenticationUser.firstName.label" default="First Name" />
	</label>
	<g:textField name="firstName" maxlength="64" value="${authenticationUserInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="authenticationUser.lastName.label" default="Last Name" />
	</label>
	<g:textField name="lastName" maxlength="64" value="${authenticationUserInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="authenticationUser.email.label" default="Email" />
	</label>
	<g:field type="email" name="email" value="${authenticationUserInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: authenticationUserInstance, field: 'roles', 'error')} ">
	<label for="roles">
		<g:message code="authenticationUser.roles.label" default="Roles" />
	</label>
	<g:select id="role" name="role.id" from="${security.Role.list()}" optionKey="id" required="" value="${authenticationUserInstance?.role?.id}" class="many-to-one"/>
</div>

