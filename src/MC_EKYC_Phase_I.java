/********************************************************************
 *               TransTrac Technology Services Pvt Ltd.             *
 *                             CREDOPAY                             *
 *------------------------------------------------------------------*
 *  Author			: Anand Kathirvel								*
 *  Role			: Lead, Quality Assurance - Product Delivery	*
 *  Date			: 26-Apr-2022									*
 *  Program Name	: Merchant_Onboarding.java						*
 *  Description		: This automation program is used to create a	*
 *  				  complete Merchant Onboarding flow.          	*
 *------------------------------------------------------------------*
 ********************************************************************
 * Rev Date		Editor Name		Rev History							*
 ********************************************************************
 * 03-Aug-22	Anand.K			Added the Headless browser option 
 * 12-Jul-22	Anand.K			Re-arranged the code to be executed 1 by 1.
 * 05-Jul-22	Ajai Godwin		Added KYC page code.
 * 10-Jun-22	Ajai Godwin		Added Risk-Info page code.
 * 26-Apr-22	Anand.K			Created initial program.
 ********************************************************************/

/**
 * -------------- HEADERS & LIBRARIES ----------------
 */
// General Libraries
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.SplittableRandom;

// Related to Excel Data Reading
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Triplet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class MC_EKYC_Phase_I {
	/****************************************************************
	 * -------------- METHOD - readExcelData - Login ----------------
	 *****************************************************************/
	public static Triplet<String[][], Integer, Integer> readExcelData_Login() throws IOException {
		String filePath = "C:\\Users\\Anand\\OneDrive - Transtrac Technology Services Private Limited\\CODING\\CPBS_UI_Automation\\Testing_Selenium_Inputs\\EKYC_UI_Input_SecondaryDocs.xlsx";
//		if file
		System.out.println("** Processing Excel - Reading ( Login ) sheet !!!");
		XSSFWorkbook wBook = new XSSFWorkbook(filePath);
		String[][] data_Login = null;
		XSSFSheet wSheet_1 = wBook.getSheet("Login");

		int rowCount_1 = wSheet_1.getLastRowNum();
		int columnCount_1 = wSheet_1.getRow(0).getLastCellNum() - 1;

		System.out.println("Row # = " + rowCount_1);
		System.out.println("Col # = " + columnCount_1);

		data_Login = new String[rowCount_1 * 2][columnCount_1 * 2];
		System.out.println(rowCount_1 * 2 + "," + columnCount_1 * 2);

		DataFormatter dfr = new DataFormatter();
		Triplet<String[][], Integer, Integer> obj_LoginData = Triplet.with(data_Login, Integer.valueOf(rowCount_1),
				Integer.valueOf(columnCount_1));

		for (int i = 0; i <= rowCount_1; i++) {
			for (int j = 0; j <= columnCount_1; j++) {
				data_Login[i][j] = dfr.formatCellValue(wSheet_1.getRow(i).getCell(j));
			}
		}
		wBook.close();
		return obj_LoginData;
	}

	/******************************************************************************
	 * -------------- METHOD - readExcelData - MerchantOnboarding ----------------
	 ******************************************************************************/
	public static Triplet<String[][], Integer, Integer> readExcelData_MerchantOnboarding() throws IOException {
		String filePath = "C:\\Users\\Anand\\OneDrive - Transtrac Technology Services Private Limited\\CODING\\CPBS_UI_Automation\\Testing_Selenium_Inputs\\EKYC_UI_Input_SecondaryDocs.xlsx";
//		if file
		System.out.println("** Processing Excel - Reading ( Merchant Onboarding ) sheet !!!");
		XSSFWorkbook wBook = new XSSFWorkbook(filePath);
		String[][] data_MerchantOnboard = null;
		XSSFSheet wSheet_2 = wBook.getSheet("Merchant_Onboard");

		int rowCount_2 = wSheet_2.getLastRowNum();
		int columnCount_2 = wSheet_2.getRow(0).getLastCellNum() - 1;

		System.out.println("Row # = " + rowCount_2);
		System.out.println("Col # = " + columnCount_2);

		data_MerchantOnboard = new String[rowCount_2 * 2][columnCount_2 * 2];
		System.out.println(rowCount_2 * 2 + "," + columnCount_2 * 2);

		DataFormatter dfr = new DataFormatter();
		Triplet<String[][], Integer, Integer> obj_MerchantOnboardData = Triplet.with(data_MerchantOnboard,
				Integer.valueOf(rowCount_2), Integer.valueOf(columnCount_2));

		for (int i = 0; i <= rowCount_2; i++) {
			for (int j = 0; j <= columnCount_2; j++) {
				data_MerchantOnboard[i][j] = dfr.formatCellValue(wSheet_2.getRow(i).getCell(j));
			}
		}
		wBook.close();
		return obj_MerchantOnboardData;
	}

	/**********************************************
	 * -------------- MAIN PROGRAM ----------------
	 **********************************************/
	public static void main(String[] args) throws IOException {
		
		BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));

		try {
			/***********************************************
			 * ---------------* Driver Setup ---------------
			 ***********************************************/
			String var_Driver_Path = "C:\\Users\\Anand\\OneDrive - Transtrac Technology Services Private Limited\\CODING\\CPBS_UI_Automation\\chromedriverr.exe";
			String var_Website_Url = "https://ucpbs.credopay.info/users/login";
			
			System.setProperty("webdriver.chrome.driver", var_Driver_Path);
			
			WebDriver driver;
			while (true) {
				System.out.println("\n0 - Browser");
				System.out.println("1 - HeadLess");
				System.out.println("Enter your input :");
				String opt_Browser = inp.readLine();
				
				if (opt_Browser.equals("0")) {
					System.out.println("0 - Browser chosen \n");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.get(var_Website_Url);
					break;
				} else if (opt_Browser.equals("1")) {
					System.out.println("1 - HeadLess chosen \n");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--headless");
					options.addArguments("--window-size=1366,768");
					driver = new ChromeDriver(options);
					driver.get(var_Website_Url);
					break;
				} else {
					System.out.println("Entered WRONG input \'" + opt_Browser + "\'");
					continue;
				}
			}
			System.out.println("** Please wait for a while - Browser Driver is getting ready !!!");

			/******************************************************
			 * ---------------* Initialize Variable ---------------
			 /*****************************************************/
			int T1 = 1000, T2 = 2000, T3 = 3000, T4 = 4000, T5 = 5000, T10 = 10000; // Thread value
			int sec1 = 1, sec2 = 2, sec3 = 3, sec4 = 4, sec5 = 5, sec6 = 6, sec7 = 7, sec8 = 8, sec9 = 9, sec10 = 10,
					sec20 = 20, sec30 = 30, sec40 = 40, sec50 = 50, sec60 = 60, sec70 = 70, sec80 = 80, sec90 = 90,
					sec100 = 100, sec110 = 110, sec120 = 120; // Wait seconds

			/********** Login **********/
			String var_login_Username, var_login_Password;

			/********** Sales-Info **********/
			String var_Sales_Agg_App_No, var_Sales_Application_Date, var_Sales_Aggreement_Date, var_Sales_Aggregator,
					var_Sales_Super_Merchant, var_Sales_Group_Merchant, var_Sales_Region, var_Sales_Bank_Name,
					var_Sales_Bank_MID, var_Sales_DSA_Distributor, var_Sales_Person, var_Sales_Subvention_Type,
					var_Sales_Partner_Sharing, var_Sales_Loan_Reimbursement;

			/********** Company-Info **********/
			String var_Comp_Legal_Name, var_Comp_Brand_Name, var_Comp_Registered_Address, var_Comp_Registered_Pincode,
					var_Comp_State, var_Comp_City, var_Comp_Type_of_Business, var_Comp_Establised_Year,
					var_Comp_Registered_Number, var_Comp_Company_Pan, var_Comp_GSTIN, var_Comp_Turnover_Year,
					var_Comp_Turnover_Amount, var_Comp_Nature_of_Business, var_Comp_MCC, var_Comp_Merchant_Type,
					var_Comp_Contact_Name, var_Comp_Mobile, var_Comp_Alternate_Mobile, var_Comp_Telephone,
					var_Comp_Email, var_Comp_Statement_Frequency, var_Comp_Statement_Type,
					var_Comp_Statement_Email_Required, var_Comp_Statement_Email_ID;

			/********** Personal-Info **********/
			String var_Prsnl_Honorific, var_Prsnl_First_Name, var_Prsnl_Last_Name, var_Prsnl_DOB, var_Prsnl_Address,
					var_Prsnl_Pincode, var_Prsnl_State, var_Prsnl_City, var_Prsnl_Mobile, var_Prsnl_Telephone,
					var_Prsnl_EMail, var_Prsnl_Pan, var_Prsnl_Nationality, var_Prsnl_Aadhar_Number,
					var_Prsnl_Passport_Number, var_Prsnl_Passport_Exp_Date, var_Prsnl_Own_House, var_Prsnl_Add_Partner;

			/********** Risk-Info **********/
			String var_Risk_Weekdays_from, var_Risk_Weekdays_to, var_Risk_Weekends_from, var_Risk_Weekends_to,
					var_Risk_Expected_Card_Business, var_Risk_Average_Bill_Amount, var_Risk_Velocity_Check_Minutes,
					var_Risk_Velocity_Check_Count, var_Risk_Settlement_Days, var_Risk_Cibil_Score,
					var_Risk_Merchant_Type_Code, var_Risk_International_Card_Acceptance, var_Risk_International_Daily,
					var_Risk_International_Weekly, var_Risk_International_Monthly, var_Risk_Name_of_POS,
					var_Risk_Member_Since, var_Risk_Current_MDR, var_Risk_AEPS_Daily, var_Risk_AEPS_Weekly,
					var_Risk_AEPS_Monthly, var_Risk_AEPS_Minimum, var_Risk_AEPS_Maximum, var_Risk_UPI_Daily,
					var_Risk_UPI_Weekly, var_Risk_UPI_Monthly, var_Risk_UPI_Minimum, var_Risk_UPI_Maximum,
					var_Risk_MicroATM_Daily, var_Risk_MicroATM_weekly, var_Risk_MicroATM_Monthly,
					var_Risk_MicroATM_Minimum, var_Risk_MicroATM_Maximum, var_Risk_POS_Daily, var_Risk_POS_Weekly,
					var_Risk_POS_Monthly, var_Risk_POS_Minimum, var_Risk_POS_Maximum;

			/********** Bank-Info **********/
			String var_Bank_Acct_Type, var_Bank_Acct_number, var_Bank_IFSC_code;

			/********** Terminals **********/
			String var_Terminal_Location, var_Terminal_Address, var_Terminal_Pincode, var_Terminal_Sim_Number,
					var_Terminal_Terminal_Type, var_Terminal_Device_Model, var_Terminal_Device_Owned,
					var_Terminal_Device_Price, var_Terminal_Installation_Fee, var_Terminal_Rental_Plan,
					var_Terminal_Rental_Type, var_Terminal_Rental_Mode, var_Terminal_Rental_Start_Date,
					var_Terminal_Rental_Threshold, var_Terminal_Maximum_Usage_Daily, var_Terminal_Maximum_Usage_Weekly,
					var_Terminal_Maximum_Usage_Monthly, var_Terminal_Velocity_Check_Minutes,
					var_Terminal_Velocity_Check_Count;

			/********** KYC-Info **********/
			/*** Primary Docs ***/
			String var_KYC_Proof_Drive_Link, var_KYC_PAN_Doc_Num, var_KYC_Businees_Addr_Doc_Num,
					var_KYC_Aadhaar_Doc_Num, var_KYC_Bank_Cancel_Cheque_Doc_Num, var_KYC_Latest_Photo_Doc_Num,
					var_KYC_Aggr_Merchant_Aggrement_Doc_Num;
			String var_KYC_Passport_Doc_Num, var_KYC_Driving_License_Doc_Num, var_KYC_Ration_Card_Doc_Num,
					var_KYC_Voters_ID_Doc_Num, var_KYC_GST_Certificate_Doc_Num;
			/*** Secondary Docs ***/
			String var_KYC_PAN_Proof_Link, var_KYC_Business_Addr_Proof_Link, var_KYC_Aadhar_Proof_Link,
					var_KYC_Bank_Cancel_Cheque_Proof_Link, var_KYC_Latest_Photo_Proof_Link,
					var_KYC_Merchant_Aggrement_Proof_Link;
			String var_KYC_Passport_Proof_Link, var_KYC_Driving_License_Proof_Link, var_KYC_Ration_Card_Proof_Link,
					var_KYC_Voters_ID_Proof_Link, var_KYC_GST_Certificate_Proof_Link;

			/********** Login Data from excel file **********/
			Triplet<String[][], Integer, Integer> obj_data_Login = readExcelData_Login();
			String var_data_Login[][] = (String[][]) obj_data_Login.getValue0();
			int var_rowCount_Login = (int) obj_data_Login.getValue1();

			/*********************************
			 * --- FLUENT WAIT ----
			 **********************************/
			Wait<WebDriver> Flu_Wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(sec120))
					.pollingEvery(Duration.ofSeconds(sec2))
					.ignoreAll(null);
			

			for (int i = 1; i <= var_rowCount_Login; i++) {
				var_login_Username = var_data_Login[i][0].trim();
				var_login_Password = var_data_Login[i][1].trim();

				/********** Merchant onboard data from Excel file **********/
				Triplet<String[][], Integer, Integer> obj_data_MerchantOnboard = readExcelData_MerchantOnboarding();
				String var_data_MerchantOnboard[][] = (String[][]) obj_data_MerchantOnboard.getValue0();
				int var_rowCount_MerchantOnboard = (int) obj_data_MerchantOnboard.getValue1();

				/*********************************************
				 * -------------- LOGIN FORM ----------------
				 *********************************************/
				System.out.println("-------------- LOGIN FORM ----------------");

				// FIELD : Username
				driver.findElement(By.id("username")).sendKeys(String.valueOf(var_login_Username)); // Username
				System.out.println("var_login_Username  \t\t = " + var_login_Username);

				// FIELD : Password
				driver.findElement(By.id("password")).sendKeys(String.valueOf(var_login_Password)); // Password
				System.out.println("var_login_Password  \t\t = " + var_login_Password);

				// FIELD : Captcha | Find the sum of captcha value
				String captchaInputValue = driver.findElement(By.id("question")).getText();
				String[] captchaValue = captchaInputValue.split(" \\+ ");
				int captchaFirstValue = Integer.parseInt(captchaValue[0].trim());
				String[] captchaSecondValueSplit = captchaValue[1].split("=");
				int captchaSecondValue = Integer.parseInt(captchaSecondValueSplit[0].trim());
				int captcha = captchaFirstValue + captchaSecondValue;
				driver.findElement(By.id("answer")).sendKeys(String.valueOf(captcha));

				// BUTTON : Login
				driver.findElement(By.id("js_login-submit-btn")).click();

				/*************************************************************
				 * -------------- Menu : MERCHANT ONBOARDING ----------------
				 *************************************************************/
				// MENU : Merchant Management
//				Thread.sleep(T2);
//				driver.findElement(By.xpath("//span[contains(text(),'Merchant Management')]")).click();
				Flu_Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Merchant Management')]"))).click(); // Merchant_Mgmt

				// MENU : Merchants(sub-menu)
//				Thread.sleep(T1);
//				driver.findElement(By.xpath("//span[contains(text(),'Merchants')]"))).click();
				Flu_Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Merchants')]"))).click(); // Merchants
				
				for (int x = 5; x <= var_rowCount_MerchantOnboard; x++) {

					/********** Sales-Info **********/
					var_Sales_Agg_App_No = String.valueOf(var_data_MerchantOnboard[x][1].trim()); // Auto-gen-value
					var_Sales_Application_Date = String.valueOf(var_data_MerchantOnboard[x][2].trim()); // Auto-gen-value
					var_Sales_Aggreement_Date = String.valueOf(var_data_MerchantOnboard[x][3].trim()); // Auto-gen-value
					var_Sales_Aggregator = String.valueOf(var_data_MerchantOnboard[x][4].trim());
					var_Sales_Super_Merchant = String.valueOf(var_data_MerchantOnboard[x][5].trim());
					var_Sales_Group_Merchant = String.valueOf(var_data_MerchantOnboard[x][6].trim());
					var_Sales_Region = String.valueOf(var_data_MerchantOnboard[x][7].trim());
					var_Sales_Bank_Name = String.valueOf(var_data_MerchantOnboard[x][8].trim());
					var_Sales_Bank_MID = String.valueOf(var_data_MerchantOnboard[x][9].trim());
					var_Sales_DSA_Distributor = String.valueOf(var_data_MerchantOnboard[x][10].trim());
					var_Sales_Person = String.valueOf(var_data_MerchantOnboard[x][11].trim());
					var_Sales_Subvention_Type = String.valueOf(var_data_MerchantOnboard[x][12].trim()); // Checkbox-Bank-Discount
					var_Sales_Partner_Sharing = String.valueOf(var_data_MerchantOnboard[x][13].trim()); // Checkbox
					var_Sales_Loan_Reimbursement = String.valueOf(var_data_MerchantOnboard[x][18].trim()); // Checkbox

					/********** Company-info **********/
					var_Comp_Legal_Name = String.valueOf(var_data_MerchantOnboard[x][28].trim());
					var_Comp_Brand_Name = String.valueOf(var_data_MerchantOnboard[x][29].trim());
					var_Comp_Registered_Address = String.valueOf(var_data_MerchantOnboard[x][30].trim());
					var_Comp_Registered_Pincode = String.valueOf(var_data_MerchantOnboard[x][31].trim());
					var_Comp_State = String.valueOf(var_data_MerchantOnboard[x][32].trim());
					var_Comp_City = String.valueOf(var_data_MerchantOnboard[x][33].trim());
					var_Comp_Type_of_Business = String.valueOf(var_data_MerchantOnboard[x][34].trim());
					var_Comp_Establised_Year = String.valueOf(var_data_MerchantOnboard[x][35].trim());
					var_Comp_Registered_Number = String.valueOf(var_data_MerchantOnboard[x][36].trim());
					var_Comp_Company_Pan = String.valueOf(var_data_MerchantOnboard[x][37].trim());
					var_Comp_GSTIN = String.valueOf(var_data_MerchantOnboard[x][38].trim());
					var_Comp_Turnover_Year = String.valueOf(var_data_MerchantOnboard[x][39].trim());
					var_Comp_Turnover_Amount = String.valueOf(var_data_MerchantOnboard[x][40].trim());
					var_Comp_Nature_of_Business = String.valueOf(var_data_MerchantOnboard[x][41].trim());
					var_Comp_MCC = String.valueOf(var_data_MerchantOnboard[x][42].trim());
					var_Comp_Merchant_Type = String.valueOf(var_data_MerchantOnboard[x][43].trim());
					var_Comp_Contact_Name = String.valueOf(var_data_MerchantOnboard[x][44].trim());
					var_Comp_Mobile = String.valueOf(var_data_MerchantOnboard[x][45].trim());
					var_Comp_Alternate_Mobile = String.valueOf(var_data_MerchantOnboard[x][46].trim());
					var_Comp_Telephone = String.valueOf(var_data_MerchantOnboard[x][47].trim());
					var_Comp_Email = String.valueOf(var_data_MerchantOnboard[x][48].trim());
					var_Comp_Statement_Frequency = String.valueOf(var_data_MerchantOnboard[x][49].trim());
					var_Comp_Statement_Type = String.valueOf(var_data_MerchantOnboard[x][50].trim());
					var_Comp_Statement_Email_Required = String.valueOf(var_data_MerchantOnboard[x][51].trim());
					var_Comp_Statement_Email_ID = String.valueOf(var_data_MerchantOnboard[x][52].trim());

					/********** Personal-info **********/
					var_Prsnl_Honorific = String.valueOf(var_data_MerchantOnboard[x][53].trim());
					var_Prsnl_First_Name = String.valueOf(var_data_MerchantOnboard[x][54].trim());
					var_Prsnl_Last_Name = String.valueOf(var_data_MerchantOnboard[x][55].trim());
					var_Prsnl_DOB = String.valueOf(var_data_MerchantOnboard[x][56].trim());
					var_Prsnl_Address = String.valueOf(var_data_MerchantOnboard[x][57].trim());
					var_Prsnl_Pincode = String.valueOf(var_data_MerchantOnboard[x][58].trim());
					var_Prsnl_State = String.valueOf(var_data_MerchantOnboard[x][59].trim());
					var_Prsnl_City = String.valueOf(var_data_MerchantOnboard[x][60].trim());
					var_Prsnl_Mobile = String.valueOf(var_data_MerchantOnboard[x][61].trim());
					var_Prsnl_Telephone = String.valueOf(var_data_MerchantOnboard[x][62].trim());
					var_Prsnl_EMail = String.valueOf(var_data_MerchantOnboard[x][63].trim());
					var_Prsnl_Pan = String.valueOf(var_data_MerchantOnboard[x][64].trim());
					var_Prsnl_Nationality = String.valueOf(var_data_MerchantOnboard[x][65].trim());
					var_Prsnl_Aadhar_Number = String.valueOf(var_data_MerchantOnboard[x][66].trim());
					var_Prsnl_Passport_Number = String.valueOf(var_data_MerchantOnboard[x][67].trim());
					var_Prsnl_Passport_Exp_Date = String.valueOf(var_data_MerchantOnboard[x][68].trim());
					var_Prsnl_Own_House = String.valueOf(var_data_MerchantOnboard[x][69].trim());
					var_Prsnl_Add_Partner = String.valueOf(var_data_MerchantOnboard[x][70].trim());

					/********** Risk-Info **********/
					var_Risk_Weekdays_from = String.valueOf(var_data_MerchantOnboard[x][71].trim());
					var_Risk_Weekdays_to = String.valueOf(var_data_MerchantOnboard[x][72].trim());
					var_Risk_Weekends_from = String.valueOf(var_data_MerchantOnboard[x][73].trim());
					var_Risk_Weekends_to = String.valueOf(var_data_MerchantOnboard[x][74].trim());
					var_Risk_Expected_Card_Business = String.valueOf(var_data_MerchantOnboard[x][75].trim());
					var_Risk_Average_Bill_Amount = String.valueOf(var_data_MerchantOnboard[x][76].trim());
					var_Risk_Velocity_Check_Minutes = String.valueOf(var_data_MerchantOnboard[x][77].trim());
					var_Risk_Velocity_Check_Count = String.valueOf(var_data_MerchantOnboard[x][78].trim());
					var_Risk_Settlement_Days = String.valueOf(var_data_MerchantOnboard[x][79].trim());
					var_Risk_Cibil_Score = String.valueOf(var_data_MerchantOnboard[x][80].trim());
					var_Risk_Merchant_Type_Code = String.valueOf(var_data_MerchantOnboard[x][81].trim());
					var_Risk_International_Card_Acceptance = String.valueOf(var_data_MerchantOnboard[x][82].trim());
					var_Risk_International_Daily = String.valueOf(var_data_MerchantOnboard[x][83].trim());
					var_Risk_International_Weekly = String.valueOf(var_data_MerchantOnboard[x][84].trim());
					var_Risk_International_Monthly = String.valueOf(var_data_MerchantOnboard[x][85].trim());
					var_Risk_Name_of_POS = String.valueOf(var_data_MerchantOnboard[x][86].trim());
					var_Risk_Member_Since = String.valueOf(var_data_MerchantOnboard[x][87].trim());
					var_Risk_Current_MDR = String.valueOf(var_data_MerchantOnboard[x][88].trim());
					var_Risk_AEPS_Daily = String.valueOf(var_data_MerchantOnboard[x][96].trim());
					var_Risk_AEPS_Weekly = String.valueOf(var_data_MerchantOnboard[x][97].trim());
					var_Risk_AEPS_Monthly = String.valueOf(var_data_MerchantOnboard[x][98].trim());
					var_Risk_AEPS_Minimum = String.valueOf(var_data_MerchantOnboard[x][99].trim());
					var_Risk_AEPS_Maximum = String.valueOf(var_data_MerchantOnboard[x][100].trim());
					var_Risk_UPI_Daily = String.valueOf(var_data_MerchantOnboard[x][101].trim());
					var_Risk_UPI_Weekly = String.valueOf(var_data_MerchantOnboard[x][102].trim());
					var_Risk_UPI_Monthly = String.valueOf(var_data_MerchantOnboard[x][103].trim());
					var_Risk_UPI_Minimum = String.valueOf(var_data_MerchantOnboard[x][104].trim());
					var_Risk_UPI_Maximum = String.valueOf(var_data_MerchantOnboard[x][105].trim());
					var_Risk_MicroATM_Daily = String.valueOf(var_data_MerchantOnboard[x][106].trim());
					var_Risk_MicroATM_weekly = String.valueOf(var_data_MerchantOnboard[x][107].trim());
					var_Risk_MicroATM_Monthly = String.valueOf(var_data_MerchantOnboard[x][108].trim());
					var_Risk_MicroATM_Minimum = String.valueOf(var_data_MerchantOnboard[x][109].trim());
					var_Risk_MicroATM_Maximum = String.valueOf(var_data_MerchantOnboard[x][110].trim());
					var_Risk_POS_Daily = String.valueOf(var_data_MerchantOnboard[x][111].trim());
					var_Risk_POS_Weekly = String.valueOf(var_data_MerchantOnboard[x][112].trim());
					var_Risk_POS_Monthly = String.valueOf(var_data_MerchantOnboard[x][113].trim());
					var_Risk_POS_Minimum = String.valueOf(var_data_MerchantOnboard[x][114].trim());
					var_Risk_POS_Maximum = String.valueOf(var_data_MerchantOnboard[x][115].trim());

					/********** Bank-info **********/
					var_Bank_Acct_Type = String.valueOf(var_data_MerchantOnboard[x][116].trim());
					var_Bank_Acct_number = String.valueOf(var_data_MerchantOnboard[x][117].trim());
					var_Bank_IFSC_code = String.valueOf(var_data_MerchantOnboard[x][118].trim());

					/********** Terminals **********/
					var_Terminal_Location = String.valueOf(var_data_MerchantOnboard[x][119].trim());
					var_Terminal_Address = String.valueOf(var_data_MerchantOnboard[x][120].trim());
					var_Terminal_Pincode = String.valueOf(var_data_MerchantOnboard[x][121].trim());
					var_Terminal_Sim_Number = String.valueOf(var_data_MerchantOnboard[x][122].trim());
					var_Terminal_Terminal_Type = String.valueOf(var_data_MerchantOnboard[x][123].trim());
					var_Terminal_Device_Model = String.valueOf(var_data_MerchantOnboard[x][124].trim());
					var_Terminal_Device_Owned = String.valueOf(var_data_MerchantOnboard[x][125].trim());
					var_Terminal_Device_Price = String.valueOf(var_data_MerchantOnboard[x][126].trim());
					var_Terminal_Installation_Fee = String.valueOf(var_data_MerchantOnboard[x][127].trim());
					var_Terminal_Rental_Plan = String.valueOf(var_data_MerchantOnboard[x][128].trim());
					var_Terminal_Rental_Type = String.valueOf(var_data_MerchantOnboard[x][129].trim());
					var_Terminal_Rental_Mode = String.valueOf(var_data_MerchantOnboard[x][130].trim());
					var_Terminal_Rental_Start_Date = String.valueOf(var_data_MerchantOnboard[x][131].trim());
					var_Terminal_Rental_Threshold = String.valueOf(var_data_MerchantOnboard[x][132].trim());
					var_Terminal_Maximum_Usage_Daily = String.valueOf(var_data_MerchantOnboard[x][133].trim());
					var_Terminal_Maximum_Usage_Weekly = String.valueOf(var_data_MerchantOnboard[x][134].trim());
					var_Terminal_Maximum_Usage_Monthly = String.valueOf(var_data_MerchantOnboard[x][135].trim());
					var_Terminal_Velocity_Check_Minutes = String.valueOf(var_data_MerchantOnboard[x][136].trim());
					var_Terminal_Velocity_Check_Count = String.valueOf(var_data_MerchantOnboard[x][137].trim());

					/********** KYC Verification **********/
					/********** Primary Docs **********/
					// PAN, Aathar, Bank Details
					var_KYC_Proof_Drive_Link = String.valueOf(var_data_MerchantOnboard[x][138].trim()); // Folder OR

					// - 1 - PAN
					var_KYC_PAN_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][139].trim());
					var_KYC_PAN_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][140].trim());

					// - 2 - Aathar
					var_KYC_Aadhaar_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][141].trim());
					var_KYC_Aadhar_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][142].trim());

					// - 3 - Bank Details
					var_KYC_Bank_Cancel_Cheque_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][143].trim());
					var_KYC_Bank_Cancel_Cheque_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][144].trim());

					/********** Secondary Documents **********/
					// Business Addr, Passport, Driving License, Ration Card, Voters ID,
					// Photographs, Merchant Aggrement, GST Certificate
					// - 4 - Business Addr
					var_KYC_Businees_Addr_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][145].trim());
					var_KYC_Business_Addr_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][146].trim());

					// - 5 - Passport
					var_KYC_Passport_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][147].trim());
					var_KYC_Passport_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][148].trim());

					// - 6 - Driving License
					var_KYC_Driving_License_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][149].trim());
					var_KYC_Driving_License_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][150].trim());

					// - 7 - Ration Card
					var_KYC_Ration_Card_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][151].trim());
					var_KYC_Ration_Card_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][152].trim());

					// - 8 - Voters ID
					var_KYC_Voters_ID_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][153].trim());
					var_KYC_Voters_ID_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][154].trim());

					// - 9 - Photograph
					var_KYC_Latest_Photo_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][155].trim());
					var_KYC_Latest_Photo_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][156].trim());

					// - 10 - Aggregator Merchant Agreement
					var_KYC_Aggr_Merchant_Aggrement_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][157].trim());
					var_KYC_Merchant_Aggrement_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][158].trim());

					// - 11 - GST Certification
					var_KYC_GST_Certificate_Doc_Num = String.valueOf(var_data_MerchantOnboard[x][159].trim());
					var_KYC_GST_Certificate_Proof_Link = String.valueOf(var_data_MerchantOnboard[x][160].trim());


					// BUTTON : Click the CREATE button
					driver.findElement(By.xpath("//button[normalize-space()='Create']")).click(); // CREATE btn
					Thread.sleep(T2);

					/****************************************************************************
					 * -------------- Menu : MERCHANT ONBOARDING ==> SALES INFO ----------------
					 ****************************************************************************/
					System.out.println("\n-------------- MERCHANT ONBOARDING ==> SALES INFO ----------------");

					// Random# generator for Aggregator Application# & Bank MID
					SplittableRandom splittableRandom = new SplittableRandom();
					int randomSplittableNumber = splittableRandom.nextInt(999, 99999999);

					// MENU : Sales Info(sub-menu)
					driver.findElement(By.xpath("//a[@data-btn-id = 'js_merchants-sales-form-submit-btn']")).getText(); // SalesInfo

					// FIELD 1 : Aggregator Application Number
					Thread.sleep(T1);
					if (var_Sales_Agg_App_No.equalsIgnoreCase("Any")) {
						var_Sales_Agg_App_No = "EKYCM" + randomSplittableNumber;
						driver.findElement(By.name("sales_information[aggregator_application_number]")).sendKeys(var_Sales_Agg_App_No);
					} else {
						driver.findElement(By.name("sales_information[aggregator_application_number]")).sendKeys(var_Sales_Agg_App_No);
					}
					System.out.println("var_Sales_Agg_App_No  \t\t = " + var_Sales_Agg_App_No);

					// FIELD 2 : Application Date (Calendar)
					Date date = new Date();
					SimpleDateFormat formattedDate = new SimpleDateFormat("YYYY-MM-dd");
					String currentDate = formattedDate.format(date);
					driver.findElement(By.name("sales_information[application_date]")).sendKeys(currentDate);
					System.out.println("var_Sales_Application_Date  \t = " + currentDate);

					// FIELD 3 : Agreement Date (Calendar)
					driver.findElement(By.name("sales_information[aggreement_date]")).sendKeys(currentDate);
					System.out.println("var_Sales_Aggreement_Date  \t = " + currentDate);

					// FIELD 4 : Aggregator
					driver.findElement(By.xpath("//span[@aria-labelledby = 'select2-js_aggregator-select2-container']")).click(); // click the field
					Thread.sleep(T3);

					driver.findElement(By.xpath("//input[@aria-controls='select2-js_aggregator-select2-results']")).sendKeys(var_Sales_Aggregator);

					Thread.sleep(T5);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Sales_Aggregator + "']")).click();
					System.out.println("var_Sales_Aggregator  \t\t = " + var_Sales_Aggregator);

					// FIELD 5 : Super Merchant
					Thread.sleep(T1);
					var_Sales_Super_Merchant = driver
							.findElement(By.xpath("//span[@id='select2-js_super-merchant-select2-container']"))
							.getText();
					System.out.println("var_Sales_Super_Merchant  \t = " + var_Sales_Super_Merchant);

					// FIELD 6 : Group Merchant
					Thread.sleep(T1);
					var_Sales_Group_Merchant = driver
							.findElement(By.xpath("//span[@id='select2-js_group-merchant-select2-container']"))
							.getText();
					System.out.println("var_Sales_Group_Merchant  \t = " + var_Sales_Group_Merchant);

					// FIELD 7 : Region
					driver.findElement(By.xpath("//span[@id='select2-js_region-select2-container']")).click();

					Thread.sleep(T5);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Sales_Region + "']")).click();
					System.out.println("var_Sales_Region  \t\t = " + var_Sales_Region);

					// FIELD 8 : Bank Name
					Thread.sleep(T1);
					driver.findElement(By.xpath("(//select[@name='sponsor_bank_information[bank_name]'])")).click();

					if (var_Sales_Bank_Name == "City Union Bank") {
						// Field 8:
						Thread.sleep(T1);
						driver.findElement(By.xpath("//select[@name='sponsor_bank_information[bank_name]']"))
								.sendKeys(var_Sales_Bank_Name);
						System.out.println("var_Sales_Bank_Name  \t\t = " + var_Sales_Bank_Name);

					} else {
						Select nameOfTheBank = new Select(driver
								.findElement(By.xpath("(//select[@name='sponsor_bank_information[bank_name]'])")));
						nameOfTheBank.selectByVisibleText(var_Sales_Bank_Name);
						System.out.println("var_Sales_Bank_Name  \t\t = " + var_Sales_Bank_Name);

						// Field 9: Bank-MID (only for YES bank)
						Thread.sleep(T1);
						if (var_Sales_Bank_MID.equalsIgnoreCase("Any")) {
//							System.out.println("\t IF ANY - Inside Block");
							String var_Random_Bank_MID = String.valueOf(randomSplittableNumber + 1);
							driver.findElement(By.xpath("//input[@name='sponsor_bank_information[bank_mid]']"))
									.sendKeys(var_Random_Bank_MID);
							System.out.println("var_Random_Bank_MID  \t\t = " + var_Random_Bank_MID);
//							System.out.println("\t IF ANY - EXIT");
						} else {
//							System.out.println("\t ELSE ANY - Inside Block");
							driver.findElement(By.xpath("//input[@name='sponsor_bank_information[bank_mid]']"))
									.sendKeys(var_Sales_Bank_MID);
							System.out.println("var_Sales_Bank_MID  \t\t = " + var_Sales_Bank_MID);
//							System.out.println("\t ELSE ANY - EXIT");
						}
					}
					/*
					 * Field 10 - 27 
					 * were assigned to Sales-Info input fields but code was NOT IMPLEMENTED yet. 
					 * var_Sales_DSA_Distributor => Field[10] 
					 * var_Sales_Person => Field[11] 
					 * var_Sales_Subvention_Type => Field[12] 
					 * var_Sales_Partner_Sharing => Field[13-17] 
					 * var_Sales_Loan_Reimbursement => Field[18-27]
					 */

					// Action Items / Elements
					// BUTTON : Save
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-sales-form-submit-btn']")).click();
					System.out.println("\t< Sales-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println("\t< Sales-Info > POP-UP Status = " + driver.findElement(By.id("swal2-title")).getText());

//					Flu_Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T2);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Sales-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> COMPANY INFO ----------------
					 */
					System.out.println("\n-------------- MERCHANT ONBOARDING ==> COMPANY INFO ----------------");
					Thread.sleep(T2);
					driver.findElement(By.xpath("//a[normalize-space()='Company Info']")).click();

					// Field : 28 --> Legal Name
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='company_information[legal_name]']"))
							.sendKeys(var_Comp_Legal_Name);
					System.out.println("var_Comp_Legal_Name \t\t = " + var_Comp_Legal_Name);

					// Field : 29 --> Brand Name
					driver.findElement(By.xpath("//input[@name='company_information[brand_name]']"))
							.sendKeys(var_Comp_Brand_Name);
					System.out.println("var_Comp_Brand_Name \t\t = " + var_Comp_Brand_Name);

					// Field : 30 --> Registered Address
					driver.findElement(By.xpath("//input[@name='company_information[registered_address]']"))
							.sendKeys(var_Comp_Registered_Address);
					System.out.println("var_Comp_Registered_Address \t = " + var_Comp_Registered_Address);

					// Field : 31 --> Registered Pincode
					Thread.sleep(T1);
					driver.findElement(By.xpath("//span[@id='select2-js_pincode-select2-container']")).click();
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@aria-controls='select2-js_pincode-select2-results']")).sendKeys(var_Comp_Registered_Pincode);
					Thread.sleep(T1);
					driver.findElement(By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']")).click();
					System.out.println("var_Comp_Registered_Pincode \t = " + var_Comp_Registered_Pincode);

					// Field : 32 --> State
					driver.findElement(By.xpath("//input[@name='company_information[registered_state]']")).getText();
					System.out.println("var_Comp_State \t\t\t = " + var_Comp_State);

					// Field : 33 --> City
					driver.findElement(By.xpath("//input[@name='company_information[registered_city]']")).getText();
					System.out.println("var_Comp_City \t\t\t = " + var_Comp_City);

					// Field : 34 --> Type_of_Business
					driver.findElement(By.xpath("//select[@name='company_information[business_type]']")).click();
					Select Business_Type = new Select(
							driver.findElement(By.xpath("//select[@name='company_information[business_type]']")));
					Business_Type.selectByVisibleText(var_Comp_Type_of_Business);
					Thread.sleep(T2);
					System.out.println("var_Comp_Type_of_Business \t = " + var_Comp_Type_of_Business);

					// Field : 35 --> Establised_Year
//					driver.findElement(By.xpath("//input[@name='company_information[established_year]']")).sendKeys(currentDate);
//						// OR
//					String var_Estb_Date, var_Estb_Month, var_Estb_Year;
//
//					String value_Estb_Year[] = var_Comp_Establised_Year.split("-");
//					var_Estb_Date = value_Estb_Year[0].trim();
//					var_Estb_Month = value_Estb_Year[1].trim();
//					var_Estb_Year = value_Estb_Year[2].trim();
//
////					System.out.println("var_Estb_Date = " + var_Estb_Date);
////					System.out.println("var_Estb_Month = " + var_Estb_Month);
////					System.out.println("var_Estb_Year = " + var_Estb_Year);
//
//					driver.findElement(By.xpath("//input[@name='company_information[established_year]']")).click();
//					Thread.sleep(T1);
//					driver.findElement(By.xpath("//div[@class='drp-calendar left single']//select[@class='monthselect']")).click();
//
//					// Processing Established-Month
//					Select Estb_month_value = new Select(driver.findElement(By.xpath("//div[@class='drp-calendar left single']//select[@class='monthselect']")));
//					Estb_month_value.selectByVisibleText(var_Estb_Month);
//					System.out.println("var_Estb_Month = " + var_Estb_Month);
//
//					// Processing Established-Year
//					Select Estb_year_value = new Select(driver.findElement(By.xpath("//div[@class='drp-calendar left single']//select[@class='yearselect']")));
//					Estb_year_value.selectByVisibleText(var_Estb_Year);
//					System.out.println("var_Estb_Year = " + var_Estb_Year);
//
//					// Processing Established-Date
//					Thread.sleep(T1);
//					driver.findElement(By.xpath("//td[normalize-space()='" + var_Estb_Date + "']")).click();
//					System.out.println("var_Estb_Date = " + var_Estb_Date);

					// Field : 36 --> Registered_Number
					driver.findElement(By.xpath("//input[@name='company_information[registered_number]']"))
							.sendKeys(var_Comp_Registered_Number);
					System.out.println("var_Comp_Registered_Number \t = " + var_Comp_Registered_Number);

					// Field : 37 --> Company_Pan
					if (var_Comp_Company_Pan.equalsIgnoreCase("Any")) {
						// Random# generator for Aggregator Application# & Bank MID
						SplittableRandom splittableRandom_1 = new SplittableRandom();
						int randomSplittableNumber_1 = splittableRandom_1.nextInt(1, 9999);
						var_Comp_Company_Pan = "QATPC" + randomSplittableNumber_1 + "X";

						driver.findElement(By.xpath("//input[@name='company_information[pan]']"))
								.sendKeys(var_Comp_Company_Pan);
					} else {
						driver.findElement(By.xpath("//input[@name='company_information[pan]']"))
								.sendKeys(var_Comp_Company_Pan);
					}
					System.out.println("var_Comp_Company_Pan \t\t = " + var_Comp_Company_Pan);

					// Field : 38 --> GSTIN
					driver.findElement(By.xpath("//input[@name='company_information[gstin]']"))
							.sendKeys(var_Comp_GSTIN);
					System.out.println("var_Comp_GSTIN \t\t\t = " + var_Comp_GSTIN);

					// Field : 39 --> Turnover_Year
					driver.findElement(By.xpath("//select[@name='company_information[turnover_year]']")).click();
					Select TurnOver_Year = new Select(
							driver.findElement(By.xpath("//select[@name='company_information[turnover_year]']")));
					TurnOver_Year.selectByVisibleText(var_Comp_Turnover_Year);
					System.out.println("var_Comp_Turnover_Year \t\t = " + var_Comp_Turnover_Year);

					// Field : 40 --> Turnover_Amount
					driver.findElement(By.xpath("//input[@name='company_information[turnover_amount]']"))
							.sendKeys(var_Comp_Turnover_Amount);
					System.out.println("var_Comp_Turnover_Amount \t = " + var_Comp_Turnover_Amount);

					// Field : 41 --> Nature_of_Business
					driver.findElement(By.xpath("//input[@name='company_information[business_nature]']"))
							.sendKeys(var_Comp_Nature_of_Business);
					System.out.println("var_Comp_Nature_of_Business \t = " + var_Comp_Nature_of_Business);

					// Field : 42 --> MCC - Merchant Category Code
					Thread.sleep(T2);
					driver.findElement(By.xpath("//span[@id='select2-js_mcc-select2-container']")).click();
					Thread.sleep(T3);
					driver.findElement(By.xpath("//input[@aria-controls='select2-js_mcc-select2-results']"))
							.sendKeys(var_Comp_MCC);
					Thread.sleep(T4);
					driver.findElement(By.xpath("//li[starts-with(text(),'" + var_Comp_MCC + "')]")).click();
					System.out.println("var_Comp_MCC \t\t\t = " + var_Comp_MCC);

					// Field : 43 --> Merchant_Type
					Thread.sleep(T5);
					driver.findElement(
							By.xpath("//span[@aria-labelledby='select2-js_merchant-type-select2-container']")).click();
					Thread.sleep(T5);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Comp_Merchant_Type + "']")).click();
//					driver.findElement(By.xpath("//span[@id='select2-js_merchant-type-select2-container']")).click();
//					driver.findElement(By.xpath("(//span[@class='select2-results'])[1]/ul/li[normalize-space()='POS+Micro ATM']")).click();
//					Thread.sleep(T4);
//					driver.findElement(By.xpath("(//span[@class='select2-results'])[1]/ul/li[19]")).click();

					System.out.println("var_Comp_Merchant_Type \t\t = " + var_Comp_Merchant_Type);

					// Field : 44 --> Contact_Name
					driver.findElement(By.xpath("//input[@name='company_information[contact_name]']"))
							.sendKeys(var_Comp_Contact_Name);
					System.out.println("var_Comp_Contact_Name \t\t = " + var_Comp_Contact_Name);

					// Field : 45 --> Mobile
					driver.findElement(By.xpath("//input[@name='company_information[contact_mobile]']"))
							.sendKeys(var_Comp_Mobile);
					System.out.println("var_Comp_Mobile \t\t = " + var_Comp_Mobile);

					// Field : 46 --> Alternate_Mobile
					driver.findElement(By.xpath("//input[@name='company_information[contact_alternate_mobile]']"))
							.sendKeys(var_Comp_Alternate_Mobile);
					System.out.println("var_Comp_Alternate_Mobile \t = " + var_Comp_Alternate_Mobile);

					// Field : 47 --> Telephone
					driver.findElement(By.xpath("//input[@name='company_information[contact_phone]']"))
							.sendKeys(var_Comp_Telephone);
					System.out.println("var_Comp_Telephone \t\t = " + var_Comp_Telephone);

					// Field : 48 --> Email
					driver.findElement(By.xpath("//input[@name='company_information[contact_email]']"))
							.sendKeys(var_Comp_Email);
					System.out.println("var_Comp_Email \t\t\t = " + var_Comp_Email);

					if (var_login_Username.equalsIgnoreCase("ucpbsmaster")) {
						// Field : 49 --> Statement_Frequency
						Thread.sleep(T1);
						driver.findElement(By.xpath("//select[@name='company_information[statement_frequency]']"))
								.click();
						Thread.sleep(T2);
						Select Statement_Frequency = new Select(driver
								.findElement(By.xpath("//select[@name='company_information[statement_frequency]']")));
						Statement_Frequency.selectByVisibleText(var_Comp_Statement_Frequency);
						System.out.println("var_Comp_Statement_Frequency \t\t\t = " + var_Comp_Statement_Frequency);

						// Field : 50 --> Statement_Type
						Thread.sleep(T1);
						driver.findElement(By.xpath(
								"//div[@class='my-2']/child::div[input[@type='radio']/following-sibling:: label[contains(text(),'"
										+ var_Comp_Statement_Type + "')]]"))
								.click();
						System.out.println("var_Comp_Statement_Type \t\t = " + var_Comp_Statement_Type);

						// Field : 51 --> Statement_Email_Required
						Thread.sleep(T1);
						if (var_Comp_Statement_Email_Required.equalsIgnoreCase("Yes")) {
							// Field : 51 --> Statement_Email_Required
							driver.findElement(By.xpath("//label[normalize-space()='Statement Email Required']"))
									.click();
							System.out.println(
									"var_Comp_Statement_Email_Required \t\t = " + var_Comp_Statement_Email_Required);

							// Field : 52 --> Statement_Email_ID
							Thread.sleep(T1);
							driver.findElement(By.xpath("//input[@role='searchbox']"))
									.sendKeys(var_Comp_Statement_Email_ID);
							driver.findElement(By.xpath("//li[contains(.,'" + var_Comp_Statement_Email_ID + "')]"))
									.click();
							System.out.println("var_Comp_Statement_Email_ID \t\t = " + var_Comp_Statement_Email_ID);
						}
					}

					// Action Items / Elements
					// BUTTON : Save
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-company-form-submit-btn']")).click();
					System.out.println("\t< Company-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println("\t< Company-Info > POP-UP Status = "
							+ driver.findElement(By.id("swal2-content")).getText());
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T1);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Company-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> PERSONAL INFO ----------------
					 */
					System.out.println("\n-------------- MERCHANT ONBOARDING ==> PERSONAL INFO ----------------");
					Thread.sleep(T1);
					driver.findElement(By.xpath("//a[normalize-space()='Personal Info']")).click();
					Thread.sleep(T2);

					// Field : 53 --> Honorific
					driver.findElement(By.xpath("//select[@name='personal_information[0][title]']")).click();
					Thread.sleep(T1);
					driver.findElement(By.xpath("(//select[@name='personal_information[0][title]'])//option[@value='"
							+ var_Prsnl_Honorific + "']")).click();

					System.out.println("var_Prsnl_Honorific \t\t = " + var_Prsnl_Honorific);

					// Field : 54 --> First Name
					driver.findElement(By.xpath("//input[@name='personal_information[0][first_name]']"))
							.sendKeys(var_Prsnl_First_Name);
					System.out.println("var_Prsnl_First_Name \t\t = " + var_Prsnl_First_Name);

					// Field : 55 --> Last Name
					driver.findElement(By.xpath("//input[@name='personal_information[0][last_name]']"))
							.sendKeys(var_Prsnl_Last_Name);
					System.out.println("var_Prsnl_Last_Name \t\t = " + var_Prsnl_Last_Name);
					Thread.sleep(T2);

					// Field : 56 --> DOB
					String var_DOB_Date, var_DOB_Month, var_DOB_Year;

					String value_DOB[] = var_Prsnl_DOB.split("-");
					var_DOB_Date = value_DOB[0].trim();
					var_DOB_Month = value_DOB[1].trim();
					var_DOB_Year = value_DOB[2].trim();

//					System.out.println("var_DOB_Date = " + var_DOB_Date);
//					System.out.println("var_DOB_Month = " + var_DOB_Month);
//					System.out.println("var_DOB_Year = " + var_DOB_Year);

					driver.findElement(By.xpath("//input[@name='personal_information[0][dob]']")).click();
					Thread.sleep(T1);

					// Processing DOB-Year
					Select dob_year_value = new Select(driver.findElement(
							By.xpath("//div[@class='drp-calendar left single']//select[@class='yearselect']")));
					dob_year_value.selectByVisibleText(var_DOB_Year);
					System.out.println("var_DOB_Year \t\t\t = " + var_DOB_Year);

					// Processing DOB-Month
//					System.out.println("************ Before Click");
					driver.findElement(
							By.xpath("//div[@class='drp-calendar left single']//select[@class='monthselect']")).click();
//					System.out.println("************ After Click");
//					System.out.println("************ Before Click 1");
					Select dob_month_value = new Select(driver.findElement(
							By.xpath("//div[@class='drp-calendar left single']//select[@class='monthselect']")));
					dob_month_value.selectByVisibleText(var_DOB_Month);
					System.out.println("var_DOB_Month \t\t\t = " + var_DOB_Month);

					// Processing DOB-Day
					Thread.sleep(T1);
					driver.findElement(By.xpath("//td[normalize-space()='" + var_DOB_Date + "']")).click();

//					driver.findElement(By.xpath("//td[@class='available'][normalize-space()='16']")).click();
					System.out.println("var_DOB_Date \t\t\t = " + var_DOB_Date);

					driver.findElement(By.xpath("(//button[@type='button'][normalize-space()='Apply'])[4]")).click();

					// Field : 57 --> Address
					driver.findElement(By.xpath("//input[@name='personal_information[0][address]']"))
							.sendKeys(var_Prsnl_Address);
					System.out.println("var_Prsnl_Address \t\t = " + var_Prsnl_Address);

					// Field : 58 --> Pincode
//					Thread.sleep(T1);
					driver.findElement(By.xpath(
							"(//span[@class='select2-selection select2-selection--single js_personal-pincode-select2 form-control'])[1]"))
							.click();
					driver.findElement(By.xpath(
							"//span[@class='select2-search select2-search--dropdown']//input[@role='searchbox']"))
							.sendKeys(var_Prsnl_Pincode);
					Thread.sleep(T2);
					driver.findElement(
							By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"))
							.click();

					System.out.println("var_Prsnl_Pincode \t\t = " + var_Prsnl_Pincode);

					// Field : 59 --> State
					System.out.println("var_Prsnl_State \t\t = " + driver
							.findElement(By.xpath("(//input[@class='form-control js_personal-state'])[1]")).getText());

					// Field : 60 --> City
					System.out.println("var_Prsnl_City \t\t\t = " + driver
							.findElement(By.xpath("(//input[@class='form-control js_personal-city'])[1]")).getText());

					// Field : 61 --> Mobile
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][mobile]']"))
							.sendKeys(var_Prsnl_Mobile);
					System.out.println("var_Prsnl_Mobile \t\t = " + var_Prsnl_Mobile);

					// Field : 62 --> Telephone
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][phone]']"))
							.sendKeys(var_Prsnl_Telephone);
					System.out.println("var_Prsnl_Telephone \t\t = " + var_Prsnl_Telephone);

					// Field : 63 --> Email
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][email]']"))
							.sendKeys(var_Prsnl_EMail);
					System.out.println("var_Prsnl_EMail \t\t = " + var_Prsnl_EMail);

					// Field : 64 --> Pan
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][pan]']"))
							.sendKeys(var_Prsnl_Pan);
					System.out.println("var_Prsnl_Pan \t\t\t = " + var_Prsnl_Pan);

					// Field : 65 --> Nationality
//					driver.findElement(By.xpath("//input[@name='personal_information[0][nationality]']")).sendKeys(var_Prsnl_Nationality);
					System.out.println("var_Prsnl_Nationality \t\t = " + driver
							.findElement(By.xpath("//input[@name='personal_information[0][nationality]']")).getText());

					// Field : 66 --> Aadhar number
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][aadhar_number]']"))
							.sendKeys(var_Prsnl_Aadhar_Number);
					System.out.println("var_Prsnl_Aadhar_Number \t = " + var_Prsnl_Aadhar_Number);

					// Field : 67 --> Passport Number
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][passport_number]']"))
							.sendKeys(var_Prsnl_Passport_Number);
					System.out.println("var_Prsnl_Passport_Number \t = " + var_Prsnl_Passport_Number);

					// Field : 68 --> Passport Expiry Date
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='personal_information[0][passport_expiry_date]']"))
							.sendKeys(var_Prsnl_Passport_Exp_Date);
					System.out.println("var_Prsnl_Passport_Exp_Date \t = " + var_Prsnl_Passport_Exp_Date);

					/*
					 * Field 69 - 70 were assigned to Personal-Info input fields but code was NOT
					 * IMPLEMENTED yet. var_Prsnl_Own_House => Field[60] var_Prsnl_Add_Partner =>
					 * Field[70]
					 */

					// Action Items / Elements
					// BUTTON : Save
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-personal-form-submit-btn']")).click();
					System.out.println("\t< Personal-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println("\t< Personal-Info > POP-UP Status = "
							+ driver.findElement(By.id("swal2-content")).getText());
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T1);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Personal-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> RISK INFO ----------------
					 */
					System.out.println("\n-------------- MERCHANT ONBOARDING ==> RISK INFO ----------------");
					Thread.sleep(T2);
					driver.findElement(By.xpath("//a[normalize-space()='Risk Info']")).click();

					// Field : 71 --> Business Hours - Weekdays From
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[weekday_start_hour]']"))
							.sendKeys(var_Risk_Weekdays_from);
					System.out.println("var_Risk_Weekdays_from \t\t = " + var_Risk_Weekdays_from);

					// Field : 72 --> Business Hours - Weekdays To
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[weekday_end_hour]']"))
							.sendKeys(var_Risk_Weekdays_to);
					System.out.println("var_Risk_Weekdays_to \t\t = " + var_Risk_Weekdays_to);

					// Field : 73 --> Business Hours - Weekends From
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[weekend_start_hour]']"))
							.sendKeys(var_Risk_Weekends_from);
					System.out.println("var_Risk_Weekends_from \t\t = " + var_Risk_Weekends_from);

					// Field : 74 --> Business Hours - Weekends To
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[weekend_end_hour]']"))
							.sendKeys(var_Risk_Weekends_to);
					System.out.println("var_Risk_Weekends_to \t\t = " + var_Risk_Weekends_to);

					// Field : 75 --> Expected Card Business
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[expected_card_business]']"))
							.sendKeys(var_Risk_Expected_Card_Business);
					System.out.println("var_Risk_Expect_Card_Buss \t = " + var_Risk_Expected_Card_Business);

					// Field : 76 --> Average Bill Amount
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[average_bill_amount]']"))
							.sendKeys(var_Risk_Average_Bill_Amount);
					System.out.println("var_Risk_Average_Bill_Amount \t = " + var_Risk_Average_Bill_Amount);

					// Field : 77 --> Velocity Check Minutes
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[velocity_check_minutes]']"))
							.sendKeys(var_Risk_Velocity_Check_Minutes);
					System.out.println("var_Risk_Vlcty_Check_Mints \t = " + var_Risk_Velocity_Check_Minutes);

					// Field : 78 --> Velocity Check Count
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[velocity_check_count]']"))
							.sendKeys(var_Risk_Velocity_Check_Count);
					System.out.println("var_Risk_Velocity_Check_Count \t = " + var_Risk_Velocity_Check_Count);

					// Field : 79--> Settlement Days
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[settlement_days]']"))
							.sendKeys(var_Risk_Settlement_Days);
					System.out.println("var_Risk_Settlement_Days \t = " + var_Risk_Settlement_Days);

					// Field : 80--> Cibil Score
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[cibil_score]']"))
							.sendKeys(var_Risk_Cibil_Score);
					System.out.println("var_Risk_Cibil_Score \t\t = " + var_Risk_Cibil_Score);

//					// Field : 81-->Merchant Type Code

//					// Field : 82--> International Card Acceptance (Check-box)
					driver.findElement(By.xpath(
							"//input[@id='internationalCardAcceptance']/following-sibling::label[contains(@for,'internationalCardAcceptance')]"))
							.click();

					// Field : 83--> Daily
					Thread.sleep(T3);
					driver.findElement(By.xpath("//input[@name='risk_information[international_max_daily_usage]']"))
							.sendKeys(var_Risk_International_Daily);
					System.out.println("var_Risk_International_Daily \t = " + var_Risk_International_Daily);

					// Field : 84--> Weekly
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[internatioanl_max_weekly_usage]']"))
							.sendKeys(var_Risk_International_Weekly);
					System.out.println("var_Risk_International_Weekly \t = " + var_Risk_International_Weekly);

					// Field : 85--> Monthly
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[international_max_monthly_usage]']"))
							.sendKeys(var_Risk_International_Monthly);
					System.out.println("var_Risk_International_Monthly \t = " + var_Risk_International_Monthly);

					// Field : 86--> Name of POS
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[current_pos_name]']"))
							.sendKeys(var_Risk_Name_of_POS);
					System.out.println("var_Risk_Name_of_POS \t\t = " + var_Risk_Name_of_POS);

//				// Field : 87--> Member since

//					driver.findElement(By.xpath("//input[@class='form-control js_date-picker-alt']")).click();
//					driver.findElement(By.xpath("//td[@class='today active start-date active end-date available']"))
//							.click();
//					driver.findElement(By.xpath("(//button[@type='button'][normalize-space()='Apply'])[2]")).click();
//					System.out.println("var_Establised_Year = " + driver
//							.findElement(By.xpath("//td[@class='today active start-date active end-date available']"))
//							.getText());

					// Field : 88--> Current MDR
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='risk_information[current_mdr]']"))
							.sendKeys(var_Risk_Current_MDR);
					System.out.println("var_Risk_Current_MDR \t\t = " + var_Risk_Current_MDR);

					// Field 89 - 96 --> Transaction sets Auto selected

					// Field : 97--> AEPS Daily

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][0][daily_limit]']"))
							.sendKeys(var_Risk_AEPS_Daily);
					System.out.println("var_Risk_AEPS_Daily \t\t = " + var_Risk_AEPS_Daily);

					// Field : 98--> AEPS Weekly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][0][weekly_limit]']"))
							.sendKeys(var_Risk_AEPS_Weekly);
					System.out.println("var_Risk_AEPS_Weekly \t\t = " + var_Risk_AEPS_Weekly);

					// Field : 99--> AEPS Monthly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][0][monthly_limit]']"))
							.sendKeys(var_Risk_AEPS_Monthly);
					System.out.println("var_Risk_AEPS_Monthly \t\t = " + var_Risk_AEPS_Monthly);

					// Field : 100--> AEPS Minimum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][0][minimum_amount]']"))
							.sendKeys(var_Risk_AEPS_Minimum);
					System.out.println("var_Risk_AEPS_Minimum \t\t = " + var_Risk_AEPS_Minimum);

					// Field : 101--> AEPS Maximum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][0][maximum_amount]']"))
							.sendKeys(var_Risk_AEPS_Maximum);
					System.out.println("var_Risk_AEPS_Maximum \t\t = " + var_Risk_AEPS_Maximum);

					// Field : 102--> UPI Daily

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][1][daily_limit]']"))
							.sendKeys(var_Risk_UPI_Daily);
					System.out.println("var_Risk_UPI_Daily \t\t = " + var_Risk_UPI_Daily);

					// Field : 103--> UPI Weekly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][1][weekly_limit]']"))
							.sendKeys(var_Risk_UPI_Weekly);
					System.out.println("var_Risk_UPI_Weekly \t\t = " + var_Risk_UPI_Weekly);

					// Field : 104--> UPI Monthly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][1][monthly_limit]']"))
							.sendKeys(var_Risk_UPI_Monthly);
					System.out.println("var_Risk_UPI_Monthly \t\t = " + var_Risk_UPI_Monthly);

					// Field : 104--> UPI Minimum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][1][minimum_amount]']"))
							.sendKeys(var_Risk_UPI_Minimum);
					System.out.println("var_Risk_UPI_Minimum \t\t = " + var_Risk_UPI_Minimum);

					// Field : 105--> UPI Maximum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][1][maximum_amount]']"))
							.sendKeys(var_Risk_UPI_Maximum);
					System.out.println("var_Risk_UPI_Maximum \t\t = " + var_Risk_UPI_Maximum);

					// Field : 106--> MicroATM Daily

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][2][daily_limit]']"))
							.sendKeys(var_Risk_MicroATM_Daily);
					System.out.println("var_Risk_MicroATM_Daily \t = " + var_Risk_MicroATM_Daily);

					// Field : 107--> MicroATM Weekly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][2][weekly_limit]']"))
							.sendKeys(var_Risk_MicroATM_weekly);
					System.out.println("var_Risk_MicroATM_weekly \t = " + var_Risk_MicroATM_weekly);

					// Field : 108--> MicroATM Monthly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][2][monthly_limit]']"))
							.sendKeys(var_Risk_MicroATM_Monthly);
					System.out.println("var_Risk_MicroATM_Monthly \t = " + var_Risk_MicroATM_Monthly);

					// Field : 109--> MicroATM Minimum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][2][minimum_amount]']"))
							.sendKeys(var_Risk_MicroATM_Minimum);
					System.out.println("var_Risk_MicroATM_Minimum \t = " + var_Risk_MicroATM_Minimum);

					// Field : 110--> MicroATM Maximum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][2][maximum_amount]']"))
							.sendKeys(var_Risk_MicroATM_Maximum);
					System.out.println("var_Risk_MicroATM_Maximum \t = " + var_Risk_MicroATM_Maximum);

					// Field : 110--> POS Daily

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][3][daily_limit]']"))
							.sendKeys(var_Risk_POS_Daily);
					System.out.println("var_Risk_POS_Daily \t\t = " + var_Risk_POS_Daily);

					// Field : 110--> POS Weekly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][3][weekly_limit]']"))
							.sendKeys(var_Risk_POS_Weekly);
					System.out.println("var_Risk_POS_Weekly \t\t = " + var_Risk_POS_Weekly);

					// Field : 110--> POS Monthly

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][3][monthly_limit]']"))
							.sendKeys(var_Risk_POS_Monthly);
					System.out.println("var_R0isk_POS_Monthly \t\t = " + var_Risk_POS_Monthly);

					// Field : 110--> POS Minimum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][3][minimum_amount]']"))
							.sendKeys(var_Risk_POS_Minimum);
					System.out.println("var_Risk_POS_Minimum \t\t = " + var_Risk_POS_Minimum);

					// Field : 110--> POS Maximum

					driver.findElement(
							By.xpath("//input[@name='risk_information[transaction_limits][3][maximum_amount]']"))
							.sendKeys(var_Risk_POS_Maximum);
					System.out.println("var_Risk_POS_Maximum \t\t = " + var_Risk_POS_Maximum);

					// Action Items / Elements
					// BUTTON : Save
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-risk-form-submit-btn']")).click();
					System.out.println("\t< Risk-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T5);
					System.out.println(
							"\t< Risk-Info > POP-UP Status = " + driver.findElement(By.id("swal2-content")).getText());
//					Flu_Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T1);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Risk-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> BANK INFO ----------------
					 */
					System.out.println("\n-------------- MERCHANT ONBOARDING ==> BANK INFO ----------------");
					Thread.sleep(T1);
					driver.findElement(By.xpath("//a[normalize-space()='Bank']")).click();
					Thread.sleep(T2);

					// Field : 116 --> Bank Account Type
					driver.findElement(By.xpath("//select[@name='bank_information[account_type]']")).click();
					Thread.sleep(T1);
					driver.findElement(By.xpath("(//select[@name='bank_information[account_type]'])//option[@value='"
							+ var_Bank_Acct_Type + "']")).click();

					System.out.println("var_Bank_Acct_Type \t = " + var_Bank_Acct_Type);

					// Field : 117 --> Bank Account Number
					driver.findElement(By.xpath("//input[@name='bank_information[account_number]']"))
							.sendKeys(var_Bank_Acct_number);
					System.out.println("var_Bank_Acct_number \t = " + var_Bank_Acct_number);

					// Field : 118 --> Bank IFSC code
					driver.findElement(By.xpath("//span[@id='select2-js_ifsc-bank-select2-container']")).click();
					driver.findElement(By.xpath(
							"//span[@class='select2-search select2-search--dropdown']//input[@role='searchbox']"))
							.sendKeys(var_Bank_IFSC_code);
					Thread.sleep(T2);
					driver.findElement(
							By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"))
							.click();

					System.out.println("var_Bank_IFSC_code  \t = " + var_Bank_IFSC_code);

					// Action Items / Elements
					// BUTTON : Save
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-bank-form-submit-btn']")).click();
					System.out.println("\t< Bank-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println(
							"\t< Bank-Info > POP-UP Status = " + driver.findElement(By.id("swal2-content")).getText());
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T1);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Bank-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> Terminals ----------------
					 */

					System.out.println("\n-------------- MERCHANT ONBOARDING ==> Terminals ----------------");
//					Thread.sleep(T1);
//					driver.findElement(By.xpath("//a[normalize-space()='Terminals']")).click();

					Thread.sleep(T5);
//					driver.findElement(By.xpath("//a[@class='js_merchant-terminals-create-btn btn btn-outline-success mb-3 ld-ext-right float-right']")).click();
					driver.findElement(By.xpath(
							"//div[@id='js_merchant-terminals']/div[1]/child::a[normalize-space()='Add Terminal']"))
							.click();
//					driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/div[1]/div[2]/div/div[7]/div[1]/div[1]/a")).click();
//

					// Field : 119 --> var_Terminal_Location
					Thread.sleep(T3);
					driver.findElement(By.xpath("//input[@name='location']")).sendKeys(var_Terminal_Location);
					System.out.println("var_Terminal_Location \t\t\t = " + var_Terminal_Location);

					// Field : 120 --> var_Terminal_Address
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='address']")).sendKeys(var_Terminal_Address);
					System.out.println("var_Terminal_Address \t\t\t = " + var_Terminal_Address);

					// Field : 121 --> var_Terminal_Pincode
					Thread.sleep(T1);
					driver.findElement(By.xpath("//span[@id='select2-js_terminal-pincode-select2-container']")).click();
					Thread.sleep(T2);
					driver.findElement(
							By.xpath("//input[@aria-controls='select2-js_terminal-pincode-select2-results']"))
							.sendKeys(var_Terminal_Pincode);
					Thread.sleep(T1);
					driver.findElement(
							By.xpath("//li[@class='select2-results__option select2-results__option--highlighted']"))
							.click();
					System.out.println("var_Terminal_Pincode \t\t\t = " + var_Terminal_Pincode);

					// Field : 122 --> var_Terminal_Sim_Number
					Thread.sleep(T1);
					driver.findElement(By.xpath("//input[@name='sim_number']")).sendKeys(var_Terminal_Sim_Number);
					System.out.println("var_Terminal_Sim_Number \t\t = " + var_Terminal_Sim_Number);

					// Field : 123 --> var_Terminal_Terminal_Type
					Thread.sleep(T3);
					driver.findElement(By.xpath("//span[@id='select2-js_terminal-type-select2-container']")).click();
					Thread.sleep(T2);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Terminal_Terminal_Type + "']"))
							.click();
					System.out.println("var_Terminal_Terminal_Type \t\t = " + var_Terminal_Terminal_Type);

					// Field : 124 --> var_Terminal_Device_Model
					Thread.sleep(T3);
					driver.findElement(By.xpath("//span[@id='select2-js_device-model-select2-container']")).click();
					Thread.sleep(T2);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Terminal_Device_Model + "']")).click();
					System.out.println("var_Terminal_Device_Model \t\t = " + var_Terminal_Device_Model);

					// Field : 125 --> var_Terminal_Device_Owned

					driver.findElement(By.xpath("//select[@name='device_owned']")).click();
					Select Device_owned = new Select(driver.findElement(By.xpath("//select[@name='device_owned']")));
					Device_owned.selectByVisibleText(var_Terminal_Device_Owned);
					Thread.sleep(T2);
					System.out.println("var_Terminal_Device_Owned \t\t = " + var_Terminal_Device_Owned);

					// Field : 126 --> var_Terminal_Device_Price

					driver.findElement(By.xpath("//input[@name='device_price']")).sendKeys(var_Terminal_Device_Price);
					System.out.println("var_Terminal_Device_Price \t\t = " + var_Terminal_Device_Price);

					// Field : 127 --> var_Terminal_Installation_Fee

					driver.findElement(By.xpath("//input[@name='installation_fee']"))
							.sendKeys(var_Terminal_Installation_Fee);
					System.out.println("var_Terminal_Installation_Fee \t\t = " + var_Terminal_Installation_Fee);

					// Field : 128 --> var_Terminal_Rental_Plan
					Thread.sleep(T3);
					driver.findElement(By.xpath("//span[@id='select2-js_rental-plan-select2-container']")).click();
					Thread.sleep(T3);
					driver.findElement(By.xpath("//li[normalize-space()='" + var_Terminal_Rental_Plan + "']")).click();
					System.out.println("var_Terminal_Rental_Plan \t\t = " + var_Terminal_Rental_Plan);

					//
					// Field : 129 --> var_Terminal_Rental_Type

//					driver.findElement(By.xpath("//select[@name='rental_type']")).click();
//					Select Rental_Type = new Select(
//							driver.findElement(By.xpath("//select[@name='rental_type']")));
//					Rental_Type.selectByVisibleText(var_Terminal_Rental_Type);
//					Thread.sleep(T2);
//					System.out.println("var_Terminal_Rental_Type = " + var_Terminal_Rental_Type);

//					// Field : 130 --> var_Terminal_Rental_Mode
//					driver.findElement(By.xpath("//select[@name='rental_mode']")).click();
//					Select Rental_Type = new Select(
//							driver.findElement(By.xpath("//select[@name='rental_mode']']")));
//					Rental_Type.selectByVisibleText(var_Terminal_Rental_Mode);
//					Thread.sleep(T2);
//					System.out.println("var_Terminal_Rental_Mode = " + var_Terminal_Rental_Mode);
//

					// Field : 131 --> var_Terminal_Rental_Type

//					driver.findElement(By.xpath("//input[@name='rental_threshold']")).sendKeys(var_Terminal_Rental_Threshold);
//					System.out.println("var_Terminal_Rental_Threshold = " + var_Terminal_Rental_Threshold);

					// Field : 132 --> var_Maximum_usage_daily

					driver.findElement(By.xpath("//input[@name='max_usage_daily']"))
							.sendKeys(var_Terminal_Maximum_Usage_Daily);
					System.out.println("var_Terminal_Maximum_Usage_Daily \t = " + var_Terminal_Maximum_Usage_Daily);

					// Field : 133 --> var_Maximum_usage_weekly

					driver.findElement(By.xpath("//input[@name='max_usage_weekly']"))
							.sendKeys(var_Terminal_Maximum_Usage_Weekly);
					System.out.println("var_Terminal_Maximum_Usage_Weekly \t = " + var_Terminal_Maximum_Usage_Weekly);

					// Field : 134 --> var_Maximum_usage_monthly

					driver.findElement(By.xpath("//input[@name='max_usage_montly']"))
							.sendKeys(var_Terminal_Maximum_Usage_Monthly);
					System.out.println("var_Terminal_Maximum_Usage_Monthly \t = " + var_Terminal_Maximum_Usage_Monthly);

					// Field : 135 --> var_Terminal_Velocity_Check_Minutes

					driver.findElement(By.xpath("//input[@name='velocity_check_minutes']"))
							.sendKeys(var_Terminal_Velocity_Check_Minutes);
					System.out
							.println("var_Terminal_Velocity_Check_Minutes \t = " + var_Terminal_Velocity_Check_Minutes);

					// Field : 136 --> var_Terminal_Velocity_Check_Count

					driver.findElement(By.xpath("//input[@name='velocity_check_count']"))
							.sendKeys(var_Terminal_Velocity_Check_Count);
					System.out.println("var_Terminal_Velocity_Check_Count \t = " + var_Terminal_Velocity_Check_Count);

					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_terminals-form-submit-btn']")).click();
					System.out.println("\t< Terminal-Info > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println("\t< Terminal-Info > POP-UP Status = "
							+ driver.findElement(By.id("swal2-content")).getText());
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// BUTTON : Next
					Thread.sleep(T1);
					driver.findElement(By.xpath("//button[@id='js_merchant-form-next']")).click();
					System.out.println("\t< Terminal-Info > NEXT button  = CLICKED");

					/**
					 * -------------- Menu : MERCHANT ONBOARDING ==> KYC ----------------
					 */

					System.out.println("\n-------------- MERCHANT ONBOARDING ==> KYC ----------------");
//					Thread.sleep(T1);
//					driver.findElement(By.xpath("//a[normalize-space()='KYC']")).click();
//					Thread.sleep(T2);

					// Field :138 --> PAN Card
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[0][number]']")).sendKeys(var_KYC_PAN_Doc_Num);
					System.out.println("var_KYC_PAN_Doc_Num \t\t\t = " + var_KYC_PAN_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='PAN Card']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_PAN_Proof_Link);
					System.out.println(
							"var_KYC_PAN_Proof_Link \t\t\t = " + var_KYC_Proof_Drive_Link + var_KYC_PAN_Proof_Link);

					// Field :139 --> Aadhaar Card
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[1][number]']"))
							.sendKeys(var_KYC_Aadhaar_Doc_Num);
					System.out.println("var_KYC_Aadhaar_Doc_Num \t\t = " + var_KYC_Aadhaar_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Aadhaar']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Aadhar_Proof_Link);
					System.out.println(
							"var_KYC_Aadhar_Proof_Link \t\t = " + var_KYC_Proof_Drive_Link + var_KYC_Aadhar_Proof_Link);

					// Field :140 --> Cancelled Cheque
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[2][number]']"))
							.sendKeys(var_KYC_Bank_Cancel_Cheque_Doc_Num);
					System.out.println("var_KYC_Bank_Cancel_Cheque_Doc_Num \t = " + var_KYC_Bank_Cancel_Cheque_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Cancelled Cheque']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Bank_Cancel_Cheque_Proof_Link);
					System.out.println("var_KYC_Bank_Cancel_Cheque_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Bank_Cancel_Cheque_Proof_Link);

					// Field :141 --> Business Address Proof
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[3][number]']"))
							.sendKeys(var_KYC_Businees_Addr_Doc_Num);
					System.out.println("var_KYC_Businees_Addr_Doc_Num \t\t = " + var_KYC_Businees_Addr_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Business Address Proof']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Business_Addr_Proof_Link);
					System.out.println("var_KYC_Business_Addr_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Business_Addr_Proof_Link);

					// Field :142 --> Passport
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[4][number]']"))
							.sendKeys(var_KYC_Passport_Doc_Num);
					System.out.println("var_KYC_Passport_Doc_Num \t\t = " + var_KYC_Passport_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Passport']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Passport_Proof_Link);
					System.out.println("var_KYC_Passport_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Passport_Proof_Link);

					// Field :143 --> Driving License
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[5][number]']"))
							.sendKeys(var_KYC_Driving_License_Doc_Num);
					System.out.println("var_KYC_Driving_License_Doc_Num \t\t = " + var_KYC_Driving_License_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Driving Licence']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Driving_License_Proof_Link);
					System.out.println("var_KYC_Driving_License_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Driving_License_Proof_Link);

					// Field :144 --> Ration Card
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[6][number]']"))
							.sendKeys(var_KYC_Ration_Card_Doc_Num);
					System.out.println("var_KYC_Ration_Card_Doc_Num \t\t = " + var_KYC_Ration_Card_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Ration Card']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Ration_Card_Proof_Link);
					System.out.println("var_KYC_Ration_Card_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Ration_Card_Proof_Link);

					// Field :145 --> Voters ID
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[7][number]']"))
							.sendKeys(var_KYC_Voters_ID_Doc_Num);
					System.out.println("var_KYC_Voters_ID_Doc_Num \t\t = " + var_KYC_Voters_ID_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Voter ID']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Voters_ID_Proof_Link);
					System.out.println("var_KYC_Voters_ID_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Voters_ID_Proof_Link);

					// Field :146 --> Photograph
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[8][number]']"))
							.sendKeys(var_KYC_Latest_Photo_Doc_Num);
					System.out.println("var_KYC_Latest_Photo_Doc_Num = " + var_KYC_Latest_Photo_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Photograph']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Latest_Photo_Proof_Link);
					System.out.println("var_KYC_Latest_Photo_Proof_Link = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Latest_Photo_Proof_Link);

					// Field :147 --> Aggregator and Merchant Aggrement
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[9][number]']"))
							.sendKeys(var_KYC_Aggr_Merchant_Aggrement_Doc_Num);
					System.out.println(
							"var_KYC_Aggr_Merchant_Aggrement_Doc_Num = " + var_KYC_Aggr_Merchant_Aggrement_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='Aggregator and Merchant Aggrement']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_Merchant_Aggrement_Proof_Link);
					System.out.println("var_KYC_Merchant_Aggrement_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_Merchant_Aggrement_Proof_Link);

					// Field :148 --> GST Registration Certificate
					Thread.sleep(T2);
					driver.findElement(By.xpath("//input[@name='documents[10][number]']"))
							.sendKeys(var_KYC_GST_Certificate_Doc_Num);
					System.out.println("var_KYC_GST_Certificate_Doc_Num \t\t = " + var_KYC_GST_Certificate_Doc_Num);
					driver.findElement(By.xpath("//input[@data-value='GST Registration Certificate']"))
							.sendKeys(var_KYC_Proof_Drive_Link + var_KYC_GST_Certificate_Proof_Link);
					System.out.println("var_KYC_GST_Certificate_Proof_Link \t = " + var_KYC_Proof_Drive_Link
							+ var_KYC_GST_Certificate_Proof_Link);

					System.out.println("\n *** Document Uploaded Successfully !!!");
//
					Thread.sleep(T3);
					driver.findElement(By.xpath("//button[@id='js_merchants-kyc-form-submit-btn']")).click();
					System.out.println("\t< KYC > SAVE button  = CLICKED");

					// POP-UPs : Alert
					Thread.sleep(T3);
					System.out.println(
							"\t< KYC > POP-UP Status = " + driver.findElement(By.id("swal2-content")).getText());
					driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

					// Merchant Tab close
					Thread.sleep(T2);
					driver.findElement(By.xpath("//*[@id=\"js_merchant-form\"]/div/div/div[1]/button/span")).click();

				} /* Merchant on-board FOR-Loop */
				
			} /* Login FOR-loop */
			
			/* CLOSING - Browser Driver  */
			Thread.sleep(T5);
			driver.close();

		} catch (Exception e) {
			System.err.println(e);
		} 

	} /* Main() ends*/

} /* Class() Ends*/
