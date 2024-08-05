package com.shadan.expenseservice.dao;

import com.shadan.expenseservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseDao extends JpaRepository<Expense, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Expense e")
    int getMaxId();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.username = :username AND e.category IN :categories AND e.expenseDate BETWEEN :startDate AND :endDate")
    Double calculateTotalExpense(List<String> categories,LocalDateTime startDate,LocalDateTime endDate,String username);

    @Query("SELECT e FROM Expense e WHERE e.username = :username")
    List<Expense> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Expense e WHERE e.username = :username")
    void deleteByUsername(String username);
}
