package cn.ivfzhou.reserve_platform.entity.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderPriceResult implements Serializable {

    private double allPrice;

    private List<List<String>> priceDetails;

}
