package lt.codeacademy.Enum;

public enum Role {
    STUDENT("Student"),
    TEACHER("Teacher"),
    ADMIN("Admin");
private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
