package com.akimatBot.entity.custom;

import com.akimatBot.web.dto.RestaurantBranchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity(name = "branches")
public class RestaurantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int     id;

    private String branchName;

    @ManyToOne
    private City city;

    public RestaurantBranch() {
    }

    public RestaurantBranch(String branchName) {
        this.branchName = branchName;
    }

    public RestaurantBranchDTO getDTO() {
        RestaurantBranchDTO restaurantBranchDTO = new RestaurantBranchDTO();
        restaurantBranchDTO.setBranchName(this.getBranchName());
        restaurantBranchDTO.setId(this.getId());
        restaurantBranchDTO.setCity(this.getCity().getDTO());
        return restaurantBranchDTO;
    }
}
