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
package gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import gameserver.dao.PlayerPetsDAO;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.ToyPet;
import gameserver.network.aion.serverpackets.SM_PET;
import gameserver.utils.PacketSendUtility;

import java.util.List;

/**
 * @author xitanium
 */
public class ToyPetService {

    public static final ToyPetService getInstance() {
        return SingletonHolder.instance;
    }

    private ToyPetService() {

    }

    public void createPetForPlayer(Player player, int petId, int decorationId, String name) {
        DAOManager.getDAO(PlayerPetsDAO.class).insertPlayerPet(player, petId, decorationId, name);
        List<ToyPet> list = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
        if (list == null)
            return;
        ToyPet pet = null;
        for (ToyPet p : list) {
            if (p.getPetId() == petId)
                pet = p;
        }

        if (pet != null) {
            PacketSendUtility.sendPacket(player, new SM_PET(1, pet));
        }
    }

    public void surrenderPet(Player player, int petId) {
        List<ToyPet> list = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
        if (list == null)
            return;
        ToyPet pet = null;
        for (ToyPet p : list) {
            if (p.getPetId() == petId)
                pet = p;
        }
        DAOManager.getDAO(PlayerPetsDAO.class).removePlayerPet(player, pet.getPetId());
        PacketSendUtility.sendPacket(player, new SM_PET(2, pet));
    }

    public void summonPet(Player player, int petId) {
        if (player.getToyPet() != null)
            dismissPet(player, petId);
        ToyPet pet = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPet(player.getObjectId(), petId);
        if (pet != null) {
            player.setToyPet(pet);
            pet.setMaster(player);
            PacketSendUtility.broadcastPacket(player, new SM_PET(3, pet), true);
        }
    }

    public void dismissPet(Player player, int petId) {
        if (player.getToyPet() == null)
            return;
        int uid = player.getToyPet().getDatabaseIndex();
        player.setToyPet(null);
        PacketSendUtility.broadcastPacket(player, new SM_PET(4, uid), true);
    }

    public void onPlayerLogin(Player player) {
        List<ToyPet> playerPets = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
        if (playerPets != null && playerPets.size() > 0) {
            PacketSendUtility.sendPacket(player, new SM_PET(0, playerPets));
        }
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final ToyPetService instance = new ToyPetService();
    }
}