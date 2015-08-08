<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.matrimony.constants.Constants"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			My Profile
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="#" id="side_menu_quickSearch" class="side-menu-link">
				Quick Search
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="#" id="side_menu_quickSearch" class="side-menu-link">
				Advanced Search
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="#" id="side_menu_quickSearch" class="side-menu-link">
				My Choice
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="#" id="side_menu_quickSearch" class="side-menu-link">
				Who choose me?
			</a>
		</td>
	</tr>
	<tr>
		<td class="menu-separator"></td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=viewMyProfile" id="side_menu_viewMyProfile" class="side-menu-link">
				<bean:message key="menu.item.my_profile.view"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=personalInfo" id="side_menu_personalInfo" class="side-menu-link">
				<bean:message key="menu.item.personal.info"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=mySoulMate" id="side_menu_mySoulMate" class="side-menu-link">
				<bean:message key="menu.item.my_soul_mate.info"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=myContact" id="side_menu_myContact" class="side-menu-link">
				<bean:message key="menu.item.my_contact"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=familyRelatives" id="side_menu_familyRelatives" class="side-menu-link">
				<bean:message key="menu.item.family.relatives"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=educationCareer" id="side_menu_educationCareer" class="side-menu-link">
				<bean:message key="menu.item.education.career"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" nowrap>
			<a href="/MyProfile.do?action=view&reload=true&sm=myInterests" id="side_menu_myInterests" class="side-menu-link">
				<bean:message key="menu.item.interests"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=profilePhoto" id="side_menu_profilePhoto" class="side-menu-link">
				<bean:message key="menu.item.profile.photo"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=horoscope" id="side_menu_horoscope" class="side-menu-link">
				<bean:message key="menu.item.horoscope"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=mySettings" id="side_menu_mySettings" class="side-menu-link">
				<bean:message key="menu.item.settings"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item">
			<a href="/MyProfile.do?action=view&reload=true&sm=changePassword" id="side_menu_changePassword" class="side-menu-link">
				<bean:message key="menu.item.change.password"/>
			</a>
		</td>
	</tr>
</table>

<script>
	var currSideMenu = document.getElementById("side_menu_" + "<%=session.getAttribute(Constants.CURRENT_SIDE_MENU)%>");
	if (currSideMenu)
	{
		currSideMenu.className = currSideMenu.className + " selected";
		// currSideMenu = document.getElementById("side_menu_aboutUs");
	}
</script>
