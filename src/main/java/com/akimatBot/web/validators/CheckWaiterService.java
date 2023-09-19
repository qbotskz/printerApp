package com.akimatBot.web.validators;

import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.repository.repos.GuestRepo;
import com.akimatBot.repository.repos.OrderItemRepository;
import com.akimatBot.services.EmployeeService;
import com.akimatBot.services.WaiterShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component(value = "checkWaiterService")
public class CheckWaiterService
//public class PermissionEvaluatorImpl implements PermissionEvaluator
{
    public CheckWaiterService() {
    }

    @Autowired
    public WaiterShiftService waiterShiftService;

    @Autowired
    GuestRepo guestRepo;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public boolean isOpenShift(long code) {
        code = Long.parseLong(httpServletRequest.getHeader("code"));
        System.out.println("Code => " + code);
        return waiterShiftService.hasOpenedShift(Long.parseLong(httpServletRequest.getHeader("code")));

//        return waiterShiftService.hasOpenedShift(code);
    }

//    @Override
//    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
//        return false;
//    }
//
//    @Override
//    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
//        return false;
//    }

    public boolean isPrecheckOrder(long guestId) {
        return guestRepo.getOrderStatusOfGuest(guestId) == OrderStatus.PRECHECK;
    }

    public boolean isPrecheckOrderOfOrderItem(long orderItemId) {
        return orderItemRepository.getOrderStatusOfOrderItem(orderItemId) == OrderStatus.PRECHECK;
    }

    public boolean hasRoleByCode(long code, String roleName) {
        return employeeService.hasRoleByCode(code, roleName);
    }
}