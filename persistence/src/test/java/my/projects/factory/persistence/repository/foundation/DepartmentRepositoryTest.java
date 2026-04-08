package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = DepartmentRepositoryTest.PersistenceConfig.class)
class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository repository;

    @Test
    void shouldSaveAndLoadDepartment() {
        Department dep = new Department();
        dep.setCode("IT");
        dep.setName("IT Department");
        dep.setLeader("Alice");

        repository.saveAndFlush(dep);
        entityManager.clear();

        Department loaded = repository.findById(dep.getId()).orElseThrow();

        assertEquals("IT", loaded.getCode());
        assertEquals("IT Department", loaded.getName());
        assertEquals("Alice", loaded.getLeader());
    }

    @Test
    void shouldGenerateIdOnSave() {
        Department dep = new Department();
        dep.setCode("HR");
        dep.setName("Human Resources");

        Department saved = repository.save(dep);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFailWhenNameIsNull() {
        Department dep = new Department();
        dep.setCode("IT");

        assertThrows(Exception.class, () -> repository.saveAndFlush(dep));
    }

    @Test
    void shouldFailOnDuplicateCode() {
        Department d1 = new Department();
        d1.setCode("IT");
        d1.setName("IT department");

        Department d2 = new Department();
        d2.setCode("IT");
        d2.setName("IT support");

        repository.saveAndFlush(d1);

        assertThrows(Exception.class, () -> repository.saveAndFlush(d2));
    }

    @Test
    void shouldFailOnDuplicateName() {
        Department d1 = new Department();
        d1.setCode("IT");
        d1.setName("SharedName");

        Department d2 = new Department();
        d2.setCode("HR");
        d2.setName("SharedName");

        repository.saveAndFlush(d1);

        assertThrows(Exception.class, () -> repository.saveAndFlush(d2));
    }

    @Test
    void shouldUpdateDepartment() {
        Department dep = new Department();
        dep.setCode("IT");
        dep.setName("IT Department");

        repository.saveAndFlush(dep);

        dep.setLeader("Bob");
        repository.saveAndFlush(dep);

        Department updated = repository.findById(dep.getId()).orElseThrow();

        assertEquals("Bob", updated.getLeader());
    }

    @Test
    void shouldFailWhenCodeTooLong() {
        Department dep = new Department();
        dep.setCode("X".repeat(100)); // > 50
        dep.setName("Dept");

        assertThrows(Exception.class, () -> repository.saveAndFlush(dep));
    }

    @Configuration
    @EnableJpaRepositories(basePackageClasses = DepartmentRepository.class)
    @EntityScan(basePackageClasses = Department.class)
    static class PersistenceConfig {}
}