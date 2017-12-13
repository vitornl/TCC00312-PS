package permissions;

import java.util.ArrayList;

public class PermissoesSingleton {

	private static PermissoesSingleton perm = null;
	private ArrayList<String> permissoes;
	
	private PermissoesSingleton() {
		
	}
	
	public static PermissoesSingleton getPermissoesSingleton() {
		if(perm == null) {
			perm = new PermissoesSingleton();
			perm.permissoes = new ArrayList<String>();
		}
		
		return perm;
	}
	
	public void setPermissoes(ArrayList<String> permissoes) {
		this.permissoes = permissoes;
	}
	
	public ArrayList<String> getPermissoes() {
		return permissoes;
	}
}
