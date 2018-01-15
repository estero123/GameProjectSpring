package com.tylkowski.gamecms.controller;

import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes(value = {"userRoles", "device"})
public class MainController {

    @ModelAttribute("device")
    public String isMobile(Device device) {
        String deviceType;
        if (device.isNormal()) {
            deviceType = "computer";
        } else {
            deviceType = "mobile";
        }
        return deviceType;
    }

}
