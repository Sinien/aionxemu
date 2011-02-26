/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is private software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alpha team is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alpha team.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model;

/**
 * @author xavier
 */
public enum DuelResult {
    DUEL_WON(1300098, (byte) 2),
    DUEL_LOST(1300099, (byte) 0);

    private int msgId;
    private byte resultId;

    private DuelResult(int msgId, byte resultId) {
        this.msgId = msgId;
        this.resultId = resultId;
    }

    public int getMsgId() {
        return msgId;
    }

    public byte getResultId() {
        return resultId;
    }
}