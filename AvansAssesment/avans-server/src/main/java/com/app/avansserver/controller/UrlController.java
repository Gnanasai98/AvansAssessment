package com.app.avansserver.controller;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.app.avansserver.Entity.Url;
import com.app.avansserver.repository.UrlRepository;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/urls")
public class UrlController 
{
		@Autowired
	    private UrlRepository urlRepository;

	    @PostMapping("")
	    public ResponseEntity<Url> createUrl(@RequestBody Url url) {
	        // Generate a unique short ID
	        String shortId = generateShortId();
	        url.setShortId(shortId);

	        // Set the expiration time to 5 minutes from now
	        Date expiresAt = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
	        url.setExpiresAt(expiresAt);

	        // Save the URL to the database
	        urlRepository.save(url);

	        // Return the short URL to the client
	        Url shortUrl = new Url(null, shortId, expiresAt);
	        return ResponseEntity.ok(shortUrl);
	    }

	    @GetMapping("/{shortId}")
	    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortId) {
	        // Look up the URL by its short ID
	        Url url = urlRepository.findByShortId(shortId);
	        if (url == null) {
	            return ResponseEntity.notFound().build();
	        }

	        // Check if the URL has expired
	        if (url.getExpiresAt().before(new Date())) {
	            urlRepository.delete(url);
	            return ResponseEntity.status(HttpStatus.GONE).build();
	        }

	        // Redirect the user to the long URL
	        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
	            .location(URI.create(url.getLongUrl()))
	            .build();
	    }

	    private String generateShortId() {
	        // Generate a random short ID of length 8
	        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < 8; i++) {
	            int index = (int) (Math.random() * characters.length());
	            sb.append(characters.charAt(index));
	        }
	        return sb.toString();
	    }
}
