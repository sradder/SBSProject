package com.onlinebanking.models;

// Generated Oct 19, 2014 4:55:42 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserPublicKey generated by hbm2java
 */
@Entity
@Table(name = "user_public_key", catalog = "pitchforkbank", uniqueConstraints = @UniqueConstraint(columnNames = "public key"))
public class UserPublicKey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 70343691653851147L;
	private String userId;
	private String publicKey;

	public UserPublicKey() {
	}

	public UserPublicKey(String userId, String publicKey) {
		this.userId = userId;
		this.publicKey = publicKey;
	}

	@Id
	@Column(name = "userId", unique = true, nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "public key", unique = true, nullable = false, length = 64)
	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
