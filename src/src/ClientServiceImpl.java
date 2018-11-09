import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService{

	
	private static final long serialVersionUID = 1L;


	protected ClientServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public boolean notifyTask(Shape s) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void notifyMessage(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyClient(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyClientLeave(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyNewFile() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyOpenFile() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyManagerLeave() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyKick() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyKickMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean notifyApproval(String username) throws RemoteException {
		return false;
		// TODO Auto-generated method stub
		
	}

}
