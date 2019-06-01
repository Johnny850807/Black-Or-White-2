package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.sprites.Sprite;

import java.util.Objects;


public abstract class CollisionHandler {
	private TargetPair targetPair;

	public CollisionHandler(Object s1Type, Object s2Type) {
		targetPair = new TargetPair(s1Type, s2Type);
	}

	public abstract void onCollision(Sprite s1, Sprite s2);


	public Object getFirstType() {
		return targetPair.getFirst();
	}

	public Object getSecondType() {
		return targetPair.getSecond();
	}

	public TargetPair getTargetPair() {
		return targetPair;
	}

	public static class TargetPair {
		private Object first;
		private Object second;

		public TargetPair(Object first, Object second) {
			this.first = Objects.requireNonNull(first);
			this.second = Objects.requireNonNull(second);
		}

		public Object getFirst() {
			return first;
		}

		public Object getSecond() {
			return second;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			TargetPair that = (TargetPair) obj;
			return (first.equals(that.first) && second.equals(that.second)) ||
					(first.equals(that.second) && second.equals(that.first));
		}

		@Override
		public int hashCode() {
			int firstHash = first.hashCode();
			int secondHash = second.hashCode();
			int smallerHash = Math.min(firstHash, secondHash);
			int biggerHash = Math.max(firstHash, secondHash);
			return Objects.hash(smallerHash, biggerHash);
		}
	}
}
