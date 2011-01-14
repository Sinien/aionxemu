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
package gameserver.network.aion;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.ConnectionFactory;
import com.aionemu.commons.network.Dispatcher;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * ConnectionFactory implementation that will be creating AionConnections
 *
 * @author -Nemesiss-
 */
public class GameConnectionFactoryImpl implements ConnectionFactory {
    /**
     * Create a new {@link com.aionemu.commons.network.AConnection AConnection} instance.<br>
     *
     * @param socket     that new {@link com.aionemu.commons.network.AConnection AConnection} instance will represent.<br>
     * @param dispatcher to witch new connection will be registered.<br>
     * @return a new instance of {@link com.aionemu.commons.network.AConnection AConnection}<br>
     * @throws IOException
     * @see com.aionemu.commons.network.AConnection
     * @see com.aionemu.commons.network.Dispatcher
     */

    /* (non-Javadoc)
      * @see com.aionemu.commons.network.ConnectionFactory#create(java.nio.channels.SocketChannel, com.aionemu.commons.network.Dispatcher)
      */
    @Override
    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
        // TODO Auto-generated method stub
        return new AionConnection(socket, dispatcher);
    }
}
