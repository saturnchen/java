package com.fx.jfree.chart.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fx.jfree.chart.candlestick.JfreeCandlestickChart;
import com.fx.jfree.chart.model.Trade;
import com.fx.jfree.chart.utils.TimeUtils;


/**
 * The Class FxMarketPxFeeder.
 * 
 * @author ashraf
 */
public class FxMarketPxFeeder {

	private JfreeCandlestickChart jfreeCandlestickChart;
	private String stockTradesFile; 
	private int simulationTime;
	private ExecutorService executorService;
	
	public FxMarketPxFeeder(JfreeCandlestickChart jfreeCandlestickChart, String stockTradesFile, int simulationTime) {
		super();
		this.executorService = Executors.newCachedThreadPool();
		this.stockTradesFile = stockTradesFile;
		this.jfreeCandlestickChart = jfreeCandlestickChart;
		this.simulationTime = simulationTime;
	}

	public void run() {
		executorService.execute(() -> read());
	}

	private void read() {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getResourceAsStream(stockTradesFile)))) {
			while (true) {
				Thread.sleep(simulationTime);
				String line = br.readLine();
				if (line != null) {
					// Parse line and convert it to trade
					String[] tradeElements = line.split(Constants.DELIMITER);
					Trade t = new Trade(tradeElements[Constants.STOCK_IDX],
							TimeUtils.convertToMillisTime(tradeElements[Constants.TIME_IDX]),
							Double.parseDouble(tradeElements[Constants.PRICE_IDX]),
							Long.parseLong(tradeElements[Constants.SIZE_IDX]));
					// Add trade to the jfreeCandlestickChart 
					jfreeCandlestickChart.onTrade(t);
				} else {
					executorService.shutdown();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
