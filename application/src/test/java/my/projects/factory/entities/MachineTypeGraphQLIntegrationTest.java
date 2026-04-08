package my.projects.factory.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.projects.factory.persistence.entity.foundation.MachineType;
import my.projects.factory.persistence.repository.foundation.MachineTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MachineTypeGraphQLIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MachineTypeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateMachineType_throughGraphQL_toDatabase() throws Exception {

        String mutation = """
        mutation {
          createMachineType(input: {
            code: "MT-001",
            name: "Cutter",
            description: "Cuts things"
          }) {
            id
            code
            name
            description
          }
        }
    """;

        Map<String, String> request = Map.of("query", mutation);

        mockMvc.perform(post("/graphql")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // safely serializes JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.createMachineType.code").value("MT-001"))
                .andExpect(jsonPath("$.data.createMachineType.name").value("Cutter"));

        List<MachineType> all = repository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.getFirst().getCode()).isEqualTo("MT-001");
    }
}
