//package kz.alibek.solva.job;
//
//import kz.alibek.solva.model.entity.CurrencyRate;
//import kz.alibek.solva.repository.CurrencyRateRepository;
//import kz.alibek.solva.service.RestTemplateService;
//import lombok.extern.java.Log;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@EnableScheduling
//@Log
//@Component
//public class CheckCurrency {
//
//    @Autowired
//    private CurrencyRateRepository currencyRateRepository;
//
//    @Value("${api.uri}")
//    private String uri;
//
//    @Autowired
//    private RestTemplateService restTemplateService;
//
////    @Scheduled(cron = "0 12 * * ? *")
//    @Scheduled(cron = "* * * * * *")
//    public void checkCurrency(){
//        log.info("Start check currencyJob");
//        check("USD", "KZT");
//        check("USD", "RUB");
//        check("KZT", "USD");
//        check("KZT", "RUB");
//        check("RUB", "USD");
//        check("RUB", "KZT");
//        log.info("Finish check currencyJob");
//
//    }
//    public void check(String firstCurrency, String secondCurrency){
//        try {
//            String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + firstCurrency + "&to_currency=" + secondCurrency +"&apikey=40FKBWSMPULDAFBD";
//            System.out.println(url);
//            String currencyRate = restTemplateService.send(url,null,null, String.class);
//            JSONObject obj = new JSONObject(currencyRate);
//            String currencyRateFrom = obj.getJSONObject("Realtime Currency Exchange Rate").getString("1. From_Currency Code");
//            String currencyRateTo = obj.getJSONObject("Realtime Currency Exchange Rate").getString("3. To_Currency Code");
//            String currencyExchangeRate = obj.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
//            CurrencyRate finalCurrencyRate = new CurrencyRate();
//            finalCurrencyRate.setFromCurrency(currencyRateFrom);
//            finalCurrencyRate.setToCurrency(currencyRateTo);
//            finalCurrencyRate.setExchangeRate(currencyExchangeRate);
//            if (currencyRate != null){
//                currencyRateRepository.save(finalCurrencyRate);
//            }
//        } catch (Exception e){
//            log.info("Job error: " + e.getCause());
//        }
//
//    }
//
//
//}
