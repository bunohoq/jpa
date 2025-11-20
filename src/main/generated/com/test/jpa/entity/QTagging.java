package com.test.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagging is a Querydsl query type for Tagging
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagging extends EntityPathBase<Tagging> {

    private static final long serialVersionUID = -1227245204L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagging tagging = new QTagging("tagging");

    public final QBoard board;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final QTag tag;

    public QTagging(String variable) {
        this(Tagging.class, forVariable(variable), INITS);
    }

    public QTagging(Path<? extends Tagging> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagging(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagging(PathMetadata metadata, PathInits inits) {
        this(Tagging.class, metadata, inits);
    }

    public QTagging(Class<? extends Tagging> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

