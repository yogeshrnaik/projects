package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.utils.Utils;
import com.matrimony.vo.enums.Addiction;
import com.matrimony.vo.enums.BloodGroup;
import com.matrimony.vo.enums.Build;
import com.matrimony.vo.enums.Diet;
import com.matrimony.vo.enums.Gender;
import com.matrimony.vo.enums.Looks;
import com.matrimony.vo.enums.MaritalStatus;
import com.matrimony.vo.enums.OptionsEnum;
import com.matrimony.vo.enums.PetPreference;
import com.matrimony.vo.enums.SkinTone;
import com.matrimony.vo.enums.SpecialCondition;

@PersistenceCapable
@EmbeddedOnly
public class PersonalInfoVO implements Serializable {

	private static final long serialVersionUID = 8176483828025999544L;

	@Persistent
	private String piFirstName;

	@Persistent
	private String piLastName;

	@Persistent
	private Gender piGender = Gender.MALE;

	@Persistent
	private MaritalStatus piMaritalStatus = MaritalStatus.NO_ANSWER;

	@Persistent
	private OptionsEnum piChildrenStatus = OptionsEnum.NO_ANSWER;

	@Persistent
	private Date piBirthDate;

	@Persistent
	private String piKnownLanguages;

	@Persistent
	private Diet piDiet = Diet.NO_ANSWER;

	@Persistent
	private PetPreference piPetPreference = PetPreference.NO_ANSWER;

	@Persistent
	private String piAboutMe;

	@Persistent
	private int piHeight = 0; // in centi-meters

	@Persistent
	private int piWeight = 0;

	@Persistent
	private Build piBuild = Build.NO_ANSWER;

	@Persistent
	private SkinTone piSkinTone = SkinTone.NO_ANSWER;

	@Persistent
	private Looks piLooks = Looks.NO_ANSWER;

	@Persistent
	private BloodGroup piBloodGroup = BloodGroup.NO_ANSWER;

	@Persistent
	private OptionsEnum piUseSpecs = OptionsEnum.NO_ANSWER;

	@Persistent
	private Addiction piSmoking = Addiction.NO_ANSWER;

	@Persistent
	private Addiction piDrinking = Addiction.NO_ANSWER;

	@Persistent
	private SpecialCondition piSpecialCondition = SpecialCondition.NO_ANSWER;

	@Persistent
	private String piThingsInMyBedRoom;

	@Persistent
	private String piMyIdeaOfFirstDate;

	@Persistent
	private String piThingsICantLiveWithout;

	@Persistent
	private String piIdealSoulMate;

	@Persistent
	private String piHobbies;

	@Persistent
	private Date piLastChangedOn = null;

	public PersonalInfoVO() {

	}

	public PersonalInfoVO copy(PersonalInfoVO personal) {
		this.piFirstName = personal.piFirstName;
		this.piLastName = personal.piLastName;
		this.piGender = personal.piGender;
		this.piMaritalStatus = personal.piMaritalStatus;
		this.piChildrenStatus = personal.piChildrenStatus;
		this.piBirthDate = personal.piBirthDate;
		this.piKnownLanguages = personal.piKnownLanguages;
		this.piDiet = personal.piDiet;
		this.piPetPreference = personal.piPetPreference;
		this.piAboutMe = personal.piAboutMe;
		this.piHeight = personal.piHeight;
		this.piWeight = personal.piWeight;
		this.piBuild = personal.piBuild;
		this.piSkinTone = personal.piSkinTone;
		this.piLooks = personal.piLooks;
		this.piBloodGroup = personal.piBloodGroup;
		this.piUseSpecs = personal.piUseSpecs;
		this.piSmoking = personal.piSmoking;
		this.piDrinking = personal.piDrinking;
		this.piSpecialCondition = personal.piSpecialCondition;
		this.piThingsInMyBedRoom = personal.piThingsInMyBedRoom;
		this.piMyIdeaOfFirstDate = personal.piMyIdeaOfFirstDate;
		this.piThingsICantLiveWithout = personal.piThingsICantLiveWithout;
		this.piIdealSoulMate = personal.piIdealSoulMate;
		this.piHobbies = personal.piHobbies;
		this.piLastChangedOn = personal.piLastChangedOn;
		return this;
	}

	public String getName() {
		return piFirstName + " " + piLastName;
	}

	public String getAge() {
		// calculate age and return
		return Utils.getAgeInYearsAndMonths(piBirthDate);
	}

	public String getFirstName() {
		return piFirstName;
	}

	public void setFirstName(String firstName) {
		this.piFirstName = firstName;
	}

	public String getLastName() {
		return piLastName;
	}

	public void setLastName(String lastName) {
		this.piLastName = lastName;
	}

	public Gender getGender() {
		return piGender;
	}

	public void setGender(Gender gender) {
		this.piGender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return piMaritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.piMaritalStatus = maritalStatus;
	}

	public String getMaritalStatusCode() {
		return piMaritalStatus.getStatus();
	}

	public void setMaritalStatusCode(String status) {
		this.piMaritalStatus = MaritalStatus.getMaritalStatus(status);
	}

	public OptionsEnum getChildrenStatus() {
		return piChildrenStatus;
	}

	public void setChildrenStatus(OptionsEnum childrenStatus) {
		this.piChildrenStatus = childrenStatus;
	}

	public String getChildrenStatusCode() {
		return piChildrenStatus.getOption();
	}

	public void setChildrenStatusCode(String code) {
		this.piChildrenStatus = OptionsEnum.getOption(code);
	}

	public Date getBirthDate() {
		return piBirthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.piBirthDate = birthDate;
	}

	public String getKnownLanguages() {
		return piKnownLanguages;
	}

	public void setKnownLanguages(String knownLanguages) {
		this.piKnownLanguages = knownLanguages;
	}

	public Diet getDiet() {
		return piDiet;
	}

	public void setDiet(Diet diet) {
		this.piDiet = diet;
	}

	public String getDietCode() {
		return piDiet.getDietType();
	}

	public void setDietCode(String code) {
		this.piDiet = Diet.getDiet(code);
	}

	public PetPreference getPetPreference() {
		return piPetPreference;
	}

	public void setPetPreference(PetPreference petPreference) {
		this.piPetPreference = petPreference;
	}

	public String getPetPreferenceCode() {
		return piPetPreference.getPreference();
	}

	public void setPetPreferenceCode(String pref) {
		this.piPetPreference = PetPreference.getPreference(pref);
	}

	public String getAboutMe() {
		return piAboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.piAboutMe = aboutMe;
	}

	public int getHeight() {
		return piHeight;
	}

	public void setHeight(int height) {
		this.piHeight = height;
	}

	public int getWeight() {
		return piWeight;
	}

	public void setWeight(int weight) {
		this.piWeight = weight;
	}

	public Build getBuild() {
		return piBuild;
	}

	public void setBuild(Build build) {
		this.piBuild = build;
	}

	public String getBuildCode() {
		return piBuild.getCode();
	}

	public void setBuildCode(String build) {
		this.piBuild = Build.getBuild(build);
	}

	public SkinTone getSkinTone() {
		return piSkinTone;
	}

	public void setSkinTone(SkinTone skinTone) {
		this.piSkinTone = skinTone;
	}

	public String getSkinToneCode() {
		return piSkinTone.getTone();
	}

	public void setSkinToneCode(String tone) {
		this.piSkinTone = SkinTone.getSkinTone(tone);
	}

	public Looks getLooks() {
		return piLooks;
	}

	public void setLooks(Looks looks) {
		this.piLooks = looks;
	}

	public String getLooksCode() {
		return piLooks.getLookType();
	}

	public void setLooksCode(String code) {
		this.piLooks = Looks.getLooks(code);
	}

	public BloodGroup getBloodGroup() {
		return piBloodGroup;
	}

	public void setBloodGroup(BloodGroup bloodGroup) {
		this.piBloodGroup = bloodGroup;
	}

	public String getBloodGroupCode() {
		return piBloodGroup.getGroup();
	}

	public void setBloodGroupCode(String grp) {
		this.piBloodGroup = BloodGroup.getBloodGroup(grp);
	}

	public OptionsEnum getUseSpecs() {
		return piUseSpecs;
	}

	public void setUseSpecs(OptionsEnum useSpecs) {
		this.piUseSpecs = useSpecs;
	}

	public String getUseSpecsCode() {
		return piUseSpecs.getOption();
	}

	public void setUseSpecsCode(String code) {
		this.piUseSpecs = OptionsEnum.getOption(code);
	}

	public Addiction getSmoking() {
		return piSmoking;
	}

	public void setSmoking(Addiction smoking) {
		this.piSmoking = smoking;
	}

	public String getSmokingCode() {
		return piSmoking.getCode();
	}

	public void setSmokingCode(String code) {
		this.piSmoking = Addiction.getAddiction(code);
	}

	public Addiction getDrinking() {
		return piDrinking;
	}

	public void setDrinking(Addiction drinking) {
		this.piDrinking = drinking;
	}

	public String getDrinkingCode() {
		return piDrinking.getCode();
	}

	public void setDrinkingCode(String code) {
		this.piDrinking = Addiction.getAddiction(code);
	}

	public SpecialCondition getSpecialCondition() {
		return piSpecialCondition;
	}

	public void setSpecialCondition(SpecialCondition specialCondition) {
		this.piSpecialCondition = specialCondition;
	}

	public String getSpecialConditionCode() {
		return piSpecialCondition.getCode();
	}

	public void setSpecialConditionCode(String code) {
		this.piSpecialCondition = SpecialCondition.getSpecialCondition(code);
	}

	public String getThingsInMyBedRoom() {
		return piThingsInMyBedRoom;
	}

	public void setThingsInMyBedRoom(String thingsInMyBedRoom) {
		this.piThingsInMyBedRoom = thingsInMyBedRoom;
	}

	public String getMyIdeaOfFirstDate() {
		return piMyIdeaOfFirstDate;
	}

	public void setMyIdeaOfFirstDate(String myIdeaOfFirstDate) {
		this.piMyIdeaOfFirstDate = myIdeaOfFirstDate;
	}

	public String getThingsICantLiveWithout() {
		return piThingsICantLiveWithout;
	}

	public void setThingsICantLiveWithout(String thingsICantLiveWithout) {
		this.piThingsICantLiveWithout = thingsICantLiveWithout;
	}

	public String getIdealSoulMate() {
		return piIdealSoulMate;
	}

	public void setIdealSoulMate(String idealSoulMate) {
		this.piIdealSoulMate = idealSoulMate;
	}

	public String getHobbies() {
		return piHobbies;
	}

	public void setHobbies(String hobbies) {
		this.piHobbies = hobbies;
	}

	public Date getLastChangedOn() {
		return piLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.piLastChangedOn = lastChangedOn;
	}
}
