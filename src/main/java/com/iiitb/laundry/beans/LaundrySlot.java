package com.iiitb.laundry.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class LaundrySlot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer slotId;
	
	@NotNull
	private String startTime;
	
	@NotNull
	private String endTime;
	
	@NotNull
	private Integer noOfMachines;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Hostel hostel;

	public Integer getSlotId() {
		return slotId;
	}

	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}

	public Integer getNoOfMachines() {
		return noOfMachines;
	}

	public void setNoOfMachines(Integer noOfMachines) {
		this.noOfMachines = noOfMachines;
	}

	public Hostel getHostel() {
		return hostel;
	}

	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}

