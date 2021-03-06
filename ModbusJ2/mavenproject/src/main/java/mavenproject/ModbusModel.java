package mavenproject;

import java.util.List;

public class ModbusModel {
	private String ipAddress;
	private Integer port;
	private Integer unitId;
	private Integer key;
	private Integer ntryConnect;
	private String mdevId;
	private Integer minutes;
	
	private List<StartAndCountInfoModel> startAndCount;
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public List<StartAndCountInfoModel> getStartAndCount() {
		return startAndCount;
	}
	public void setStartAndCount(List<StartAndCountInfoModel> startAndCount) {
		this.startAndCount = startAndCount;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Integer getNtryConnect() {
		return ntryConnect;
	}
	public void setNtryConnect(Integer ntryConnect) {
		this.ntryConnect = ntryConnect;
	}
	public String getMdevId() {
		return mdevId;
	}
	public void setMdevId(String mdevId) {
		this.mdevId = mdevId;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	
}
