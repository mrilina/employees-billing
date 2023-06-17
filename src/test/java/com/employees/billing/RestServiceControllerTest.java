package com.employees.billing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.employees.billing.entity.Account;
import com.employees.billing.entity.Employee;
import com.employees.billing.entity.History;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Тестирование методов обработки сведений о служащем, балльном счете и истории.
 *
 * @author Irina Ilina
 */
public class RestServiceControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Тест создания служащего и балльного счета.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void createEmployee() throws Exception {
        String uri = "/api/employees";
        Employee employee = new Employee();
        employee.setUserName("USER_NAME");
        String inputJson = super.mapToJson(employee);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "");
    }

    /**
     * Тест получения информации о служащем.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void getEmployee() throws Exception {
        String uri = "/api/employees/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee employee = super.mapFromJson(content, Employee.class);
        assertNotNull(employee);
    }

    /**
     * Тест получения баланса с балльного счета.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void getBalance() throws Exception {
        String uri = "/api/accounts/1/balance";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        double balance = super.mapFromJson(content, Double.class);
        assertEquals(balance, 0.0, 0.1f);
    }

    /**
     * Тест обработки сведений о балльном счете.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void getAccount() throws Exception {
        String uri = "/api/accounts/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Account account = super.mapFromJson(content, Account.class);
        assertEquals(account.getId().longValue(), 1L);
    }

    /**
     * Тест начисления баллов на счет.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void depositAmount() throws Exception {
        String uriDepositAmount = "/api/accounts/1/deposit/200.0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriDepositAmount)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // получение сведений о балльном счете
        String uri = "/api/accounts/1";
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Account account = super.mapFromJson(content, Account.class);
        assertEquals(account.getBalance(), 200, 0.1f);
    }

    /**
     * Тест списания баллов со счета.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void depositWithdrawAmount() throws Exception {
        String uriWithdrawAmount = "/api/accounts/1/withdraw/100.0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriWithdrawAmount)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // получение сведений о балльном счете
        String uri = "/api/accounts/1";
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Account account = super.mapFromJson(content, Account.class);
        assertEquals(account.getBalance(), 100, 0.1f);
    }

    /**
     * Тест получения истории.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void getHistory() throws Exception {
        String uri = "/api/accounts/5/history";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        History history = super.mapFromJson(content, History.class);
        assertNotNull(history);
    }

    /**
     * Тест удаления сведений о служащем.
     *
     * @throws Exception исключительная ситуация
     */
    @Test
    public void deleteEmployee() throws Exception {
        String uriDeleteEmployee = "/api/employees/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uriDeleteEmployee)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
    }
}
