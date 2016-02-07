/*
 * Copyright (c) 2015, Rachel Orrell <rachel.orrell@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Rachel Orrell,  nor the names of its 
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Utility class to perform operations needed in scenes (i.e., changing scenes, formatting data, showing dialogs)
 * @author Rachel Orrell
 */
public class SceneUtility {

    private IndexedController controller;

    /**
     *
     */
    public static String rootPath = "";
    private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public SceneUtility() {
    }

    /**
     * Set root path for fxml files
     * @param path the string containing the root path for all fxml files in the project
     */
    public static void setRootPath(String path) {
        rootPath = path;
    }
    
    /**
     * Loads a new scene in the current stage with both a primary and secondary id
     * @param ae ActionEvent from button calling this method
     * @param currentFxml filename of current scene
     * @param targetFxml filename of desired scene
     * @param changer the SceneChanger object defining parameters
     * @see SceneChanger
     */
    public void changeScene(ActionEvent ae, String currentFxml, String targetFxml, SceneChanger changer) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rootPath + targetFxml)); //create loader for desired scene
        Parent p;
        try {
            p = loader.load(); //could potentially throw IOException
            controller = loader.getController(); //set SceneUtility field to loader's controller (controller of desired fxml page)
            controller.setMainIndex(changer.getMainIndex()); //set primary id for controller from method parameter
            controller.setSubIndex(changer.getSubIndex()); //set secondary id for controller from method parameter
            controller.setParent(p); //set parent for controller from parent object assigned above
            controller.setPrevious(currentFxml); //set previous for controller from method parameter holding the current fxml file name
            controller.setMsg(changer.getMsg()); //set custom message for controller
            controller.setup(); //call the setup method now that all parameters have been set (inherited by all controllers in this project from IndexedController interface)
            Scene s = new Scene(p); //create scene from parent node
            Node node = changer.getNode() == null ? (Node)ae.getTarget() : changer.getNode();
            Stage app_stage = (Stage) node.getScene().getWindow();  //get the stage from the calling button's window
            app_stage.setScene(s); //set the scene for this stage to the desired scene
            app_stage.show(); //show the stage; this will be seemless provided the two fxml root nodes are the same size and look similar
        } catch (IOException ex) {
            Logger.getLogger(SceneUtility.class.getName()).log(Level.SEVERE, null, ex); //log exception if thrown by loader.load() operation
        }
    }
    
    /**
     * Converts a String into a SimpleStringProperty
     * @param text the String to convert
     * @return SimpleStringProperty
     */
    private static SimpleStringProperty getStringProperty(String text) {
        return new SimpleStringProperty(text);
    }
    
    /**
     * Formats a StringProperty as a phone number (xxx) xxx-xxxx
     * @param textProperty the StringProperty to format
     * @return SimpleStringProperty
     */
    public static SimpleStringProperty formatAsPhoneNumberProperty(StringProperty textProperty) {
        return getStringProperty(formatAsPhoneNumber(textProperty.get()));
    }
    
    /**
     * Formats a String as a phone number (xxx) xxx-xxxx
     * @param text the String to format
     * @return String
     */
    public static String formatAsPhoneNumber(String text) {
        if(text == null) return "";
        text = removeNumberFormatting(text, null); //remove non-digit characters
        StringBuilder phone = new StringBuilder(text);
        if(text.matches("\\A\\d{10}\\Z")) //if we have exactly ten digits, do all the formatting, working backward
            phone.insert(6, "-").insert(3, ") ").insert(0, "(");
        else {
            if(phone.length() > 10)
                phone.setLength(10); //cut excess characters
            if(phone.length() > 6) //do formatting incrementally, working backward, depending on length
                phone.insert(6, "-");
            if(phone.length() > 3)
                phone.insert(3, ") ");
            if(phone.length() > 0)
                phone.insert(0, "(");
        }
        return phone.toString();
    }
    
    /**
     * Removes all non-digit characters from a String excepting specified characters
     * @param text the String from which to remove formatting
     * @param allowedChars an array of non-digit characters that should not be removed
     * @return String
     */
    public static String removeNumberFormatting(String text, char[] allowedChars) {
        if(text == null) return "";
        if(allowedChars == null) allowedChars = new char[0]; //if null is passed as the array, just initialize it as an empty array to avoid problems
        StringBuilder num = new StringBuilder(text); //allows deleteCharAt function
        for(int i=0; i<num.length(); i++) { 
            String ch = num.substring(i, i + 1); //get current character in num as String to allow matches function
            if(!ch.matches("\\d{1}")) { //if ch is not a digit
                boolean notAllowedChar = true; //initially assume that ch is not an allowed character
                for(int j=0; j<allowedChars.length; j++) { //go through the allowed characters (non-digit)
                    if(ch.equals(Character.toString(allowedChars[j]))) { //if this character equals ch
                        notAllowedChar = false; //then notAllowedChar=false (meaning that ch is an allowed character)
                        break; //now we don't need to go through the rest of the array
                    }
                }
                if(notAllowedChar) //at this point we know ch isn't a digit, and if it's also not an allowedChar
                    num.deleteCharAt(i--); //delete this character out of num, and decrement i so we don't miss anything in the iteration
            }
        }
        return num.toString();
    }
    
    /**
     * Format String as a whole number with commas delimiting the thousands
     * @param text the String to be formatted
     * @return String
     */
    public static String formatAsNumber(String text) {
        long num = new Long(removeNumberFormatting(text, null)); //use long in case of large numbers, remove number formatting with no allowed non-digit characters
        DecimalFormat myFormat = new DecimalFormat("###,###"); //create custom decimal format with commas delimiting the thousands
        return myFormat.format(num); //format the number (returns string), and return result
    }
    
    /**
     * Format String as currency
     * @param text the String to format
     * @return String
     */
    public static String formatAsCurrency(String text) {
        if(text == null || text.trim().equals("")) return "";
        BigDecimal num = new BigDecimal(removeNumberFormatting(text, new char[]{'.'})); //create Big Decimal from text with all characters that are neither digits nor periods removed
        NumberFormat myFormat = DecimalFormat.getCurrencyInstance(); //get currency formatter
        return myFormat.format(num); //format the number (returns string), and return result
    }
    
    /**
     * Format StringProperty as currency
     * @param text the StringProperty to format
     * @return SimpleStringProperty
     */
    public static SimpleStringProperty formatAsCurrencyProperty(StringProperty text) {
        return getStringProperty(formatAsCurrency(text.get()));
    }
    
    /**
     * Format String as number with two decimal places
     * @param text the String to format
     * @return String
     */
    public static String formatAsDecimal(String text) {
        String formatted = formatAsCurrency(text); //format first as that will handle null values
        if(formatted.equals("")) return formatted; //return empty string if that is the result
        return formatted.substring(1); //otherwise remove dollar sign
    }
    
    /**
     * Format StringProperty as number with two deimal places
     * @param text the StringProperty to format
     * @return SimpleStringProperty
     */
    public static SimpleStringProperty formatAsDecimalProperty(StringProperty text) {
        return getStringProperty(formatAsDecimal(text.get()));
    }
    
    /**
     * Format String as date xx/xx/xxxx, allowing shorthand (i.e., 1/1 or 1/1/14)
     * @param text the String to format
     * @return String
     * @throws IllegalArgumentException if not valid date or valid date shorthand
     */
    public static String formatAsDate(String text) throws IllegalArgumentException {
        if(text == null) return "";
        int year = Calendar.getInstance().get(Calendar.YEAR); //get current year
        if(text.matches("\\A\\d{1,2}/\\d{1,2}\\Z")) //text omits year
            text += "/" + year; //add current year
        else if(text.matches("\\A\\d{1,2}/\\d{1,2}/\\d{2}")) //text uses two digit year
            text = text.substring(0, text.lastIndexOf("/")+1) + Integer.toString(year).substring(0, 2) + 
                    text.substring(text.lastIndexOf("/")+1, text.length()); //add first two digits of current year
        try {   //try to format the text as a date. 
            return parseAndFormatDate(text); //if it is a valid date, return the formated date
        } 
        catch (ParseException pe) { //if it isn't a valid date
            throw new IllegalArgumentException("Invalid date."); //convert ParseException to IllegalArgumentException with message
        }
    }
    
    /**
     * Format String as date xx/xx/xxxx
     * @param text the String to format
     * @return String
     * @throws ParseException if not valid date
     */
    private static String parseAndFormatDate(String text) throws ParseException {
        Date d = dateFormat.parse(text); //will throw ParseException if the text is not a valid date
        return dateFormat.format(d); //if it is a valid date, return the formated date
    }
    
    /**
     * Format Date as MM/dd/yyyy
     * @param date the Date object to format
     * @return SimpleStringProperty
     */
    public static SimpleStringProperty formatAsDateProperty(Date date) {
        return new SimpleStringProperty(dateFormat.format(date)); //return formatted date
    }
    
    /**
     * Format Date as MM/dd/yyyy
     * @param date the Date object to format
     * @return String
     */
    public static String formatAsDate(Date date) {
        return dateFormat.format(date);
    }
    
    /**
     * Parse date String into format for a Date field in a derby database; invalid input will result in a null return value
     * @param text the date String to parse
     * @return Date object
     */
    public static Date parseDateForDB(String text) {
        Date d = null;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {   
            d = dateFormat.parse(text); //try to parse text as date object
        } 
        catch (ParseException pe) {
            //if the parse fails, the result will just be that a null Date will be returned, which is fine as long as database accepts null values
        }
        return d;
    }
    
    /**
     * Show a confirmation dialog to the user
     * @param text dialog content text will be "Are you sure you want to " + text + "?"
     * @return boolean indicating whether the user clicked OK
     */
    public static boolean showConfirmationDialog(String text) {
        Alert alert = new Alert(AlertType.CONFIRMATION); //create dialog with OK and cancel buttons
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to " + text + "?");

        return alert.showAndWait().get() == ButtonType.OK; //return boolean indicating whether user clicked OK
    }
    
    /**
     * Show a custom error dialog to the user
     * @param text the content text to show the user
     */
    public static void showError(String text) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);

        alert.showAndWait(); //show the dialog and wait for the user to click OK
    }
    
    /**
     * Show an error dialog associated with a specific action to the user; intended to promote uniformity of error dialog message syntax
     * @param text a phrase beginning with a gerund; the content text will be "There was an error " + text + "."
     */
    public static void showActionError(String text) {
        showError("There was an error " + text + ".");
    }
    
    /**
     * Show an error dialog associated with putting too large a decimal number in a text field (assumes a number formatted to 2 decimal places)
     * @param fieldName the text of the label associated with the textfield
     * @param precision the allowed precision of the field (total number of digits allowed to the left and right of the decimal)
     */
    public static void showDecimalPrecisionError(String fieldName, int precision) {
        String capitalized = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        showError(capitalized + " cannot have more than " + precision + " digits after being formatted to 2 decimal places.");
    }
    
    /**
     * Show an error stating "Please fill in all required fields (indicated with bold red font)."
     */
    public static void showMissingFieldError() {
        showError("Please fill in all required fields (indicated with bold red font).");
    }
    
    /**
     * Checks whether all the given TextFields have been filled out
     * @param fields a collection of TextFields
     * @return true if all fields completed; false otherwise
     */
    public static boolean requiredFieldsCompleted(Collection<TextField> fields) {
        for(TextField field : fields) { //iterate through collection
            if(field.getText().trim().equals("")) //if text of TextField, after trimming, is empty
                return false;
        }
        return true;
    }
}
