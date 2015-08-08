<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
	<td class="contents-header">
		<bean:message key="membership.contents.heading"/>
	</td>
</tr>
<tr>
	<td class="contents">
		<div class="text" style="padding-top: 5px; padding-left: 10px; padding-bottom: 10px">
	 		<b><bean:message key="membership.label.membership.fee" /></b> 
			<u><bean:message key="membership.fee" /></u><br>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="text" valign="top">
						<img alt="Step 1" src="/images/step1.jpg">
					</td>
					<td class="text">Register on the web site &amp; Create your Matrimonial profile</td>
				</tr>
				<tr>
					<td class="text">
						<img alt="Step 2" src="/images/step2.jpg">
					</td>
					<td class="text">Pay the membership fee by any of the payment options</td>
				</tr>
				<tr>
					<td class="text">
						<img alt="Step 3" src="/images/step3.jpg">
					</td>
					<td class="text">Publish your profile &amp; Search other profiles</td>
				</tr>
				<tr>
					<td class="text" colspan="2">
						<u><bean:message key="membership.label.note" /></u> 
						You can create your complete profile on the web site before making the payment.<br>
						<font color="red">
							You will be able to publish your profile and access other profiles,
							in 3-5 working days after we receive your payment.
						</font>
					</td>
				</tr>
			</table>
	 	</div>
		<div class="para-heading">
			<bean:message key="membership.label.payment.options" />
		</div>
		<div class="text" style="padding-left: 10px; padding-bottom: 10px">
	 		<u><b><bean:message key="membership.label.net.banking.or.bank.deposit" /></b></u><br>
			Deposit Membership Fee in our 
			<b><bean:message key="membership.bank.name" /></b> account. <br>
			
			<bean:message key="membership.label.account.number" /> 
			<b><bean:message key="membership.bank.account.number" /></b> <br>
			
			<bean:message key="membership.label.account.name" /> 
			<bean:message key="membership.bank.account.name" /> <br>
			
			<bean:message key="membership.label.account.type" /> 
			<bean:message key="membership.bank.account.type" /> <br>
			
			<bean:message key="membership.label.ifsc.code" /> 
			<b><bean:message key="membership.bank.ifsc.code" /></b> <br>
			
			<bean:message key="membership.label.micr.code" /> 
			<b><bean:message key="membership.bank.micr.code" /></b> <br>
			
			<bean:message key="membership.label.bank.branch" /> 
			<bean:message key="membership.bank.branch" /> <br><br>
			
			<u><b><bean:message key="membership.label.cheque.demand_draft.money_order" /> </b></u><br>
			Send Cheque / Demand Draft / Money Order payable to 
			<bean:message key="membership.cheque.demand_draft.money_order.payable.to" /> <br> 
			to our address: 
			<u><bean:message key="postal.address.complete" /></u>
			<br><br>
			
			After making the payment, send us e-mail at
			<a class="link" href='mailto:<bean:message key="contact_us.email.address" />'>
				<bean:message key="contact_us.email.address" />
			</a>
			or
			<br>
			Contact us on following numbers: <br>
			<bean:message key="contact_us.telephone.number.1" /> <br>
			<bean:message key="contact_us.telephone.number.2" />  <br>
			<u><bean:message key="contact_us.timings.heading" /></u>
			<bean:message key="contact_us.timings.from.to" />
			<bean:message key="contact_us.timings.closed" />
	 	</div>
	</td>
</tr>
</table>