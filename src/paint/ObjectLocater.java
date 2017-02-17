package paint;

import java.util.function.Supplier;

public class ObjectLocater implements ObjectCreated {
	protected Supplier<Layer> layerSupplier;
	
	public ObjectLocater(Supplier<Layer> layerSupplier) {
		this.layerSupplier = layerSupplier;
	}
	
	
	@Override
	public void created(PaintedObject o) {
		layerSupplier.get().addObject(o);
	}

}
