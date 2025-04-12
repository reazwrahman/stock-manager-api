package api.stock.manager.chain_of_responsibility;

import api.stock.manager.stock.RequestModel;
import api.stock.manager.stock.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValidationRunner {

    public static void main(String[] args) {
        System.out.println("------- TESTING CHAIN OF RESPONSIBILITY PATTERN -------- ");
        testAccessKeyValidator();
        testRequestSizeValidator();
        testStockBodyValidator();
    }

    public static void testAccessKeyValidator(){
        System.out.println();
        System.out.println("------- TESTING ACCESS KEY VALIDATOR -------- ");
        RequestModel request = new RequestModel();
        request.setAccessKey("The end is nigh");
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("TSLA", new BigDecimal(12), new BigDecimal(13)));
        request.setStocks(stocks);

        ValidatorEntryPoint validator = setupValidator();

        validator.accessKeyValidator.m_accessKey = "The dark knight rises";
        System.out.println(validator.validate(request).errorMessage);
    }

    public static void testRequestSizeValidator(){
        System.out.println();
        System.out.println("------- TESTING REQUEST SIZE VALIDATOR -------- ");
        RequestModel request = new RequestModel();
        request.setAccessKey("The end is nigh");
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("TSLA", new BigDecimal(12), new BigDecimal(13)));
        stocks.add(new Stock("AAPL", new BigDecimal(11), new BigDecimal(13)));

        request.setStocks(stocks);

        ValidatorEntryPoint validator = setupValidator();
        validator.accessKeyValidator.m_accessKey = "The end is nigh";
        validator.sizeValidator.m_maxSize = 1;
        System.out.println(validator.validate(request).errorMessage);
    }

    public static void testStockBodyValidator(){
        System.out.println();
        System.out.println("------- TESTING REQUEST SIZE VALIDATOR -------- ");
        RequestModel request = new RequestModel();
        request.setAccessKey("The end is nigh");
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("", new BigDecimal(10), new BigDecimal(13)));

        request.setStocks(stocks);

        ValidatorEntryPoint validator = setupValidator();
        validator.accessKeyValidator.m_accessKey = "The end is nigh";
        validator.sizeValidator.m_maxSize = 50;
        System.out.println(validator.validate(request).errorMessage);
    }

    private static ValidatorEntryPoint setupValidator(){
        ValidatorEntryPoint validator = new ValidatorEntryPoint();
        validator.accessKeyValidator = new AccessKeyValidator();
        validator.sizeValidator = new RequestSizeValidator();
        validator.bodyValidator = new StockBodyValidator();
        validator.validatorChain.add(validator.accessKeyValidator);
        validator.validatorChain.add(validator.sizeValidator);
        validator.validatorChain.add(validator.bodyValidator);
        return validator;
    }
}
