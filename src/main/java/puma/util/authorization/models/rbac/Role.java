package puma.util.authorization.models.rbac;

public class Role {
	private String roleName;
	
	public Role(String name) {
		this.roleName = name;
	}

	public String getRoleName() {
		return this.roleName;
	}
	
	@Override
	public boolean equals(Object other) {
		Role otherRole;
		try {
			otherRole = (Role) other;
		} catch (ClassCastException e) {
			return false;
		}
		if (this.getRoleName().equals(otherRole.getRoleName()))
			return true;
		return false;
	}
}
