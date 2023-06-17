package com.employees.billing.service;

import com.employees.billing.entity.History;
import com.employees.billing.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис по работе с историей.
 *
 * @author Irina Ilina
 */
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    /**
     * Записать историю.
     *
     * @param history сведения
     */
    @Transactional
    public void addHistory(History history) {
        historyRepository.saveAndFlush(history);
    }

    /**
     * Показать историю.
     *
     * @param id идентификатор
     * @return сведения
     */
    @Transactional
    public History showHistory(Long id) {
        return historyRepository.findById(id).get();
    }

    /**
     * Удалить историю.
     *
     * @param id идентификатор
     */
    @Transactional
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }

}
