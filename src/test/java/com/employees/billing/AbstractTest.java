package com.employees.billing;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Безовый класс для тестирования.
 *
 * @author Irina Ilina
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmployeesBillingApplication.class)
@WebAppConfiguration
public abstract class AbstractTest {

    /** Mock. */
    protected MockMvc mvc;

    /** Контекст. */
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Преобразует объект в json строку.
     *
     * @param obj объект
     * @return json cтрока
     * @throws JsonProcessingException исключительная ситуация
     */
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Преобразует json строку в объект.
     *
     * @param json json строка
     * @param clazz класс
     * @return объект
     * @param <T> объект
     * @throws JsonParseException исключительная ситуация
     * @throws JsonMappingException исключительная ситуация
     * @throws IOException исключительная ситуация
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
        return objectMapper.readValue(json, clazz);
    }
}

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
abstract class IgnoreHibernatePropertiesInJackson{ }