package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.vo.enums.ProfileStatus;

@PersistenceCapable
@EmbeddedOnly
public class MatrimonyProfileVO implements Serializable {

	private static final long serialVersionUID = -3367539306873159182L;

	@Persistent
	private Date mpRegistrationDate;

	@Persistent
	private Date mpMembershipExpiryDate;

	@Persistent
	private ProfileStatus mpProfileStatus = ProfileStatus.PAYMENT_PENDING;

	// @Persistent
	// private BasicInfoVO basicInfo;

	@Persistent
	private PersonalInfoVO mpPersonal;

	@Persistent
	private SoulMateVO mpSoulMate;

	@Persistent
	private FamilyInfoVO mpFamily;

	@Persistent
	private EducationCareerVO mpEducationCareer;

	@Persistent
	private ContactDetailsVO mpContact;

	@Persistent
	private HoroscopeVO mpHoroscope;

	@Persistent
	private PhotoVO mpPhotoVO;

	public MatrimonyProfileVO() {
		mpRegistrationDate = new Date();
		// basicInfo = new BasicInfoVO();
		mpPersonal = new PersonalInfoVO();
		mpSoulMate = new SoulMateVO();
		mpFamily = new FamilyInfoVO();
		mpEducationCareer = new EducationCareerVO();
		mpContact = new ContactDetailsVO();
		mpHoroscope = new HoroscopeVO();
		mpPhotoVO = new PhotoVO();
	}

	public MatrimonyProfileVO copy(MatrimonyProfileVO profile) {
		this.mpContact.copy(profile.mpContact);
		this.mpEducationCareer.copy(profile.mpEducationCareer);
		this.mpFamily.copy(profile.mpFamily);
		this.mpHoroscope.copy(profile.mpHoroscope);
		this.mpMembershipExpiryDate = profile.mpMembershipExpiryDate;
		this.mpPersonal.copy(profile.mpPersonal);
		this.mpProfileStatus = profile.mpProfileStatus;
		this.mpRegistrationDate = profile.mpRegistrationDate;
		this.mpSoulMate.copy(profile.mpSoulMate);
		this.mpPhotoVO.copy(profile.mpPhotoVO);
		return this;
	}

	public Date getRegistrationDate() {
		return mpRegistrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.mpRegistrationDate = registrationDate;
	}

	public Date getMembershipExpiryDate() {
		return mpMembershipExpiryDate;
	}

	public void setMembershipExpiryDate(Date membershipExpiryDate) {
		this.mpMembershipExpiryDate = membershipExpiryDate;
	}

	public ProfileStatus getProfileStatus() {
		return mpProfileStatus;
	}

	public void setProfileStatus(ProfileStatus profileStatus) {
		this.mpProfileStatus = profileStatus;
	}

	// public BasicInfoVO getBasicInfo() {
	// return basicInfo;
	// }
	//
	// public void setBasicInfo(BasicInfoVO basic) {
	// this.basicInfo = basic;
	// }

	public PersonalInfoVO getPersonal() {
		return mpPersonal;
	}

	public void setPersonal(PersonalInfoVO personal) {
		this.mpPersonal = personal;
	}

	public SoulMateVO getSoulMate() {
		return mpSoulMate;
	}

	public void setSoulMate(SoulMateVO soulMate) {
		this.mpSoulMate = soulMate;
	}

	public FamilyInfoVO getFamily() {
		return mpFamily;
	}

	public void setFamily(FamilyInfoVO family) {
		this.mpFamily = family;
	}

	public EducationCareerVO getEducationCareer() {
		return mpEducationCareer;
	}

	public void setEducationCareer(EducationCareerVO educationCareer) {
		this.mpEducationCareer = educationCareer;
	}

	public ContactDetailsVO getContact() {
		return mpContact;
	}

	public void setContact(ContactDetailsVO contact) {
		this.mpContact = contact;
	}

	public HoroscopeVO getHoroscope() {
		return mpHoroscope;
	}

	public void setHoroscope(HoroscopeVO horoscope) {
		this.mpHoroscope = horoscope;
	}

	public PhotoVO getPhotoVO() {
		return mpPhotoVO;
	}

	public void setPhotoVO(PhotoVO photoVO) {
		this.mpPhotoVO = photoVO;
	}

	// public String toString() {
	// StringBuilder result = new StringBuilder();
	// result.append("birthDate = ").append(birthDate);
	// return result.toString();
	// }
}
