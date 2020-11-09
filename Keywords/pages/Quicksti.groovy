package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.concurrent.locks.Condition

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement


import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utilities.DynamicLocators
import utilities.SafeActions

public class Quicksti {

	SafeActions safe=new SafeActions()


	// Entering payment amount details like amount and quantity


	// Entering user personal details like name, card number
	@Keyword
	def setUserPaymentDetails(String firstName,String lastName,String cardNum){

		//	String inputXpath=DynamicLocators.inputFieldLocator.replace("<id>","txtFirstName")

		safe.safeType(	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/FIRST_NAME')
				, firstName, 'FirstName', (([
					GlobalVariable.pageLoadTime]) as int[]))



		safe.safeType( 	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/LAST_NAME')
				, lastName, 'LAstName', (([
					GlobalVariable.pageLoadTime]) as int[]))

		safe.safeType( 	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/CARD_NUMBER')
				, cardNum, 'CardNum', (([
					GlobalVariable.pageLoadTime]) as int[]))
		String cardNumber=null;

		if(cardNum.startsWith("3")){
			cardNumber=cardNum.substring(11)
		}
		else {
			cardNumber=cardNum.substring(12)
		}

		return cardNumber;

	}
	@Keyword
	def setPaymentAmountDetailsForSinglePayment(String amount,String quantity){

		safe.safeType(	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/PAYMENT_AMOUNT')
				, amount, 'amount', (([
					GlobalVariable.pageLoadTime]) as int[]))

		safe.safeType(	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/QUANTITY')
				, quantity, 'Quantity', (([
					GlobalVariable.pageLoadTime]) as int[]))

	}

	// set expiration date of credit or debit card
	@Keyword
	def setExpDate(String sExpMonth,String sExpYear,String securityCode){

		safe.safeSelectOptionInDropdownByVisibleText(findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/EXP_MONTH'), sExpMonth, 'expMonth', (([
			GlobalVariable.pageLoadTime]) as int[]))


		safe.safeSelectOptionInDropdownByVisibleText(findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/EXP_YEAR'), sExpYear,' expYear', (([
			GlobalVariable.pageLoadTime]) as int[]))

		safe.safeType(findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Information_Page/SECURITY_CODE'), securityCode, 'securityCode',  (([
			GlobalVariable.pageLoadTime]) as int[]))
	}



	// verification of entered  user details
	@Keyword
	def verifyUserPaymentDetails(String paymentAmount,String firstName,String lastName,String cardNumber){

		String amount=WebUI.getText(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/AMOUNT'))
		String actualAmount=amount.substring(1)
		//	println(actualAmount)
		WebUI.verifyMatch(actualAmount, paymentAmount, true,FailureHandling.STOP_ON_FAILURE)


		String fullName=firstName +" "+lastName;
		//println(fullName)
		String name=WebUI.getText(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/NAME'))
		WebUI.verifyMatch(name, fullName, true,FailureHandling.STOP_ON_FAILURE)

		String cardNum=WebUI.getText(	findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/CARD_NUMBER'))
		String lastNum=cardNum.substring(3)


		WebUI.verifyMatch(lastNum, cardNumber, true,FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def setAmountDetailsForMultiBureau(String index,String paymentType,String bureauNum,String referenceNum,String quantity){
		//Payment type
		String paymentTypeXpath ="//select[@id='Opt_00"+bureauNum+"_0"+index+"']"
		TestObject PaymentType=new TestObject()
		PaymentType.addProperty("xpath",ConditionType.EQUALS,paymentTypeXpath)
		safe.safeSelectOptionInDropdownByVisibleText(PaymentType, paymentType, 'Payment Type', (([
			GlobalVariable.pageLoadTime]) as int[]))

		//Reference Number
		String refXpath="//input[@id='Inv_00"+bureauNum+"_0"+index+"']"
		TestObject referenceNumber=new TestObject()
		referenceNumber.addProperty("xpath", ConditionType.EQUALS, refXpath)
		safe.safeType(referenceNumber, referenceNum, 'RefrenceNumber', (([
			GlobalVariable.pageLoadTime]) as int[]))



		// Quantity
		String quantityXpath="//input[@id='Qty_00"+bureauNum+"_0"+index+"']";
		TestObject Quantity =new TestObject()
		Quantity.addProperty("xpath", ConditionType.EQUALS, quantityXpath)
		safe.safeType(Quantity, quantity, 'Quntity', (([
			GlobalVariable.pageLoadTime]) as int[]))

	}
	@Keyword
	def setCommnets(String bureauNum,String index,String sComments){
		String commentXpath="//input[@id='Com_00"+bureauNum+"_0"+index+"']";
		TestObject comment=new TestObject()
		comment.addProperty("xpath", ConditionType.EQUALS, commentXpath)
		safe.safeType(comment, sComments, 'Comments', (([
			GlobalVariable.pageLoadTime]) as int[]))

	}

	//verification of Payment Approval
	@Keyword
	def verifyPaymentApproval(TestObject testObject){

		String paymentId=WebUI.getText(	findTestObject('Object Repository/CERTPAY_QUICKSTI/Payment_Details_Verification_Page/PAYMENT_ID'))

		if(WebUI.verifyElementPresent(	testObject, 30))
		{
			KeywordUtil.markPassed("Payment is approved")
			//WebUI.executeJavaScript("alert('Payment is approved')", null)
			Thread.sleep(2000);
		}
		else
		{
			KeywordUtil.markFailed("Payment is not approved")
			//WebUI.executeJavaScript("alert('Payment is not approved')", null)
		}
		println paymentId;
		return paymentId;
	}
	@Keyword
	def getAttributeValue(TestObject object){

		def attributeValue=WebUI.getAttribute(object,'value')
		println attributeValue;
		return attributeValue;

	}

	
	
//	@Keyword
//	def TakeScreenShot(TestObject object){
//
//		WebElement element = WebUiCommonHelper.findWebElement(object, 20)
//		WebDriver driver = DriverFactory.getWebDriver();
//		ScreenShot screenshot = new AShot().takeScreenShot(driver,element);
//		ImageIO.write(screenshot.getImage(),"PNG",new File())
//		
//	}

	
	
	
	
	
	
}