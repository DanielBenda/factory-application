package my.projects.factory.domain.mapper;

public interface EntityMapper<M, E> {
    M toModel(E entity);
    E toEntity(M model);
}

