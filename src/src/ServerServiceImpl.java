import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {

	
	private static final long serialVersionUID = 1L;
	private Vector<Shape> shapelist = new Vector<Shape>();
	private HashMap<String, ClientService> clientlist = new HashMap<String, ClientService>();
	private Vector<String> messagelist = new Vector<String>();
	private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock cl = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock ml = new ReentrantReadWriteLock();
	

	protected ServerServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public void register(String username, ClientService c) throws RemoteException {
		// TODO Auto-generated method stub
		cl.writeLock().lock();
		try {
			clientlist.put(username, c);
		} finally {
			cl.writeLock().unlock();
		}
	}
	
	@Override
	public void registerManager(ClientService manager) throws RemoteException {
		clientlist.put("Manager", manager);
		
	}

	@Override
	public void broadcast(Shape s) throws RemoteException {
		rw.writeLock().lock();
		shapelist.addElement(s);
		rw.writeLock().unlock();
		cl.readLock().lock();
		for (Entry<String, ClientService> entry : clientlist.entrySet()) {
			entry.getValue().notifyTask(s);
		}
		cl.readLock().unlock();
	}

	@Override
	public Vector<Shape> getShapeList() throws RemoteException {
		// TODO Auto-generated method stub
		rw.readLock().lock();
		try {
			Vector<Shape> list = shapelist;
			return list;
		} finally {
			rw.readLock().unlock();
		}
	}

	@Override
	public void boradcastMessage(String message) throws RemoteException {
		ml.writeLock().lock();
		messagelist.addElement(message);
		ml.writeLock().unlock();
		cl.readLock().lock();
		for (Entry<String, ClientService> entry : clientlist.entrySet()) {
			entry.getValue().notifyMessage(message);
		}
		cl.readLock().unlock();
	}

	@Override
	public void leave(String username) throws RemoteException {
		Date d = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
        String dateNowStr = sdf.format(d); 
		ml.writeLock().lock();
		messagelist.addElement(dateNowStr+" Manager: " + username+"has left.");
		ml.writeLock().unlock();
		cl.writeLock().lock();
		clientlist.remove(username);
		for (Entry<String, ClientService> entry : clientlist.entrySet()) {
			entry.getValue().notifyClientLeave(username);
		}
		cl.writeLock().unlock();
		
	}

	@Override
	public HashMap<String, ClientService> getClientsList() throws Exception {
		// TODO Auto-generated method stub
		cl.readLock().lock();
		try {
			HashMap<String, ClientService> list = clientlist;
			return list;
		} finally {
			cl.readLock().unlock();
		}

	}

	@Override
	public void boardcastClient(String username) throws RemoteException {
		Date d = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
        String dateNowStr = sdf.format(d); 
		ml.writeLock().lock();
		messagelist.addElement(dateNowStr+" Manager: Welcom " + username);
		ml.writeLock().unlock();
		cl.readLock().lock();
		for (Entry<String, ClientService> entry : clientlist.entrySet()) {
			entry.getValue().notifyClient(username);
		}
		cl.readLock().unlock();
	}

	@Override
	public Vector<String> getMessageList() throws Exception {
		ml.readLock().lock();
		try{
			Vector<String> list = messagelist;
			return list;
		}finally{
			ml.readLock().unlock();
		}
		
	}

	@Override
	public boolean newFile() throws Exception {
		rw.writeLock().lock();
		shapelist = new Vector<Shape>();
		rw.writeLock().unlock();
		cl.readLock().lock();
		try{
			for (Entry<String, ClientService> entry : clientlist.entrySet()) {
				entry.getValue().notifyNewFile();
			}
			return true;
		}finally{
			cl.readLock().unlock();
		}
	}

	@Override
	public boolean openFile(Vector<Shape> list) throws Exception {
		// TODO Auto-generated method stub
		rw.writeLock().lock();
		shapelist = list;
		rw.writeLock().unlock();
		cl.readLock().lock();
		try{
			for (Entry<String, ClientService> entry : clientlist.entrySet()) {
				entry.getValue().notifyOpenFile();
			}
			return true;
		}finally{
			cl.readLock().unlock();
		}
	}

	@Override
	public void managerLeave() throws RemoteException {
		for (Entry<String, ClientService> entry : clientlist.entrySet()) {
			entry.getValue().notifyManagerLeave();
		}
		
	}

	@Override
	public boolean kickUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		cl.writeLock().lock();
		Date d = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
        String dateNowStr = sdf.format(d); 
		try{
			if(clientlist.containsKey(username)){
				clientlist.get(username).notifyKick();
				clientlist.remove(username);
				String message = dateNowStr+" Manager: " + username+" has been kicked out.";
				messagelist.addElement(message);
				for (Entry<String, ClientService> entry : clientlist.entrySet()) {
					entry.getValue().notifyKickMessage(message);
				}
				return true;
			}
			return false;
		}finally{
			cl.writeLock().unlock();
		}	
	}

	@Override
	public boolean approval(String username) throws RemoteException {
		if(clientlist.get("Manager").notifyApproval(username)){
			return true;
		}
		return false;
	}

	@Override
	public void removeClient(String username) throws RemoteException {
		cl.writeLock().lock();
		try{
			clientlist.remove(username);
		}finally{
			cl.writeLock().unlock();
		}
	}

	@Override
	public boolean checkUserName(String username) throws RemoteException {
		cl.readLock().lock();
		try{
			if(clientlist.containsKey(username)){
				return false;
			}
			return true;
		}finally{
			cl.readLock().unlock();
		}
		
	}

	

}
