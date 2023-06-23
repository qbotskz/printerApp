package com.akimatBot.web.dto;

import com.akimatBot.repository.repos.ChequeRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerAddToCartDTO {
    ChequeDTO cheque;
    OrderItemDTO orderItem;
}