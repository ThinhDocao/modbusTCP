package mavenproject;

import java.time.LocalDateTime;

public class ConfigMeterEventLog {

	private LocalDateTime startDate;
	private Integer minutes;
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	
}
