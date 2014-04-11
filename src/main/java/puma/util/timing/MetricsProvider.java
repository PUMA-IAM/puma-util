package puma.util.timing;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used for representing objects that provide metrics (i.e.,
 * the measurements).
 * 
 * @author maartend
 *
 */
public interface MetricsProvider extends Remote {
	
	/**
	 * Returns the metrics as JSON string.
	 * 
	 * @return
	 */
	public String getMetrics() throws RemoteException;

}
