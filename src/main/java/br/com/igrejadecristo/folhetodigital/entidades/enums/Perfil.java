package br.com.igrejadecristo.folhetodigital.entidades.enums;


public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	PASTOR(2, "ROLE_PASTOR"),
	MEMBRO(3, "ROLE_MEMBRO"),
	LIDER(4, "ROLE_LIDER"),
	VISITANTE(5, "ROLE_VISITANTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
