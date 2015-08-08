package com.matrimony.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

public class ServeImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private ImagesService imageService = ImagesServiceFactory.getImagesService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BlobKey blobKey = new BlobKey(req.getParameter("bk"));

		boolean thumnail = Boolean.valueOf(req.getParameter("tn"));
		if (thumnail) {
			res.sendRedirect(imageService.getServingUrl(blobKey, 120, false));
		} else {
			res.sendRedirect(imageService.getServingUrl(blobKey));
		}
		// blobstoreService.serve(blobKey, res);
	}
}
