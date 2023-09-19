package com.akimatBot.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReportDailyWithTaxDTO {

    GeneralShiftDTO generalShift;

    List<PaymentTypeReportDTO> paymentTypeReports;

    EmployeeDTO currentEmployee;

    Double total;
}
