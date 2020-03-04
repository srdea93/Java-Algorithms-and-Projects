package dea_s_hw6;

import java.util.EventListener;

public interface PriceListener extends EventListener{
	public void priceEventOccurred(PriceEvent e);
}
