package ipcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPCalculator {
	
	private int subNetBit[] = new int[]{0, 0, 0, 0};
	private int hostAddr[] = new int[4];
	private int netAddr[] = new int[4];
	private int broadAddr[] = new int[4];
	
	public boolean checkIpAddress(String ip) {
		Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
		Matcher matcher = pattern.matcher(ip);
		if(!matcher.lookingAt())
			return false;
		changeHostToInt(ip);
		for(int oct : hostAddr) {
			if(oct < 0 || oct > 255)
				return false;
		}
		return true;
	}
	
	private void changeHostToInt(String host) {
		String splitHost[] = host.split("\\.");
		for(int i = 0; i < 4; i++)
			hostAddr[i] = Integer.parseInt(splitHost[i]);
	}
	
	public String getNetworkAddress() {
		for(int i = 0; i < 4; i++)
			netAddr[i] = hostAddr[i]&subNetBit[i];
		return netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+netAddr[3];
	}
	
	public String getBroadCast() {	
		for(int i = 0; i < 4; i++)
			broadAddr[i] = hostAddr[i]|(subNetBit[i]^255);
		return broadAddr[0]+"."+broadAddr[1]+"."+broadAddr[2]+"."+broadAddr[3];
	}
	
	public String getHostRange () {
		if(subNetBit[3] == 255)
			return netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+netAddr[3];
		
		return netAddr[0]+"."+netAddr[1]+"."+netAddr[2]+"."+(netAddr[3]+1)+" ~ "
				+broadAddr[0]+"."+broadAddr[1]+"."+broadAddr[2]+"."+(broadAddr[3]-1);
	}
	
	public String getUsableHost(int bit) {
		if(subNetBit[3] == 255)
			return String.valueOf(0);
		return String.valueOf((int)Math.pow(2, 32-bit)-2);
	}
	
	public String getSubnet(int bit) {
		int oct = bit/8;
		
		for(int i = 0; i < 4; i++)
			subNetBit[i] = 0;
		
		for(int i = 0; i < oct; i++)
			subNetBit[i] = 255;
		
		for(int i = 0; i < bit % 8; i++)
			subNetBit[oct] += 2 << (6 - i);
		
		return subNetBit[0] + "." + subNetBit[1] + "." + subNetBit[2] + "." + subNetBit[3];
	}

}
