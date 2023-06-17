package com.employees.billing.controller;

import com.employees.billing.entity.History;
import com.employees.billing.exception.ResourceNotFoundException;
import com.employees.billing.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер по работе с историей ведения балльного счета.
 *
 * @author Irina Ilina
 */
@RestController
@RequestMapping("/api")
public class HistoryController {

    /** Сервис по работе с историей. */
    @Autowired
    private HistoryService historyService;

    /**
     * Записать историю.
     *
     * @param history сведения
     */
    public void addHistory(History history) {
        historyService.addHistory(history);
    }

    /**
     * Показать историю.
     *
     * @param id идентификатор
     * @return сведения
     */
    @GetMapping("/accounts/{id}/history")
    public History showHistory(@PathVariable Long id) {
        History history = historyService.showHistory(id);
        if (history == null) {
            throw new ResourceNotFoundException("История с идентификатором " + id + " не найдена в базе данных");
        }
        return history;
    }

    /**
     * Удалить историю.
     *
     * @param id идентификатор
     */
    public void deleteHistory(Long id) {
        historyService.deleteHistory(id);
    }

}
