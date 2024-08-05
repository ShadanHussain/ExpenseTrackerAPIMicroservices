package com.shadan.expenseservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserInterface {
    @PostMapping("/getUser")
    boolean getUser(@RequestHeader(value = "Authorization") String authorization);
}
