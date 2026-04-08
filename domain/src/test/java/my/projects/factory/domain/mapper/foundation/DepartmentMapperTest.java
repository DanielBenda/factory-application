package my.projects.factory.domain.mapper.foundation;

import my.projects.factory.domain.model.foundation.DepartmentModel;
import my.projects.factory.persistence.entity.foundation.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DepartmentMapperTest {

    private DepartmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DepartmentMapper();
    }

    @Test
    void shouldMapEntityToModel() {
        // given
        Department entity = new Department();
        entity.setId(UUID.randomUUID());
        entity.setCode("HR");
        entity.setLeader("John Doe");
        entity.setName("Human Resources");

        // when
        DepartmentModel model = mapper.toModel(entity);

        // then
        assertNotNull(model);
        assertEquals("HR", model.code());
        assertEquals("John Doe", model.leader());
        assertEquals("Human Resources", model.name());
    }

    @Test
    void shouldMapModelToEntity() {
        // given
        DepartmentModel model = DepartmentModel.builder()
                .code("IT")
                .leader("Jane Smith")
                .name("IT Department")
                .build();

        // when
        Department entity = mapper.toEntity(model);

        // then
        assertNotNull(entity);
        assertEquals("IT", entity.getCode());
        assertEquals("Jane Smith", entity.getLeader());
        assertEquals("IT Department", entity.getName());
    }
}