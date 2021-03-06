package mavenproject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.util.ModbusUtil;

public class main {
	
	private String URL_CJ = "";
	private String USER_CJ = "";
	private String PW_CJ = "";	
	public static HashMap<Integer, Boolean> flag = new HashMap<Integer,Boolean>(); 
	public static HashMap<Integer, ConfigMeterEventLog> startDisConnect;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Object local = null;
//		TCPMasterConnection con = null;
//		ModbusTCPTransaction trans = null;
//		final InetAddress address = InetAddress.getByName("113.164.176.132");
//		con = new TCPMasterConnection(address);
//		con.setPort(2253);
//		con.setTimeout(1000);
//		con.connect();
//		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(40072,10);
//		   ainRes.setUnitID(1);
//		   trans = new ModbusTCPTransaction(con);
////           trans.setRetries(5);
////           trans.setRequest(request);
//           trans.setReconnecting(true);
//           trans.setRequest(ainRes);
//           int k = 0;	
//           do {
//               trans.execute();
//               ModbusResponse res = trans.getResponse();
//               System.out.println("out put: " + ((ReadMultipleRegistersResponse) res).getRegisterValue(k));
//               k++;
//           } while (k < 10);
		
		
		
//		Object local = null;
//		TCPMasterConnection con = null;
//		ModbusTCPTransaction trans = null;
//		final InetAddress address = InetAddress.getByName("192.168.1.27");
//		con = new TCPMasterConnection(address);
//		con.setPort(1502);
//		con.setTimeout(1000);
//		con.connect();
//		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(0,10);
//		   ainRes.setUnitID(3);
//		   trans = new ModbusTCPTransaction(con);
////           trans.setRetries(5);
////           trans.setRequest(request);
//           trans.setReconnecting(true);
//           trans.setRequest(ainRes);
//           int k = 0;	
//           do {
//               trans.execute();
//               ModbusResponse res = trans.getResponse();
//               System.out.println("out put: " + ((ReadMultipleRegistersResponse) res).getRegisterValue(k));
//               k++;
//           } while (k < 10);
//		int addressId = 9129;
//		int fa = 20;
//		
//		Object local = null;
//		TCPMasterConnection con = null;
//		ModbusTCPTransaction trans = null;
//		final InetAddress address = InetAddress.getByName("14.241.228.79");
//		con = new TCPMasterConnection(address);
//		con.setPort(5501);
//		con.setTimeout(1000);
//		con.connect();
//		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(addressId,fa);
////		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(0,10);
//		ReadMultipleRegistersResponse aina = new ReadMultipleRegistersResponse();
//		   ainRes.setUnitID(5);
//		   trans = new ModbusTCPTransaction(con);
////           trans.setRetries(5);
////           trans.setRequest(request);
//           trans.setReconnecting(true);
//           trans.setRequest(ainRes);
//           int k = 0;	
//           int j=0;
//           do {
//        	   j++;
//               trans.execute();
//               ModbusResponse res = trans.getResponse();
//               aina = (ReadMultipleRegistersResponse) res;
//               System.out.print("addressId ( "+addressId+" ) : " + ((ReadMultipleRegistersResponse) res).getRegisterValue(k));
//               System.out.print(" m? hex : " + res.getHexMessage());
//               System.out.println();
//               if(j==4) {
//            	   j=0;
//            	   System.out.print("----- Total ( toInt ): " + toInt(aina.getRegister(k-3),aina.getRegister(k-2),aina.getRegister(k-1),aina.getRegister(k)));
//            	   System.out.println();
//            	   System.out.print("----- Total ( toFloat ): " + toFloat(aina.getRegister(k-3),aina.getRegister(k-2),aina.getRegister(k-1),aina.getRegister(k)));
//            	   System.out.println();
//            	   System.out.print("----- Total ( toLong ): " + toLong(aina.getRegister(k-3),aina.getRegister(k-2),aina.getRegister(k-1),aina.getRegister(k)));
//            	   System.out.println();
//               }
//               k++;
//               addressId++;
//           } while (k < fa);
//		
		
//		List<modbusAddressModel>  listModbus  = new ArrayList<>();		
//		Statement stmt = null;
//		try (Connection conn = getConnection()) {
//			StringBuffer sqlObject = new StringBuffer();
//	        	
//			sqlObject.append("	SELECT	");
//			sqlObject.append("	    deviceconfig_id deviceConfigId ,	");
//			sqlObject.append("	    register_type registerType ,	");
//			sqlObject.append("	    register_offset registerOffset ,	");
//			sqlObject.append("	    meter_data meterData  ,	");
//			sqlObject.append("	    data_type dataType ,	");
//			sqlObject.append("	    size_byte sizeByte	");
//			sqlObject.append("	FROM	");
//			sqlObject.append("	    modbus_address	");
//			sqlObject.append("	ORDER BY	");
//			sqlObject.append("	    deviceconfig_id ,	");
//			sqlObject.append("	    register_offset	");
//	        	
//	        	stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sqlObject.toString());	
//				
//				while (rs.next()) {
//					modbusAddressModel oData = new modbusAddressModel();				
//					oData = getModbusAddress(rs);				
//					listModbus.add(oData);
//	            }
//			    
//			    stmt.close();
//			    if (conn != null) conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//		HashMap<Integer, List<modbusAddressModel>> map = new HashMap<Integer,List<modbusAddressModel>>(); 
//		HashMap<Integer, Object> modbusList = new HashMap<Integer,Object>(); 
//		
//		if(listModbus.size() > 0) {
//			List<modbusAddressModel> device = new ArrayList<>();	
//			int deviceConfigId = listModbus.get(0).getDeviceConfigId();
//			for(int i =0;i < listModbus.size(); i++) {
//				if(i== listModbus.size() - 1) {
//					device.add(listModbus.get(i));
//					map.put(deviceConfigId, device);
//					break;
//				}
//				
//				if(listModbus.get(i).getDeviceConfigId() != deviceConfigId) {
//					map.put(deviceConfigId, device);
//					deviceConfigId = listModbus.get(i).getDeviceConfigId();
//					device = new ArrayList<>();	
//					device.add(listModbus.get(i));
//				}else {
//					device.add(listModbus.get(i));
//				}
//			}
//		}
//		
//		
//		ModbusModel modbusModel = new ModbusModel();
//		Set<Integer> keySet = map.keySet();
//		for(Integer key : keySet) {
//			try {
//				HashMap<Integer, InputRegister> rgOffset = new HashMap<Integer,InputRegister>(); 
//				modbusModel = new ModbusModel();
//				
//				stmt = null;
//				try (Connection conn = getConnection()) {
//					StringBuffer sqlObject = new StringBuffer();
//			        	
//					sqlObject.append("	SELECT	");
//					sqlObject.append("	    addressip ipAddress ,	");
//					sqlObject.append("	    port ,	");
//					sqlObject.append("	    unitid unitId	");
//					sqlObject.append("	FROM	");
//					sqlObject.append("	    modbus_info	");
//					sqlObject.append("	WHERE	");
//					sqlObject.append("	    id = "+key+"	");
//			        	
//			        	stmt = conn.createStatement();
//						ResultSet rs = stmt.executeQuery(sqlObject.toString());	
//						
//						while (rs.next()) {
//							modbusModel = modbusConfigInfi(rs);				
//			            }
//					    
//					    stmt.close();
//					    if (conn != null) conn.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				
//				List<modbusAddressModel> dd =  map.get(key);
//				
//				if(dd.size()>0) {
//					int startModbus =dd.get(0).getRegisterOffSet();
//					int vt = 1;
//					List<StartAndCountInfoModel> listST = new ArrayList<>();
//					StartAndCountInfoModel st = new StartAndCountInfoModel();
//					while(2>1) {
//						st.setStart(startModbus);
//						int countEnd = (dd.get(dd.size()- vt).getRegisterOffSet() ) ;
//						if(countEnd - startModbus < 10) {
//							st.setCount(countEnd + (dd.get(dd.size()- vt).getSizeByte() /2) - 1) ;
//							listST.add(st);
//							if(vt == 1) {
//								modbusModel.setStartAndCount(listST);
//								break;
//							}else {
//								st = new StartAndCountInfoModel();
//								startModbus = (dd.get(dd.size()- vt + 1).getRegisterOffSet() ) ;
//								vt =1; 
//							}
//						}else {
//							vt++;
//						}
//					}
//					
//				}
//				
//				Object local = null;
//				TCPMasterConnection con = null;
//				ModbusTCPTransaction trans = null;
//				final InetAddress address = InetAddress.getByName(modbusModel.getIpAddress());
//				con = new TCPMasterConnection(address);
//				con.setPort(modbusModel.getPort());
////				con.setTimeout(1000);
//				con.connect();
//				int countAddress =  0;
//				for(StartAndCountInfoModel ls : modbusModel.getStartAndCount()) {
//					int addressId = ls.getStart();
//					int fa = ls.getCount() - addressId + 1 ;
//					countAddress = addressId;
//					
//					ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(addressId,fa);
//					ReadMultipleRegistersResponse aina = new ReadMultipleRegistersResponse();
//					   ainRes.setUnitID(modbusModel.getUnitId());
//					   trans = new ModbusTCPTransaction(con);
//		
//					   trans.setRetries(5);
//			           trans.setReconnecting(true);
//			           trans.setRequest(ainRes);
//			           trans.execute();
//			           ModbusResponse res = trans.getResponse();
//			           aina = (ReadMultipleRegistersResponse) res;
//			           
//			           for(int jk = 0;jk < fa; jk++) {
//			        	   if(aina.getRegisterValue(jk) == 65535) {
//			        		   aina.getRegister(jk).setValue(0);
//			        	   }
//			        	   rgOffset.put(countAddress, aina.getRegister(jk));
//			        	   countAddress ++;
//			           }
//				}
//				con.close();
//				
//				List<InputRegister> putArray = new ArrayList<>();	
//				for(modbusAddressModel lr : dd) {
//					int ff = lr.getRegisterOffSet();
//					putArray = new ArrayList<>();	
//					int sizebyte = lr.getSizeByte() / 2;
//					if(sizebyte == 1) {
//						lr.setValueNewByLong(new Long(rgOffset.get(lr.getRegisterOffSet()).getValue()));
//						continue;
//					}
//						
//					for(int v = 0; v<sizebyte;v++) {
//						putArray.add(rgOffset.get(lr.getRegisterOffSet() + v));
//					}
//					
//					if(lr.getDataType().contains("Unsigned_int")) {
//						if(lr.getDataType().contains("swap")) {
//							lr.setValueNewByLong(toIntSwap(putArray));
//						}else {
//							lr.setValueNewByLong(toInt(putArray));
//						}
//					}else if(lr.getDataType().contains("Float")) {
//						if(lr.getDataType().contains("swap")) {
//							lr.setValueNewByFloat(toFloatSwap(putArray));
//						}else {
//							lr.setValueNewByFloat(toFloat(putArray));
//						}
////			9113			System.out.println(lr.getRegisterOffSet() +" -- " + lr.getValueNewByFloat());
//						
//					}else if(lr.getDataType().contains("SPECICAL")) {
//						if(lr.getDataType().contains("swap")) {
//							lr.setValueNewByLong(toIntSwap(putArray));
//						}else {
//							lr.setValueNewByLong(toInt(putArray));
//						}
//					}
//				}
//				modbusList.put(key, rgOffset);
//				
//				
//				String columnInsert = "yyyymmdd,yyyymmddhhmmss,";
//				String valueInsert = "";
//				
//				valueInsert += "'"+DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()) +"' ,";  
//				valueInsert += "'"+DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) +"' ,";  
//				for(modbusAddressModel lr : dd) {
//					columnInsert += lr.getMeterData()  + " ,";
//					String val = String.valueOf(lr.getValueNewByLong()!=null ? lr.getValueNewByLong() : (lr.getValueNewByFloat() !=null ? lr.getValueNewByFloat() : 0));
//					valueInsert += "'"+ val +"' ," ;
//				}
//				columnInsert= columnInsert.substring(0,columnInsert.length() - 1);
//				valueInsert= valueInsert.substring(0,valueInsert.length() - 1);
//				
//				stmt = null;
//				try (Connection conn = getConnection()) {
//					StringBuffer sqlObject = new StringBuffer();
//			        	
//					sqlObject.append("	insert into modbus_data	(");
//					sqlObject.append("	    "+columnInsert+" ");
//					sqlObject.append("	    ) 	");
//					sqlObject.append("	  values	");
//					sqlObject.append("	(	");
//					sqlObject.append("	    "+ valueInsert +"	");
//					sqlObject.append("	)	");
//			        	
//			        	stmt = conn.createStatement();
//						int rs = stmt.executeUpdate(sqlObject.toString());	
//						
//					    
//					    stmt.close();
//					    if (conn != null) conn.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			
//			
//			
//			
//		}
//		System.out.println(map);
//		System.out.println(modbusList);
		
		
		startDisConnect = new HashMap<Integer, ConfigMeterEventLog>();
		try {
			JobDetail jobrun = JobBuilder.newJob(runJob.class)
					.withIdentity("runJob", "group1").build();
			runJob.flag = flag;
			runJob.startDisConnect = startDisConnect;
			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "group1")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
					.withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * * * ?"))
					.build();
			
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(jobrun, trigger1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int addressId = 0;
//		int fa = 52;
		
//		ModbusModel model = new ModbusModel();
//		model.setIpAddress("192.168.1.26");
//		model.setPort(8899);
//		model.setTimeOut(1000);
//		model.setAddressId(0);
//		model.setCountAddressId(52);
//		Object local = null;
//		TCPMasterConnection con = null;
//		ModbusTCPTransaction trans = null;
//		final InetAddress address = InetAddress.getByName(model.getIpAddress());
//		con = new TCPMasterConnection(address);
//		con.setPort(model.getPort());
//		con.setTimeout(model.getTimeOut());
//		con.connect();
//		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(addressId,fa);
////		ReadMultipleRegistersRequest ainRes = new ReadMultipleRegistersRequest(0,10);
//		ReadMultipleRegistersResponse aina = new ReadMultipleRegistersResponse();
//		
//		   ainRes.setUnitID(1);
//		   trans = new ModbusTCPTransaction(con);
////           trans.setRetries(5);
////           trans.setRequest(request);
//           trans.setReconnecting(true);
//           trans.setRequest(ainRes);
//           int k = 0;	
//           int j=0;
//           trans.execute();
//           ModbusResponse res = trans.getResponse();
//           aina = (ReadMultipleRegistersResponse) res;
//           
//           
//           
//           System.out.print(" m? hex : " + res.getHexMessage());
//           do {
//        	   j++;
//               
////               InputRegister[] registers = ((ReadMultipleRegistersResponse) res).getRegisters();
//               
//               System.out.print("addressId ( "+addressId+" ) : " + ((ReadMultipleRegistersResponse) res).getRegisterValue(addressId));
//               
//               System.out.println();
//               if(addressId == 3) {
//                	   System.out.print("----- Total ( 1-2-3 ): " + toLong64bit(aina.getRegister(addressId-3),aina.getRegister(addressId-2),aina.getRegister(addressId-1),aina.getRegister(addressId)));
//                	   System.out.println();
//               }else if(addressId==8) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==10) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==12) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==14) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==16) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==18) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==23) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==25) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==27) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==29) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==31) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==33) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==35) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==37) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==39) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==41) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==43) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==45) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==47) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==49) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }else if(addressId==51) {
//            	   System.out.print("----- Total ( "+(addressId - 1)+"-"+addressId+" ): " + toInt32bit(aina.getRegister(addressId-1),aina.getRegister(addressId)));
//            	   System.out.println();
//               }
//               
//               addressId++;
//           } while (addressId < fa);
		
		
	}
}
