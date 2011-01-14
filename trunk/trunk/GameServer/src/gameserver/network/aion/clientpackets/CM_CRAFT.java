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

package gameserver.network.aion.clientpackets;

import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionClientPacket;
import gameserver.services.CraftService;

/**
 * @author Mr. Poke
 */
public class CM_CRAFT extends AionClientPacket {
    @SuppressWarnings("unused")
    private int unk;
    private int targetTemplateId;
    private int recipeId;
    private int targetObjId;

    /**
     * @param opcode
     */
    public CM_CRAFT(int opcode) {
        super(opcode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        unk = readC();
        targetTemplateId = readD();
        recipeId = readD();
        targetObjId = readD();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();

        if (player == null || !player.isSpawned())
            return;

        //disallow crafting in shutdown progress..
        if (player.getController().isInShutdownProgress())
            return;

        CraftService.startCrafting(player, targetTemplateId, recipeId, targetObjId);
    }
}
