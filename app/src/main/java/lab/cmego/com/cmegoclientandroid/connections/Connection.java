package lab.cmego.com.cmegoclientandroid.connections;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public interface Connection {
    void connect();
    void disconnect();
    void resolveState();
    boolean isConnected();
    void setListener(ConnectionStatusListener listener);
    ConnectionDescriptor getConnectionDescriptor();
    enum ConnectionState {INITIAL, AWAITING_CONNECTION, CONNECTED, AWAITING_DISCONNECTION, DISCONNECTED};
}
