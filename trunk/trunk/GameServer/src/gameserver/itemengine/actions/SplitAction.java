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
package gameserver.itemengine.actions;

import gameserver.model.TaskId;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.Storage;
import gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import gameserver.services.ItemService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SplitAction")
public class SplitAction extends AbstractItemAction {

    @Override
    public void act(final Player player, final Item parentItem, Item targetItem) {
        PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(),
                parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 5000, 0, 0));
        player.getController().cancelTask(TaskId.ITEM_USE);
        player.getController().addNewTask(TaskId.ITEM_USE,
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem
                                .getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 1, 0));

                        Storage inventory = player.getInventory();
                        inventory.removeFromBagByObjectId(parentItem.getObjectId(), 1);
                        int decomposedId = parentItem.getItemId() - 1;
                        switch (parentItem.getItemId()) {
                            case 152000112:
                            case 152000328:
                                decomposedId -= 2;
                                break;
                            case 152000213:
                            case 152000327:
                                decomposedId -= 3;
                                break;
                            case 152000326:
                                decomposedId -= 4;
                        }
                        ItemService.addItem(player, decomposedId, 3);
                    }
                }, 5000));
    }

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        int itemId = parentItem.getItemTemplate().getTemplateId();
        if (itemId >= 152000329 && itemId <= 152000331) {
            // There are Unique items with the same name and these Epic
            // items don't split although specified as splittable in the client
            return false;
        }
        return true;
    }

}
