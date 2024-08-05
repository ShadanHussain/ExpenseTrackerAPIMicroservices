package com.shadan.expenseservice.controller;

import com.shadan.expenseservice.model.Expense;
import com.shadan.expenseservice.model.ExpenseFilter;
import com.shadan.expenseservice.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getExpenses() {
        System.out.println(LocalDateTime.now());
        return expenseService.getExpenses();
    }

    @PostMapping("/addExpense")
    public ResponseEntity<String> addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @PutMapping("/updateExpense")
    public ResponseEntity<String> updateExpense(@RequestBody Expense expense) {
        System.out.println("Update called");
        return expenseService.updateExpense(expense);
    }

    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Integer id) {
        return expenseService.deleteExpense(id);
    }

    @PostMapping("/totalExpense/customFilter")
    public ResponseEntity<Double> getTotalExpense(@RequestBody ExpenseFilter expenseFilter) {
        System.out.println(expenseFilter);
        return expenseService.getTotalExpense(expenseFilter);
    }

    @PostMapping("/totalExpense/{filter}")
    public ResponseEntity<Double> getTotalExpenseLastWeek(@PathVariable String filter,@RequestBody List<String> categories) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate;
        switch (filter) {
            case "lastWeek" -> startDate = endDate.minusDays(7);
            case "lastMonth" -> startDate = endDate.minusMonths(1);
            case "last3Month" -> startDate = endDate.minusMonths(3);
            case "lastYear" -> startDate = endDate.minusYears(1);
            default -> {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return expenseService.getTotalExpense(new ExpenseFilter(categories, startDate, endDate));
    }

    @DeleteMapping("/deleteAllExpenses")
    public ResponseEntity<String> deleteAllExpenses() {
        System.out.println("This method also called");
        return expenseService.deleteAllExpenses();
    }
}
