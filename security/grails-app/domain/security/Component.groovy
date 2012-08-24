package security

class Component {
	String name
	static belongsTo = Role
    static constraints = {
		name(nullable: false, blank: false)
    }
	@Override
	public String toString() {
		return name;
	}
}
