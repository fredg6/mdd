package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.response.PostDto;
import com.orion.mdd.security.service.CustomUserDetails;
import com.orion.mdd.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> feed(@RequestParam Sort.Direction sortDirection, Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserFeed(sortDirection, principal.getUsername()));
    }
}