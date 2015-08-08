<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.matrimony.constants.Constants"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header" colspan="2">
			<bean:message key="home.menu.heading"/>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=aboutUs" id="side_menu_aboutUs" class="side-menu-link">
				<bean:message key="menu.item.about_us"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=membership" id="side_menu_membership" class="side-menu-link">
				<bean:message key="menu.item.membership"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=features" id="side_menu_features" class="side-menu-link">
				<bean:message key="menu.item.features"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=guidelines" id="side_menu_guidelines" class="side-menu-link">
				<bean:message key="menu.item.guidelines"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=faqs" id="side_menu_faqs" class="side-menu-link">
				<bean:message key="menu.item.faqs"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=privacy" id="side_menu_privacy" class="side-menu-link">
				<bean:message key="menu.item.privacy"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2" nowrap>
			<a href="/Menu.do?action=view&hm=terms" id="side_menu_terms" class="side-menu-link">
				<bean:message key="menu.item.terms"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=contactUs" id="side_menu_contactUs" class="side-menu-link">
				<bean:message key="menu.item.contact_us"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="side-menu-item" colspan="2">
			<a href="/Menu.do?action=view&hm=siteMap" id="side_menu_siteMap" class="side-menu-link">
				<bean:message key="menu.item.site_map"/>
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

