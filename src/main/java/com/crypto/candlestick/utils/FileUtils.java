package com.crypto.candlestick.utils;

import com.crypto.candlestick.domain.CandleStick;
import com.crypto.candlestick.domain.Tick;
import com.crypto.candlestick.marketdata.ResponseBase;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<CandleStick> getCandleSticksFromFile(String fileName){
        try {
            InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get(fileName)));
            ResponseBase<CandleStick> response = JsonUtils.parseResponse(inputStream, CandleStick.class);
            return response.getResult().getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Tick> getTickListFromFile(String fileName) {
        Path path = Paths.get(fileName);
        List<Tick> tickList = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(path.toFile(), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while(line != null){
                tickList.add(JsonUtils.strToObject(line, Tick.class));
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tickList;
    }





}
