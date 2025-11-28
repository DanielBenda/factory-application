package repository;

import entity.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, UUID> {

    Optional<Worker> findBySurname(String surname);
}
