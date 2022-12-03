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
//        check();
//        log.info("Finish check currencyJob");
//
//    }
//    public void check(){
//        try {
////
//            String currencyRate = restTemplateService.send(uri,null,null, String.class);
//            JSONObject obj = new JSONObject(currencyRate);
//            String currencyRateFrom = obj.getJSONObject("Realtime Currency Exchange Rate").getString("1. From_Currency Code");
//            String currencyRateTo = obj.getJSONObject("Realtime Currency Exchange Rate").getString("3. To_Currency Code");
//            String currencyExchangeRate = obj.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
//            CurrencyRate finalCurrencyRate = new CurrencyRate();
//            finalCurrencyRate.setFromCurrency(currencyRateFrom);
//            finalCurrencyRate.setToCurrency(currencyRateTo);
//            finalCurrencyRate.setExchangeRate(currencyExchangeRate);
////            System.out.println(currencyRate.setCurrency(restTemplateService.send(uri,null,null, CurrencyRateDto.class)));
////            log.info(currencyRate);
//                        if (currencyRate != null){
//                currencyRateRepository.save(finalCurrencyRate);
//            }
//        } catch (Exception e){
//            log.info("Job error: " + e.getCause());
//        }
//
//    }
//}
