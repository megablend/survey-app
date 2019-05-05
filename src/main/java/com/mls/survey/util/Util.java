/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.util;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public final class Util {
    
    private Util() {}
    
    public static double answersPercentage(int totalAnswers, int totalResponse) {
        return ((double) (totalAnswers * 100) / totalResponse);
    }
}
