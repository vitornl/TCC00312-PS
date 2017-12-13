package main;

import java.util.ArrayList;

import permissions.PermissoesSingleton;
import views.Invisivel;

public class Principal {
	private static ArrayList<String> permissoes;
	
	static {
		permissoes = new ArrayList<String>();
		permissoes.add("ROLE_ADMIN");
		permissoes.add("ROLE_USER1");
		PermissoesSingleton perm = PermissoesSingleton.getPermissoesSingleton();
		perm.setPermissoes(permissoes);
	}
	
	public static void main(String[] args) {
		Invisivel frameInvisivel = new Invisivel();
		frameInvisivel.setVisible(false);
	}
	
}
