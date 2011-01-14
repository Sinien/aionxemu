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

package gameserver.model.templates.bonus;

/**
 * @author Rolandas
 */

public enum InventoryBonusType {
    BOSS,            // %Quest_L_boss; siege related?
    COIN,            // %Quest_L_coin
    ENCHANT,
    FOOD,            // %Quest_L_food
    FORTRESS,        // %Quest_L_fortress; sends promotion mails with medals?
    GOODS,            // %Quest_L_Goods
    ISLAND,            // %Quest_L_3_island; siege related?
    MAGICAL,        // %Quest_L_magical
    MANASTONE,        // %Quest_L_matter_option
    MASTER_RECIPE,    // %Quest_ta_l_master_recipe
    MATERIAL,        // %Quest_L_material
    MEDAL,            // %Quest_L_medal
    MEDICINE,        // %Quest_L_medicine; potions and remedies
    MOVIE,            // %Quest_L_Christmas; cut scenes
    NONE,
    RECIPE,            // %Quest_L_Recipe
    REDEEM,            // %Quest_L_Rnd_Redeem and %Quest_L_redeem
    TASK,            // %Quest_L_task; craft related
}
