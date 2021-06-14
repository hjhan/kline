# Get CandleStick examples

https://crypto.com/fe-ex-api/market-data/v2/public/get-candlestick?depth=1000&instrument_name=BTC_USDT&timeframe=1m
this url return 600, so 600 minutes range of trades, 10 hours
# Get Trades examples

https://crypto.com/fe-ex-api/market-data/v2/public/get-trades?depth=1000&instrument_name=BTC_USDT
The trades are reversed ordered by ts
Due to the get-trades url only return the latest 200 trades, so start a scheduled job to query the trades 
every 10 seconds, parse the trades json to object and save to H2 embedded database

# Use Jackson ObjectMapper map json to Domain Object

# Use Trades List to generate KLine i.e 1m

1. first round the timestamp to 1 minute by divide 60*1000
2. group the tick into map
3. reduce the map to kline

using Map Reduce

# Alternative try use Flink to process the data
