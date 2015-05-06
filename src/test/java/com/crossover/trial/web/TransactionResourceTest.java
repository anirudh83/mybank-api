package com.crossover.trial.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
* Created by anirudh on 04/05/15.
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplication.class})
@WebAppConfiguration
public class TransactionResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    static final String content = "{  \"uniqueTransactionToken\": \"123\",\n" +
            "    \"accountNumberFrom\" : \"1233\",\n" +
            "    \"accountNumberTo\" : 12345,\n" +
            "    \"amount\" :\"500\"\n" +
            "    \"description\" :\"transfer\"\n" +
            "}";

    @Test
    public void testTransaction() throws Exception {
        mockMvc.perform(post("/api/accounts/transfer")
                .content(content));

    }

}
