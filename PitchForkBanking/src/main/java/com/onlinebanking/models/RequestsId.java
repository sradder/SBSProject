package com.onlinebanking.models;

// Generated Oct 19, 2014 4:55:42 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RequestsId generated by hbm2java
 */
@Embeddable
public class RequestsId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3287875787367792232L;
	private String fromUserId;
	private String toUserId;

	public RequestsId() {
	}

	public RequestsId(String fromUserId, String toUserId) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
	}

	@Column(name = "fromUserId", nullable = false, length = 36)
	public String getFromUserId() {
		return this.fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	@Column(name = "toUserId", nullable = false, length = 36)
	public String getToUserId() {
		return this.toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RequestsId))
			return false;
		RequestsId castOther = (RequestsId) other;

		return ((this.getFromUserId() == castOther.getFromUserId()) || (this
				.getFromUserId() != null && castOther.getFromUserId() != null && this
				.getFromUserId().equals(castOther.getFromUserId())))
				&& ((this.getToUserId() == castOther.getToUserId()) || (this
						.getToUserId() != null
						&& castOther.getToUserId() != null && this
						.getToUserId().equals(castOther.getToUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getFromUserId() == null ? 0 : this.getFromUserId()
						.hashCode());
		result = 37 * result
				+ (getToUserId() == null ? 0 : this.getToUserId().hashCode());
		return result;
	}

}