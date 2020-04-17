package com.daos.repo;

import com.daos.Charts;
import com.entities.Chart;


public class ChartsRepository extends AbstractRepository<Chart> implements Charts {

    public ChartsRepository(){
        this.objectsClass = Chart.class;
    }
}
