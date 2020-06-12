package com.example.financial_savings.modules.checks;

public class CheckRegexModule {

    private static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    private static final String REGEX_PASSWORD = "[a-zA-Z0-9]{6,50}";
    private static final String REGEX_NAME = "[^a-z0-9A-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]";

    public static boolean isEmail(String str) {
//        return str.matches(REGEX_EMAIL);
        return true;
    }

    public static boolean isPassword(String str) {
        return str.matches(REGEX_PASSWORD);
    }

    public static boolean isName(String str) {
//        return str.matches(REGEX_NAME);
        return true;
    }
}
