package com.matrimony.services.impl;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserServiceFactory;
import com.matrimony.exceptions.AppException;
import com.matrimony.services.factory.PMF;
import com.matrimony.services.interfaces.UserService;
import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.Gender;

public class UserServiceImpl implements UserService {
	private final com.google.appengine.api.users.UserService gaeUserService = UserServiceFactory.getUserService();

	public UserVO getUserByEmail(String email) throws AppException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			// search based on email
			String query = "select from " + UserVO.class.getName() + " where email == '" + email + "'";
			List<UserVO> usersList = (List<UserVO>) pm.newQuery(query).execute();
			if (usersList.isEmpty()) {
				return null;
			}
			return usersList.get(0);
		} catch (Exception e) {
			throw new AppException("Error while retrieving User with email: '" + email + "'.", e);
		}
	}

	public UserVO persistUser(UserVO userVO) throws AppException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Date now = new Date();
			if (userVO.getId() != null) {
				// update existing user
				UserVO userFromDataStore = pm.getObjectById(UserVO.class, userVO.getId());

				if (!userFromDataStore.getPassword().equals(userVO.getPassword())) {
					// change the password
					userFromDataStore.setPassword(userVO.getPassword());
				}

				if (userFromDataStore.getMatrimonyProfile().getContact().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getContact().getLastChangedOn())) {
					// Contact Info is changed - update Contact VO
					userFromDataStore.getMatrimonyProfile().getContact()
							.copy(userVO.getMatrimonyProfile().getContact());
					userFromDataStore.getMatrimonyProfile().getContact().setLastChangedOn(now);
				}
				if (userFromDataStore.getMatrimonyProfile().getEducationCareer().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getEducationCareer().getLastChangedOn())) {
					// Education Career Info is changed - update Education Career VO
					userFromDataStore.getMatrimonyProfile().getEducationCareer().copy(
							userVO.getMatrimonyProfile().getEducationCareer());
					userFromDataStore.getMatrimonyProfile().getEducationCareer().setLastChangedOn(now);
				}

				if (userFromDataStore.getMatrimonyProfile().getFamily().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getFamily().getLastChangedOn())) {
					// Family Info is changed - update Family VO
					userFromDataStore.getMatrimonyProfile().getFamily().copy(userVO.getMatrimonyProfile().getFamily());
					userFromDataStore.getMatrimonyProfile().getFamily().setLastChangedOn(now);
				}

				if (userFromDataStore.getMatrimonyProfile().getHoroscope().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getHoroscope().getLastChangedOn())) {
					// Horoscope Info is changed - update Horoscope VO
					userFromDataStore.getMatrimonyProfile().getHoroscope().copy(
							userVO.getMatrimonyProfile().getHoroscope());
					userFromDataStore.getMatrimonyProfile().getHoroscope().setLastChangedOn(now);
				}

				if (userFromDataStore.getMatrimonyProfile().getPersonal().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getPersonal().getLastChangedOn())) {
					// Personal Info is changed - update Personal Info VO
					userFromDataStore.getMatrimonyProfile().getPersonal().copy(
							userVO.getMatrimonyProfile().getPersonal());
					userFromDataStore.getMatrimonyProfile().getPersonal().setLastChangedOn(now);
				}

				if (userFromDataStore.getMatrimonyProfile().getSoulMate().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getSoulMate().getLastChangedOn())) {
					// Soul Mate Info is changed - update Soul Mate VO
					userFromDataStore.getMatrimonyProfile().getSoulMate().copy(
							userVO.getMatrimonyProfile().getSoulMate());
					userFromDataStore.getMatrimonyProfile().getSoulMate().setLastChangedOn(now);
				}

				if (userFromDataStore.getMatrimonyProfile().getPhotoVO().getLastChangedOn().before(
						userVO.getMatrimonyProfile().getPhotoVO().getLastChangedOn())) {
					// Photo is changed - update Photo VO
					userFromDataStore.getMatrimonyProfile().getPhotoVO()
							.copy(userVO.getMatrimonyProfile().getPhotoVO());
					userFromDataStore.getMatrimonyProfile().getPhotoVO().setLastChangedOn(now);
				}

				// return the UserVO by copying data from UserVO fetched from Data Store
				// return userVO.copy(userFromDataStore);
				return userFromDataStore;
			} else {
				// new user - set last changed on date
				userVO.setLastChangedOn(now);
				userVO.getMatrimonyProfile().getContact().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getEducationCareer().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getFamily().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getHoroscope().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getPersonal().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getSoulMate().setLastChangedOn(now);
				userVO.getMatrimonyProfile().getPhotoVO().setLastChangedOn(now);

				pm.makePersistent(userVO);
				return userVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Could not save user information.", e);
		} finally {
			pm.close();
		}
	}
	
	public List<UserVO> searchUser() {
//		Query query = new Query("UserVO");
		return null;
	}

	// public UserVO registerUser(String email, String firstName, String lastName, Gender gender, Date birthDate,
	// String password) throws AppException {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
