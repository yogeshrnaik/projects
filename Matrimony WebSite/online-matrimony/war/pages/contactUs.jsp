<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="contents-header">
			<bean:message key="contact_us.contents.heading"/>
		</td>
	</tr>
	<tr>
		<td class="contents">
		<div class="text"
			style="padding-left: 10px; padding-top: 0px">
			<ul type="square" style="padding-left: 10px;">
				<li>
					<b><bean:message key="contact_us.telephone.desc" /></b> 
					<br>
					<bean:message key="contact_us.telephone.number.1" /> 
					<bean:message key="common.text.or" /> 
					<br>
					<bean:message key="contact_us.telephone.number.2" />
					<br>
					<u><bean:message key="contact_us.timings.heading" /></u> 
					<bean:message key="contact_us.timings.from.to" />
					<bean:message key="contact_us.timings.closed" />
					<br><br>
				</li>
				<li>
					<b><bean:message key="contact_us.email.desc" /></b> 
					<a class="link" href='mailto:<bean:message key="contact_us.email.address" />'>
						<bean:message key="contact_us.email.address" />
					</a>
					<br>
					<bean:message key="contact_us.email.note" />
					<br><br>
				</li>
				<li>
					<b><bean:message key="contact_us.postal_address.desc" /></b> <br>
					<bean:message key="postal_address.line.1" /> <br>
					<bean:message key="postal_address.line.2" /> <br>
					<bean:message key="postal_address.line.3" />
				</li>
			</ul>
		</div>
		</td>
	</tr>
</table>