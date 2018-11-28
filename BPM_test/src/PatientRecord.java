/**
 * 
 */

/**
 * @author lines
 *
 */
public class PatientRecord {
    
	Integer eventNum;
	Long caseIdentifier;
	Integer	unixStart;
	Integer	unixEnd;
	String	activityName;
	String	startDate;
	String	endDate;
	Integer bloodDrawCount;
	Double remainingTime;
	
	public Integer getEventNum() {
		return eventNum;
	}
	public void setEventNum(Integer eventNum) {
		this.eventNum = eventNum;
	}
	public Long getCaseIdentifier() {
		return caseIdentifier;
	}
	public void setCaseIdentifier(Long caseIdentifier) {
		this.caseIdentifier = caseIdentifier;
	}
	public Integer getUnixStart() {
		return unixStart;
	}
	public void setUnixStart(Integer unixStart) {
		this.unixStart = unixStart;
	}
	public Integer getUnixEnd() {
		return unixEnd;
	}
	public void setUnixEnd(Integer unixEnd) {
		this.unixEnd = unixEnd;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getBloodDrawCount() {
		return bloodDrawCount;
	}
	public void setBloodDrawCount(Integer bloodDrawCount) {
		this.bloodDrawCount = bloodDrawCount;
	}
	public Double getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(Double remainingTime) {
		this.remainingTime = remainingTime;
	}
	
	
}
