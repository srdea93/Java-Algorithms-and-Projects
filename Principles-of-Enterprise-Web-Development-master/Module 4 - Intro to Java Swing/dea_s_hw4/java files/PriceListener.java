package dea_s_hw4;

import java.util.EventListener;

public interface PriceListener extends EventListener{
	public void priceEventOccurred(PriceEvent e);
}
