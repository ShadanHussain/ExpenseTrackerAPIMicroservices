package com.shadan.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "EXPENSE-SERVICE")
public interface ExpenseInterface {
    @DeleteMapping("/deleteAllExpenses")
    public ResponseEntity<String> deleteAllExpenses(@RequestHeader(value = "Authorization") String authorization);
}
