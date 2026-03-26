package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.filter.foundation.MachineFilter;
import my.projects.factory.domain.mapper.foundation.MachineMapper;
import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.domain.service.foundation.MachineService;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.support.FilterExecutor;
import my.projects.factory.persistence.entity.foundation.Machine;
import my.projects.factory.persistence.repository.foundation.MachineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link MachineService} that handles business logic
 * for Machine entities. Uses {@link MachineMapper} to convert between
 * {@link Machine} entities and {@link MachineModel} domain models.
 */
@Service
public class MachineServiceImpl
        extends PageableServiceImpl<MachineModel, Machine, UUID>
        implements MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper mapper;

    public MachineServiceImpl(MachineRepository repository, MachineMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.machineRepository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link MachineModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all operation types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code code} field.
     * </p>
     *
     * @param filter   optional filter containing search criteria (nameQuery or codeQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link MachineModel} instances
     */
    @Override
    public Page<MachineModel> findMachines(MachineFilter filter, Pageable pageable) {

        Map<String, String> filters = new HashMap<>();

        if (filter != null) {
            filters.put("name", filter.nameQuery());
            filters.put("code", filter.codeQuery());
        }

        Map<String, Function<String, Supplier<Page<Machine>>>> queryMap = Map.of(
                "name", value -> () -> machineRepository.findByNameContainingIgnoreCase(value, pageable),
                "code", value -> () -> machineRepository.findByCodeContainingIgnoreCase(value, pageable)
        );

        return FilterExecutor.execute(
                filters,
                queryMap,
                () -> machineRepository.findAll(pageable),
                mapper::toModel
        );
    }
}