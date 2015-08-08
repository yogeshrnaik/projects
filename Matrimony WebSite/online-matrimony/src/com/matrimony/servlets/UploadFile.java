package com.matrimony.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.matrimony.constants.Constants;
import com.matrimony.forms.UserForm;
import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.UserAttributes;

public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(UploadFile.class.getName());

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private ImagesService imageService = ImagesServiceFactory.getImagesService();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

		List<BlobKey> blobList = blobs.get("myProfilePhoto");

		if (blobList == null) {
			// save error message in session and redirect to Profile Photo page
			req.getSession(true).setAttribute(Constants.SAVE_NOT_OK_MSG, "Select an image and then click on Upload.");
			// req.setAttribute(Constants.SAVE_NOT_OK_MSG, "Select an image and then click on Upload.");
			String fwdURL = "/MyProfile.do?action=view&" + Constants.SIDE_MENU_PARAM + "="
					+ Constants.PROFILE_PHOTO_MENU;
			res.sendRedirect(fwdURL);
			return;
			// req.getRequestDispatcher(fwdURL).forward(req, res);
		}

		BlobKey blobKey = blobs.get("myProfilePhoto").get(0);

		String profilePhotoBlobKey = blobKey.getKeyString();

		// Does not WORK - get the profilePhotoURL and set that into UserVO from session
		// String profilePhotoURL = imageService.getServingUrl(blobKey);

		// TODO: comment later or use log4j
		log.info("profilePhotoBlobKey = " + profilePhotoBlobKey);

		req.getSession(true).setAttribute(Constants.PROFILE_PHOTO_BLOB_KEY, profilePhotoBlobKey);

		// UserForm userForm = (UserForm) req.getSession(true).getAttribute("userForm");
		// userForm.getUser().getMatrimonyProfile().getPhotoVO().setProfilePhotoBlobKey(profilePhotoBlobKey);
		//
		// UserVO userVO = (UserVO) req.getSession(true).getAttribute(Constants.USER_SESSION_ATTR);
		// userVO.getMatrimonyProfile().getPhotoVO().setProfilePhotoBlobKey(profilePhotoBlobKey);

		// save the UserVO in Data Store by calling MyProfile action
		String redirectURL = "/MyProfile.do?action=save&modifiedUserAttributeCode="
				+ UserAttributes.PROFILE_PHOTO.getName();
		res.sendRedirect(redirectURL);
	}
}
