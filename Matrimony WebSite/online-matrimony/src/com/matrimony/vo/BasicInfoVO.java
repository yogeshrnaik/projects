package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.utils.Utils;
import com.matrimony.vo.enums.OptionsEnum;
import com.matrimony.vo.enums.Diet;
import com.matrimony.vo.enums.Gender;
import com.matrimony.vo.enums.MaritalStatus;
import com.matrimony.vo.enums.PetPreference;

@PersistenceCapable
@EmbeddedOnly
public class BasicInfoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/*

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;

	@Persistent
	private Gender gender = Gender.MALE;

	@Persistent
	private MaritalStatus maritalStatus = MaritalStatus.NO_ANSWER;

	@Persistent
	private OptionsEnum childrenStatus = OptionsEnum.NO_ANSWER;

	@Persistent
	private Date birthDate;

	@Persistent
	private int birthHour;

	@Persistent
	private int birthMin;

	@Persistent
	private String birthPlace;

	@Persistent
	private String knownLanguages;

	@Persistent
	private Diet diet = Diet.NO_ANSWER;

	@Persistent
	private PetPreference petPreference = PetPreference.NO_ANSWER;

	public BasicInfoVO() {

	}

	public String getName() {
	return firstName + " " + lastName;
	}

	public String getAge() {
	// calculate age and return
	return Utils.getAgeInYearsAndMonths(birthDate);
	}

	public String getFirstName() {
	return firstName;
	}

	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	public String getLastName() {
	return lastName;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public Gender getGender() {
	return gender;
	}

	public void setGender(Gender gender) {
	this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
	return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
	this.maritalStatus = maritalStatus;
	}

	public OptionsEnum getChildrenStatus() {
	return childrenStatus;
	}

	public void setChildrenStatus(OptionsEnum childrenStatus) {
	this.childrenStatus = childrenStatus;
	}

	public Date getBirthDate() {
	return birthDate;
	}

	public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
	}

	public int getBirthHour() {
	return birthHour;
	}

	public void setBirthHour(int birthHour) {
	this.birthHour = birthHour;
	}

	public int getBirthMin() {
	return birthMin;
	}

	public void setBirthMin(int birthMin) {
	this.birthMin = birthMin;
	}

	public String getBirthPlace() {
	return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
	this.birthPlace = birthPlace;
	}

	public String getKnownLanguages() {
	return knownLanguages;
	}

	public void setKnownLanguages(String knownLanguages) {
	this.knownLanguages = knownLanguages;
	}

	public Diet getDiet() {
	return diet;
	}

	public void setDiet(Diet diet) {
	this.diet = diet;
	}

	public PetPreference getPetPreference() {
	return petPreference;
	}

	public void setPetPreference(PetPreference petPreference) {
	this.petPreference = petPreference;
	}

	*/
}
