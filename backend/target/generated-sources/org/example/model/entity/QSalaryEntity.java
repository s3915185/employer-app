package org.example.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalaryEntity is a Querydsl query type for SalaryEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalaryEntity extends EntityPathBase<SalaryEntity> {

    private static final long serialVersionUID = 1748440133L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalaryEntity salaryEntity = new QSalaryEntity("salaryEntity");

    public final DatePath<java.time.LocalDate> dateDebut = createDate("dateDebut", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dateFinished = createDate("dateFinished", java.time.LocalDate.class);

    public final QEmployerEntity employer;

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Float> numberAC = createNumber("numberAC", Float.class);

    public final NumberPath<Float> numberAF = createNumber("numberAF", Float.class);

    public final StringPath numberAVS = createString("numberAVS");

    public final NumberPath<Float> numberAvsAiApg = createNumber("numberAvsAiApg", Float.class);

    public QSalaryEntity(String variable) {
        this(SalaryEntity.class, forVariable(variable), INITS);
    }

    public QSalaryEntity(Path<? extends SalaryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalaryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalaryEntity(PathMetadata metadata, PathInits inits) {
        this(SalaryEntity.class, metadata, inits);
    }

    public QSalaryEntity(Class<? extends SalaryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employer = inits.isInitialized("employer") ? new QEmployerEntity(forProperty("employer")) : null;
    }

}

