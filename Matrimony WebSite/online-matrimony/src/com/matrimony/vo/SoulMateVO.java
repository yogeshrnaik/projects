package com.matrimony.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.vo.enums.Addiction;
import com.matrimony.vo.enums.Build;
import com.matrimony.vo.enums.Diet;
import com.matrimony.vo.enums.Education;
import com.matrimony.vo.enums.Looks;
import com.matrimony.vo.enums.MaritalStatus;
import com.matrimony.vo.enums.OptionsEnum;
import com.matrimony.vo.enums.PetPreference;
import com.matrimony.vo.enums.Profession;
import com.matrimony.vo.enums.SkinTone;
import com.matrimony.vo.enums.SpecialCondition;

@PersistenceCapable
@EmbeddedOnly
public class SoulMateVO implements Serializable {

	private static final long serialVersionUID = 661239787327078400L;

	@Persistent
	private Integer smiAgeMin;

	@Persistent
	private Integer smiAgeMax;

	@Persistent
	private MaritalStatus[] smiMaritalStatus = new MaritalStatus[0];

	@Persistent
	private OptionsEnum[] smiChildrenStatus = new OptionsEnum[0];

	@Persistent
	private Integer smiHeightMin;

	@Persistent
	private Integer smiHeightMax;

	@Persistent
	private Build[] smiBuild = new Build[0];

	@Persistent
	private SkinTone[] smiSkinTone = new SkinTone[0];

	@Persistent
	private Looks[] smiLooks = new Looks[0];

	@Persistent
	private OptionsEnum[] smiUseSpecs = new OptionsEnum[0];

	@Persistent
	private PetPreference[] smiPetPreference = new PetPreference[0];

	@Persistent
	private Education[] smiEducation = new Education[0];

	@Persistent
	private Profession[] smiProfession = new Profession[0];

	@Persistent
	private Integer annualIncome;

	// @Persistent
	// private Integer incomeMax;

	@Persistent
	private Diet[] smiDiet = new Diet[0];

	@Persistent
	private Addiction[] smiSmoking = new Addiction[0];

	@Persistent
	private Addiction[] smiDrinking = new Addiction[0];

	@Persistent
	private SpecialCondition[] smiSpecialCondition = new SpecialCondition[0];

	@Persistent
	private String smiOtherDetails;

	@Persistent
	private Date smiLastChangedOn = null;

	public SoulMateVO() {

	}

	public SoulMateVO copy(SoulMateVO soulMate) {
		this.smiAgeMin = soulMate.smiAgeMin;
		this.smiAgeMax = soulMate.smiAgeMax;
		this.smiMaritalStatus = soulMate.smiMaritalStatus;
		this.smiChildrenStatus = soulMate.smiChildrenStatus;
		this.smiHeightMin = soulMate.smiHeightMin;
		this.smiHeightMax = soulMate.smiHeightMax;
		this.smiBuild = soulMate.smiBuild;
		this.smiSkinTone = soulMate.smiSkinTone;
		this.smiLooks = soulMate.smiLooks;
		this.smiUseSpecs = soulMate.smiUseSpecs;
		this.smiPetPreference = soulMate.smiPetPreference;
		this.smiEducation = soulMate.smiEducation;
		this.smiProfession = soulMate.smiProfession;
		this.annualIncome = soulMate.annualIncome;
		// this.incomeMin = soulMate.incomeMin;
		// this.incomeMax = soulMate.incomeMax;
		this.smiDiet = soulMate.smiDiet;
		this.smiSmoking = soulMate.smiSmoking;
		this.smiDrinking = soulMate.smiDrinking;
		this.smiSpecialCondition = soulMate.smiSpecialCondition;
		this.smiOtherDetails = soulMate.smiOtherDetails;
		this.smiLastChangedOn = soulMate.smiLastChangedOn;
		return this;
	}

	public Integer getAgeMin() {
		return smiAgeMin;
	}

	public void setAgeMin(Integer ageMin) {
		this.smiAgeMin = ageMin;
	}

	public Integer getAgeMax() {
		return smiAgeMax;
	}

	public void setAgeMax(Integer ageMax) {
		this.smiAgeMax = ageMax;
	}

	public String[] getMaritalStatus() {
		return MaritalStatus.toStringArray(smiMaritalStatus);
	}

	public List<MaritalStatus> getMaritalStatusOptions() {
		return Arrays.asList(this.smiMaritalStatus);
		/*List<MaritalStatus> result = new ArrayList<MaritalStatus>();
		for (String s : smiMaritalStatus) {
			result.add(MaritalStatus.getMaritalStatus(s));
		}
		return result;*/
	}

	public void setMaritalStatus(String[] maritalStatus) {
		this.smiMaritalStatus = MaritalStatus.toMaritalStatusArray(maritalStatus);
	}

	public String[] getChildrenStatus() {
		return OptionsEnum.toStringArray(this.smiChildrenStatus);
	}

	public List<OptionsEnum> getChildrenStatusOptions() {
		return Arrays.asList(this.smiChildrenStatus);

		/*List<OptionsEnum> result = new ArrayList<OptionsEnum>();
		for (String s : smiChildrenStatus) {
			result.add(OptionsEnum.getOption(s));
		}
		return result;*/
	}

	public void setChildrenStatus(String[] childrenStatus) {
		this.smiChildrenStatus = OptionsEnum.toOptionsEnumArray(childrenStatus);
	}

	public Integer getHeightMin() {
		return smiHeightMin;
	}

	public void setHeightMin(Integer heightMin) {
		this.smiHeightMin = heightMin;
	}

	public Integer getHeightMax() {
		return smiHeightMax;
	}

	public void setHeightMax(Integer heightMax) {
		this.smiHeightMax = heightMax;
	}

	public String[] getBuild() {
		return Build.toStringArray(this.smiBuild);
	}

	public List<Build> getBuildOptions() {
		return Arrays.asList(this.smiBuild);
		/*List<Build> result = new ArrayList<Build>();
		for (String s : smiBuild) {
			result.add(Build.getBuild(s));
		}
		return result;*/
	}

	public void setBuild(String[] build) {
		this.smiBuild = Build.toBuildArray(build);
	}

	public String[] getSkinTone() {
		return SkinTone.toStringArray(smiSkinTone);
	}

	public List<SkinTone> getSkinToneOptions() {
		return Arrays.asList(this.smiSkinTone);
		/*List<SkinTone> result = new ArrayList<SkinTone>();
		for (String s : smiSkinTone) {
			result.add(SkinTone.getSkinTone(s));
		}
		return result;*/
	}

	public void setSkinTone(String[] skinTone) {
		this.smiSkinTone = SkinTone.toSkinToneArray(skinTone);
	}

	public String[] getLooks() {
		return Looks.toStringArray(this.smiLooks);
	}

	public List<Looks> getLooksOptions() {
		return Arrays.asList(this.smiLooks);
		/*List<Looks> result = new ArrayList<Looks>();
		for (String s : smiLooks) {
			result.add(Looks.getLooks(s));
		}
		return result;*/
	}

	public void setLooks(String[] looks) {
		this.smiLooks = Looks.toLooksArray(looks);
	}

	public String[] getUseSpecs() {
		return OptionsEnum.toStringArray(this.smiUseSpecs);
	}

	public List<OptionsEnum> getUseSpecsOptions() {
		return Arrays.asList(this.smiUseSpecs);
		/*List<OptionsEnum> result = new ArrayList<OptionsEnum>();
		for (String s : smiUseSpecs) {
			result.add(OptionsEnum.getOption(s));
		}
		return result;*/
	}

	public void setUseSpecs(String[] useSpecs) {
		this.smiUseSpecs = OptionsEnum.toOptionsEnumArray(useSpecs);
	}

	public String[] getPetPreference() {
		return PetPreference.toStringArray(this.smiPetPreference);
	}

	public List<PetPreference> getPetPreferenceOptions() {
		return Arrays.asList(this.smiPetPreference);
		/*List<PetPreference> result = new ArrayList<PetPreference>();
		for (String s : smiPetPreference) {
			result.add(PetPreference.getPreference(s));
		}
		return result;*/
	}

	public void setPetPreference(String[] petPreference) {
		this.smiPetPreference = PetPreference.toPetPreferenceArray(petPreference);
	}

	public String[] getEducation() {
		return Education.toStringArray(this.smiEducation);
	}

	public List<Education> getEducationOptions() {
		return Arrays.asList(this.smiEducation);
		/*List<Education> result = new ArrayList<Education>();
		for (String s : smiEducation) {
			result.add(Education.getEducation(s));
		}
		return result;*/
	}

	public void setEducation(String[] education) {
		this.smiEducation = Education.toEducationArray(education);
	}

	public String[] getProfession() {
		return Profession.toStringArray(this.smiProfession);
	}

	public List<Profession> getProfessionOptions() {
		return Arrays.asList(this.smiProfession);
		/*List<Profession> result = new ArrayList<Profession>();
		for (String s : smiProfession) {
			result.add(Profession.getProfession(s));
		}
		return result;*/
	}

	public void setProfession(String[] profession) {
		this.smiProfession = Profession.toProfessionArray(profession);
	}

	/*public Integer getIncomeMin() {
		return incomeMin;
	}

	public void setIncomeMin(Integer incomeMin) {
		this.incomeMin = incomeMin;
	}

	public Integer getIncomeMax() {
		return incomeMax;
	}

	public void setIncomeMax(Integer incomeMax) {
		this.incomeMax = incomeMax;
	}*/

	public Integer getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String[] getDiet() {
		return Diet.toStringArray(this.smiDiet);
	}

	public List<Diet> getDietOptions() {
		return Arrays.asList(this.smiDiet);
		/*List<Diet> result = new ArrayList<Diet>();
		for (String s : smiDiet) {
			result.add(Diet.getDiet(s));
		}
		return result;*/
	}

	public void setDiet(String[] diet) {
		this.smiDiet = Diet.toDietArray(diet);
	}

	public String[] getSmoking() {
		return Addiction.toStringArray(smiSmoking);
	}

	public List<Addiction> getSmokingOptions() {
		return Arrays.asList(this.smiSmoking);
		/*List<Addiction> result = new ArrayList<Addiction>();
		for (String s : smiSmoking) {
			result.add(Addiction.getAddiction(s));
		}
		return result;*/
	}

	public void setSmoking(String[] smoking) {
		this.smiSmoking = Addiction.toAddictionArray(smoking);
	}

	public String[] getDrinking() {
		return Addiction.toStringArray(smiDrinking);
	}

	public List<Addiction> getDrinkingOptions() {
		return Arrays.asList(this.smiDrinking);
		/*List<Addiction> result = new ArrayList<Addiction>();
		for (String s : smiDrinking) {
			result.add(Addiction.getAddiction(s));
		}
		return result;*/
	}

	public void setDrinking(String[] drinking) {
		this.smiDrinking = Addiction.toAddictionArray(drinking);
	}

	public String[] getSpecialCondition() {
		return SpecialCondition.toStringArray(this.smiSpecialCondition);
	}

	public List<SpecialCondition> getSpecialConditionOptions() {
		return Arrays.asList(this.smiSpecialCondition);
		/*List<SpecialCondition> result = new ArrayList<SpecialCondition>();
		for (String s : smiSpecialCondition) {
			result.add(SpecialCondition.getSpecialCondition(s));
		}
		return result;*/
	}

	public void setSpecialCondition(String[] specialCondition) {
		this.smiSpecialCondition = SpecialCondition.toSpecialConditionArray(specialCondition);
	}

	public String getOtherDetails() {
		return smiOtherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.smiOtherDetails = otherDetails;
	}

	public Date getLastChangedOn() {
		return smiLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.smiLastChangedOn = lastChangedOn;
	}
}
