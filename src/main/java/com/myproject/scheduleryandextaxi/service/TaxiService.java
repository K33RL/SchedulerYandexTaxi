package com.myproject.scheduleryandextaxi.service;

import com.myproject.scheduleryandextaxi.apiclient.TaxiApiClient;
import com.myproject.scheduleryandextaxi.model.Coordinate;
import com.myproject.scheduleryandextaxi.model.MomentPrice;
import com.myproject.scheduleryandextaxi.model.Price;
import com.myproject.scheduleryandextaxi.properties.YandexProperties;
import com.myproject.scheduleryandextaxi.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class TaxiService {
    private final YandexProperties yandexProperties;
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;
    private AtomicInteger price;

    public TaxiService(YandexProperties yandexProperties, TaxiApiClient taxiApiClient, PriceRepository priceRepository,
                       MeterRegistry meterRegistry) {
        this.yandexProperties = yandexProperties;
        this.taxiApiClient = taxiApiClient;
        this.priceRepository = priceRepository;
        price = new AtomicInteger();
        meterRegistry.gauge("priceTaxi", price);
    }

    public void getPrice(Coordinate startPoint, Coordinate endPoint) {
        String rll = startPoint.toString() + "~" + endPoint.toString();

        Price currentPrice = taxiApiClient.getPrice(yandexProperties.getClid(), yandexProperties.getApiKey(), rll);
        if (currentPrice.getOptions().isEmpty()) {
            throw new RuntimeException("Options are empty");
        }

        double priceDouble = currentPrice.getOptions().get(0).getPrice();
        price.set((int) priceDouble);
        MomentPrice momentPrice = new MomentPrice(LocalDateTime.now(ZoneId.systemDefault()), priceDouble);
        priceRepository.save(momentPrice);
    }

    public List<MomentPrice> getAllPrice() {
        return priceRepository.findAll();
    }
}
