package mavenproject;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.InputRegister;

public class RunableThead extends runJob implements Runnable{

	
	private Integer key;
	private Timer timer;
	private int td = 2;
//	private LocalDateTime  startDisConnect;
	private boolean disConnectTF = false;
	private ModbusModel modbusModel;
	
	RunableThead(Integer key){
		this.key = key;
		
	}
	
	Statement stmt = null;
	@Override
	public void run() {
		
//		td = 5;
//		TimerTask timerTask = new TimerTask() {
//			
//			@Override
//			public void run() {
				System.out.println(td + " : " + key);
				try {
				
			    HashMap<Integer, InputRegister> rgOffset = new HashMap<Integer,InputRegister>(); 
			    modbusModel = new ModbusModel();
				
				stmt = null;
				try (Connection conn = getConnection()) {
					StringBuffer sqlObject = new StringBuffer();
			        	
					sqlObject.append("	SELECT	");
					sqlObject.append("	    addressip ipAddress ,	");
					sqlObject.append("	    port ,	");
					sqlObject.append("	    unitid unitId ,	");
					sqlObject.append("	    mdev_id mdevId ,");
					sqlObject.append("	    MINUTESDISCONNECT minutes ");
					sqlObject.append("	FROM	");
					sqlObject.append("	    modbus_info	");
					sqlObject.append("	WHERE Status = 1	");
					sqlObject.append("	 AND   id = "+key+"	");
			        	
			        	stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(sqlObject.toString());	
						
						while (rs.next()) {
							modbusModel = modbusConfigInfi(rs);		
							modbusModel.setKey(key);
			            }
					    
					    stmt.close();
					    if (conn != null) conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				td = modbusModel.getMinutes();
				List<modbusAddressModel> dd =  map.get(key);
				
				if(dd.size()>0) {
					int startModbus =dd.get(0).getRegisterOffSet();
					int vt = 1;
					List<StartAndCountInfoModel> listST = new ArrayList<>();
					StartAndCountInfoModel st = new StartAndCountInfoModel();
					while(2>1) {
						st.setStart(startModbus);
						int countEnd = (dd.get(dd.size()- vt).getRegisterOffSet() ) ;
						if(countEnd - startModbus < 40) {
							st.setCount(countEnd + (dd.get(dd.size()- vt).getSizeByte() /2) - 1) ;
							listST.add(st);
							if(vt == 1) {
								modbusModel.setStartAndCount(listST);
								break;
							}else {
								st = new StartAndCountInfoModel();
								startModbus = (dd.get(dd.size()- vt + 1).getRegisterOffSet() ) ;
								vt =1; 
							}
						}else {
							vt++;
						}
					}
					
				}
				
				try {
					TCPMasterConnection con = null;
					ModbusTCPTransaction trans = null;
					final InetAddress address = InetAddress.getByName(modbusModel.getIpAddress());
					con = new TCPMasterConnection(address);
					con.setPort(modbusModel.getPort());
//					con.setTimeout(1000);
					con.connect();
					int countAddress =  0;
					for(StartAndCountInfoModel ls : modbusModel.getStartAndCount()) {
						int addressId = ls.getStart();
						int fa = ls.getCount() - addressId + 1 ;
						countAddress = addressId;
						
						ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(addressId,fa);
						ReadMultipleRegistersResponse aina = new ReadMultipleRegistersResponse();
						   ainRes.setUnitID(modbusModel.getUnitId());
						   trans = new ModbusTCPTransaction(con);
			
						   trans.setRetries(5);
//				           trans.setReconnecting(true);
				           trans.setRequest(ainRes);
//				           trans.isReconnecting();
				           trans.execute();
				           disConnectTF = false;
				           ModbusResponse res = trans.getResponse();
				           aina = (ReadMultipleRegistersResponse) res;
				           
				           for(int jk = 0;jk < fa; jk++) {
				        	   if(aina.getRegisterValue(jk) == 65535) {
				        		   aina.getRegister(jk).setValue(0);
				        	   }
				        	   rgOffset.put(countAddress, aina.getRegister(jk));
				        	   countAddress ++;
				           }
					}
					con.close();
				}catch (Exception e) {
				disConnectTF = true;
				
//					e.printStackTrace();
				}
				if(!disConnectTF) {
					
					if(flag.get(key)== false) {
						flag.put(key, true);
						ConfigMeterEventLog configMeterEventLog = new ConfigMeterEventLog();
						configMeterEventLog.setStartDate(LocalDateTime.now());
						startDisConnect.put(key, configMeterEventLog);
						
						InsertMeterEventLog("reconnect",modbusModel.getMdevId());
						startDisConnect.put(key, new ConfigMeterEventLog());
					}
					
					List<InputRegister> putArray = new ArrayList<>();	
					for(modbusAddressModel lr : dd) {
						putArray = new ArrayList<>();	
						int sizebyte = lr.getSizeByte() / 2;
						if(sizebyte == 1) {
							lr.setValueNewByLong(new Long(rgOffset.get(lr.getRegisterOffSet()).getValue()));
						}else {
							for(int v = 0; v<sizebyte;v++) {
								putArray.add(rgOffset.get(lr.getRegisterOffSet() + v));
							}
							
							if(lr.getDataType().contains("Unsigned_int")) {
								if(lr.getByteOrder().equalsIgnoreCase("4321")) {
									lr.setValueNewByLong(toIntSwap(putArray));
								}else if(lr.getByteOrder().equalsIgnoreCase("1234")) {
									lr.setValueNewByLong(toInt(putArray));
								}
							}else if(lr.getDataType().contains("Float")) {
								if(lr.getByteOrder().equalsIgnoreCase("4321")) {
									lr.setValueNewByFloat(toFloatSwap(putArray));
								}else if(lr.getByteOrder().equalsIgnoreCase("1234")) {
									lr.setValueNewByFloat(toFloat(putArray));
								}
								
							}else if(lr.getDataType().contains("SPECICAL")) {
								if(lr.getByteOrder().equalsIgnoreCase("4321")) {
									lr.setValueNewByLong(toIntSwap(putArray));
								}else if(lr.getByteOrder().equalsIgnoreCase("1234")) {
									lr.setValueNewByLong(toInt(putArray));
								}
							}
						}

						if(lr.getValueNewByLong()!=null) {
							if(sizebyte >=4) {
								lr.setValueReturn(new BigDecimal(lr.getValueNewByLong()* lr.getFactor()).toString());
							}else {
								lr.setValueReturn(String.valueOf(lr.getValueNewByLong()* lr.getFactor()));
							}
						}else if(lr.getValueNewByFloat()!=null) {
							lr.setValueReturn(String.valueOf(lr.getValueNewByFloat()* lr.getFactor()));
						}
						
						
						
						
					}
					modbusList.put(key, rgOffset);
					
					
					HashMap<Integer, modelChannel> modelChannel = new HashMap<Integer, modelChannel>();;
					
					String columnInsert = "yyyymmdd,yyyymmddhhmmss,";
//					columnInsert += "DEVICECONFIG_ID,";
					String valueInsert = "";
					
					LocalDateTime nowTime = LocalDateTime.now();
					valueInsert += "'"+DateTimeFormatter.ofPattern("yyyyMMdd").format(nowTime) +"' ,";  
					valueInsert += "'"+DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(nowTime) +"' ,";  
//					valueInsert += key + " ,";
					for(modbusAddressModel lr : dd) {
						if(lr.getChannel()!=null && lr.getChannel()!=0 && lr.getStatusInsert() == 1) {
							modelChannel model = new modelChannel();
							model.setYyyymmddhh(DateTimeFormatter.ofPattern("yyyyMMddHH").format(nowTime));
							model.setDst(0);
							model.setMdevId(modbusModel.getMdevId());
							model.setMdevType("Meter");
							model.setDeviceType("Modem");
							model.setFullLocation("loc_8");
							model.setHh("00");
							model.setLocationId(8);
							model.setMeterId(0);
							model.setModemId(0);
							model.setSupplierId(1);
							model.setValueCNT(2);
							model.setValue(lr.getValueReturn());
							modelChannel.put(lr.getChannel(), model);
							
						}
						columnInsert += lr.getMeterData()  + " ,";
						String val = "0";
//						if(lr.getValueNewByLong()!=null) {
//							val = "" + lr.getValueNewByLong();
//						}else if(lr.getValueNewByFloat() !=null) {
//							val = "" + lr.getValueNewByFloat();
//						}
						val = "" + lr.getValueReturn();
						valueInsert += "'"+ val +"' ," ;
					}
					columnInsert= columnInsert.substring(0,columnInsert.length() - 1);
					valueInsert= valueInsert.substring(0,valueInsert.length() - 1);
					
					InsertDataModbus(columnInsert,valueInsert);
					if(modelChannel.size()>0) {
						lpEm lpem = new lpEm(modelChannel);
						Thread thread = new Thread(lpem);
						thread.start();
					}
					
				}else {
					ConfigMeterEventLog configMeterEventLog = startDisConnect.get(key);
					if(configMeterEventLog.getStartDate()==null) {
						configMeterEventLog.setStartDate(LocalDateTime.now());
					}
					if(configMeterEventLog.getMinutes()!=null) {
						configMeterEventLog.setMinutes(configMeterEventLog.getMinutes() + 1);
						startDisConnect.put(key, configMeterEventLog);
						if(configMeterEventLog.getMinutes() == td) {
							InsertMeterEventLog("DisConnectMdev",modbusModel.getMdevId());
							flag.put(key, false);
						}
						
					}else {
						configMeterEventLog.setMinutes(1);
					}
					
					
				}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
//				if(td==0 || !disConnectTF) {
//					InsertMeterEventLog("DisConnectMdev",modbusModel.getMdevId());
//					if(td == 0) {
////						InsertMeterEventLog("DisConnectMdev",startDisConnect);
//					}
//					timer.cancel();
//				}
					
//				timer.cancel();
//				td--;
				
//			}
//		};
//		long delay = 60000L;
//		timer = new Timer("Timer");
//		timer.schedule(timerTask, 0,delay);
		
	}
	
	private void InsertMeterEventLog(String statusConnect,String mdevId) {

		try{
			 String deviceconFig =null;
			 Connection conn = null;
			 StringBuffer sqlObject;
				deviceconFig = mdevId;
				    
			 
			    if(deviceconFig!=null) {
			    conn = getConnection(); 
				sqlObject = new StringBuffer();
	        	
				sqlObject.append("	insert into meterevent_log( ");
				sqlObject.append("		ACTIVATOR_ID, ");
				sqlObject.append("		METEREVENT_ID, ");
				sqlObject.append("		OPEN_TIME, ");
				sqlObject.append("		ACTIVATOR_TYPE, ");
				sqlObject.append("		MESSAGE, ");
				sqlObject.append("		SUPPLIER_ID, ");
				sqlObject.append("		WRITETIME, ");
				sqlObject.append("		YYYYMMDD, ");
				sqlObject.append("		FLAG_PMS) ");
				sqlObject.append("		values('"+deviceconFig+"', ");
				sqlObject.append("		'"+statusConnect+"', ");
				sqlObject.append("	'"+DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(startDisConnect.get(key).getStartDate())+"', ");
				sqlObject.append("		'EnergyMeter', ");
				sqlObject.append("	'dd', ");
				sqlObject.append("		'1', ");
				sqlObject.append("	'"+DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())+"', ");
				sqlObject.append("	'"+DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now())+"', ");
				sqlObject.append("		'0') ");
		        	stmt = conn.createStatement();
					stmt.executeUpdate(sqlObject.toString());	
					
				    
				    stmt.close();
				    if (conn != null) conn.close();
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}
	private void InsertDataModbus(String columnInsert,String valueInsert) {
		stmt = null;
		try (Connection conn = getConnection()) {
			StringBuffer sqlObject = new StringBuffer();
	        	
			sqlObject.append("	insert into modbus_data	(");
			sqlObject.append("	    "+columnInsert+" ");
			sqlObject.append("	    ) 	");
			sqlObject.append("	  values	");
			sqlObject.append("	(	");
			sqlObject.append("	    "+ valueInsert +"	");
			sqlObject.append("	)	");
	        	
	        	stmt = conn.createStatement();
				stmt.executeUpdate(sqlObject.toString());	
				
			    
			    stmt.close();
			    if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void InsertLpEm(HashMap<Integer, modelChannel> modelChannel) {
		
		Set<Integer> keySet = modelChannel.keySet();
		for(Integer key : keySet) {
			
		}
		stmt = null;
		try (Connection conn = getConnection()) {
			StringBuffer sqlObject = new StringBuffer();
	        	
			sqlObject.append("	select 1 from lp_em ");
			sqlObject.append("	where channel = 1 ");
			sqlObject.append("	AND mdev_id = 'ML31717095509' ");
			sqlObject.append("	AND yyyymmddhh = '2020082522' ");
	        	
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlObject.toString());	
				
				while (rs.next()) {
					modbusModel = modbusConfigInfi(rs);		
					modbusModel.setKey(key);
	            }
				
			    
			    stmt.close();
			    if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
