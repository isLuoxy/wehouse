package cn.l99.wehouse.test.controller;

import cn.l99.wehouse.controller.HouseController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(HouseController.class)
@Slf4j
public class houseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHouseByParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/zufang/sz/p"))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}
