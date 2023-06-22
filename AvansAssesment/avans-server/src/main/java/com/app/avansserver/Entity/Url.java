package com.app.avansserver.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Url {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String longUrl;
	    private String shortId;
	    private Date expiresAt;

	    public Url(String longUrl, String shortId, Date expiresAt) {
	        this.longUrl = longUrl;
	        this.shortId = shortId;
	        this.expiresAt = expiresAt;
	    }

	    public Url() {}

	    public String getLongUrl() {
	        return longUrl;
	    }

	    public void setLongUrl(String longUrl) {
	        this.longUrl = longUrl;
	    }

	    public String getShortId() {
	        return shortId;
	    }

	    public void setShortId(String shortId) {
	        this.shortId = shortId;
	    }

	    public Date getExpiresAt() {
	        return expiresAt;
	    }

	    public void setExpiresAt(Date expiresAt) {
	        this.expiresAt = expiresAt;
	    }
}
