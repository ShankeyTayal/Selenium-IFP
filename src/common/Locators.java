package common;

public interface Locators
{
  interface Button
  {
    public static final String LOGIN_BTN = "//form[input[@id='l_password']]/button[text()='Submit']"; 
    public static final String SEARCH_BUTTON = "go"; 
  }
  
  interface TextField
  {
    public static final String LOGIN_USERNAME = "l_email";
    public static final String LOGIN_PASSWORD = "l_password";
    public static final String SEARCH = "search";
    
  } 
  
  interface Link
  {
    public static final String LOGO_LINK = "logo-link";
    public static final String LOGOUT = "Logout";
    public static final String POLICY_NUMBER = "Policy Number";
  } 
  
  interface GenericXpath
  {
    public static final String ROW_XPATH = "//tr[starts-with(@id,'row_')]";
    public static final String DOWNLOAD_REPORT = "//input[@value = 'Download Report ']";
    
    
  } 
  interface ClassName
  {
    public static final String COLUMN= "table_text";
    
  } 
}
