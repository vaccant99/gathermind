package woongjin.gatherMind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController


{
     @GetMapping(path = "basicauth")
 public String basicAuthCheck() {
         return "success";
     }
//

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtTokenProvider.createToken(authentication.getName());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }


}


