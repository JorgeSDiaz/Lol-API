package rg.lol.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invokers/v1")
public class InvokersController {
    @GetMapping("/health")
    public String healthRequest() {
        return "Health";
    }
}
