package com.app.avansserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.avansserver.Entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long>
{
		Url findByLongUrl(String longUrl);
	    Url findByShortId(String shortId);
}
