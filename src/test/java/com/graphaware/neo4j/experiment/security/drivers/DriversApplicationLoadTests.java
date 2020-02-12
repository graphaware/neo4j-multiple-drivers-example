package com.graphaware.neo4j.experiment.security.drivers;

import com.graphaware.neo4j.experiment.security.drivers.neo4j.DriverProvider;
import com.graphaware.neo4j.experiment.security.drivers.utils.DbContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DriversApplicationLoadTests {

    private static final Logger LOG = LoggerFactory.getLogger(DriversApplicationLoadTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverProvider driverProvider;

    @BeforeEach
    void loadData() {
        DbContextHolder.setUsername("admin");
        driverProvider.getDriver().session().run("MATCH (n) DETACH DELETE n CREATE (n:Person{name: \"John Doe\"}), (m:Person{name: \"Bob Roe\"})");
    }

    @Test
    void cachedDriverLoadTest() throws Exception {
        this.mockMvc.perform(get("/cached").header("username", "admin")).andExpect(status().isOk());
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(get("/cached").header("username", "admin")).andExpect(status().isOk());
        }

        LOG.info("Queries with cached driver took " + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    void routingDriverLoadTest() throws Exception {
        this.mockMvc.perform(get("/routing").header("username", "admin")).andExpect(status().isOk());
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(get("/routing").header("username", "admin")).andExpect(status().isOk());
        }

        LOG.info("Queries with routing driver took " + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    void naiveDriverLoadTest() throws Exception {
        this.mockMvc.perform(get("/naive").header("username", "admin")).andExpect(status().isOk());
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(get("/naive").header("username", "admin")).andExpect(status().isOk());
        }

        LOG.info("Queries with naive driver took " + (System.currentTimeMillis() - start) + "ms");
    }

}
