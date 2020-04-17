package com.daos.repu;

import com.daos.Charts;
import com.entities.Chart;

public class ChartsRepusitory extends AbstractRepusitory<Chart> implements Charts {

    public ChartsRepusitory(){objectsClass = Chart.class;}

}
