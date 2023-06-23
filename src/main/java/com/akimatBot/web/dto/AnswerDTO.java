package com.akimatBot.web.dto;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
public class AnswerDTO {
    String key;
    String answer;

    public Map<Object, Object> getJson(){
        Map<Object, Object> map = new TreeMap<>();
        if (key != null){
            map.put(key, answer);
        }else {
            map.put("answer", answer);
        }
        return map;
    }

    public AnswerDTO(String answer) {
        this.answer = answer;
    }
}
