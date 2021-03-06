/**
 * Copyright 2012-2015 Rafal Lewczuk <rafal.lewczuk@jitlogic.com>
 * <p/>
 * This is free software. You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jitlogic.zorka.common.tracedata;

import com.jitlogic.zorka.common.util.ObjectInspector;

import java.util.Map;

/**
 * Calculates utilization (in percent) from two components obtained from
 */
public class UtilizationMetric extends Metric {

    public UtilizationMetric(int id, String name, Map<String, Object> attrs) {
        super(id, name, attrs);
    }

    public UtilizationMetric(int id, int templateId, String name, Map<String, Object> attrs) {
        super(id, templateId, name, attrs);
    }

    public UtilizationMetric(MetricTemplate template, String name, Map<String, Object> attrs) {
        super(template, name, attrs);
    }

    @Override
    public Number getValue(long clock, Object value) {
        Number rawNom = (Number) ObjectInspector.get(value, getTemplate().getNomField());
        Number rawDiv = (Number) ObjectInspector.get(value, getTemplate().getDivField());

        if (rawNom == null || rawDiv == null) {
            return 0.0;
        }

        double curNom = rawNom.longValue();
        double curDiv = rawDiv.longValue();

        Double rslt = 0.0;

        if (curDiv != 0) {
            rslt = 100.0 * curNom / curDiv;
        }

        Double multiplier = getTemplate().getMultiplier();
        return multiplier != 1.0 ? multiplier * rslt : rslt;
    }

}
