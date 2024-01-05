package rg.lol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rg.lol.service.InvokersService;

@RestController
@RequestMapping("/api/invokers/v1")
public class InvokersController {
    @Autowired
    InvokersService invokersService;

    @GetMapping("/health")
    public String healthRequest() {
        return "Health";
    }

    @GetMapping(value = "/player/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> lolPlayerData(@PathVariable String nickname) {
        return new ResponseEntity<>(invokersService.getLolPlayerDataByNickname(nickname), HttpStatus.OK);
    }

    @GetMapping(value = "/player/icon/{nickname}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> lolPlayerIcon(@PathVariable String nickname) {
        return new ResponseEntity<>(invokersService.getLolPlayerIcon(nickname), HttpStatus.OK);
    }
}
