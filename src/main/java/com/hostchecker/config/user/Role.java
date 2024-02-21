package com.hostchecker.config.user;

public enum Role {
	
	USER,
	ADMIN;
	
	public static Role fromString(String role) {
        try {
        	role = role.trim();
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
           
            return null;
        }
    }

}
