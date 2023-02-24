package com.reddot.security.jwt

class JWTWithJKS {
//    private KeyStore keyStore;
//    @Value("${jwt.expiration.time}")
//    private Long jwtExpirationInMillis;
//
//    @PostConstruct
//    public void init() {
//        try {
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/server.jks");
//            keyStore.load(resourceAsStream, "secret".toCharArray());
//        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
//            throw new SpringRedditException("Exception occurred while loading keystore");
//        }
//
//    }
//
//    public String generateToken(Authentication authentication) {
//        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
//        return Jwts.builder()
//            .setSubject(principal.getUsername())
//            .setIssuedAt(from(Instant.now()))
//            .signWith(getPrivateKey())
//            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//            .compact();
//    }
//
//    public String generateTokenWithUserName(String username) {
//        return Jwts.builder()
//            .setSubject(username)
//            .setIssuedAt(from(Instant.now()))
//            .signWith(getPrivateKey())
//            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//            .compact();
//    }
//
//    private PrivateKey getPrivateKey() {
//        try {
//            return (PrivateKey) keyStore.getKey("server", "secret".toCharArray());
//        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
//            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
//        }
//    }
//
//    public boolean validateToken(String jwt) {
//        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
//        return true;
//    }
//
//    private PublicKey getPublickey() {
//        try {
//            return keyStore.getCertificate("springblog").getPublicKey();
//        } catch (KeyStoreException e) {
//            throw new SpringRedditException("Exception occured while " +
//                    "retrieving public key from keystore");
//        }
//    }
//
//    public String getUsernameFromJwt(String token) {
//        Claims claims = parser()
//            .setSigningKey(getPublickey())
//            .parseClaimsJws(token)
//            .getBody();
//
//        return claims.getSubject();
//    }
//
//    public Long getJwtExpirationInMillis() {
//        return jwtExpirationInMillis;
//    }
}