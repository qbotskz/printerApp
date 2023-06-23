package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
public class WaiterShiftDTO {

    Boolean opened;

    Date openingTime;

    public Map<Object, Object> getJson(){
        Map<Object, Object> shift = new TreeMap<>();
        Map<Object, Object> map = new TreeMap<>();

        map.put("opened", opened);
        map.put("openingTime", openingTime);

        shift.put("waiterShift", map);

        return shift;
    }

}
