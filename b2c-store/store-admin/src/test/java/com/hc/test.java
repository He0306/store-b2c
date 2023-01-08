package com.hc;

import com.hc.constants.UserConstants;
import com.hc.utils.MD5Util;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
public class test {

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("admin" + UserConstants.USER_SLAT));
    }
}
