package com.tech.linkShort;

import com.tech.linkShort.entities.LinkDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.tech.linkShort.util.JsonUtil.writeValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LinkControllerTest {

    private final static String TOKEN1 = "7CtcYl4dw48F" ;

    private final static String URL1 = "https://stackoverflow.com/questions/42725199/how-to-use-mockmvcresultmatchers-jsonpath" ;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/link/" + TOKEN1))
                .andExpect(status().isFound())
                .andDo(print());
               // .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void post() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/api/link/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(new LinkDTO(URL1))))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
