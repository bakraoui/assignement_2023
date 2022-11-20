package ma.octo.assignement.domain.util;

public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String role;
    RoleType(String roleName) {
        this.role = roleName;
    }

    public String getRole() {
        return role;
    }
}
