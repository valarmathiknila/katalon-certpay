package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import utilities.SafeActions

public class Search_Page {
	SafeActions safe=new SafeActions()

	//Payment_Page payment=new Payment_Page()



	@Keyword
	def setSearchDetails(String lastName,String cardNum,String accNumber){
		safe.safeType(findTestObject('Object Repository/SEARCH_PAYMENT/BILLING_LAST_NAME'), lastName, 'LastName', (([
			GlobalVariable.pageLoadTime]) as int[]))
		try{
			if(WebUI.verifyElementChecked(	findTestObject('Object Repository/SEARCH_PAYMENT/CREDIT_OR_DEBIT_CARD'),30,FailureHandling.OPTIONAL	)){

				safe.safeType(  	findTestObject('Object Repository/SEARCH_PAYMENT/CARD_NUMBER'),cardNum , 'CArdNum',  (([
					GlobalVariable.pageLoadTime]) as int[]))
				KeywordUtil.markPassed("Credit card is checked and cardNum is entered ")
			}
			else {
				safe.safeType(	findTestObject('Object Repository/SEARCH_PAYMENT/ACC_NUMBER')
						, accNumber, 'Acc num',  (([
							GlobalVariable.pageLoadTime]) as int[]))
				KeywordUtil.markPassed("Acc num enterd")
			}
		}
		catch(StepFailedException e){
			KeywordUtil.markFailed("Unable to verify element is checked or not")
		}
	}

	@Keyword
	def verifyNoPaymentsFound(){
		boolean flag=true;

		if(	WebUI.verifyElementPresent(findTestObject('Object Repository/SEARCH_PAYMENT/NO_PAYMENTS_FOUND'), 30)){
			KeywordUtil.markPassed("No payments displayed")
			flag=false;

		}
		else {

			KeywordUtil.markFailed("Payments displayed")
			flag=true;

		}
		Thread.sleep(2000);
		return flag;
	}

	//Switch to Another Window
	@Keyword
	def switchToWindow(){
		WebUI.click(findTestObject('Object Repository/SEARCH_PAYMENT/SEARCH_PAYMENT'))
		//		int index=WebUI.getWindowIndex()
		//		String title1=WebUI.getWindowTitle()
		//		println(title1)
		WebUI.switchToWindowIndex(1)

	}
	@Keyword
	def validateApprovedTransactionsInReports(String paymentId,cardtype,String paymentAmount){
		boolean bFlag=true;

		try{
			if(bFlag){
				String paymentid=WebUI.getText(	findTestObject('SEARCH_PAYMENT/REPORTS_PAYMNET_ID'))
				WebUI.verifyMatch(paymentid, paymentId, true,FailureHandling.STOP_ON_FAILURE)
				String cardType=WebUI.getText(	findTestObject('SEARCH_PAYMENT/REPORTS_CARD_TYPE'))
				WebUI.verifyMatch(cardType, cardtype, true, FailureHandling.STOP_ON_FAILURE)
				String amount=WebUI.getText(findTestObject('SEARCH_PAYMENT/REPORTS_AMOUNT'))
				println "Amount from GUI = "+amount
				String sAmount=amount.substring(1)
				println paymentAmount
				WebUI.verifyMatch(sAmount, paymentAmount, true, FailureHandling.STOP_ON_FAILURE)
				KeywordUtil.markPassed("Verified payment details successfully ")
			}
			else{
				KeywordUtil.markFailed("Payment details are not matched")
				bFlag=false;
			}
		}
		catch(Exception e){
			KeywordUtil.markFailed("Unable to verify match")

		}

	}
	def validatePaymentAmount(String PaymentAmount,String ConvFee){
		boolean bFlag=true;

		try{
			if(bFlag){

				String amount=WebUI.getText(findTestObject('CERTPAY_ADMIN/PAYMENT_AMOUNT'))
				println "Amount from GUI = "+amount
				String sAmount=amount.substring(1)
				println PaymentAmount
				WebUI.verifyMatch(sAmount, PaymentAmount, true, FailureHandling.STOP_ON_FAILURE)
				String convfee=WebUI.getText(findTestObject('CERTPAY_ADMIN/CONV_FEE'))
				println "Amount from GUI = "+convfee
				String sConvfee=convfee.substring(1)

				String CFee=ConvFee.substring(1)
				println CFee
				WebUI.verifyMatch(sConvfee, CFee, true, FailureHandling.STOP_ON_FAILURE)
				KeywordUtil.markPassed("Verified payment details successfully ")
			}
			else{
				KeywordUtil.markFailed("Payment details are not matched")
				bFlag=false;
			}
		}
		catch(Exception e){
			KeywordUtil.markFailed("Unable to verify match")

		}

	}
	def validateRefundAmount(String RefundAmount,String ConvFee){
		boolean bFlag=true;

		try{
			if(bFlag){

				String amount=WebUI.getText(findTestObject('CERTPAY_ADMIN/REFUND_AMT'))
				println "RefundAmount from GUI = "+amount
				String sAmount=amount.substring(1)
				println RefundAmount

				WebUI.verifyMatch(sAmount, RefundAmount, true, FailureHandling.STOP_ON_FAILURE)
				String convfee=WebUI.getText(findTestObject('CERTPAY_ADMIN/REFUND_CONV_AMT'))
				println "RefundConvFee from GUI = "+convfee
				String sConvfee=convfee.substring(1)
				println ConvFee

				String sCfee=ConvFee.substring(1)
				WebUI.verifyMatch(sConvfee, sCfee, true, FailureHandling.STOP_ON_FAILURE)
				KeywordUtil.markPassed("Verified Refund payment details successfully")
			}
			else{
				KeywordUtil.markFailed("Payment details are not matched")
				bFlag=false;
			}
		}
		catch(Exception e){
			KeywordUtil.markFailed("Unable to verify match")

		}

	}


}
