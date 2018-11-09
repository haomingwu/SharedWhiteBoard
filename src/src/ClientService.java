import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

	public boolean notifyTask(Shape s) throws RemoteException;
	
	public void notifyMessage(String s) throws RemoteException;
	
	public void notifyClient(String s) throws RemoteException;
	
	public void notifyClientLeave(String s) throws RemoteException;
	
	public void notifyNewFile() throws RemoteException;
	
	public void notifyOpenFile() throws RemoteException;
	
	public void notifyManagerLeave() throws RemoteException;
	
	public void notifyKick() throws RemoteException;
	
	public void notifyKickMessage(String message) throws RemoteException;
	
	public boolean notifyApproval(String username) throws RemoteException;
}
