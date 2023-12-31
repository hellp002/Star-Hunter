package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logic.Projectile;

public class RenderableHolder {
	private static RenderableHolder instance = new RenderableHolder();

	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;

	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			return compare(o1, o2);
		};
	}

	private int compare(IRenderable o1, IRenderable o2) {
		if (o1.getZ() > o2.getZ()) 
			return 1;
		return -1;
	}



	public void add(IRenderable entity) {
		entities.add(entity);
		if (entity instanceof Projectile) {
			return;
		}
		Collections.sort(entities, comparator);
	}

	public void update() {
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isDeleted())
				entities.remove(i);

		}
	}

	public List<IRenderable> getEntities() {
		return entities;
	}

	public static RenderableHolder getInstance() {
		return instance;
	}

}
