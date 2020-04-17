package com.daos.repo;

import com.daos.Clasaments;
import com.entities.ClasamentEntry;

import java.util.List;
import java.util.Optional;

public class ClasamentsRepository extends AbstractRepository<ClasamentEntry> implements Clasaments {

    public ClasamentsRepository(){
        this.objectsClass = ClasamentEntry.class;
    }

}
