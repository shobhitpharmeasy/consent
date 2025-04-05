//package com.pharmeast.consent.controller;
//
//@org.springframework.web.bind.annotation.RestController
//@org.springframework.web.bind.annotation.RequestMapping("/api")
//@lombok.RequiredArgsConstructor
//
//public class AuthController {
//
//    private final org.springframework.security.authentication.AuthenticationManager
//        authenticationManager;
//    private final com.pharmeast.consent.utils.JwtUtils jwtUtils;
//
//    @org.springframework.web.bind.annotation.PostMapping("/login")
//    public java.util.Map<String, String> login(
//        @org.springframework.web.bind.annotation.RequestBody
//        java.util.Map<String, String> loginRequest
//    ) {
//        String username = loginRequest.get("username");
//        String password = loginRequest.get("password");
//
//        org.springframework.security.core.Authentication authentication
//            = authenticationManager.authenticate(
//            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
//                username, password));
//
//        org.springframework.security.core.userdetails.UserDetails userDetails
//            = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
//        String role = userDetails.getAuthorities().stream().map(
//                                     org.springframework.security.core.GrantedAuthority::getAuthority)
//                                 .collect(
//                                     java.util.stream.Collectors.joining(","));
//
//        String token = jwtUtils.generateToken(username, role);
//
//        return java.util.Map.of("token", token);
//    }
//
//    @org.springframework.web.bind.annotation.GetMapping("/user")
//    public java.util.Map<String, String> getUserDetails(
//        @org.springframework.web.bind.annotation.RequestHeader("Authorization")
//        String token
//    ) {
//        String username = jwtUtils.extractUsername(
//            token.replace("Bearer ", ""));
//        return java.util.Map.of("username", username);
//    }
//}
