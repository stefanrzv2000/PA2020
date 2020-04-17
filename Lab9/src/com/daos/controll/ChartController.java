package com.daos.controll;

import com.daos.Charts;
import com.entities.Chart;

import java.util.Map;

public class ChartController extends AbstractController<Chart> implements Charts {
    public ChartController() {
        super(Chart.class);
    }

    @Override
    protected Chart createEntity(Map<String, String> values) {
        return Chart.fromMap(values);
    }
}
