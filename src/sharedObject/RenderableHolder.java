package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logic.ChampionMonster;
import logic.NormalMonster;

public class RenderableHolder {
	private static RenderableHolder instance = new RenderableHolder();

	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;

	static {
//		loadResource();
	}

	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			else if (o1.getZ() == o2.getZ()) {
				if (o1.getY() > o2.getY()) {
					return 1;
				} else if (o1.getY() == o2.getY()) {
					if (o1 instanceof ChampionMonster && o2 instanceof NormalMonster) {
						return 1;
					}
					return -1;
				}
				return -1;
			}

			return -1;

		};
	}

	public static RenderableHolder getInstance() {
		return instance;
	}

	public void add(IRenderable entity) {
		entities.add(entity);
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

}
