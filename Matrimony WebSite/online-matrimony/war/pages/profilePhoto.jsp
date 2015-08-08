<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="com.matrimony.vo.enums.UserAttributes"%>
<%@page import="com.matrimony.forms.UserForm"%>
<%@page import="com.matrimony.constants.Constants"%>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	String uploadURL = blobstoreService.createUploadUrl("/upload");
	
	UserForm userForm = (UserForm)session.getAttribute("userForm");
	String profilePhotoBlobKey = userForm.getUser().getMatrimonyProfile().getPhotoVO().getProfilePhotoBlobKey();
	String profilePhotoURL = "/serve?bk=" + profilePhotoBlobKey + "&tn=false";
	
%>

<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			My Profile Photo
		</td>
	</tr>
	<tr>
		<td class="contents">
			<logic:notEmpty name="<%=Constants.SAVE_OK_MSG%>" scope="request">
				<div id="SuccessMessage" class="SuccessMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.SAVE_OK_MSG%>"/>
				</div>
			</logic:notEmpty>				
	        <logic:notEmpty name="<%=Constants.SAVE_NOT_OK_MSG%>">
				<div id="SaveErrorMessage" class="ErrorMessage" style="padding-top: 10px; padding-bottom: 10px;">
					<bean:write name="<%=Constants.SAVE_NOT_OK_MSG%>"/>
				</div>
			</logic:notEmpty>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<form action="<%= uploadURL %>" method="post" enctype="multipart/form-data">
							<input type="file" name="myProfilePhoto" accept="image/*" size="80">
							<input type="submit" value="  Upload  " class="FormButton">
						</form>
					</td>
				</tr>
				<tr><td><br></td></tr>
				<tr>
					<td class="profile_info" valign="middle" width="15%" align="left">
						<logic:empty name="userForm" property="user.matrimonyProfile.photoVO.profilePhotoBlobKey">
							<img alt="Upload Photo" src="/images/NoPicture.gif" border="0">
						</logic:empty>
						<logic:notEmpty name="userForm" property="user.matrimonyProfile.photoVO.profilePhotoBlobKey">
							<img src="<%= profilePhotoURL %>" border="0">
						</logic:notEmpty>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>