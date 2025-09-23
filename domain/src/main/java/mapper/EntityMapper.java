package mapper;

public interface EntityMapper<M, E> {
    M toModel(E entity);
    E toEntity(M model);
}

