package com.thebizio.biziosalonms.dto.checkout;

import com.thebizio.biziosalonms.dto.item.ItemDetailDto;
import com.thebizio.biziosalonms.dto.item.ItemListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class CheckoutSessionResponseDto {

    private Double grossTotal;
    private Double discount;
    private String discountStr;
    private Double taxes;
    private Double netTotal;
    private String taxStr;
    private List<ItemListDto> items;

}
