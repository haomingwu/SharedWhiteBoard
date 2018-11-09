import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StartServer {

	public StartServer() {

	}

	public void start(String port, String address) throws Exception {

		int portnumber = Integer.parseInt(port);
		ServerService server = new ServerServiceImpl();
		LocateRegistry.createRegistry(portnumber);
		Naming.bind(address, server);

	}

}
