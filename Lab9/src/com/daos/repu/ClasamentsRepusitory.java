package com.daos.repu;

import com.daos.Clasaments;
import com.entities.ClasamentEntry;

public class ClasamentsRepusitory extends AbstractRepusitory<ClasamentEntry> implements Clasaments {

    public ClasamentsRepusitory(){objectsClass = ClasamentEntry.class;}

}
