import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.testdata.ExcelData as ExcelData
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

ExcelData data = findTestData('Data Files/Certpay/SingleBureau_SingleLine')
List<String> paymentIdList=[]
for (def index : (1..data.getRowNumbers())) {
	firstName=data.getValue('FirstName',index)
	lastName=data.getValue('LastName', index)
	expMonth=data.getValue('ExpMonth',index)
	expYear=data.getValue('ExpYear',index)
	securityCode=data.getValue('SecurityCode',index)
	paymentAmount=data.getValue('PaymentAmount',index)
	paymentType=data.getValue('PaymentType',index)
	quantity=data.getValue('Quantity',index)
	comments=data.getValue('Comments',index)
	referenceNum=data.getValue('ReferenceNum',index)
	address=data.getValue('Address',index)
	zipCode=data.getValue('Zipcode',index)
	telephone=data.getValue('Telephone',index)
	emailAddress=data.getValue('EmailAddress',index)
	cardNum=data.getValue('CardNumber',index)
	cardType=data.getValue('CardType',index)
	RefundAmount=data.getValue('RefundAmount',index)
	
'Open Certpay Quicksti application'
CustomKeywords.'utilities.SafeActions.openBrowser'(GlobalVariable.Quicksti_Url, (([GlobalVariable.pageLoadTime]) as int[]))

'Enter \'Bureau code\''
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/BUREAU_CODE'), GlobalVariable.bureau_code, 
    'Bureau code', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter \'Teller Id\''
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/TELLER_ID'), GlobalVariable.teller_id, 
    'Bureau code', (([GlobalVariable.pageLoadTime]) as int[]))

'Click on \'Next\''
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/NEXT_BUTTON'), 'Next button', 
        (([GlobalVariable.pageLoadTime]) as int[]))

' Click on \'Click here to manually enter card details\''
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/MANUAL_ENTRY_BUTTON'), 
    'ManualEntry', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter firstname,lastname,card number'
CardNumber = CustomKeywords.'pages.Quicksti.setUserPaymentDetails'(firstName, lastName,cardNum)

'Select Payment type'
CustomKeywords.'utilities.SafeActions.safeSelectOptionInDropdownByVisibleText'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/PAYMENT_TYPE'), 
   paymentType, 'paymentType', (([GlobalVariable.pageLoadTime]) as int[]))

'ENter exp date,security code'
CustomKeywords.'pages.Quicksti.setExpDate'(expMonth, expYear, securityCode)

'Enter paymnet amount, quantity'
CustomKeywords.'pages.Quicksti.setPaymentAmountDetailsForSinglePayment'(paymentAmount, quantity)

'Enter reference num'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/REFRENCE_NUMBER'), 
    referenceNum, 'Reference Number', (([GlobalVariable.pageLoadTime]) as int[]))

amount_Value = CustomKeywords.'pages.Quicksti.getAttributeValue'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/SUB_TOTAL'))

'Enter Comments'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/COMMENTS'), 'Testing', 
    'Comments', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter address'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/ADDRESS'), address, 
    'Address', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter zip code'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/ZIP_CODE'), zipCode, 
    'zipcode', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter telephone'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/TELEPHONE'), telephone, 
    'Telephone', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter email address'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/EMAIL_ADDRESS'), 
    emailAddress, 'EMAIL address', (([GlobalVariable.pageLoadTime]) as int[]))

'Click on \'Next\''
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/NEXT_BUTTON'), 
    'Next', (([GlobalVariable.pageLoadTime]) as int[]))

'Verify user details'
CustomKeywords.'pages.Quicksti.verifyUserPaymentDetails'(paymentAmount, firstName, lastName, CardNumber)

WebUI.delay(2)

ConvFee=WebUI.getText(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/CONV_FEE'))

WebUI.scrollToElement(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/PROCESS'), 30)

'Click on \'Process\'\r\n'
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/PROCESS'), 
    'Process', (([GlobalVariable.pageLoadTime]) as int[]))

paymentId = CustomKeywords.'pages.Quicksti.verifyPaymentApproval'(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/APPROVED'))

	'Open admin site'
	WebUI.navigateToUrl(GlobalVariable.Admin_url)
	
	'Enter username,password'
	CustomKeywords.'pages.Bureau_Login_Page.loginToAdminSite'(GlobalVariable.AdminUsername, GlobalVariable.AdminPassword)
	
	
	
	'Click on TransactionMenu'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/TRANSACTION_MENU'), 'TransactionMenu', (([GlobalVariable.pageLoadTime]) as int[]))

	'Click on Search'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/TRANSACTION_SEARCH'), 'TransactionSearch', (([GlobalVariable.pageLoadTime]) as int[]))

	'Enter Payment id'
	CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_ADMIN/PAYMENT_ID'), paymentId, 'Payment id',
			(([GlobalVariable.pageLoadTime]) as int[]))
	
	'Click on Go button'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/GO_BUTTON'), 'Go button', (([GlobalVariable.pageLoadTime]) as int[]))
	
	WebUI.delay(3)
	
	paymentIdList.add(paymentId)
	
	println(paymentIdList)

	'Click on Actions button'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/ACTIONS_BUTTON'), 'Actions button', (([GlobalVariable.pageLoadTime]) as int[]))
	
	'Click on Refund button'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/REFUND_MENU'), 'Refund Menu', (([GlobalVariable.pageLoadTime]) as int[]))
	
	WebUI.delay(2)
	
	'Click on CreditReturn button'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_ADMIN/CREDIT_RETURN'), 'Credit Return', (([GlobalVariable.pageLoadTime]) as int[]))
	
	
	'Enter Refund Amount'
	CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('Object Repository/CERTPAY_ADMIN/REFUND_AMOUNT'), RefundAmount, 'Payment id',
			(([GlobalVariable.pageLoadTime]) as int[]))
	
	String sConvAmount=ConvFee.substring(1)
	
	'Enter Conv Amount'
	CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('Object Repository/CERTPAY_ADMIN/REFUND_CONVFEE'), sConvAmount, 'Payment id',
			(([GlobalVariable.pageLoadTime]) as int[]))

	'Click on Process Reversal button'
	CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('Object Repository/CERTPAY_ADMIN/PROCESS_REVERSAL'), 'Process Reversal', (([GlobalVariable.pageLoadTime]) as int[]))
	
	WebUI.delay(2)
	
	CustomKeywords.'pages.Search_Page.validateRefundAmount'(RefundAmount,ConvFee)
}
