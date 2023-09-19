package com.akimatBot.web.validators;

import com.akimatBot.entity.enums.OrderStatus;
import com.akimatBot.repository.repos.ChequeRepo;
import com.akimatBot.repository.repos.GuestRepo;
import com.akimatBot.repository.repos.OrderItemRepository;
import com.akimatBot.services.EmployeeService;
import com.akimatBot.services.OrderService;
import com.akimatBot.services.WaiterShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component(value = "permissionEvaluator")
public class PermissionEvaluatorImpl
//public class PermissionEvaluatorImpl implements PermissionEvaluator
{
    public PermissionEvaluatorImpl() {
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
    OrderService orderService;

    @Autowired
    ChequeRepo chequeRepo;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public boolean isOpenShift() {
        long code = Long.parseLong(httpServletRequest.getHeader("code"));
        System.out.println("Code => " + code);
        return waiterShiftService.hasOpenedShift(code);
    }

    public boolean isOpenShiftByChatId(long chatId) {
        return waiterShiftService.hasOpenedShiftByChatId(chatId);
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

    public boolean isActiveOrderGuestId(long guestId) {
        return guestRepo.getOrderStatusOfGuest(guestId) == OrderStatus.ACTIVE;
    }

    public boolean isActiveOrderByOrderId(long orderId) {
        return orderService.getOrderStatus(orderId) == OrderStatus.ACTIVE;
    }

    public boolean isActiveOrderByOrderItem(long orderItemId) {
        return orderItemRepository.getOrderStatusOfOrderItem(orderItemId) == OrderStatus.ACTIVE;
    }

    public boolean isActiveOrderByCheque(long chequeId) {
        return chequeRepo.getOrderStatus(chequeId) == OrderStatus.ACTIVE;
    }

    public boolean isPrecheckOrderByOrder(long orderId) {
        return orderService.getOrderStatus(orderId) == OrderStatus.PRECHECK;
    }

    public boolean isNotDoneOrderByOrderId(long orderId) {
        return orderService.getOrderStatus(orderId) != OrderStatus.DONE;
    }

    public boolean isPrecheckOrderByCheque(long chequeId) {
        return chequeRepo.getOrderStatus(chequeId) == OrderStatus.PRECHECK;
    }

    public boolean hasRoleByCode(long code, String roleName) {
        return employeeService.hasRoleByCode(code, roleName);
    }

    public boolean hasRole(String roleName) {
        try {
            Long code = getLong(httpServletRequest.getHeader("code"));
            Long chatId = getLong(httpServletRequest.getHeader("chatId"));
            if (code != null) {
                return employeeService.hasRoleByCode(code, roleName);
            } else if (chatId != null) {
                return employeeService.hasRoleByChatId(chatId, roleName);
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public Long getLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isMyOrderOrCanEditOther(long orderId) {
        Long code = getLong(httpServletRequest.getHeader("code"));
        return hasRole("EDIT_OTHER_DESK") || orderService.hisOrder(code, orderId);
    }

    public boolean isMyOrderOrCanEditOtherByChequeId(long chequeId) {
        Long code = getLong(httpServletRequest.getHeader("code"));
        return hasRole("EDIT_OTHER_DESK") || orderService.hisOrderByChequeId(code, chequeId);
    }

    public boolean isMyOrderOrCanEditOtherByGuestId(long guestId) {
        Long code = getLong(httpServletRequest.getHeader("code"));
        return hasRole("EDIT_OTHER_DESK") || orderService.hisOrderByGuestId(code, guestId);
    }

    public boolean isMyOrderOrCanEditOtherByOrderItemId(long orderItemId) {
        Long code = getLong(httpServletRequest.getHeader("code"));
        return hasRole("EDIT_OTHER_DESK") || orderService.hisOrderByOrderItemId(code, orderItemId);
    }
    ///////////////////////////////////////////////////////////

    public boolean isWaiterByChatId() {
        Long chatId = getLong(httpServletRequest.getHeader("chatId"));
        return employeeService.hasCodeByChatId(chatId);
    }
}