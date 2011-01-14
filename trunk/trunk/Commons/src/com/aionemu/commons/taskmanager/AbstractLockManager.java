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

package com.aionemu.commons.taskmanager;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lord_rex and MrPoke
 */
public abstract class AbstractLockManager {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock(); // Later could be used.

    public final void writeLock() {
        writeLock.lock();
    }

    public final void writeUnlock() {
        writeLock.unlock();
    }

    public final void readLock() {
        readLock.lock();
    }

    public final void readUnlock() {
        readLock.unlock();
    }
}
