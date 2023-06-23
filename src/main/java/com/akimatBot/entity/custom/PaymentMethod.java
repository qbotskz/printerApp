package com.akimatBot.entity.custom;

import com.akimatBot.repository.TelegramBotRepositoryProvider;
import com.akimatBot.web.dto.PaymentMethodDTO;
import com.akimatBot.web.dto.PaymentTypeDTO;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long     id;
    private String name;
    private boolean active;

//    @OneToMany(mappedBy = "paymentMethod")
//    List<PaymentType> paymentTypes;

    public PaymentMethodDTO getPaymentMethodDTO(){
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(this.getId());
        paymentMethodDTO.setName(this.getName());
        paymentMethodDTO.setPaymentTypes(getPaymentTypesDTO());

        return paymentMethodDTO;
    }

    public List<PaymentTypeDTO> getPaymentTypesDTO(){
        List<PaymentType> types = TelegramBotRepositoryProvider.getPaymentTypeRepo().findAllByActiveIsTrueAndPaymentMethodId(this.getId());
        List<PaymentTypeDTO> dtos = new ArrayList<>();
        for (PaymentType paymentType : types){
            dtos.add(paymentType.getPaymentTypeDTO());
        }
        return dtos;
    }

}
