package woongjin.gatherMind.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.HashMap;
import java.util.Map;

@RestController
//헬스체크
@RequestMapping("/api")
public class CheckController

{

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/check")
    public ResponseEntity<?> check(){
        Map<String,String> responseData = new HashMap<>();
        responseData.put("env",env);
        responseData.put("serverPort",serverPort);
        responseData.put("serverAddress",serverAddress);
        responseData.put("serverName",serverName);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv(){


        return ResponseEntity.ok(env);
    }



}
