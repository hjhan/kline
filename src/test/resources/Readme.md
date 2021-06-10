# Get CandleStick examples

https://crypto.com/fe-ex-api/market-data/v2/public/get-candlestick?depth=1000&instrument_name=BTC_USDT&timeframe=1m

# Get Trades examples

https://crypto.com/fe-ex-api/market-data/v2/public/get-trades?depth=1000&instrument_name=BTC_USDT
The trades are reversed ordered by ts
# Use Jackson ObjectMapper map json to Domain Object

# Use Trades List to generate KLine i.e 1m

1. first round the timestamp to 1 minute by divide 60*1000
2. group the tick into map
3. reduce the map to kline

using Map Reduce

# Alternative try use Flink to process the data
