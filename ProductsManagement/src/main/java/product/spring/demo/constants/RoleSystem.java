package product.spring.demo.constants;

public enum RoleSystem {

	ADMIN(1, "ADMIN"),
	MEMBER(2, "MEMBER"),
	;

	private final int code;
	private final String key;

	private RoleSystem(int id, String key) {
		this.code = id;
		this.key = key;
	}

	public int getCode() {
		return code;
	}

	public String getKey() {
		return key;
	}

}
