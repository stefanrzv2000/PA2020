package com.daos.controll;

import com.daos.Clasaments;
import com.entities.ClasamentEntry;

import java.util.Map;

public class ClasamentsController extends AbstractController<ClasamentEntry> implements Clasaments {
    public ClasamentsController() {
        super(ClasamentEntry.class);
    }

    @Override
    protected ClasamentEntry createEntity(Map<String, String> values) {
        return ClasamentEntry.fromMap(values);
    }
}
