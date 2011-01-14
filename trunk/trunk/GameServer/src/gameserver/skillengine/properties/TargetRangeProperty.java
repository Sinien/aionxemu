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
package gameserver.skillengine.properties;

import gameserver.model.alliance.PlayerAllianceMember;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Trap;
import gameserver.model.gameobjects.player.Player;
import gameserver.skillengine.model.Skill;
import gameserver.utils.MathUtil;
import gameserver.world.Executor;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRangeProperty")
public class TargetRangeProperty
        extends Property {

    private static final Logger log = Logger.getLogger(TargetRangeProperty.class);

    @XmlAttribute(required = true)
    protected TargetRangeAttribute value;

    @XmlAttribute
    protected int distance;

    @XmlAttribute
    protected int maxcount;

    /**
     * Gets the value of the value property.
     */
    public TargetRangeAttribute getValue() {
        return value;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean set(final Skill skill) {
        final List<Creature> effectedList = skill.getEffectedList();

        skill.setTargetRangeAttribute(value);
        switch (value) {
            case ONLYONE:
                skill.setMaxEffected(1);
                break;
            case AREA:
                final Creature firstTarget = skill.getFirstTarget();
                if (firstTarget == null) {
                    log.warn("CHECKPOINT: first target is null for skillid " + skill.getSkillTemplate().getSkillId());
                    return false;
                }

                skill.setMaxEffected(maxcount);
                skill.getEffector().getKnownList().doOnAllObjects(new Executor<AionObject>() {
                    @Override
                    public boolean run(AionObject nextObject) {
                        // firstTarget is already added, look: FirstTargetProperty
                        if (firstTarget == nextObject)
                            return true;

                        // TODO this is a temporary hack for traps
                        if (skill.getEffector() instanceof Trap && ((Trap) skill.getEffector()).getCreator() == nextObject)
                            return true;

                        if (!(nextObject instanceof Creature))
                            return true;

                        Creature nextCreature = (Creature) nextObject;

                        // Creature with no life stats are not supposed to be attacked
                        if (nextCreature.getLifeStats() == null)
                            return true;

                        // TODO: here value +4 till better move controller developed
                        if (!nextCreature.getLifeStats().isAlreadyDead()
                                && MathUtil.isIn3dRange(firstTarget, nextCreature, distance + 4)) {
                            effectedList.add(nextCreature);
                        }

                        return true;
                    }
                }, true);
                break;
            case PARTY:
                if (skill.getEffector() instanceof Player) {
                    skill.setMaxEffected(6);
                    Player effector = (Player) skill.getEffector();
                    if (effector.isInAlliance()) {
                        effectedList.clear();
                        for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance().getMembersForGroup(effector.getObjectId())) {
                            if (!allianceMember.isOnline()) continue;
                            Player member = allianceMember.getPlayer();
                            if (MathUtil.isIn3dRange(effector, member, distance + 4))
                                effectedList.add(member);
                        }
                    } else if (effector.isInGroup()) {
                        effectedList.clear();
                        for (Player member : effector.getPlayerGroup().getMembers()) {
                            //TODO: here value +4 till better move controller developed
                            if (member != null && MathUtil.isIn3dRange(effector, member, distance + 4))
                                effectedList.add(member);
                        }
                    }
                }
                break;
            case NONE:
                break;
            case PARTY_WITHPET:
                if (skill.getEffector() instanceof Player) {
                    skill.setMaxEffected(12);
                    Player effector = (Player) skill.getEffector();
                    if (effector.isInAlliance()) {
                        effectedList.clear();
                        for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance().getMembersForGroup(effector.getObjectId())) {
                            if (!allianceMember.isOnline()) continue;
                            Player member = allianceMember.getPlayer();
                            if (MathUtil.isIn3dRange(effector, member, distance + 4)) {
                                effectedList.add(member);
                                if (member.getSummon() != null)
                                    effectedList.add(member.getSummon());
                            }
                        }
                    } else if (effector.isInGroup()) {
                        effectedList.clear();
                        for (Player member : effector.getPlayerGroup().getMembers()) {
                            //TODO: here value +4 till better move controller developed
                            if (member != null && MathUtil.isIn3dRange(effector, member, distance + 4)) {
                                effectedList.add(member);
                                if (member.getSummon() != null)
                                    effectedList.add(member.getSummon());
                            }
                        }
                    }
                }
                break;
            //case POINT:
        }
        return true;
	}
}
