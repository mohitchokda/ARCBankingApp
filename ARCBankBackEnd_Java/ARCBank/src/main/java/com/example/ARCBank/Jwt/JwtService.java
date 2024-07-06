package com.example.ARCBank.Jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    
    private SecretKey getKey() {
    	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    
    //Build Token
    public String generateToken(Authentication auth) {
    	String token = Jwts.builder()
    						.subject(auth.getName())//username
    						.issuedAt(new Date(System.currentTimeMillis()))
    						.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
    						.signWith(getKey(),Jwts.SIG.HS256)
    						.compact();
    	
    	System.out.println("JWT Token : "+token);
    						
    	return token;
    }
   
    //Validate Token
    public boolean validate(String token) throws Exception{
    	try{
    		//System.out.println("Token Validation");
    		Jwts.parser()
    		.verifyWith(getKey())
    		.build()
    		.parse(token);
    		return true;
    		
    	}catch(Exception ex) {
    		throw new Exception(ex.getMessage());
    	}
    }
    
    //Extract username
    public String getUsernameFromToken(String token) {
    	String uname =  Jwts.parser()
    			.verifyWith(getKey())
    			.build()
    			.parseSignedClaims(token)
    			.getPayload().getSubject();
    	
    	//System.out.println("Token Name "+uname);
    	
    	return uname;
    }
    
    public long getExpirationTime() {
        return jwtExpiration;
    }
}
