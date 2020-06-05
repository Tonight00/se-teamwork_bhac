package com.shakespace.template.in_fragment4;

public class log_state {

    private static int sta = 0;

    public static int getvalue() {
        return sta;
    }

    public static void setvalue(int x) {
        log_state.sta = x;
    }
}

