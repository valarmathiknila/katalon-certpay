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
ExcelData data = findTestData('Certpay/MultiBureau_MultiLine')
List<String> amountList = []

List<String> referenceList = []

List<String> paymentTypeList = []

List<String> quantityList = []
List<String> commentsList = []
List<String> paymentIdList=[]
ArrayList<String> list=new ArrayList<String>();
for (def index : (1..data.getRowNumbers())) {
	firstName=data.getValue('FirstName',index)
	lastName=data.getValue('LastName', index)
	cardNum=data.getValue('CardNumber',index)
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
	
	cardType=data.getValue('CardType',index)
	amountList = paymentAmount.split(',')
	referenceList=referenceNum.split(',')
	paymentTypeList=paymentType.split(',')
	commentsList=comments.split(',')
	quantityList=quantity.split(',')
	
' Open certpay Quicksti appliction'
CustomKeywords.'utilities.SafeActions.openBrowser'(GlobalVariable.Quicksti_Url, (([GlobalVariable.pageLoadTime]) as int[]))

'Click on \'Use multi bureau entry screen\' checkbox'
CustomKeywords.'utilities.SafeActions.safeCheck'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/MULTI_BUREAU_CHECKBOX'), 'Multi Bureau', 
        (([GlobalVariable.pageLoadTime]) as int[]))

'Enter \'Teller Id\''
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/TELLER_ID'), GlobalVariable.teller_id, 
    'Bureau code', (([GlobalVariable.pageLoadTime]) as int[]))

'Click on \'Next\''
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Login_ Page/NEXT_BUTTON'), 'Next button', 
        (([GlobalVariable.pageLoadTime]) as int[]))
//for (int j=0;j<=amountList.size()-1;j++){
	
//	String k=Integer.toString(j);
	
for(int i=0;i<=amountList.size()-1;i++){
	
	
	String s=Integer.toString(i);

'Enter comments'
CustomKeywords.'pages.Quicksti.setCommnets'('0', s, commentsList.get(i))


'Enter Payment amount'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/PAYMENT_AMOUNT'), 
    amountList.get(i), 'Payment Amount', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter paymnet type,reference Num,quantity'
CustomKeywords.'pages.Quicksti.setAmountDetailsForMultiBureau'(s, paymentTypeList.get(i), '0', referenceList.get(i), quantityList.get(i))
}
	
for(int i=0;i<=amountList.size()-1;i++){
	
	
	String s=Integer.toString(i);

'Enter Payment amount'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/MULTIBUREAU_PAYMENT_AMOUNT'), 
    amountList.get(i), 'Payment Amount', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter paymnet type,reference Num,quantity'
CustomKeywords.'pages.Quicksti.setAmountDetailsForMultiBureau'(s, 'Registration Fees', '1', referenceList.get(i), quantityList.get(i))
}

for(int i=0;i<=amountList.size()-1;i++){
	
	
	String s=Integer.toString(i);
'Enter Payment amount'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/MULTIBUREAU_PAY_AMOUNT2'), 
    amountList.get(i), 'Payment Amount', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter paymnet type,reference Num,quantity'
CustomKeywords.'pages.Quicksti.setAmountDetailsForMultiBureau'(s, 'Vehicles', '2', referenceList.get(i), quantityList.get(i))

'Enter comments'
CustomKeywords.'pages.Quicksti.setCommnets'('2', s, commentsList.get(i))
}


'Click on \'Click here to manually enter card details\''
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/MANUAL_ENTRY_BUTTON'), 
    'ManualEntry', (([GlobalVariable.pageLoadTime]) as int[]))

amount_Value = CustomKeywords.'pages.Quicksti.getAttributeValue'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/SUB_TOTAL'))

'Enter firstname,lastname,card number'
CardNumber = CustomKeywords.'pages.Quicksti.setUserPaymentDetails'(firstName, lastName, cardNum)

'Enter exp date,security code'
CustomKeywords.'pages.Quicksti.setExpDate'(expMonth, expYear, securityCode)

'Enter address'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/ADDRESS'), address, 
    'Address', (([GlobalVariable.pageLoadTime]) as int[]))

'Enter zip code'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/ZIP_CODE'),zipCode, 
    'zipcode', (([GlobalVariable.pageLoadTime]) as int[]))

'enter telephone'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/TELEPHONE'), telephone, 
    'Telephone', (([GlobalVariable.pageLoadTime]) as int[]))

'enter email address'
CustomKeywords.'utilities.SafeActions.safeType'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/EMAIL_ADDRESS'), 
emailAddress, 'EMAIL address', (([GlobalVariable.pageLoadTime]) as int[]))

'Click on Next'
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/NEXT_BUTTON'), 
    'Next', (([GlobalVariable.pageLoadTime]) as int[]))

'Verify details like first and last name,amount,card num'
CustomKeywords.'pages.Quicksti.verifyUserPaymentDetails'(amountList.get(0),firstName,lastName, 
    CardNumber)

ConvFee=WebUI.getText(findTestObject('CERTPAY_QUICKSTI/Payment_Information_Page/CONV_FEE'))

WebUI.scrollToElement(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/PROCESS'), 30)

'Click on Process'
CustomKeywords.'utilities.SafeActions.safeClick'(findTestObject('CERTPAY_QUICKSTI/Payment_Details_Verification_Page/PROCESS'), 
    'Process', (([GlobalVariable.pageLoadTime]) as int[]))

'Verify payment approval'
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
	
	WebUI.delay(5)
	
	WebUI.scrollToPosition(800, 900)
	
	CustomKeywords.'pages.Search_Page.validatePaymentAmount'(amountList.get(0),ConvFee)
	
	paymentIdList.add(paymentId)
	println(paymentIdList)
}
return paymentIdList;