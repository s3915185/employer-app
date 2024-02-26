package org.example.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployerEntity is a Querydsl query type for EmployerEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmployerEntity extends EntityPathBase<EmployerEntity> {

    private static final long serialVersionUID = 844454518L;

    public static final QEmployerEntity employerEntity = new QEmployerEntity("employerEntity");

    public final DatePath<java.time.LocalDate> dateAffiliation = createDate("dateAffiliation", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dateRadiation = createDate("dateRadiation", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final StringPath numberIDE = createString("numberIDE");

    public final ListPath<SalaryEntity, QSalaryEntity> salaries = this.<SalaryEntity, QSalaryEntity>createList("salaries", SalaryEntity.class, QSalaryEntity.class, PathInits.DIRECT2);

    public final StringPath type = createString("type");

    public QEmployerEntity(String variable) {
        super(EmployerEntity.class, forVariable(variable));
    }

    public QEmployerEntity(Path<? extends EmployerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployerEntity(PathMetadata metadata) {
        super(EmployerEntity.class, metadata);
    }

}

