package mavenproject;

public class modbusAddressModel {

	private Integer deviceConfigId;
	private Integer registerType;
	private Integer registerOffSet;
	private String meterData;
	private String dataType;
	private Integer sizeByte;
	private Double factor;
	private String byteOrder;
	private Integer channel;
	private Integer statusInsert;
	private Long valueNewByLong;
	private Float valueNewByFloat;
	private String valueReturn;
	public Integer getDeviceConfigId() {
		return deviceConfigId;
	}
	public void setDeviceConfigId(Integer deviceConfigId) {
		this.deviceConfigId = deviceConfigId;
	}
	public Integer getRegisterType() {
		return registerType;
	}
	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}
	public Integer getRegisterOffSet() {
		return registerOffSet;
	}
	public void setRegisterOffSet(Integer registerOffSet) {
		this.registerOffSet = registerOffSet;
	}
	public String getMeterData() {
		return meterData;
	}
	public void setMeterData(String meterData) {
		this.meterData = meterData;
	}
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getSizeByte() {
		return sizeByte;
	}
	public void setSizeByte(Integer sizeByte) {
		this.sizeByte = sizeByte;
	}
	public Long getValueNewByLong() {
		return valueNewByLong;
	}
	public void setValueNewByLong(Long valueNewByLong) {
		this.valueNewByLong = valueNewByLong;
	}
	public Float getValueNewByFloat() {
		return valueNewByFloat;
	}
	public void setValueNewByFloat(Float valueNewByFloat) {
		this.valueNewByFloat = valueNewByFloat;
	}
	
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	public String getByteOrder() {
		return byteOrder;
	}
	public void setByteOrder(String byteOrder) {
		this.byteOrder = byteOrder;
	}
	public String getValueReturn() {
		return valueReturn;
	}
	public void setValueReturn(String valueReturn) {
		this.valueReturn = valueReturn;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public Integer getStatusInsert() {
		return statusInsert;
	}
	public void setStatusInsert(Integer statusInsert) {
		this.statusInsert = statusInsert;
	}
	
	
}
