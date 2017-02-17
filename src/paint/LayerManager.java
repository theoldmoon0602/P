package paint;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LayerManager {
	protected List<Layer> layers;
	protected int current;
	
	public LayerManager() {
		layers = new ArrayList<>();
		layers.add(new Layer());
		current = 0;
	}
	
	public Layer getCurrentLayer() {
		return layers.get(current);
	}
	
	public void forEachVisibleLayers(Consumer<Layer> f) {
		for (Layer layer : layers) {
			f.accept(layer);
		}
	}
	
	public void addLayer() {
		layers.add(new Layer());
		current = layers.size()-1;
	}
	public int upLayer() {
		current++;
		if (current >= layers.size()) {
			current = layers.size() -1;
		}
		return current;
	}
	public int downLayer() {
		current--;
		if (current < 0) {
			current = 0;
		}
		return current;
	}
	public int getLayerNum() {
		return layers.size();
	}
	public int removeCurrentLayer() {
		layers.remove(current);
		current--;
		if (layers.size() == 0) {
			addLayer();
		}
		return layers.size();
	}

}
