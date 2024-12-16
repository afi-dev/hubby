package com.afidev.hubby;

import net.minecraft.text.Text;

public class TextUtils {

    public static Text parseColor(String message) {
        message = message.replaceAll("&([0-9a-fk-or])", "§$1");
        return Text.literal(message);
    }
}
