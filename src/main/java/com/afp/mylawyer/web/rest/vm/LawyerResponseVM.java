package com.afp.mylawyer.web.rest.vm;

import java.util.List;

import com.afp.mylawyer.service.dto.LawyerDTO;

public class LawyerResponseVM extends ResponseVM {
	
	private List<LawyerDTO> lawyers;

	public List<LawyerDTO> getLawyers() {
		return lawyers;
	}

	public void setLawyers(List<LawyerDTO> lawyers) {
		this.lawyers = lawyers;
	}

}
