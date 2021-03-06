package com.hexacta.enhanced.authentication

class Role {
	String name
	static hasMany = [roles: Role, permissions: Permission, components: Component]
	
    static constraints = {
		name(blank:false, unique: true)
		roles(nullable: true)
		components(nullable: true)
		permissions(nullable: true)
    }
	
	@Override
	public String toString() {
		return name;
	}
}
