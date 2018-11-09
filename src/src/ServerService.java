import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

public interface ServerService extends Remote{

	public void register(String username, ClientService c) throws RemoteException;
	
	public void registerManager(ClientService manager) throws RemoteException;
	
	public void broadcast(Shape s) throws RemoteException;
	
	public Vector<Shape> getShapeList() throws RemoteException;
	
	public void boradcastMessage(String message) throws RemoteException;
	
	public void leave(String username) throws RemoteException;
	
	public HashMap<String, ClientService> getClientsList() throws Exception;
	
	public void boardcastClient(String s) throws RemoteException;
	
	public Vector<String> getMessageList() throws Exception;
	
	public boolean newFile() throws Exception;
	
	public boolean openFile(Vector<Shape> list) throws Exception;
	
	public void managerLeave() throws RemoteException;
	
	public boolean kickUser(String username) throws RemoteException;
	
	public boolean approval(String username) throws RemoteException;
	
	public void removeClient(String username) throws RemoteException;
	
	public boolean checkUserName(String username) throws RemoteException;
	
}
