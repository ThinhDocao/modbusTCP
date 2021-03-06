package mavenproject;

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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.util.ModbusUtil;

public class runJob implements Job{
	
	public static HashMap<Integer, List<modbusAddressModel>> map;
	public static HashMap<Integer, Object> modbusList;
	public static HashMap<Integer, Boolean> flag;
	public static HashMap<Integer, ConfigMeterEventLog> startDisConnect;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(new Date());
		List<modbusAddressModel>  listModbus  = new ArrayList<>();		
		Statement stmt = null;
		try (Connection conn = getConnection()) {
			StringBuffer sqlObject = new StringBuffer();
	        	
			sqlObject.append("	SELECT	");
			sqlObject.append("	    deviceconfig_id deviceConfigId ,	");
			sqlObject.append("	    register_type registerType ,	");
			sqlObject.append("	    register_offset registerOffset ,	");
			sqlObject.append("	    meter_data meterData  ,	");
			sqlObject.append("	    data_type dataType ,	");
			sqlObject.append("	    size_byte sizeByte,	");
			sqlObject.append("	    factor factor,	");
			sqlObject.append("	    byte_order byteOrder,	");
			sqlObject.append("	    CHANNEL channel,	");
			sqlObject.append("	    STATUS_INSERT_TABLE statusInsert	");
			sqlObject.append("	FROM	");
			sqlObject.append("	    modbus_address	");
			sqlObject.append("	    WHERE EXISTS (SELECT 1 FROM modbus_info where id = modbus_address.deviceconfig_id AND status = 1)	");
			sqlObject.append("	ORDER BY	");
			sqlObject.append("	    deviceconfig_id ,	");
			sqlObject.append("	    register_offset	");
	        	
	        	stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlObject.toString());	
				
				while (rs.next()) {
					modbusAddressModel oData = new modbusAddressModel();				
					oData = getModbusAddress(rs);				
					listModbus.add(oData);
	            }
			    
			    stmt.close();
			    if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		map = new HashMap<Integer,List<modbusAddressModel>>(); 
		modbusList = new HashMap<Integer,Object>(); 
		
		if(listModbus.size() > 0) {
			List<modbusAddressModel> device = new ArrayList<>();	
			int deviceConfigId = listModbus.get(0).getDeviceConfigId();
			for(int i =0;i < listModbus.size(); i++) {
				if(i== listModbus.size() - 1) {
					device.add(listModbus.get(i));
					map.put(deviceConfigId, device);
					break;
				}
				
				if(listModbus.get(i).getDeviceConfigId() != deviceConfigId) {
					map.put(deviceConfigId, device);
					deviceConfigId = listModbus.get(i).getDeviceConfigId();
					device = new ArrayList<>();	
					device.add(listModbus.get(i));
				}else {
					device.add(listModbus.get(i));
				}
			}
		}
		
		
		//phan luong
		Set<Integer> keySet = map.keySet();
		for(Integer key : keySet) {
			if(flag == null || flag.get(key)==null) {
				flag.put(key, true);
				startDisConnect.put(key, new ConfigMeterEventLog());
			}
			try {
				RunableThead runableThead = new RunableThead(key);
				Thread thread = new Thread(runableThead);
			    thread.start();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		System.out.println(modbusList);
		
	}
	
	protected static long toInt(List<InputRegister>input) {
		byte[] bytes = new byte[(int) Math.pow(2, input.size())];
		for(int i=0;i<input.size();i++) {
			System.arraycopy(input.get(i).toBytes(), 0, bytes, i * 2, 2);
		}
		try {
			return ModbusUtil.registersToLong(bytes) ;
		}catch (Exception e) {
			return ModbusUtil.registersToInt(bytes);
		}
	}
	
	protected static long toIntSwap(List<InputRegister>input) {
		byte[] bytes = new byte[(int) Math.pow(2, input.size())];
		for(int i=input.size()-1; i>=0 ;i--) {
			System.arraycopy(input.get(i).toBytes(), 0, bytes, ((input.size() - 1) - i) * 2, 2);
		}
		try {
			return ModbusUtil.registersToLong(bytes) ;
		}catch (Exception e) {
			return ModbusUtil.registersToInt(bytes);
		}
	}
	
	protected static float toFloat(List<InputRegister>input) {
		byte[] bytes = new byte[(int) Math.pow(2, input.size())];
		for(int i=0;i<input.size();i++) {
			System.arraycopy(input.get(i).toBytes(), 0, bytes, i * 2, 2);
		}

		int temp = ModbusUtil.registersToInt(bytes);
		if(temp == -1) {
			return -1;
		}
		return Float.intBitsToFloat(temp);
	}
	
	protected static float toFloatSwap(List<InputRegister>input) {
		byte[] bytes = new byte[(int) Math.pow(2, input.size())];
		for(int i=input.size()-1; i>=0 ;i--) {
			System.arraycopy(input.get(i).toBytes(), 0, bytes, ((input.size() - 1) - i) * 2, 2);
		}

		int temp = ModbusUtil.registersToInt(bytes);
		return Float.intBitsToFloat(temp);
	}
	
	
	protected static long toInt32bit(InputRegister first, InputRegister second) {
		byte[] bytes = new byte[4];
		System.arraycopy(first.toBytes(), 0, bytes, 0, 2);
		System.arraycopy(second.toBytes(), 0, bytes, 2, 2);

		return ModbusUtil.registersToInt(bytes);
	}

	protected static float toFloat32bit(InputRegister first, InputRegister second) {
		byte[] bytes = new byte[4];
		System.arraycopy(second.toBytes(), 0, bytes, 0, 2);
		System.arraycopy(first.toBytes(), 0, bytes, 2, 2);

		int temp = ModbusUtil.registersToInt(bytes);
		return Float.intBitsToFloat(temp);
	}

	protected static long toLong64bit(InputRegister first, InputRegister second, InputRegister th, InputRegister four) {
		byte[] bytes = new byte[24];

//		byte[] gg =  {0, 0, 0, 47, 8, 127, 19, 47, 0, 0, 0, 0};
//		ModbusUtil.registersToLong(gg);
//		ModbusUtil.registersToInt(gg);
		//[0, 0, 0, 47, 8, 127, 19, 47, 0, 0, 0, 0]
		System.arraycopy(first.toBytes(), 0, bytes, 0, 2);
		System.arraycopy(second.toBytes(), 0, bytes, 2, 2);
		System.arraycopy(th.toBytes(), 0, bytes, 4, 2);
		System.arraycopy(four.toBytes(), 0, bytes, 6, 2);

		return ModbusUtil.registersToLong(bytes);
	}
	
	protected static float toFloat64bit(InputRegister first, InputRegister second, InputRegister th, InputRegister four) {
		byte[] bytes = new byte[24];

		System.arraycopy(first.toBytes(), 0, bytes, 0, 2);
		System.arraycopy(second.toBytes(), 0, bytes, 2, 2);
		System.arraycopy(th.toBytes(), 0, bytes, 4, 2);
		System.arraycopy(four.toBytes(), 0, bytes, 6, 2);

		int temp = ModbusUtil.registersToInt(bytes);
		return Float.intBitsToFloat(temp);
	}
	

	public static Connection getConnection() throws SQLException {		
		String dbURL = "jdbc:oracle:thin:@192.168.1.17:1521:NURI";
		String username = "aimir";
		String password = "aimiramm";
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		return DriverManager.getConnection(dbURL, username, password);
	} 
	
	protected static modbusAddressModel getModbusAddress(ResultSet rs) throws SQLException {
		modbusAddressModel _oData = new modbusAddressModel();
   	 _oData.setDeviceConfigId(rs.getInt("deviceConfigId"));
   	 _oData.setRegisterType(rs.getInt("registerType"));
   	 _oData.setRegisterOffSet(rs.getInt("registerOffset"));
   	_oData.setMeterData(rs.getString("meterData"));
   	_oData.setDataType(rs.getString("dataType"));
   	_oData.setSizeByte(rs.getInt("sizeByte"));
   	_oData.setFactor(rs.getDouble("factor"));
   	_oData.setByteOrder(rs.getString("byteOrder"));
   	_oData.setChannel(rs.getInt("channel"));
   	_oData.setStatusInsert(rs.getInt("statusInsert"));
       return _oData;
   }
	
	protected static ModbusModel modbusConfigInfi(ResultSet rs) throws SQLException {
		ModbusModel _oData = new ModbusModel();
   	 _oData.setIpAddress(rs.getString("ipAddress"));
   	 _oData.setPort(rs.getInt("port"));
   	_oData.setUnitId(rs.getInt("unitId"));
   	_oData.setMdevId(rs.getString("mdevId"));
   	_oData.setMinutes(rs.getInt("minutes"));
       return _oData;
   }

}
