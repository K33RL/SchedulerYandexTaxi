package com.myproject.scheduleryandextaxi.model;

import java.util.List;
import lombok.Data;

@Data
public class Price {
    public List<Option> options;
    public String currency;
    public double distance;
    public String time_text;
}
