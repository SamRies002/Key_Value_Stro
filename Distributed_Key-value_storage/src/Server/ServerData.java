package Server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Usables.LinkInfomation;


/**
 * Data structure which holds the mappings of key to values as well as the addresses of neighbor nodes
 * 
 *
 */
public class ServerData {
	
	private final Map<String, String> keyMap;
	private final List<LinkInfomation> neighborInfo;
	private final Set<Integer> treatedIdSet;

	ServerData(){
		keyMap = Collections.synchronizedMap(new HashMap<String, String>());
		neighborInfo = Collections.synchronizedList(new ArrayList<LinkInfomation>());
		treatedIdSet = Collections.synchronizedSet(new HashSet<Integer>());
	}
	
	public Map<String, String> getKeyMap(){
		return keyMap;
	}
	
	public List<LinkInfomation> getNeighborAddresses(){
		return neighborInfo;
	}
	
	public Set<Integer> getTreatedIdSet(){
		return treatedIdSet;
	}
}
