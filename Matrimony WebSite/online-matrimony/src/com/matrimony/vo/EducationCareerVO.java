package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.vo.enums.Education;
import com.matrimony.vo.enums.Profession;

@PersistenceCapable
@EmbeddedOnly
public class EducationCareerVO implements Serializable {

	private static final long serialVersionUID = -5388819554104359616L;

	@Persistent
	private Education edcEducation = Education.NO_ANSWER;

	@Persistent
	private String edcEducationOther;

	@Persistent
	private String edcEducationDetails;

	@Persistent
	private Profession edcProfession = Profession.NO_ANSWER;

	@Persistent
	private String edcProfessionOther;

	@Persistent
	private String edcProfessionDetails;

	@Persistent
	private String edcYearsOfExperience;

	@Persistent
	private String edcAnnualIncome;

	@Persistent
	private Date edcLastChangedOn = null;

	public EducationCareerVO() {

	}

	public EducationCareerVO copy(EducationCareerVO eduCareer) {
		this.edcEducation = eduCareer.edcEducation;
		this.edcEducationDetails = eduCareer.edcEducationDetails;
		this.edcEducationOther = eduCareer.edcEducationOther;
		this.edcProfession = eduCareer.edcProfession;
		this.edcProfessionDetails = eduCareer.edcProfessionDetails;
		this.edcProfessionOther = eduCareer.edcProfessionOther;
		this.edcYearsOfExperience = eduCareer.edcYearsOfExperience;
		this.edcAnnualIncome = eduCareer.edcAnnualIncome;
		this.edcLastChangedOn = eduCareer.edcLastChangedOn;
		return this;
	}

	public Education getEducation() {
		return edcEducation;
	}

	public void setEducation(Education education) {
		this.edcEducation = education;
	}

	public String getEducationCode() {
		return edcEducation.getCode();
	}

	public void setEducationCode(String code) {
		this.edcEducation = Education.getEducation(code);
	}

	public String getEducationOther() {
		return edcEducationOther;
	}

	public void setEducationOther(String educationOther) {
		this.edcEducationOther = educationOther;
	}

	public String getEducationDetails() {
		return edcEducationDetails;
	}

	public void setEducationDetails(String educationDetails) {
		this.edcEducationDetails = educationDetails;
	}

	public Profession getProfession() {
		return edcProfession;
	}

	public void setProfession(Profession profession) {
		this.edcProfession = profession;
	}

	public String getProfessionCode() {
		return edcProfession.getCode();
	}

	public void setProfessionCode(String code) {
		this.edcProfession = Profession.getProfession(code);
	}

	public String getProfessionOther() {
		return edcProfessionOther;
	}

	public void setProfessionOther(String professionOther) {
		this.edcProfessionOther = professionOther;
	}

	public String getProfessionDetails() {
		return edcProfessionDetails;
	}

	public void setProfessionDetails(String professionDetails) {
		this.edcProfessionDetails = professionDetails;
	}

	public String getYearsOfExperience() {
		return edcYearsOfExperience;
	}

	public void setYearsOfExperience(String yearsOfExperience) {
		this.edcYearsOfExperience = yearsOfExperience;
	}

	public String getAnnualIncome() {
		return edcAnnualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.edcAnnualIncome = annualIncome;
	}

	public Date getLastChangedOn() {
		return edcLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.edcLastChangedOn = lastChangedOn;
	}

}
