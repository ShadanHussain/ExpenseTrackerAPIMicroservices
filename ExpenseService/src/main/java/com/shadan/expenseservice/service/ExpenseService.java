package com.shadan.expenseservice.service;

import com.shadan.expenseservice.config.JwtFilter;
import com.shadan.expenseservice.dao.ExpenseDao;
import com.shadan.expenseservice.model.Expense;
import com.shadan.expenseservice.model.ExpenseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ExpenseDao expenseDao;

    public ResponseEntity<List<Expense>> getExpenses() {
        String username = jwtFilter.getUsernameLocal();
        try{
            return new ResponseEntity<>(expenseDao.findByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addExpense(Expense expense) {
        int maxId = expenseDao.getMaxId();
        expense.setId(maxId + 1);
        expense.setUsername(jwtFilter.getUsernameLocal());
        try{
            expenseDao.save(expense);
            return new ResponseEntity<>("Expense added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteExpense(Integer id) {
        Expense expense = expenseDao.findById(id).orElse(null);
        if (expense == null)
            return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);
        String username = jwtFilter.getUsernameLocal();
        if (!expense.getUsername().equals(username))
            return new ResponseEntity<>("Unauthorized, this is not your expense!!", HttpStatus.UNAUTHORIZED);
        try{
            expenseDao.deleteById(id);
            return new ResponseEntity<>("Expense deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateExpense(Expense expense) {
        Expense existingExpense = expenseDao.findById(expense.getId()).orElse(null);
        if (existingExpense == null)
            return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);

        String username = jwtFilter.getUsernameLocal();
        if (!existingExpense.getUsername().equals(username))
            return new ResponseEntity<>("Unauthorized, this is not your expense!!", HttpStatus.UNAUTHORIZED);

        expense.setUsername(jwtFilter.getUsernameLocal());
        try{
            expenseDao.save(expense);
            return new ResponseEntity<>("Expense updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Double> getTotalExpense(ExpenseFilter expenseFilter) {
        String username = jwtFilter.getUsernameLocal();
        try{
            double totalExpense = expenseDao.calculateTotalExpense(
                    expenseFilter.getCategories(),
                    expenseFilter.getStartDate(),
                    expenseFilter.getEndDate(),
                    username);

            return new ResponseEntity<>(totalExpense, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> deleteAllExpenses() {
        String username = jwtFilter.getUsernameLocal();
        expenseDao.deleteByUsername(username);
        return new ResponseEntity<>("All expenses deleted successfully", HttpStatus.OK);
    }
}
