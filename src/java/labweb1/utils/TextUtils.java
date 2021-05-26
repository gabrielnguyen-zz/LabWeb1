/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.utils;

/**
 *
 * @author Gabriel Nguyen
 */
public class TextUtils {

    public String convertDate(String date) {
        String year = date.split("/")[0];
        String month = date.split("/")[1];
        String day = date.split("/")[2];
        return year + "-" + month +"-" + day;

    }
    
    public String convertDate2(String date) {
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return year + "/" + month +"/" + day;

    }
}
