/**
 * This file is part of Aion Core <aioncore.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model.broker.filter;

import gameserver.model.PlayerClass;
import gameserver.model.templates.item.ItemTemplate;

/**
 * @author ATracer
 */
public class BrokerPlayerClassExtraFilter extends BrokerPlayerClassFilter {
    private int mask;

    /**
     * @param playerClass
     */
    public BrokerPlayerClassExtraFilter(int mask, PlayerClass playerClass) {
        super(playerClass);
        this.mask = mask;
    }

    @Override
    public boolean accept(ItemTemplate template) {
        return super.accept(template) && mask == template.getTemplateId() / 100000;
    }

}
