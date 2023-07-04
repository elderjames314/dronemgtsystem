package com.blusalt.dronemgtsystem.dtos;



import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
   List<Long> medicationIds =  new ArrayList<Long>();
   private String location;
   private Long droneId;
}
